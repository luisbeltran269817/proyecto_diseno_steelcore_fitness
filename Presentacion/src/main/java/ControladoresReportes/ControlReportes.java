/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControladoresReportes;

import Controladores.IControladorAplicacion;
import PantallasAdministrarReportes.PantallaEnviarReporteCorreo;
import PantallasAdministrarReportes.PantallaExportarReporte;
import PantallasAdministrarReportes.PantallaSeleccionReportes;
import PantallasAdministrarReportes.PantallaFiltrosReporte;
import PantallasAdministrarReportes.PantallaImprimirReporte;
import PantallasAdministrarReportes.PantallaReporte;
import PantallasAdministrarReportes.PantallaOpcionesReporte;
import PantallasAdministrarReportes.PantallaPrincipalReportes;
import Utilerias.Mensaje;
import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.TipoReporteDTO;
import java.util.List;
import Excepciones.NegocioException;
import PantallasAdministrarReportes.PantallaConsultarReportes;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import fachada.FachadaAdministrarReportes;
import interfaz.IAdministrarReportes;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Dell PC
 */
public class ControlReportes implements IControlReportes {

    private final IControladorAplicacion controladorAplicacion;
    private final IAdministrarReportes administrarReportes;
    private PantallaPrincipalReportes pantallaPrincipalReportes;
    private PantallaSeleccionReportes pantallaSeleccionReportes;
    private PantallaFiltrosReporte pantallaFiltrosReporte;
    private PantallaReporte pantallaReporte;
    private PantallaOpcionesReporte pantallaOpcionesReporte;
    private PantallaImprimirReporte pantallaImprimirReporte;
    private PantallaExportarReporte pantallaExportarReporte;
    private PantallaEnviarReporteCorreo pantallaEnviarReporteCorreo;
    private PantallaConsultarReportes pantallaConsultarReportes;

    private TipoReporteDTO tipoReporteSeleccionado;
    private FiltrosReporteDTO filtrosReporte;
    private ReporteDTO reporteGenerado;

    /**
     * Constructor del controlador de reportes.
     *
     * Recibe el controlador principal de la aplicación para poder regresar a
     * las pantallas generales del sistema. Además, inicializa la fachada del
     * subsistema de reportes, que será la encargada de comunicarse con la capa
     * de negocio.
     *
     * @param controladorAplicacion controlador principal de la aplicación.
     */
    public ControlReportes(IControladorAplicacion controladorAplicacion) {
        this.controladorAplicacion = controladorAplicacion;
        this.administrarReportes = new FachadaAdministrarReportes();
    }

    @Override
    public void iniciarAdministrarReportes() {
        irAPantallaPrincipalReportes();
    }

    @Override
    public void irAPantallaPrincipalReportes() {
        cerrarPantallasReportes();
        pantallaPrincipalReportes = new PantallaPrincipalReportes(this, controladorAplicacion);
        pantallaPrincipalReportes.setVisible(true);
    }

    @Override
    public void irAPantallaPrincipalSistema() {
        cerrarPantallasReportes();
        controladorAplicacion.irAPerfilUsuario();
    }

    @Override
    public void irAPantallaSeleccionReportes() {
        cerrarPantallasReportes();
        pantallaSeleccionReportes = new PantallaSeleccionReportes(this, controladorAplicacion);
        pantallaSeleccionReportes.setVisible(true);
    }

    @Override
    public void irAPantallaFiltros() {
        cerrarPantallasReportes();
        pantallaFiltrosReporte = new PantallaFiltrosReporte(this, controladorAplicacion, tipoReporteSeleccionado);
        pantallaFiltrosReporte.setVisible(true);
    }

    @Override
    public void irAPantallaReporte() {
        cerrarPantallasReportes();
        pantallaReporte = new PantallaReporte(this, controladorAplicacion, reporteGenerado);
        pantallaReporte.setVisible(true);
    }

    @Override
    public void irAPantallaOpcionesReporte() {
        cerrarPantallasReportes();
        pantallaOpcionesReporte = new PantallaOpcionesReporte(this, controladorAplicacion, reporteGenerado);
        pantallaOpcionesReporte.setVisible(true);
    }

