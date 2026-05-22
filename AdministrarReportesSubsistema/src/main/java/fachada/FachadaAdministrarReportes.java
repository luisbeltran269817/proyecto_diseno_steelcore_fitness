/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import objetosNegociosReportes.AdministrarReportesBO;
import Excepciones.NegocioException;
import interfacesReportesBO.IAdministrarReportesBO;
import interfaz.IAdministrarReportes;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;
import java.io.File;
import java.util.List;

/**
 * Fachada del subsistema de Administración Comercial y Reportes Financieros.
 *
 * Esta clase implementa la interfaz IAdministrarReportes y representa el punto
 * de entrada del subsistema de reportes para la capa de presentación.
 *
 * La fachada no contiene lógica de negocio directamente. Su función es delegar
 * las operaciones al BO correspondiente, manteniendo separadas las
 * responsabilidades de presentación, negocio e infraestructura.
 *
 * @author Noelia E.N.
 */
public class FachadaAdministrarReportes implements IAdministrarReportes {

    private final IAdministrarReportesBO administrarReportesBO;

    /**
     * Constructor de la fachada.
     *
     * Inicializa el BO encargado de administrar la lógica de negocio de los
     * reportes.
     */
    public FachadaAdministrarReportes() {
        this.administrarReportesBO = new AdministrarReportesBO();
    }

    /**
     * Genera un reporte de acuerdo con el tipo seleccionado y los filtros
     * capturados en la pantalla.
     *
     * @param tipoReporte tipo de reporte seleccionado por el administrador.
     * @param filtros filtros utilizados para consultar y calcular la
     * información del reporte.
     * @return reporte generado con sus métricas y datos principales.
     * @throws NegocioException si el reporte no puede generarse.
     */
    @Override
    public ReporteDTO crearReporte(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros) throws NegocioException {
        return administrarReportesBO.crearReporte(tipoReporte, filtros);
    }

    /**
     * Exporta un reporte en formato PDF.
     *
     * @param reporte reporte previamente generado.
     * @param destino archivo destino seleccionado por el usuario.
     * @return archivo PDF generado.
     * @throws NegocioException si ocurre un error al generar o guardar el PDF.
     */
    @Override
    public File exportarPDF(ReporteDTO reporte, File destino) throws NegocioException {
        return administrarReportesBO.exportarPDF(reporte, destino);
    }

    /**
     * Imprime un reporte previamente generado.
     *
     * @param reporte reporte que se desea imprimir.
     * @throws NegocioException si ocurre un error al generar o imprimir el PDF.
     */
    @Override
    public void imprimirPDF(ReporteDTO reporte) throws NegocioException {
        administrarReportesBO.imprimirPDF(reporte);
    }

    /**
     * Envía un reporte por correo electrónico en formato PDF.
     *
     * @param reporte reporte que se desea enviar.
     * @param correoDestino correo electrónico del destinatario.
     * @throws NegocioException si ocurre un error al generar el PDF o enviar el
     * correo.
     */
    @Override
    public void enviarPDF(ReporteDTO reporte, String correoDestino) throws NegocioException {
        administrarReportesBO.enviarPDF(reporte, correoDestino);
    }

    /**
     * Consulta los últimos reportes generados y guardados en historial.
     *
     * @return lista de reportes históricos recientes.
     * @throws NegocioException si ocurre un error al consultar.
     */
    @Override
    public List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException {
        return administrarReportesBO.consultarUltimosReportes();
    }

    /**
     * Busca un reporte histórico por id.
     *
     * @param idReporte identificador del reporte.
     * @return reporte histórico encontrado.
     * @throws NegocioException si no se encuentra o falla la consulta.
     */
    @Override
    public ReporteHistorialDTO buscarReporteHistorialPorId(String idReporte) throws NegocioException {
        return administrarReportesBO.buscarReporteHistorialPorId(idReporte);
    }

    /**
     * Obtiene un resumen mensual con métricas reales del mes actual.
     *
     * @return métricas del mes actual.
     * @throws NegocioException si ocurre un error al calcular el resumen.
     */
    @Override
    public MetricasReporteDTO obtenerResumenMensual() throws NegocioException {
        return administrarReportesBO.obtenerResumenMensual();
    }
}
