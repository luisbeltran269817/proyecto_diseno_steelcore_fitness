/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Excepciones.NegocioException;
import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.EstadoEntrenador;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import dtosControlDeAcceso.TipoServicio;
import dtosControlDeAcceso.VisitaDTO;
import Fachada.Icontrolacceso.AccesoDenegadoException;

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

import BOsAcceso.ClaseAccesoBO;
import BOsAcceso.VisitaAccesoBO;

import objetosnegocios.ClienteBO;
import objetosnegocios.EntrenadorBO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.SucursalBO;

/**
 * Controlador principal que une todas las validaciones para dar o negar el acceso
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

    /**
     * Inicializa todos los objetos de negocio necesarios
     */
    public ControlAcceso() {
        this.membresiaBO  = new MembresiaBO();
        this.clienteBO    = new ClienteBO();
        this.sucursalBO   = new SucursalBO();
        this.entrenadorBO = new EntrenadorBO();
        this.claseBO      = new ClaseAccesoBO();   
        this.visitaBO     = new VisitaAccesoBO();  
    }

    /**
     * Asigna la sucursal donde esta corriendo el sistema actualmente
     * 
     * @param idSucursal Identificador de la sucursal
     */
    public void setIdSucursalLocal(String idSucursal) {
        this.idSucursalLocal = idSucursal;
    }

    /**
     * Revisa el codigo QR y verifica que todo este en orden para dejar entrar al socio
     * 
     * @param codigoQR Texto leido del codigo
     * @return Los datos del acceso permitido
     * @throws AccesoDenegadoException Si el socio no tiene permiso de entrar
     */
    public ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException {

        if (codigoQR == null || codigoQR.isBlank()) {
            throw new AccesoDenegadoException("Codigo QR vacio o invalido");
        }

        dtos.MembresiaDTO membresiaBase = buscarMembresiaPorQR(codigoQR);
        if (membresiaBase == null) {
            throw new AccesoDenegadoException(
                    "El codigo QR no corresponde a ningun socio registrado");
        }

        if (membresiaBase.getEstado() != dtos.MembresiaDTO.EstadoMembresia.ACTIVA) {
            String razon = switch (membresiaBase.getEstado()) {
                case VENCIDA   -> "Membresia vencida";
                case CANCELADA -> "Membresia cancelada";
                default        -> "Membresia no activa";
            };
            throw new AccesoDenegadoException(razon);
        }

        if (membresiaBase.getFechaCaducidad() != null
                && membresiaBase.getFechaCaducidad().isBefore(LocalDateTime.now())) {
            throw new AccesoDenegadoException(
                    "Membresia vencida el "
                    + membresiaBase.getFechaCaducidad().toLocalDate());
        }

        if (idSucursalLocal != null
                && membresiaBase.getIdSucursal() != null
                && !idSucursalLocal.equals(membresiaBase.getIdSucursal())) {
            String nombreSucursal = resolverNombreSucursal(membresiaBase.getIdSucursal());
            throw new AccesoDenegadoException(
                    "Esta membresia pertenece a la sucursal " + nombreSucursal
                    + "\nNo tiene acceso a esta ubicacion");
        }

        dtos.ClienteDTO clienteBase;
        try {
            clienteBase = clienteBO.buscarPorCorreo(membresiaBase.getIdCliente());
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException("Error al buscar el socio: " + ex.getMessage());
        }
        if (clienteBase == null) {
            throw new AccesoDenegadoException("Socio no encontrado en el sistema");
        }

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

    /**
     * Marca que el socio solo usara las instalaciones generales de pesas
     * 
     * @param idVisita Visita que se va a actualizar
     * @throws AccesoDenegadoException Si ocurre un error al guardar
     */
    public void registrarAreaGeneral(String idVisita) throws AccesoDenegadoException {
        try {
            visitaBO.actualizarServicio(idVisita, TipoServicio.AREA_GENERAL.name(), null);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(
                    "No se pudo registrar el area general: " + ex.getMessage());
        }
    }

    /**
     * Busca que entrenadores tienen un hueco libre en su agenda en este momento
     * 
     * @param idSucursal Sucursal a revisar
     * @return Lista de entrenadores con su estado actual
     * @throws AccesoDenegadoException Si falla la busqueda
     */
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

    /**
     * Le aparta el entrenador al socio en su visita y ocupa el horario
     * 
     * @param idVisita Visita activa del socio
     * @param idCliente Identificador del socio
     * @param idEntrenador Entrenador seleccionado
     * @param idHorario Horario que se aparto
     * @throws AccesoDenegadoException Si algo sale mal al intentar apartarlo
     */
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

    /**
     * Checa las clases a las que el socio se puede meter segun lo que paga
     * 
     * @param idSucursal Sucursal del gimnasio
     * @param idPlan Plan del socio
     * @param idCliente Identificador del socio
     * @param incluyeClases Bandera de si su membresia le permite tomar clases
     * @return Lista de clases permitidas
     * @throws AccesoDenegadoException Si su plan no lo permite
     */
    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                                String idCliente, boolean incluyeClases)
            throws AccesoDenegadoException {

        if (!incluyeClases) {
            throw new AccesoDenegadoException(
                    "Lo sentimos, tu plan no incluye inscripcion a las clases del gimnasio");
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

    /**
     * Mete al socio a la clase elegida y lo guarda en el registro de su visita
     * 
     * @param idVisita Visita actual
     * @param idClase Clase a la que se anoto
     * @param idCliente Identificador del socio
     * @throws AccesoDenegadoException Si ocurre un error al guardarlo
     */
    public void inscribirClase(String idVisita, String idClase, String idCliente)
            throws AccesoDenegadoException {
        try {
            claseBO.inscribirSocio(idClase, idCliente);
            visitaBO.actualizarServicio(idVisita, TipoServicio.CLASE.name(), idClase);
        } catch (NegocioException ex) {
            throw new AccesoDenegadoException(ex.getMessage());
        }
    }

    /**
     * Trata de adivinar de quien es el codigo escaneado buscando en la base
     * 
     * @param codigoQR El texto crudo que leyo el escaner
     * @return La membresia si la encuentra o null si no existe
     */
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

    /**
     * Saca el identificador limpio si el QR viene disfrazado de url
     * 
     * @param url La direccion completa
     * @return Solo el identificador rescatado
     */
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

    /**
     * Busca el nombre real de la sucursal para poder mostrarlo bonito en pantalla
     * 
     * @param idSucursal El identificador raro de la sucursal
     * @return El nombre legible o el mismo id si falla
     */
    private String resolverNombreSucursal(String idSucursal) {
        try {
            dtos.SucursalDTO s = sucursalBO.buscarPorId(idSucursal);
            return (s != null && s.getNombre() != null) ? s.getNombre() : idSucursal;
        } catch (NegocioException e) {
            return idSucursal;
        }
    }

    /**
     * Checa rapido si el entrenador todavia tiene espacio para atender a alguien hoy
     * 
     * @param e Los datos del entrenador
     * @return True si esta libre y false si ya se lleno
     */
    private boolean tieneHorarioLibre(dtos.EntrenadorDTO e) {
        if (e.getHorarios() == null || e.getHorarios().isEmpty()) return false;
        for (dtos.HorarioDTO h : e.getHorarios()) {
            if (h.isDisponible()) return true;
        }
        return false;
    }

    /**
     * Verifica si la membresia que pago el socio tiene derecho a pedir entrenador
     * 
     * @param idPlan Identificador de su plan
     * @return True si lo incluye
     */
    private boolean planIncluyeEntrenador(String idPlan) {
        if (idPlan == null) return false;
        String id = idPlan.toUpperCase();
        return id.contains("PREMIUM") || id.contains("GOLD") || id.contains("P002");
    }

    /**
     * Verifica si la membresia del socio cubre la entrada a clases grupales
     * 
     * @param idPlan Identificador de su plan
     * @return True si se puede meter a las clases
     */
    private boolean planIncluyeClases(String idPlan) {
        if (idPlan == null) return false;
        String id = idPlan.toUpperCase();
        return !id.contains("BASICO") && !id.contains("BASE");
    }
}