    @Override
    public void irAPantallaImprimir() {
        cerrarPantallasReportes();
        pantallaImprimirReporte = new PantallaImprimirReporte(this, controladorAplicacion, reporteGenerado);
        pantallaImprimirReporte.setVisible(true);
    }

    @Override
    public void irAPantallaExportar() {
        cerrarPantallasReportes();
        pantallaExportarReporte = new PantallaExportarReporte(this, controladorAplicacion, reporteGenerado);
        pantallaExportarReporte.setVisible(true);
    }

    @Override
    public void irAPantallaEnviarPorCorreo() {
        cerrarPantallasReportes();
        pantallaEnviarReporteCorreo = new PantallaEnviarReporteCorreo(this, controladorAplicacion, reporteGenerado);
        pantallaEnviarReporteCorreo.setVisible(true);
    }

    @Override
    public void notificarTipoSeleccionado(TipoReporteDTO tipoReporte) {
        this.tipoReporteSeleccionado = tipoReporte;
        irAPantallaFiltros();
    }

    /**
     * Solicita la creación de un reporte con base en los filtros seleccionados.
     *
     * Este método ya no genera datos directamente. La creación del reporte se
     * delega al subsistema de Administración de Reportes por medio de su
     * fachada.
     *
     * @param filtros filtros seleccionados por el administrador.
     */
    @Override
    public void crearReporte(FiltrosReporteDTO filtros) {
        try {
            this.filtrosReporte = filtros;

            ReporteDTO reporte = administrarReportes.crearReporte(tipoReporteSeleccionado, filtros);

            if (reporte == null || !reporte.isTieneDatos()) {
                Mensaje.info(null, "No se encontraron datos para los filtros seleccionados.");
                return;
            }

            this.reporteGenerado = reporte;
            irAPantallaReporte();

        } catch (NegocioException ex) {
            Mensaje.error(null, ex.getMessage());
        }
    }

    @Override
    public boolean validarDatos(FiltrosReporteDTO filtros) {
        if (filtros == null) {
            return false;
        }

        if (filtros.getFechaInicio() == null || filtros.getFechaFin() == null) {
            return false;
        }

        return !filtros.getFechaFin().isBefore(filtros.getFechaInicio());
    }

