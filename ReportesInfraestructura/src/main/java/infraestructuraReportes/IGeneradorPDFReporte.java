/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructuraReportes;

import dtosReportes.ReporteDTO;
import java.io.File;

/**
 * Interfaz para la generación de documentos PDF de reportes.
 *
 * Define las operaciones necesarias para convertir un ReporteDTO en un archivo
 * PDF. Esta interfaz pertenece a la capa de infraestructura, ya que la creación
 * física del archivo PDF es un detalle técnico externo a la lógica principal
 * del negocio.
 *
 * La capa de negocio utiliza esta interfaz para generar reportes sin depender
 * directamente de una librería específica de PDF.
 *
 * @author Noelia E.N.
 */
public interface IGeneradorPDFReporte {

    /**
     * Genera un archivo PDF temporal a partir de un reporte.
     *
     * Este método se utiliza principalmente cuando el PDF no necesita ser
     * guardado directamente por el usuario, por ejemplo cuando se va a imprimir
     * o enviar por correo electrónico.
     *
     * @param reporte reporte que se desea convertir en PDF.
     * @return archivo PDF temporal generado.
     * @throws Exception si el reporte es inválido o si ocurre un error al crear
     * el archivo temporal.
     */
    File generarTemporal(ReporteDTO reporte) throws Exception;

    /**
     * Genera un archivo PDF en una ruta específica.
     *
     * Este método se utiliza cuando el usuario decide exportar el reporte y
     * selecciona una ubicación donde desea guardar el archivo.
     *
     * @param reporte reporte que se desea convertir en PDF.
     * @param destino archivo destino seleccionado por el usuario.
     * @return archivo PDF generado.
     * @throws Exception si el reporte es inválido, el destino no existe o no se
     * puede escribir el archivo.
     */
    File generarEnRuta(ReporteDTO reporte, File destino) throws Exception;
}
