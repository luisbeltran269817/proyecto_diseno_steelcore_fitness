/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructuraReportes;

import java.awt.print.PrinterJob;
import java.io.File;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 * Impresor de reportes PDF.
 *
 * Esta clase implementa IImpresorReporte y se encarga de enviar un archivo PDF
 * a una impresora utilizando Apache PDFBox y la API de impresión de Java.
 *
 * A diferencia de Desktop.print(), esta implementación carga el PDF y lo manda
 * a un PrinterJob, lo que permite mostrar el diálogo de impresión y depender
 * menos del visor de PDF instalado en el equipo.
 *
 * Esta clase pertenece a la capa de infraestructura porque interactúa con
 * archivos PDF, librerías externas y servicios de impresión del sistema.
 *
 * @author Noelia E.N.
 */
public class ImpresorReportePDF implements IImpresorReporte {

    /**
     * Imprime un archivo PDF.
     *
     * El método valida que el archivo exista, carga el documento con PDFBox,
     * crea un trabajo de impresión y muestra el cuadro de diálogo de impresión.
     * Si el usuario confirma, el documento se manda a imprimir.
     *
     * @param archivoPDF archivo PDF que se desea imprimir.
     * @throws Exception si el archivo no existe, no se puede cargar el PDF o
     * ocurre un error durante la impresión.
     */
    @Override
    public void imprimir(File archivoPDF) throws Exception {
        validarArchivo(archivoPDF);

        try (PDDocument documento = Loader.loadPDF(archivoPDF)) {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Reporte SteelCore Fitness");

            job.setPageable(new PDFPageable(documento));

            if (job.printDialog()) {
                job.print();
            }
        }
    }

    /**
     * Valida que el archivo PDF exista y sea un archivo válido.
     *
     * @param archivoPDF archivo PDF a validar.
     * @throws IllegalArgumentException si el archivo es nulo, no existe o no es
     * un archivo.
     */
    private void validarArchivo(File archivoPDF) {
        if (archivoPDF == null) {
            throw new IllegalArgumentException("No se recibió el archivo PDF para imprimir.");
        }

        if (!archivoPDF.exists()) {
            throw new IllegalArgumentException("El archivo PDF no existe.");
        }

        if (!archivoPDF.isFile()) {
            throw new IllegalArgumentException("La ruta seleccionada no corresponde a un archivo PDF.");
        }
    }
}
