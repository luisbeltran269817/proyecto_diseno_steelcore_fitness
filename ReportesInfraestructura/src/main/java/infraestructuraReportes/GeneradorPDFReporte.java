/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructuraReportes;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.TipoReporteDTO;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Generador de archivos PDF para reportes comerciales y financieros.
 *
 * Esta clase implementa IGeneradorPDFReporte y se encarga de convertir un
 * ReporteDTO en un archivo PDF real. Utiliza la librería OpenPDF para construir
 * el documento, agregar encabezados, filtros, métricas, indicadores y detalles
 * del reporte.
 *
 * Esta clase pertenece a la capa de infraestructura porque trabaja directamente
 * con archivos físicos y con una librería externa de generación de PDF.
 *
 * El PDF generado por esta clase es el documento oficial que se usará para: -
 * Exportar el reporte. - Imprimir el reporte. - Enviar el reporte por correo
 * electrónico.
 *
 * @author Noelia E.N.
 */
public class GeneradorPDFReporte implements IGeneradorPDFReporte {

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FMT_ARCHIVO = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * Genera un PDF temporal del reporte.
     *
     * Este archivo se guarda en la carpeta temporal del sistema operativo y se
     * usa para operaciones internas como imprimir o enviar por correo.
     *
     * @param reporte reporte que se desea convertir en PDF.
     * @return archivo PDF temporal generado.
     * @throws Exception si ocurre un error al generar el archivo.
     */
    @Override
    public File generarTemporal(ReporteDTO reporte) throws Exception {
        validarReporte(reporte);

        String nombreArchivo = crearNombreArchivo(reporte);
        File archivo = new File(System.getProperty("java.io.tmpdir"), nombreArchivo);

        generarPDF(reporte, archivo);

        return archivo;
    }

    /**
     * Genera un PDF del reporte en la ruta indicada.
     *
     * Si el archivo destino no termina con ".pdf", se agrega automáticamente la
     * extensión.
     *
     * @param reporte reporte que se desea exportar.
     * @param destino archivo destino seleccionado por el usuario.
     * @return archivo PDF generado.
     * @throws Exception si ocurre un error al generar o guardar el archivo.
     */
    @Override
    public File generarEnRuta(ReporteDTO reporte, File destino) throws Exception {
        validarReporte(reporte);

        if (destino == null) {
            throw new IllegalArgumentException("No se seleccionó una ubicación para guardar el PDF.");
        }

        File archivo = destino;

        if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
            archivo = new File(archivo.getAbsolutePath() + ".pdf");
        }

        generarPDF(reporte, archivo);

