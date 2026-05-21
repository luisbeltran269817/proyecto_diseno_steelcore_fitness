package Control;

import Excepciones.NegocioException;
import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.EstadoEntrenador;
import dtosControlDeAcceso.EstadoMembresia;
import dtosControlDeAcceso.MembresiaDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import dtosControlDeAcceso.SocioDTO;
import dtosControlDeAcceso.TipoServicio;
import dtosControlDeAcceso.VisitaDTO;
import Fachada.Icontrolacceso.AccesoDenegadoException;

// ✅ CORRECCIÓN #1: Las interfaces están en el paquete "interfaces", NO en "interfacesAcceso"
import interfaces.IClaseAccesoBO;
import interfaces.IVisitaAccesoBO;

import interfaces.IClienteBO;
import interfaces.IEntrenadorBO;
import interfaces.IMembresiaBO;
import interfaces.ISucursalBO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// ✅ CORRECCIÓN #2: Las clases concretas están en el paquete "BOsAcceso", NO en "objetosAcceso"
import BOsAcceso.ClaseAccesoBO;
import BOsAcceso.VisitaAccesoBO;

import objetosnegocios.ClienteBO;
import objetosnegocios.EntrenadorBO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.SucursalBO;

/**
 * Clase de control del subsistema de acceso de socios.
 *
 * Toda la lógica de negocio pasa por aquí; no hay acceso directo a Mongo.
 * Cada operación usa la capa de BOs correspondiente:
 *
 *   procesarQR()                   → MembresiaBO + ClienteBO + VisitaAccesoBO
 *   obtenerEntrenadoresDisponibles()→ EntrenadorBO
 *   asignarEntrenador()            → EntrenadorBO + VisitaAccesoBO
 *   obtenerClasesPorPlan()         → ClaseAccesoBO
 *   inscribirClase()               → ClaseAccesoBO + VisitaAccesoBO
 *   registrarAreaGeneral()         → VisitaAccesoBO
 *
 * @author julian izaguirre
 */
public class ControlAcceso {

    private static final Logger LOG = Logger.getLogger(ControlAcceso.class.getName());

    private final IMembresiaBO    membresiaBO;
    private final IClienteBO      clienteBO;
    private final ISucursalBO     sucursalBO;
    private final IEntrenadorBO   entrenadorBO;
    private final IClaseAccesoBO  claseBO;
    private final IVisitaAccesoBO visitaBO;

    private String idSucursalLocal;

    public ControlAcceso() {
        this.membresiaBO  = new MembresiaBO();
        this.clienteBO    = new ClienteBO();
        this.sucursalBO   = new SucursalBO();
        this.entrenadorBO = new EntrenadorBO();
        this.claseBO      = new ClaseAccesoBO();   // ✅ paquete correcto
        this.visitaBO     = new VisitaAccesoBO();  // ✅ paquete correcto
    }

    public void setIdSucursalLocal(String idSucursal) {
        this.idSucursalLocal = idSucursal;
    }

    // -----------------------------------------------------------------
    //  procesarQR
    // -----------------------------------------------------------------

    /**
     * Valida el QR del socio, verifica la membresía y registra la visita.
     */
    public ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException {

        if (codigoQR == null || codigoQR.isBlank()) {
            throw new AccesoDenegadoException("Código QR vacío o inválido.");
        }

        // 1. Buscar membresía
        dtos.MembresiaDTO membresiaBase = buscarMembresiaPorQR(codigoQR);
        if (membresiaBase == null) {
            throw new AccesoDenegadoException(
                    "El código QR no corresponde a ningún socio registrado.");
        }

        // 2. Validar estado
        if (membresiaBase.getEstado() != dtos.MembresiaDTO.EstadoMembresia.ACTIVA) {
            String razon = switch (membresiaBase.getEstado()) {
                case VENCIDA   -> "Membresía vencida.";
                case CANCELADA -> "Membresía cancelada.";
                default        -> "Membresía no activa.";
            };
            throw new AccesoDenegadoException(razon);
        }

        if (membresiaBase.getFechaCaducidad() != null
                && membresiaBase.getFechaCaducidad().isBefore(LocalDateTime.now())) {
            throw new AccesoDenegadoException(
                    "Membresía vencida el "
                    + membresiaBase.getFechaCaducidad().toLocalDate() + ".");
        }

        // 3. Validar sucursal
        if (idSucursalLocal != null
                && membresiaBase.getIdSucursal() != null
                && !idSucursalLocal.equals(membresiaBase.getIdSucursal())) {
            String nombreSucursal = resolverNombreSucursal(membresiaBase.getIdSucursal());
            throw new AccesoDenegadoException(
                    "Esta membresía pertenece a la sucursal " + nombreSucursal
                    + ".\nNo tiene acceso a esta ubicación.");
        }

        // 4. Buscar cliente
        dtos.ClienteDTO clienteBase;
        try {
            clienteBase = clienteBO.buscarPorCorreo(membresiaBase.getIdCliente());
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException("Error al buscar el socio: " + ex.getMessage());
        }
        if (clienteBase == null) {
            throw new AccesoDenegadoException("Socio no encontrado en el sistema.");
        }

        // 5. Registrar visita
        String idSucursalRegistro = (idSucursalLocal != null)
                ? idSucursalLocal : membresiaBase.getIdSucursal();

        VisitaDTO visitaRegistrada;
        try {
            visitaRegistrada = visitaBO.registrarEntrada(
                    clienteBase.getIdCliente(), idSucursalRegistro);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(
                    "No se pudo registrar la entrada: " + ex.getMessage());
        }

        // 6. Construir y devolver ResultadoAccesoDTO con todos los campos del flujo
        return ResultadoAccesoDTO.concedido(
                clienteBase.getNombre() + " " + clienteBase.getApellidoPaterno(),
                visitaRegistrada.getFechaHora(),
                visitaRegistrada.getIdVisita(),
                clienteBase.getIdCliente(),
                membresiaBase.getIdPlan(),
                idSucursalRegistro,
                planIncluyeEntrenador(membresiaBase.getIdPlan()),
                planIncluyeClases(membresiaBase.getIdPlan()));
    }

