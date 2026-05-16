package Control;

import Excepciones.NegocioException;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.MembresiaDTO.EstadoMembresia;
import dtos.VisitaDTO;
import Fachada.Icontrolacceso.ResultadoAccesoDTO;
import dtos.SucursalDTO;
import interfaces.IClienteBO;
import interfaces.IMembresiaBO;
import interfaces.ISucursalBO;
import interfaces.IVisitaBO;
import objetosnegocios.ClienteBO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.VisitaBO;
import java.time.LocalDateTime;
import objetosnegocios.SucursalBO;

/**
 * 
 * @author julian izaguirre
 */
public class ControlAcceso {
    private final IClienteBO   clienteBO;
    private final IMembresiaBO membresiaBO;
    private final ISucursalBO  sucursalBO;
    private final IVisitaBO    visitaBO;
    private String idSucursalLocal;

    public ControlAcceso() {
        this.clienteBO      = new ClienteBO();
        this.membresiaBO    = new MembresiaBO();
        this.sucursalBO     = new SucursalBO();
        this.visitaBO       = new VisitaBO();
        this.idSucursalLocal = null;
    }

    public void setIdSucursalLocal(String idSucursal) {
        this.idSucursalLocal = idSucursal;
    }

    /**
     * Procesa el código QR escaneado en recepción.
     *
     * @param codigoQR string leído del QR
     * @return ResultadoAccesoDTO con cliente, membresía y visita registrada
     * @throws AccesoDenegadoException si cualquier validación falla
     * @throws NegocioException si ocurre un error de negocio
     */
    public ResultadoAccesoDTO procesarQR(String codigoQR)
            throws AccesoDenegadoException, NegocioException {

        if (codigoQR == null || codigoQR.isBlank()) {
            throw new AccesoDenegadoException("Código QR vacío o inválido.");
        }

        MembresiaDTO membresia = buscarMembresiaPorQR(codigoQR);
        if (membresia == null) {
            throw new AccesoDenegadoException(
                    "El código QR no corresponde a ningún socio registrado.");
        }

        if (membresia.getEstado() != EstadoMembresia.ACTIVA) {
            String razon = switch (membresia.getEstado()) {
                case VENCIDA   -> "Membresía vencida.";
                case CANCELADA -> "Membresía cancelada.";
                default        -> "Membresía no activa.";
            };
            throw new AccesoDenegadoException(razon);
        }

        if (membresia.getFechaCaducidad() != null
                && membresia.getFechaCaducidad().isBefore(LocalDateTime.now())) {
            throw new AccesoDenegadoException(
                    "Membresía vencida el "
                    + membresia.getFechaCaducidad().toLocalDate() + ".");
        }

        if (idSucursalLocal != null
                && membresia.getIdSucursal() != null
                && !idSucursalLocal.equals(membresia.getIdSucursal())) {
            String nombreSucursalRegistrada = resolverNombreSucursal(membresia.getIdSucursal());
            throw new AccesoDenegadoException(
                    "Esta membresía pertenece a la sucursal " + nombreSucursalRegistrada
                    + ".\nNo tiene acceso a esta ubicación.");
        }

        ClienteDTO cliente = clienteBO.buscarPorCorreo(membresia.getIdCliente());
        if (cliente == null) {
            throw new AccesoDenegadoException("Socio no encontrado en el sistema.");
        }

        String idSucursalRegistro = (idSucursalLocal != null)
                ? idSucursalLocal
                : membresia.getIdSucursal();

        SucursalDTO sucursal = null;
        try {
            sucursal = sucursalBO.buscarPorId(idSucursalRegistro);
        } catch (NegocioException ignored) { }

        VisitaDTO visitaDTO = new VisitaDTO();
        if (sucursal != null) {
            visitaDTO.setGimnasio(sucursal.getNombre());
            visitaDTO.setCalle(sucursal.getCalle());
            visitaDTO.setColonia(sucursal.getColonia());
            visitaDTO.setCiudad(sucursal.getCiudad());
        }
        visitaDTO.setFechaHora(LocalDateTime.now());

        VisitaDTO visitaGuardada = visitaBO.guardar(
                cliente.getIdCliente(), 
                idSucursalRegistro,
                visitaDTO);

        return new ResultadoAccesoDTO(cliente, membresia, visitaGuardada);
    }

    private MembresiaDTO buscarMembresiaPorQR(String codigoQR) throws NegocioException {
        String idExtraido = extraerIdDeUrl(codigoQR);
        if (idExtraido != null) {
            MembresiaDTO m = membresiaBO.buscarPorId(idExtraido);
            if (m != null) return m;
        }
        return null;
    }

    private String extraerIdDeUrl(String url) {
        if (url == null || !url.contains("?id=")) return null;
        try {
            int inicio = url.indexOf("?id=") + 4;
            int fin = url.indexOf("&", inicio);
            return (fin < 0) ? url.substring(inicio) : url.substring(inicio, fin);
        } catch (Exception e) {
            return null;
        }
    }

    private String resolverNombreSucursal(String idSucursal) {
        try {
            SucursalDTO s = sucursalBO.buscarPorId(idSucursal);
            return (s != null && s.getNombre() != null) ? s.getNombre() : idSucursal;
        } catch (NegocioException e) {
            return idSucursal;
        }
    }
}