        return archivo;
    }

    /**
     * Construye físicamente el archivo PDF.
     *
     * @param reporte reporte con la información que se colocará en el PDF.
     * @param archivo archivo donde se escribirá el documento.
     * @throws Exception si ocurre un error durante la escritura del PDF.
     */
    private void generarPDF(ReporteDTO reporte, File archivo) throws Exception {
        Document document = new Document(PageSize.LETTER, 42, 42, 36, 36);

        PdfWriter.getInstance(document, new FileOutputStream(archivo));

        document.open();

        agregarEncabezado(document, reporte);
        agregarFiltros(document, reporte);
        agregarMetricas(document, reporte);
        agregarIndicadores(document, reporte);
        agregarDetalle(document, reporte);
        agregarPie(document);

        document.close();
    }

    /**
     * Agrega el encabezado principal del reporte al PDF.
     *
     * @param document documento PDF.
     * @param reporte reporte generado.
     * @throws Exception si no se puede agregar el contenido.
     */
    private void agregarEncabezado(Document document, ReporteDTO reporte) throws Exception {
        Paragraph empresa = new Paragraph(
                "STEELCORE FITNESS",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 19, Color.BLACK)
        );
        empresa.setAlignment(Element.ALIGN_CENTER);
        empresa.setSpacingAfter(6);
        document.add(empresa);

        Paragraph titulo = new Paragraph(
                obtenerNombreReporte(reporte.getTipoReporte()),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, Color.DARK_GRAY)
        );
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(4);
        document.add(titulo);

        Paragraph subtitulo = new Paragraph(
                "Administración Comercial y Reportes Financieros",
                FontFactory.getFont(FontFactory.HELVETICA, 10, Color.GRAY)
        );
        subtitulo.setAlignment(Element.ALIGN_CENTER);
        subtitulo.setSpacingAfter(18);
        document.add(subtitulo);
    }

    /**
     * Agrega la sección de filtros aplicados al reporte.
     *
     * @param document documento PDF.
     * @param reporte reporte generado.
     * @throws Exception si no se puede agregar la tabla.
     */
    private void agregarFiltros(Document document, ReporteDTO reporte) throws Exception {
        FiltrosReporteDTO filtros = reporte.getFiltros();

        Paragraph titulo = crearTituloSeccion("Filtros aplicados");
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{35, 65});

        String fechaInicio = "—";
        String fechaFin = "—";

        if (filtros != null && filtros.getFechaInicio() != null) {
            fechaInicio = filtros.getFechaInicio().format(FMT_FECHA);
        }

        if (filtros != null && filtros.getFechaFin() != null) {
            fechaFin = filtros.getFechaFin().format(FMT_FECHA);
        }

        agregarFila(tabla, "Fecha inicio", fechaInicio);
        agregarFila(tabla, "Fecha fin", fechaFin);

        if (usaSucursal(reporte)) {
            agregarFila(tabla, "Sucursal", filtros != null ? valor(filtros.getSucursal()) : "Todos");
        }

        if (usaTipoMembresia(reporte)) {
            agregarFila(tabla, "Membresía", filtros != null ? valor(filtros.getTipoMembresia()) : "Todos");
        }

        if (usaEntrenador(reporte)) {
            agregarFila(tabla, "Entrenador", filtros != null ? valor(filtros.getEntrenador()) : "Todos");
        }

        if (usaAmenidad(reporte)) {
            agregarFila(tabla, "Amenidad", filtros != null ? valor(filtros.getAmenidad()) : "Todos");
        }

        document.add(tabla);
    }

    /**
     * Agrega las métricas principales del reporte.
     *
     * @param document documento PDF.
     * @param reporte reporte generado.
     * @throws Exception si no se puede agregar la tabla.
     */
    private void agregarMetricas(Document document, ReporteDTO reporte) throws Exception {
        MetricasReporteDTO metricas = obtenerMetricas(reporte);

        Paragraph titulo = crearTituloSeccion(obtenerTituloMetricas(reporte));
        titulo.setSpacingBefore(18);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{50, 50});

        switch (obtenerTipoReporte(reporte)) {
            case VENTAS_MEMBRESIAS:
                agregarFila(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()));
                agregarFila(tabla, "Plan más vendido", valor(metricas.getTipoMembresiaMasVendida()));
                agregarFila(tabla, "Sucursal con más ventas", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Ingresos por ventas", "$" + metricas.getTotalIngresos());
                break;

            case INGRESOS:
                agregarFila(tabla, "Ingresos totales", "$" + metricas.getTotalIngresos());
                agregarFila(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()));
                agregarFila(tabla, "Plan con mayor ingreso", valor(metricas.getTipoMembresiaMasVendida()));
                agregarFila(tabla, "Amenidad destacada", valor(metricas.getAmenidadMasSolicitada()));
                break;

            case POR_SUCURSAL:
                agregarFila(tabla, "Sucursal con más ventas", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Ingresos generados", "$" + metricas.getTotalIngresos());
                agregarFila(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()));
                agregarFila(tabla, "Plan más vendido", valor(metricas.getTipoMembresiaMasVendida()));
                break;

            case DESEMPENO_ENTRENADORES:
                agregarFila(tabla, "Entrenador destacado", valor(metricas.getEntrenadorConMasClientes()));
                agregarFila(tabla, "Sucursal relacionada", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Amenidad relacionada", valor(metricas.getAmenidadMasSolicitada()));
                agregarFila(tabla, "Base de análisis", "Citas confirmadas/completadas");
                break;

            case GENERAL:
            default:
                agregarFila(tabla, "Ingresos totales", "$" + metricas.getTotalIngresos());
                agregarFila(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()));
                agregarFila(tabla, "Renovaciones", String.valueOf(metricas.getRenovaciones()));
                agregarFila(tabla, "Nuevos socios", String.valueOf(metricas.getNuevosSocios()));
                break;
        }

        document.add(tabla);
    }

    /**
     * Agrega los indicadores destacados del reporte.
     *
     * @param document documento PDF.
     * @param reporte reporte generado.
     * @throws Exception si no se puede agregar la tabla.
     */
    private void agregarIndicadores(Document document, ReporteDTO reporte) throws Exception {
        MetricasReporteDTO metricas = obtenerMetricas(reporte);

        Paragraph titulo = crearTituloSeccion("Indicadores del reporte");
        titulo.setSpacingBefore(18);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{45, 55});

        switch (obtenerTipoReporte(reporte)) {
            case VENTAS_MEMBRESIAS:
                agregarFila(tabla, "Plan más vendido", valor(metricas.getTipoMembresiaMasVendida()));
                agregarFila(tabla, "Sucursal con más ventas", valor(metricas.getSucursalConMasVentas()));
                break;

            case INGRESOS:
                agregarFila(tabla, "Principal fuente comercial", valor(metricas.getTipoMembresiaMasVendida()));
                agregarFila(tabla, "Amenidad con mayor presencia", valor(metricas.getAmenidadMasSolicitada()));
                break;

            case POR_SUCURSAL:
                agregarFila(tabla, "Sucursal mejor posicionada", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Membresía más vendida", valor(metricas.getTipoMembresiaMasVendida()));
                agregarFila(tabla, "Amenidad más solicitada", valor(metricas.getAmenidadMasSolicitada()));
                break;

            case DESEMPENO_ENTRENADORES:
                agregarFila(tabla, "Entrenador con más clientes", valor(metricas.getEntrenadorConMasClientes()));
                agregarFila(tabla, "Sucursal relacionada", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Amenidad relacionada", valor(metricas.getAmenidadMasSolicitada()));
                break;

            case GENERAL:
            default:
                agregarFila(tabla, "Sucursal con más ventas", valor(metricas.getSucursalConMasVentas()));
                agregarFila(tabla, "Entrenador con más clientes", valor(metricas.getEntrenadorConMasClientes()));
                agregarFila(tabla, "Amenidad más solicitada", valor(metricas.getAmenidadMasSolicitada()));
                agregarFila(tabla, "Membresía más vendida", valor(metricas.getTipoMembresiaMasVendida()));
                break;
        }

        document.add(tabla);
    }

    /**
     * Agrega una tabla de detalle del reporte.
     *
     * Actualmente se genera con base en las métricas del reporte. Más adelante
     * esta sección puede llenarse con registros reales provenientes de pagos,
     * membresías, sucursales, entrenadores o amenidades.
     *
     * @param document documento PDF.
     * @param reporte reporte generado.
     * @throws Exception si no se puede agregar el detalle.
     */
    private void agregarDetalle(Document document, ReporteDTO reporte) throws Exception {
        MetricasReporteDTO metricas = obtenerMetricas(reporte);

        Paragraph titulo = crearTituloSeccion("Detalle del reporte");
        titulo.setSpacingBefore(18);
        document.add(titulo);

        PdfPTable tabla = new PdfPTable(3);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{40, 30, 30});

        agregarCeldaHeader(tabla, "Concepto");
        agregarCeldaHeader(tabla, "Dato");
        agregarCeldaHeader(tabla, "Resultado");

        switch (obtenerTipoReporte(reporte)) {
            case VENTAS_MEMBRESIAS:
                agregarFilaDetalle(tabla, "Ventas de membresías", String.valueOf(metricas.getMembresiasVendidas()), "Membresías registradas");
                agregarFilaDetalle(tabla, "Plan más vendido", valor(metricas.getTipoMembresiaMasVendida()), "Mayor demanda");
                agregarFilaDetalle(tabla, "Sucursal destacada", valor(metricas.getSucursalConMasVentas()), "Mayor venta");
                break;

            case INGRESOS:
                agregarFilaDetalle(tabla, "Ingresos totales", "$" + metricas.getTotalIngresos(), "Ingresos del periodo");
                agregarFilaDetalle(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()), "Ventas asociadas");
                agregarFilaDetalle(tabla, "Amenidad destacada", valor(metricas.getAmenidadMasSolicitada()), "Mayor presencia");
                break;

            case POR_SUCURSAL:
                agregarFilaDetalle(tabla, "Sucursal líder", valor(metricas.getSucursalConMasVentas()), "Mejor desempeño");
                agregarFilaDetalle(tabla, "Ingresos generados", "$" + metricas.getTotalIngresos(), "Total del periodo");
                agregarFilaDetalle(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()), "Volumen comercial");
                break;

            case DESEMPENO_ENTRENADORES:
                agregarFilaDetalle(tabla, "Entrenador destacado", valor(metricas.getEntrenadorConMasClientes()), "Mayor atención");
                agregarFilaDetalle(tabla, "Sucursal relacionada", valor(metricas.getSucursalConMasVentas()), "Ubicación asociada");
                agregarFilaDetalle(tabla, "Base de medición", "Citas", "Confirmadas/completadas");
                break;

            case GENERAL:
            default:
                agregarFilaDetalle(tabla, "Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas()), "$" + metricas.getTotalIngresos());
                agregarFilaDetalle(tabla, "Renovaciones", String.valueOf(metricas.getRenovaciones()), "Registradas");
                agregarFilaDetalle(tabla, "Nuevos socios", String.valueOf(metricas.getNuevosSocios()), "Registrados");
                break;
        }

        document.add(tabla);
    }

    /**
     * Agrega el pie del documento.
     *
     * @param document documento PDF.
     * @throws Exception si no se puede agregar el contenido.
     */
    private void agregarPie(Document document) throws Exception {
        Paragraph pie = new Paragraph(
                "Reporte generado por SteelCore Fitness",
                FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY)
        );
        pie.setAlignment(Element.ALIGN_CENTER);
        pie.setSpacingBefore(24);
        document.add(pie);
    }

    /**
     * Crea un título para una sección del PDF.
     *
     * @param texto texto del título.
     * @return párrafo configurado como título de sección.
     */
    private Paragraph crearTituloSeccion(String texto) {
        Paragraph titulo = new Paragraph(
                texto,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK)
        );
        titulo.setSpacingAfter(8);
        return titulo;
    }

    /**
     * Agrega una fila de dos columnas a una tabla.
     *
     * @param tabla tabla donde se agregará la fila.
     * @param campo nombre del campo.
     * @param valor valor del campo.
     */
    private void agregarFila(PdfPTable tabla, String campo, String valor) {
        PdfPCell celdaCampo = new PdfPCell(new Phrase(
                campo,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK)
        ));

        PdfPCell celdaValor = new PdfPCell(new Phrase(
                valor(valor),
                FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY)
        ));

        celdaCampo.setPadding(8);
        celdaValor.setPadding(8);

        celdaCampo.setBackgroundColor(new Color(240, 240, 245));
        celdaValor.setBackgroundColor(Color.WHITE);

        celdaCampo.setBorder(Rectangle.BOX);
        celdaValor.setBorder(Rectangle.BOX);

        celdaCampo.setBorderColor(Color.LIGHT_GRAY);
        celdaValor.setBorderColor(Color.LIGHT_GRAY);

        tabla.addCell(celdaCampo);
        tabla.addCell(celdaValor);
    }

    /**
     * Agrega una celda de encabezado para una tabla de detalle.
     *
     * @param tabla tabla donde se agregará la celda.
     * @param texto texto de la celda.
     */
    private void agregarCeldaHeader(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(
                texto,
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE)
        ));

        celda.setPadding(8);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor(new Color(70, 61, 140));

        tabla.addCell(celda);
    }

    /**
     * Agrega una fila de detalle de tres columnas.
     *
     * @param tabla tabla donde se agregará la fila.
     * @param concepto concepto del registro.
     * @param cantidad cantidad relacionada.
     * @param resultado resultado o descripción.
     */
    private void agregarFilaDetalle(PdfPTable tabla, String concepto, String cantidad, String resultado) {
        agregarCeldaNormal(tabla, concepto);
        agregarCeldaNormal(tabla, cantidad);
        agregarCeldaNormal(tabla, resultado);
    }

    /**
     * Agrega una celda normal a una tabla.
     *
     * @param tabla tabla donde se agregará la celda.
     * @param texto texto de la celda.
     */
    private void agregarCeldaNormal(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(
                valor(texto),
                FontFactory.getFont(FontFactory.HELVETICA, 10, Color.DARK_GRAY)
        ));

        celda.setPadding(8);
        celda.setBorderColor(Color.LIGHT_GRAY);

        tabla.addCell(celda);
    }

    /**
     * Valida que el reporte exista antes de generar el PDF.
     *
     * @param reporte reporte a validar.
     */
    private void validarReporte(ReporteDTO reporte) {
        if (reporte == null) {
            throw new IllegalArgumentException("No hay un reporte generado.");
        }

        if (!reporte.isTieneDatos()) {
            throw new IllegalArgumentException("El reporte no contiene datos.");
        }
    }

    /**
     * Obtiene las métricas del reporte. Si el reporte no contiene métricas,
     * regresa un objeto vacío para evitar errores de nulos.
     *
     * @param reporte reporte generado.
     * @return métricas del reporte.
     */
    private MetricasReporteDTO obtenerMetricas(ReporteDTO reporte) {
        if (reporte != null && reporte.getMetricas() != null) {
            return reporte.getMetricas();
        }

        return new MetricasReporteDTO();
    }

    /**
     * Regresa un texto seguro para mostrar en el PDF.
     *
     * @param texto texto original.
     * @return texto validado o "Todos" si está vacío.
     */
    private String valor(String texto) {
        return texto == null || texto.isBlank() ? "Todos" : texto;
    }

    /**
     * Crea un nombre automático para el archivo PDF.
     *
     * @param reporte reporte generado.
     * @return nombre de archivo para el PDF.
     */
    private String crearNombreArchivo(ReporteDTO reporte) {
        String tipo = "reporte";

        if (reporte != null && reporte.getTipoReporte() != null) {
            tipo = reporte.getTipoReporte().name().toLowerCase();
        }

        return tipo + "_" + LocalDateTime.now().format(FMT_ARCHIVO) + ".pdf";
    }

    /**
     * Obtiene el nombre legible del tipo de reporte.
     *
     * @param tipo tipo de reporte.
     * @return nombre del reporte.
     */
    private String obtenerNombreReporte(TipoReporteDTO tipo) {
        if (tipo == null) {
            return "Reporte General";
        }

        switch (tipo) {
            case GENERAL:
                return "Reporte General";
            case VENTAS_MEMBRESIAS:
                return "Reporte de Ventas de Membresías";
            case INGRESOS:
                return "Reporte de Ingresos";
            case POR_SUCURSAL:
                return "Reporte por Sucursal";
            case DESEMPENO_ENTRENADORES:
                return "Reporte de Desempeño de Entrenadores";
            default:
                return "Reporte General";
        }
    }

    private TipoReporteDTO obtenerTipoReporte(ReporteDTO reporte) {
        if (reporte == null || reporte.getTipoReporte() == null) {
            return TipoReporteDTO.GENERAL;
        }

        return reporte.getTipoReporte();
    }

    private boolean usaSucursal(ReporteDTO reporte) {
        TipoReporteDTO tipo = obtenerTipoReporte(reporte);

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private boolean usaTipoMembresia(ReporteDTO reporte) {
        TipoReporteDTO tipo = obtenerTipoReporte(reporte);

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.POR_SUCURSAL;
    }

    private boolean usaEntrenador(ReporteDTO reporte) {
        TipoReporteDTO tipo = obtenerTipoReporte(reporte);

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private boolean usaAmenidad(ReporteDTO reporte) {
        TipoReporteDTO tipo = obtenerTipoReporte(reporte);

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.POR_SUCURSAL
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private String obtenerTituloMetricas(ReporteDTO reporte) {
        switch (obtenerTipoReporte(reporte)) {
            case VENTAS_MEMBRESIAS:
                return "Métricas de ventas de membresías";
            case INGRESOS:
                return "Métricas de ingresos";
            case POR_SUCURSAL:
                return "Métricas por sucursal";
            case DESEMPENO_ENTRENADORES:
                return "Métricas de desempeño de entrenadores";
            case GENERAL:
            default:
                return "Métricas generales";
        }
    }
}