    // -----------------------------------------------------------------
    //  registrarAreaGeneral
    // -----------------------------------------------------------------

    public void registrarAreaGeneral(String idVisita) throws AccesoDenegadoException {
        try {
            visitaBO.actualizarServicio(idVisita, TipoServicio.AREA_GENERAL.name(), null);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(
                    "No se pudo registrar el área general: " + ex.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  obtenerEntrenadoresDisponibles
    // -----------------------------------------------------------------

    public List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException {
        try {
            String idSuc = (idSucursal != null && !idSucursal.isBlank())
                    ? idSucursal
                    : idSucursalLocal;
            if (idSuc == null || idSuc.isBlank()) return new ArrayList<>();

            List<dtos.EntrenadorDTO> todos = entrenadorBO.obtenerPorSucursal(idSuc);
            List<EntrenadorDTO> resultado = new ArrayList<>();
            for (dtos.EntrenadorDTO e : todos) {
                EntrenadorDTO dto = new EntrenadorDTO(
                        e.getIdEntrenador(),
                        e.getNombre(),
                        tieneHorarioLibre(e) ? EstadoEntrenador.LIBRE : EstadoEntrenador.OCUPADO,
                        e.getIdSucursal());
                resultado.add(dto);
            }
            return resultado;
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(
                    "No se pudo consultar la lista de entrenadores: " + ex.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  asignarEntrenador
    // -----------------------------------------------------------------

    public void asignarEntrenador(String idVisita, String idCliente,
                                   String idEntrenador, String idHorario)
            throws AccesoDenegadoException {
        try {
            entrenadorBO.actualizarDisponibilidadHorario(idEntrenador, idHorario, false);
            visitaBO.actualizarServicio(idVisita, TipoServicio.ENTRENADOR.name(), idEntrenador);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(ex.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  obtenerClasesPorPlan
    // -----------------------------------------------------------------

    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                                String idCliente, boolean incluyeClases)
            throws AccesoDenegadoException {

        if (!incluyeClases) {
            throw new AccesoDenegadoException(
                    "Lo sentimos, tu plan no incluye inscripción a las clases del gimnasio.");
        }

        String idSuc = (idSucursal != null && !idSucursal.isBlank())
                ? idSucursal
                : idSucursalLocal;

        try {
            return claseBO.obtenerClasesPorPlan(idSuc, idPlan, idCliente);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(ex.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  inscribirClase
    // -----------------------------------------------------------------

    public void inscribirClase(String idVisita, String idClase, String idCliente)
            throws AccesoDenegadoException {
        try {
            claseBO.inscribirSocio(idClase, idCliente);
            visitaBO.actualizarServicio(idVisita, TipoServicio.CLASE.name(), idClase);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(ex.getMessage());
        }
    }

    // -----------------------------------------------------------------
    //  Métodos privados auxiliares
    // -----------------------------------------------------------------

    private dtos.MembresiaDTO buscarMembresiaPorQR(String codigoQR) {
        try {
            String idExtraido = extraerIdDeUrl(codigoQR);
            if (idExtraido != null) {
                dtos.MembresiaDTO m = membresiaBO.buscarPorId(idExtraido);
                if (m != null) return m;
            }
            dtos.MembresiaDTO porQR = membresiaBO.buscarPorCodigoQR(codigoQR.trim());
            if (porQR != null) return porQR;
            return membresiaBO.buscarPorId(codigoQR.trim());
        } catch (NegocioException ignored) {
            return null;
        }
    }

    private String extraerIdDeUrl(String url) {
        if (url == null || !url.contains("?id=")) return null;
        try {
            int inicio = url.indexOf("?id=") + 4;
            int fin    = url.indexOf("&", inicio);
            return (fin < 0) ? url.substring(inicio) : url.substring(inicio, fin);
        } catch (Exception e) {
            return null;
        }
    }

    private String resolverNombreSucursal(String idSucursal) {
        try {
            dtos.SucursalDTO s = sucursalBO.buscarPorId(idSucursal);
            return (s != null && s.getNombre() != null) ? s.getNombre() : idSucursal;
        } catch (NegocioException e) {
            return idSucursal;
        }
    }

    private boolean tieneHorarioLibre(dtos.EntrenadorDTO e) {
        if (e.getHorarios() == null || e.getHorarios().isEmpty()) return false;
        for (dtos.HorarioDTO h : e.getHorarios()) {
            if (h.isDisponible()) return true;
        }
        return false;
    }

    private boolean planIncluyeEntrenador(String idPlan) {
        if (idPlan == null) return false;
        String id = idPlan.toUpperCase();
        return id.contains("PREMIUM") || id.contains("GOLD") || id.contains("P002");
    }

    private boolean planIncluyeClases(String idPlan) {
        if (idPlan == null) return false;
        String id = idPlan.toUpperCase();
        return !id.contains("BASICO") && !id.contains("BASE");
    }
}