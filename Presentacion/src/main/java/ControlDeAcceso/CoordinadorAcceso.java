package ControlDeAcceso;

import Controladores.ControladorAplicacion;
import Fachada.Icontrolacceso;

import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;

import java.util.List;

/**
 * CoordinadorAcceso — Mediador principal del módulo Control de Acceso de Socios.
 *
 * Extiende ControladorAplicacion del caso base (hereda login, navegacion,
 * getSucursalSeleccionada, etc.) e implementa ICoordinadorAcceso.
 *
 * Correcciones respecto a la version anterior:
 *  - Eliminados imports muertos: SocioDTO, VisitaDTO
 *  - asignarEntrenador() e inscribirClase() retornan ResultadoAccesoDTO
 *    (no ResultadoDTO, clase que no existe) segun ICoordinadorAcceso
 *  - iniciarPantallaEntrenadores() e iniciarPantallaClases() pasan el
 *    resultado y el flag del plan (constructores actualizados)
 *
 * @author julian izaguirre
 */
public class CoordinadorAcceso extends ControladorAplicacion implements ICoordinadorAcceso {

    // ── Singleton ──────────────────────────────────────────────────────────

    private static CoordinadorAcceso instancia;

    private CoordinadorAcceso() {
        super();
    }

    public static synchronized CoordinadorAcceso getInstancia() {
        if (instancia == null) {
            instancia = new CoordinadorAcceso();
        }
        return instancia;
    }

    // ── Fachada del subsistema ─────────────────────────────────────────────

    private Icontrolacceso fachadaAcceso;

    public void setFachadaAcceso(Icontrolacceso fachadaAcceso) {
        this.fachadaAcceso = fachadaAcceso;
    }

    // ── Contexto de la visita activa ───────────────────────────────────────

    private String idVisitaActiva;
    private String idClienteActivo;
    private String idPlanActivo;
    private boolean planIncluyeClasesActivo;
    private boolean planIncluyeEntrenadorActivo;

    // Guardamos el resultado completo para poder pasarlo a las pantallas hijas
    private ResultadoAccesoDTO resultadoActivo;

    // ── Navegación ─────────────────────────────────────────────────────────

    @Override
    public void iniciarPantallaEspera() {
        BC_PantallaEspera espera = new BC_PantallaEspera(this);
        espera.setVisible(true);
    }

    @Override
    public void iniciarPantallaExpediente(ResultadoAccesoDTO resultado) {
        BC_PantallaExpediente expediente = new BC_PantallaExpediente(this, resultado);
        expediente.setVisible(true);
    }

    /**
     * Abre la pantalla de entrenadores usando el contexto de la visita activa.
     * Si no hay visita activa (flujo llamado sin QR previo), no hace nada.
     */
    @Override
    public void iniciarPantallaEntrenadores() {
        if (resultadoActivo == null) return;
        BC_PantallaEntrenadores pantalla = new BC_PantallaEntrenadores(
                this, resultadoActivo, planIncluyeEntrenadorActivo);
        pantalla.setVisible(true);
    }

    /**
     * Abre la pantalla de clases usando el contexto de la visita activa.
     * Si no hay visita activa (flujo llamado sin QR previo), no hace nada.
     */
    @Override
    public void iniciarPantallaClases() {
        if (resultadoActivo == null) return;
        BC_PantallaClases pantalla = new BC_PantallaClases(
                this, resultadoActivo, planIncluyeClasesActivo);
        pantalla.setVisible(true);
    }

    // ── Lógica de acceso ───────────────────────────────────────────────────

    @Override
    public ResultadoAccesoDTO procesarQR(String codigoQR) {
        try {
            ResultadoAccesoDTO resultado = fachadaAcceso.procesarQR(codigoQR);
            // Guardar contexto para el resto del flujo
            this.resultadoActivo              = resultado;
            this.idVisitaActiva               = resultado.getIdVisita();
            this.idClienteActivo              = resultado.getIdCliente();
            this.idPlanActivo                 = resultado.getIdPlan();
            this.planIncluyeClasesActivo      = resultado.isPlanIncluyeClases();
            this.planIncluyeEntrenadorActivo  = resultado.isPlanIncluyeEntrenador();
            return resultado;
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            System.err.println("[CoordinadorAcceso] QR invalido: " + e.getMotivo());
            return null;
        }
    }

