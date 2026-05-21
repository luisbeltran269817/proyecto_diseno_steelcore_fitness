/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesReportesBO;

import Excepciones.NegocioException;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;
import java.io.File;
import java.util.List;

/**
 * Interfaz de negocio para la Administración Comercial y Reportes Financieros.
 *
 * Define las operaciones que debe implementar la capa de negocio del módulo de
 * reportes. Esta interfaz es utilizada por la fachada del subsistema de
 * reportes para delegar la lógica del caso de uso.
 *
 * La capa de negocio es responsable de: - Validar reglas de negocio
 * relacionadas con los reportes. - Crear reportes a partir de los filtros
 * seleccionados. - Coordinar la generación de archivos PDF. - Coordinar la
 * impresión de reportes. - Coordinar el envío de reportes por correo
 * electrónico.
 *
 * Esta interfaz no debe contener lógica de presentación ni lógica directa de
 * persistencia. Su implementación podrá utilizar DAOs, mappers, servicios de
 * infraestructura y objetos de dominio según sea necesario.
 *
 * @author Noelia E.N.
 */
public interface IAdministrarReportesBO {

    /**
     * Crea un reporte comercial o financiero de acuerdo con el tipo de reporte
     * solicitado y los filtros capturados por el administrador.
     *
     * Este método debe validar los filtros recibidos, consultar la información
     * necesaria y calcular las métricas correspondientes al reporte.
     *
     * @param tipoReporte tipo de reporte que se desea generar.
     * @param filtros filtros aplicados al reporte, como fecha de inicio, fecha
     * de fin, sucursal, tipo de membresía, entrenador o amenidad.
     * @return reporte generado con sus filtros, métricas principales y estado
     * de disponibilidad de datos.
     * @throws NegocioException si el tipo de reporte es nulo, los filtros son
     * inválidos o no es posible generar el reporte.
     */
    ReporteDTO crearReporte(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros) throws NegocioException;

    /**
     * Exporta un reporte generado en formato PDF.
     *
     * Este método debe validar que el reporte exista y que contenga datos.
     * Después debe solicitar a la infraestructura correspondiente la generación
     * del archivo PDF en la ruta seleccionada.
     *
     * @param reporte reporte que se desea exportar.
     * @param destino archivo destino donde se guardará el PDF.
     * @return archivo PDF generado.
     * @throws NegocioException si el reporte es inválido, el destino es nulo o
     * ocurre un error durante la generación del PDF.
     */
    File exportarPDF(ReporteDTO reporte, File destino) throws NegocioException;

    /**
     * Imprime un reporte previamente generado.
     *
     * Este método debe generar internamente un PDF temporal del reporte y
     * enviarlo al servicio de impresión correspondiente.
     *
     * @param reporte reporte que se desea imprimir.
     * @throws NegocioException si el reporte es inválido, si no se puede
     * generar el PDF temporal o si ocurre un error durante la impresión.
     */
    void imprimirPDF(ReporteDTO reporte) throws NegocioException;

    /**
     * Envía un reporte previamente generado por correo electrónico.
     *
     * Este método debe validar el reporte y el correo destino. Después debe
     * generar un PDF temporal y enviarlo como archivo adjunto.
     *
     * @param reporte reporte que se desea enviar.
     * @param correoDestino correo electrónico del destinatario.
     * @throws NegocioException si el reporte es inválido, el correo no tiene un
     * formato correcto, no se puede generar el PDF o falla el envío del correo.
     */
    void enviarPDF(ReporteDTO reporte, String correoDestino) throws NegocioException;

    /**
     * Genera un archivo PDF temporal a partir de un reporte.
     *
     * Este método es útil cuando otra operación necesita el PDF generado pero
     * no necesariamente desea guardarlo en una ubicación seleccionada por el
     * usuario, por ejemplo para imprimirlo o enviarlo por correo.
     *
     * @param reporte reporte que se desea convertir en PDF temporal.
     * @return archivo PDF temporal generado.
     * @throws NegocioException si el reporte es inválido o no se puede generar
     * el archivo temporal.
     */
    File generarPDFTemporal(ReporteDTO reporte) throws NegocioException;

    /**
     * Consulta los últimos reportes generados y guardados en historial.
     *
     * @return lista de reportes históricos recientes.
     * @throws NegocioException si ocurre un error al consultar el historial.
     */
    List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException;

    /**
     * Busca un reporte guardado en historial por su identificador.
     *
     * @param idReporte identificador del reporte histórico.
     * @return reporte histórico encontrado.
     * @throws NegocioException si el id es inválido o no se puede consultar.
     */
    ReporteHistorialDTO buscarReporteHistorialPorId(String idReporte) throws NegocioException;

    /**
     * Obtiene un resumen mensual con métricas reales del mes actual.
     *
     * @return métricas del mes actual.
     * @throws NegocioException si ocurre un error al calcular el resumen.
     */
    MetricasReporteDTO obtenerResumenMensual() throws NegocioException;
}
