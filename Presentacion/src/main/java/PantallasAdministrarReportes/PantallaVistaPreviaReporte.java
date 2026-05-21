/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.TipoReporteDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Panel que muestra la vista previa del reporte generado.
 *
 * La vista cambia según el tipo de reporte seleccionado, mostrando solamente
 * las métricas, filtros e indicadores que corresponden a ese reporte.
 *
 * @author Noelia E.N.
 */
public class PantallaVistaPreviaReporte extends JPanel {

    public static final int ANCHO_CARTA = 900;
    public static final int ALTO_CARTA = 1200;

    private final ReporteDTO reporte;

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor de la vista previa del reporte.
     *
     * @param reporte reporte generado.
     */
    public PantallaVistaPreviaReporte(ReporteDTO reporte) {
        this.reporte = reporte;
        inicializarComponentes();
    }

    /**
     * Inicializa la hoja del reporte.
     */
    private void inicializarComponentes() {
        setPreferredSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setMinimumSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));

        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(44, 56, 44, 56));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearPiePagina(), BorderLayout.SOUTH);
    }

    /**
     * Crea el encabezado del reporte.
     *
     * @return panel del encabezado.
     */
    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 24, 0));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel tituloEmpresa = new JLabel("STEELCORE FITNESS");
        tituloEmpresa.setFont(new Font("Segoe UI", Font.BOLD, 30));
        tituloEmpresa.setForeground(new Color(35, 35, 35));

        JLabel tituloReporte = new JLabel(obtenerNombreReporte());
        tituloReporte.setFont(new Font("Segoe UI", Font.BOLD, 23));
        tituloReporte.setForeground(new Color(55, 55, 55));

        JLabel subtitulo = new JLabel("Administración Comercial y Reportes Financieros");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(100, 100, 100));

        textos.add(tituloEmpresa);
        textos.add(Box.createVerticalStrut(8));
        textos.add(tituloReporte);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        panel.add(textos, BorderLayout.CENTER);
        panel.add(crearCajaPeriodo(), BorderLayout.EAST);

        return panel;
    }

    /**
     * Crea la caja donde se muestra el periodo del reporte.
     *
     * @return panel del periodo.
     */
    private JPanel crearCajaPeriodo() {
        JPanel caja = new JPanel();
        caja.setOpaque(false);
        caja.setLayout(new BoxLayout(caja, BoxLayout.Y_AXIS));
        caja.setBorder(new EmptyBorder(10, 16, 10, 16));

        FiltrosReporteDTO filtros = obtenerFiltros();

        String desde = filtros.getFechaInicio() != null
                ? filtros.getFechaInicio().format(FMT_FECHA)
                : "—";

        String hasta = filtros.getFechaFin() != null
                ? filtros.getFechaFin().format(FMT_FECHA)
                : "—";

        JLabel lblPeriodo = new JLabel("Periodo");
        lblPeriodo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPeriodo.setForeground(new Color(55, 55, 55));

        JLabel lblDesde = new JLabel("Desde: " + desde);
        lblDesde.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDesde.setForeground(new Color(85, 85, 85));

        JLabel lblHasta = new JLabel("Hasta: " + hasta);
        lblHasta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblHasta.setForeground(new Color(85, 85, 85));

        caja.add(lblPeriodo);
        caja.add(Box.createVerticalStrut(6));
        caja.add(lblDesde);
        caja.add(lblHasta);

        return caja;
    }

    /**
     * Crea el contenido central del reporte.
     *
     * @return panel de contenido.
     */
    private JPanel crearContenido() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BorderLayout(0, 24));

        contenido.add(crearSeccionFiltros(), BorderLayout.NORTH);
        contenido.add(crearSeccionMetricas(), BorderLayout.CENTER);
        contenido.add(crearSeccionIndicadores(), BorderLayout.SOUTH);

        return contenido;
    }

    /**
     * Crea la sección de filtros aplicados.
     *
     * @return panel de filtros.
     */
    private JPanel crearSeccionFiltros() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 12));
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(12, 0, 10, 0));

        JLabel titulo = crearTituloSeccion("Filtros aplicados");

        JPanel panel = new JPanel(new GridLayout(0, 2, 14, 14));
        panel.setOpaque(false);

        FiltrosReporteDTO f = obtenerFiltros();

        panel.add(crearDato("Fecha inicio", f.getFechaInicio() != null ? f.getFechaInicio().format(FMT_FECHA) : "Todos"));
        panel.add(crearDato("Fecha fin", f.getFechaFin() != null ? f.getFechaFin().format(FMT_FECHA) : "Todos"));

        if (usaSucursal()) {
            panel.add(crearDato("Sucursal", f.getSucursal()));
        }

        if (usaTipoMembresia()) {
            panel.add(crearDato("Membresía", f.getTipoMembresia()));
        }

        if (usaEntrenador()) {
            panel.add(crearDato("Entrenador", f.getEntrenador()));
        }

        if (usaAmenidad()) {
            panel.add(crearDato("Amenidad", f.getAmenidad()));
        }

        contenedor.add(titulo, BorderLayout.NORTH);
        contenedor.add(panel, BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea la sección de métricas principales según el tipo de reporte.
     *
     * @return panel de métricas.
     */
    private JPanel crearSeccionMetricas() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 12));
        contenedor.setOpaque(false);

        JLabel titulo = crearTituloSeccion(obtenerTituloMetricas());

        JPanel panel = new JPanel(new GridLayout(0, 2, 16, 16));
        panel.setOpaque(false);

        MetricasReporteDTO m = obtenerMetricas();

        switch (obtenerTipoReporte()) {
            case VENTAS_MEMBRESIAS:
                panel.add(crearMetricaGrande("Membresías vendidas", String.valueOf(m.getMembresiasVendidas())));
                panel.add(crearMetricaGrande("Plan más vendido", valor(m.getTipoMembresiaMasVendida())));
                panel.add(crearMetricaGrande("Sucursal con más ventas", valor(m.getSucursalConMasVentas())));
                panel.add(crearMetricaGrande("Ingresos por ventas", "$" + m.getTotalIngresos()));
                break;

            case INGRESOS:
                panel.add(crearMetricaGrande("Ingresos totales", "$" + m.getTotalIngresos()));
                panel.add(crearMetricaGrande("Membresías vendidas", String.valueOf(m.getMembresiasVendidas())));
                panel.add(crearMetricaGrande("Plan con más ingresos", valor(m.getTipoMembresiaMasVendida())));
                panel.add(crearMetricaGrande("Amenidad destacada", valor(m.getAmenidadMasSolicitada())));
                break;

            case POR_SUCURSAL:
                panel.add(crearMetricaGrande("Sucursal con más ventas", valor(m.getSucursalConMasVentas())));
                panel.add(crearMetricaGrande("Ingresos generados", "$" + m.getTotalIngresos()));
                panel.add(crearMetricaGrande("Membresías vendidas", String.valueOf(m.getMembresiasVendidas())));
                panel.add(crearMetricaGrande("Plan más vendido", valor(m.getTipoMembresiaMasVendida())));
                break;

            case DESEMPENO_ENTRENADORES:
                panel.add(crearMetricaGrande("Entrenador destacado", valor(m.getEntrenadorConMasClientes())));
                panel.add(crearMetricaGrande("Sucursal relacionada", valor(m.getSucursalConMasVentas())));
                panel.add(crearMetricaGrande("Amenidad relacionada", valor(m.getAmenidadMasSolicitada())));
                panel.add(crearMetricaGrande("Periodo analizado", "Citas confirmadas/completadas"));
                break;

            case GENERAL:
            default:
                panel.add(crearMetricaGrande("Ingresos totales", "$" + m.getTotalIngresos()));
                panel.add(crearMetricaGrande("Membresías vendidas", String.valueOf(m.getMembresiasVendidas())));
                panel.add(crearMetricaGrande("Nuevos socios", String.valueOf(m.getNuevosSocios())));
                panel.add(crearMetricaGrande("Renovaciones", String.valueOf(m.getRenovaciones())));
                break;
        }

        contenedor.add(titulo, BorderLayout.NORTH);
        contenedor.add(panel, BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea la sección de indicadores según el tipo de reporte.
     *
     * @return panel de indicadores.
     */
    private JPanel crearSeccionIndicadores() {
        JPanel contenedor = new JPanel(new BorderLayout(0, 12));
        contenedor.setOpaque(false);

        JLabel titulo = crearTituloSeccion("Indicadores del reporte");

        JPanel panel = new JPanel(new GridLayout(0, 2, 14, 14));
        panel.setOpaque(false);

        MetricasReporteDTO m = obtenerMetricas();

        switch (obtenerTipoReporte()) {
            case VENTAS_MEMBRESIAS:
                panel.add(crearIndicador("Plan más vendido", m.getTipoMembresiaMasVendida()));
                panel.add(crearIndicador("Sucursal con más ventas", m.getSucursalConMasVentas()));
                break;

            case INGRESOS:
                panel.add(crearIndicador("Principal fuente comercial", m.getTipoMembresiaMasVendida()));
                panel.add(crearIndicador("Amenidad con mayor presencia", m.getAmenidadMasSolicitada()));
                break;

            case POR_SUCURSAL:
                panel.add(crearIndicador("Sucursal mejor posicionada", m.getSucursalConMasVentas()));
                panel.add(crearIndicador("Membresía más vendida", m.getTipoMembresiaMasVendida()));
                panel.add(crearIndicador("Amenidad más solicitada", m.getAmenidadMasSolicitada()));
                break;

            case DESEMPENO_ENTRENADORES:
                panel.add(crearIndicador("Entrenador con más clientes", m.getEntrenadorConMasClientes()));
                panel.add(crearIndicador("Sucursal relacionada", m.getSucursalConMasVentas()));
                panel.add(crearIndicador("Amenidad relacionada", m.getAmenidadMasSolicitada()));
                break;

            case GENERAL:
            default:
                panel.add(crearIndicador("Sucursal con más ventas", m.getSucursalConMasVentas()));
                panel.add(crearIndicador("Entrenador con más clientes", m.getEntrenadorConMasClientes()));
                panel.add(crearIndicador("Amenidad más solicitada", m.getAmenidadMasSolicitada()));
                panel.add(crearIndicador("Membresía más vendida", m.getTipoMembresiaMasVendida()));
                break;
        }

        contenedor.add(titulo, BorderLayout.NORTH);
        contenedor.add(panel, BorderLayout.CENTER);

        return contenedor;
    }

    /**
     * Crea un card de dato de filtro.
     *
     * @param etiqueta etiqueta del dato.
     * @param valor valor del dato.
     * @return panel del dato.
     */
    private JPanel crearDato(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(245, 245, 250));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel lblEtiqueta = new JLabel(etiqueta.toUpperCase());
        lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEtiqueta.setForeground(new Color(115, 115, 115));

        JLabel lblValor = new JLabel(valor(valor));
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblValor.setForeground(new Color(40, 40, 40));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(6));
        card.add(lblValor);

        return card;
    }

    /**
     * Crea una métrica grande.
     *
     * @param etiqueta etiqueta.
     * @param valor valor.
     * @return panel de métrica.
     */
    private JPanel crearMetricaGrande(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(238, 238, 248));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblEtiqueta.setForeground(new Color(80, 80, 80));

        JLabel lblValor = new JLabel(valor(valor));
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 23));
        lblValor.setForeground(new Color(45, 45, 45));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(10));
        card.add(lblValor);

        return card;
    }

    /**
     * Crea un indicador.
     *
     * @param etiqueta etiqueta.
     * @param valor valor.
     * @return panel de indicador.
     */
    private JPanel crearIndicador(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(250, 250, 252));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(16, 18, 16, 18));

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblEtiqueta.setForeground(new Color(90, 90, 90));

        JLabel lblValor = new JLabel(valor(valor));
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValor.setForeground(new Color(45, 45, 45));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(8));
        card.add(lblValor);

        return card;
    }

    /**
     * Crea título de sección.
     *
     * @param texto texto.
     * @return etiqueta de título.
     */
    private JLabel crearTituloSeccion(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(new Color(45, 45, 45));
        return titulo;
    }

    /**
     * Crea el pie de página.
     *
     * @return panel del pie.
     */
    private JPanel crearPiePagina() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.setBorder(new EmptyBorder(24, 0, 0, 0));

        JLabel izquierda = new JLabel("Reporte generado por SteelCore Fitness");
        izquierda.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        izquierda.setForeground(new Color(120, 120, 120));

        JLabel derecha = new JLabel("Página 1 de 1");
        derecha.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        derecha.setForeground(new Color(120, 120, 120));

        pie.add(izquierda, BorderLayout.WEST);
        pie.add(derecha, BorderLayout.EAST);

        return pie;
    }

    private FiltrosReporteDTO obtenerFiltros() {
        if (reporte != null && reporte.getFiltros() != null) {
            return reporte.getFiltros();
        }

        return new FiltrosReporteDTO();
    }

    private MetricasReporteDTO obtenerMetricas() {
        if (reporte != null && reporte.getMetricas() != null) {
            return reporte.getMetricas();
        }

        return new MetricasReporteDTO();
    }

    private TipoReporteDTO obtenerTipoReporte() {
        if (reporte == null || reporte.getTipoReporte() == null) {
            return TipoReporteDTO.GENERAL;
        }

        return reporte.getTipoReporte();
    }

    private boolean usaSucursal() {
        TipoReporteDTO tipo = obtenerTipoReporte();

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private boolean usaTipoMembresia() {
        TipoReporteDTO tipo = obtenerTipoReporte();

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.POR_SUCURSAL;
    }

    private boolean usaEntrenador() {
        TipoReporteDTO tipo = obtenerTipoReporte();

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private boolean usaAmenidad() {
        TipoReporteDTO tipo = obtenerTipoReporte();

        return tipo == TipoReporteDTO.GENERAL
                || tipo == TipoReporteDTO.INGRESOS
                || tipo == TipoReporteDTO.POR_SUCURSAL
                || tipo == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    private String obtenerTituloMetricas() {
        switch (obtenerTipoReporte()) {
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

    private String obtenerNombreReporte() {
        switch (obtenerTipoReporte()) {
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

    private String valor(String texto) {
        return texto == null || texto.isBlank() ? "Todos" : texto;
    }
}