    @Override
    public void seleccionarAreaGeneral() {
        try {
            fachadaAcceso.registrarAreaGeneral(idVisitaActiva);
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            System.err.println("[CoordinadorAcceso] Error registrarAreaGeneral: " + e.getMotivo());
        }
    }

    // ── Entrenadores ───────────────────────────────────────────────────────

    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDisponibles() {
        try {
            String idSucursal = getSucursalSeleccionada() != null
                    ? getSucursalSeleccionada().getIdSucursal()
                    : null;
            return fachadaAcceso.obtenerEntrenadoresDisponibles(idSucursal);
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            System.err.println("[CoordinadorAcceso] Error entrenadores: " + e.getMotivo());
            return List.of();
        }
    }

    @Override
    public boolean planIncluyeEntrenador() {
        return planIncluyeEntrenadorActivo;
    }

    /**
     * Asigna el entrenador al socio en la visita activa.
     * Retorna ResultadoAccesoDTO con accesoConcedido=true en exito,
     * o accesoConcedido=false con el motivo en caso de error.
     */
    @Override
    public ResultadoAccesoDTO asignarEntrenador(String idEntrenador, String idHorario) {
        try {
            fachadaAcceso.asignarEntrenador(
                    idVisitaActiva, idClienteActivo, idEntrenador, idHorario);
            // Exito: devolvemos el resultado activo con el mensaje actualizado
            return ResultadoAccesoDTO.concedido(
                    resultadoActivo != null ? resultadoActivo.getNombreSocio() : "",
                    java.time.LocalDateTime.now(),
                    idVisitaActiva,
                    idClienteActivo,
                    idPlanActivo,
                    planIncluyeEntrenadorActivo,
                    planIncluyeClasesActivo);
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            return ResultadoAccesoDTO.denegado(
                    resultadoActivo != null ? resultadoActivo.getNombreSocio() : "",
                    dtosControlDeAcceso.EstadoMembresia.ACTIVA,
                    e.getMotivo());
        }
    }

    // ── Clases ─────────────────────────────────────────────────────────────

    @Override
    public List<ClaseDTO> obtenerClasesPlan() {
        try {
            String idSucursal = getSucursalSeleccionada() != null
                    ? getSucursalSeleccionada().getIdSucursal()
                    : null;
            return fachadaAcceso.obtenerClasesPorPlan(
                    idSucursal, idPlanActivo, idClienteActivo, planIncluyeClasesActivo);
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            System.err.println("[CoordinadorAcceso] Error clases: " + e.getMotivo());
            return List.of();
        }
    }

    @Override
    public boolean planIncluyeClases() {
        return planIncluyeClasesActivo;
    }

    /**
     * Inscribe al socio en la clase elegida.
     * Retorna ResultadoAccesoDTO con accesoConcedido=true en exito,
     * o accesoConcedido=false con el motivo en caso de cupo lleno / error.
     */
    @Override
    public ResultadoAccesoDTO inscribirClase(String idClase) {
        try {
            fachadaAcceso.inscribirClase(idVisitaActiva, idClase, idClienteActivo);
            return ResultadoAccesoDTO.concedido(
                    resultadoActivo != null ? resultadoActivo.getNombreSocio() : "",
                    java.time.LocalDateTime.now(),
                    idVisitaActiva,
                    idClienteActivo,
                    idPlanActivo,
                    planIncluyeEntrenadorActivo,
                    planIncluyeClasesActivo);
        } catch (Icontrolacceso.AccesoDenegadoException e) {
            return ResultadoAccesoDTO.denegado(
                    resultadoActivo != null ? resultadoActivo.getNombreSocio() : "",
                    dtosControlDeAcceso.EstadoMembresia.ACTIVA,
                    e.getMotivo());
        }
    }

    // ── Getters de contexto ────────────────────────────────────────────────

    public String getIdVisitaActiva()         { return idVisitaActiva; }
    public String getIdClienteActivo()        { return idClienteActivo; }
    public String getIdPlanActivo()           { return idPlanActivo; }
    public ResultadoAccesoDTO getResultadoActivo() { return resultadoActivo; }
}