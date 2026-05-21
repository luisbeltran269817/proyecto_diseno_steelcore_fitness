/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaz;

import Excepciones.NegocioException;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;
import java.io.File;
import java.util.List;

/**
 * Interfaz del subsistema de Administración Comercial y Reportes Financieros.
 *
 * Define las operaciones disponibles para la capa de presentación relacionadas
 * con la generación, exportación, impresión y envío de reportes.
 *
 * Esta interfaz funciona como punto de entrada al subsistema de reportes,
 * evitando que la presentación dependa directamente de los BOs o de la
 * infraestructura encargada de generar PDFs, imprimir o enviar correos.
 *
 * Responsabilidades principales: - Crear reportes a partir de un tipo de
 * reporte y filtros seleccionados. - Exportar reportes en formato PDF. -
 * Imprimir reportes generados. - Enviar reportes por correo electrónico.
 *
 * @author Noelia E.N
 */
public interface IAdministrarReportes {

    /**
     * Genera un reporte comercial o financiero de acuerdo con el tipo de
     * reporte seleccionado y los filtros indicados por el administrador.
     *
     * El reporte generado contiene la información necesaria para mostrarse en
     * pantalla, incluyendo sus métricas principales, filtros aplicados y estado
     * de disponibilidad de datos.
     *
     * @param tipoReporte tipo de reporte que se desea generar.
     * @param filtros filtros aplicados al reporte, como fechas, sucursal, tipo
     * de membresía, entrenador o amenidad.
     * @return reporte generado con sus métricas y datos principales.
     * @throws NegocioException si el tipo de reporte es inválido, los filtros
     * no son correctos o no es posible generar el reporte.
     */
    ReporteDTO crearReporte(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros) throws NegocioException;

    /**
     * Exporta un reporte previamente generado en formato PDF.
     *
     * El archivo se guarda en la ruta indicada por el usuario. Si el archivo no
     * tiene extensión ".pdf", el subsistema debe agregarla automáticamente.
     *
     * @param reporte reporte que se desea exportar.
     * @param destino archivo o ruta donde se guardará el PDF.
     * @return archivo PDF generado.
     * @throws NegocioException si no existe un reporte válido, si no se puede
     * escribir en la ruta seleccionada o si ocurre un error al generar el PDF.
     */
    File exportarPDF(ReporteDTO reporte, File destino) throws NegocioException;

    /**
     * Imprime un reporte previamente generado.
     *
     * El subsistema genera internamente un PDF temporal del reporte y lo envía
     * al mecanismo de impresión configurado en el equipo.
     *
     * @param reporte reporte que se desea imprimir.
     * @throws NegocioException si no existe un reporte válido, si no se puede
     * generar el PDF temporal o si ocurre un error al imprimir.
     */
    void imprimirPDF(ReporteDTO reporte) throws NegocioException;

    /**
     * Envía un reporte previamente generado por correo electrónico.
     *
     * El subsistema genera internamente un PDF temporal del reporte y lo
     * adjunta al correo enviado al destinatario indicado.
     *
     * @param reporte reporte que se desea enviar.
     * @param correoDestino correo electrónico del destinatario.
     * @throws NegocioException si el reporte no es válido, si el correo no es
     * correcto, si no se puede generar el PDF o si ocurre un error durante el
     * envío del correo.
     */
    void enviarPDF(ReporteDTO reporte, String correoDestino) throws NegocioException;

    /**
     * Consulta los últimos reportes generados y guardados en historial.
     *
     * @return lista de reportes históricos recientes.
     * @throws NegocioException si ocurre un error al consultar.
     */
    List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException;

    /**
     * Busca un reporte histórico por id.
     *
     * @param idReporte identificador del reporte.
     * @return reporte histórico encontrado.
     * @throws NegocioException si no se encuentra o falla la consulta.
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
