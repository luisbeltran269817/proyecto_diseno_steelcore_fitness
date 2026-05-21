/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructuraReportes;

import java.io.File;

/**
 * Interfaz para la impresión de reportes en formato PDF.
 *
 * Define la operación necesaria para imprimir un archivo PDF generado por el
 * sistema de reportes. Esta interfaz pertenece a la capa de infraestructura, ya
 * que la impresión depende de servicios del sistema operativo y de las
 * impresoras configuradas en el equipo.
 *
 * La capa de negocio utiliza esta interfaz para solicitar la impresión sin
 * depender directamente de una librería específica ni de la API de impresión
 * utilizada.
 *
 * @author Noelia E.N.
 */
public interface IImpresorReporte {

    /**
     * Imprime un archivo PDF previamente generado.
     *
     * @param archivoPDF archivo PDF que se desea imprimir.
     * @throws Exception si el archivo no existe, no es válido o ocurre un error
     * durante el proceso de impresión.
     */
    void imprimir(File archivoPDF) throws Exception;
}