    /**
     * Exporta el reporte generado en formato PDF.
     *
     * Muestra un selector de archivos para que el administrador elija dónde
     * guardar el documento. Después delega la exportación al subsistema de
     * reportes.
     */
    @Override
    public void exportar() {
        if (reporteGenerado == null) {
            Mensaje.error(null, "No hay un reporte generado para exportar.");
            return;
        }

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte como PDF");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivo PDF", "pdf"));
        chooser.setSelectedFile(new File("reporte.pdf"));

        int opcion = chooser.showSaveDialog(null);

        if (opcion != JFileChooser.APPROVE_OPTION) {
            return;
        }

        try {
            File archivoGenerado = administrarReportes.exportarPDF(reporteGenerado, chooser.getSelectedFile());

            Mensaje.info(null, "Reporte exportado correctamente:\n" + archivoGenerado.getAbsolutePath());

        } catch (NegocioException ex) {
            Mensaje.error(null, ex.getMessage());
        }
    }

    /**
     * Imprime el reporte generado.
     *
     * La generación temporal del PDF y el envío a impresión se delegan al
     * subsistema de reportes.
     */
    @Override
    public void imprimir() {
        if (reporteGenerado == null) {
            Mensaje.error(null, "No hay un reporte generado para imprimir.");
            return;
        }

        try {
            administrarReportes.imprimirPDF(reporteGenerado);
            Mensaje.info(null, "Reporte enviado a impresión.");

        } catch (NegocioException ex) {
            Mensaje.error(null, ex.getMessage());
        }
    }

    /**
     * Envía el reporte generado por correo electrónico.
     *
     * Valida el correo ingresado desde presentación y después delega el envío
     * real del PDF al subsistema de reportes.
     *
     * @param correo correo electrónico del destinatario.
     */
    @Override
    public void enviar(String correo) {
        if (reporteGenerado == null) {
            Mensaje.error(null, "No hay un reporte generado para enviar.");
            return;
        }

        if (!validarCorreo(correo)) {
            Mensaje.error(null, "El correo ingresado no es válido.");
            return;
        }

        try {
            administrarReportes.enviarPDF(reporteGenerado, correo);
            Mensaje.info(null, "Reporte enviado correctamente a:\n" + correo);

        } catch (NegocioException ex) {
            Mensaje.error(null, ex.getMessage());
        }
    }

    @Override
    public boolean validarCorreo(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    @Override
    public void cerrarSesion() {
        cerrarPantallasReportes();
        controladorAplicacion.cerrarSesion();
    }

    @Override
    public TipoReporteDTO getTipoReporteSeleccionado() {
        return tipoReporteSeleccionado;
    }

    @Override
    public FiltrosReporteDTO getFiltrosReporte() {
        return filtrosReporte;
    }

    @Override
    public ReporteDTO getReporteGenerado() {
        return reporteGenerado;
    }

    @Override
    public List<SucursalDTO> obtenerSucursales() throws NegocioException {
        return controladorAplicacion.obtenerSucursales();
    }

    @Override
    public List<PlanDTO> obtenerPlanes(String idSucursal) throws NegocioException {
        if (idSucursal == null || idSucursal.isBlank()) {
            return java.util.Collections.emptyList();
        }

        return controladorAplicacion.obtenerPlanesDeSucursal(idSucursal);
    }

    @Override
    public List<AmenidadDTO> obtenerAmenidades() throws NegocioException {
        return controladorAplicacion.obtenerAmenidadesExtra();
    }

    @Override
    public List<EntrenadorDTO> obtenerEntrenadores(String idSucursal) throws NegocioException {
        if (idSucursal == null || idSucursal.isBlank()) {
            return java.util.Collections.emptyList();
        }

        return controladorAplicacion.obtenerEntrenadoresDeSucursal(idSucursal);
    }

    private void cerrarPantallasReportes() {
        if (pantallaPrincipalReportes != null) {
            pantallaPrincipalReportes.dispose();
            pantallaPrincipalReportes = null;
        }

        if (pantallaSeleccionReportes != null) {
            pantallaSeleccionReportes.dispose();
            pantallaSeleccionReportes = null;
        }

        if (pantallaFiltrosReporte != null) {
            pantallaFiltrosReporte.dispose();
            pantallaFiltrosReporte = null;
        }

        if (pantallaReporte != null) {
            pantallaReporte.dispose();
            pantallaReporte = null;
        }

        if (pantallaOpcionesReporte != null) {
            pantallaOpcionesReporte.dispose();
            pantallaOpcionesReporte = null;
        }

        if (pantallaImprimirReporte != null) {
            pantallaImprimirReporte.dispose();
            pantallaImprimirReporte = null;
        }

        if (pantallaExportarReporte != null) {
            pantallaExportarReporte.dispose();
            pantallaExportarReporte = null;
        }

        if (pantallaEnviarReporteCorreo != null) {
            pantallaEnviarReporteCorreo.dispose();
            pantallaEnviarReporteCorreo = null;
        }

        if (pantallaConsultarReportes != null) {
            pantallaConsultarReportes.dispose();
            pantallaConsultarReportes = null;
        }
    }

    @Override
    public void irAPantallaConsultarReportes() {
        cerrarPantallasReportes();
        pantallaConsultarReportes = new PantallaConsultarReportes(this, controladorAplicacion);
        pantallaConsultarReportes.setVisible(true);
    }

    /**
     * Consulta los últimos reportes generados desde el subsistema de reportes.
     *
     * @return lista de reportes históricos recientes.
     * @throws NegocioException si ocurre un error al consultar el historial.
     */
    @Override
    public List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException {
        return administrarReportes.consultarUltimosReportes();
    }

    /**
     * Busca un reporte histórico por id.
     *
     * @param idReporte identificador del reporte.
     * @return reporte histórico encontrado.
     * @throws NegocioException si ocurre un error al consultar.
     */
    @Override
    public ReporteHistorialDTO buscarReporteHistorialPorId(String idReporte) throws NegocioException {
        return administrarReportes.buscarReporteHistorialPorId(idReporte);
    }

    /**
     * Obtiene el resumen mensual real del módulo de reportes.
     *
     * @return métricas calculadas del mes actual.
     * @throws NegocioException si ocurre un error al consultar el resumen.
     */
    @Override
    public MetricasReporteDTO obtenerResumenMensual() throws NegocioException {
        return administrarReportes.obtenerResumenMensual();
    }
}
