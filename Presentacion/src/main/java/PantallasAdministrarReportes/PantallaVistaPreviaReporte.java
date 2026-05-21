/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import Utilerias.Colores;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.TipoReporteDTO;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PantallaVistaPreviaReporte extends JPanel {

    /*
     * Tamaño carta proporcional
     * 612 x 792 equivale a 8.5 x 11 pulgadas
     */
    public static final int ANCHO_CARTA = 612;
    public static final int ALTO_CARTA = 792;

    private final ReporteDTO reporte;

    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PantallaVistaPreviaReporte(ReporteDTO reporte) {
        this.reporte = reporte;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setPreferredSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setMinimumSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));
        setMaximumSize(new Dimension(ANCHO_CARTA, ALTO_CARTA));

        
        //Fondo blanco porque esta hoja se va a imprimir/exportar.
        setBackground(Color.WHITE);
        setOpaque(true);
        setLayout(new BorderLayout(0, 0));
        setBorder(new EmptyBorder(32, 38, 32, 38));

        add(crearEncabezado(), BorderLayout.NORTH);
        add(crearContenido(), BorderLayout.CENTER);
        add(crearPiePagina(), BorderLayout.SOUTH);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(0, 0, 18, 0));

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));

        JLabel tituloEmpresa = new JLabel("STEELCORE FITNESS");
        tituloEmpresa.setFont(new Font("Segoe UI", Font.BOLD, 20));
        tituloEmpresa.setForeground(new Color(35, 35, 35));

        JLabel tituloReporte = new JLabel(obtenerNombreReporte());
        tituloReporte.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tituloReporte.setForeground(new Color(55, 55, 55));

        JLabel subtitulo = new JLabel("Administración Comercial y Reportes Financieros");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subtitulo.setForeground(new Color(100, 100, 100));

        textos.add(tituloEmpresa);
        textos.add(Box.createVerticalStrut(4));
        textos.add(tituloReporte);
        textos.add(Box.createVerticalStrut(3));
        textos.add(subtitulo);

        panel.add(textos, BorderLayout.CENTER);
        panel.add(crearCajaPeriodo(), BorderLayout.EAST);

        return panel;
    }

    private JPanel crearCajaPeriodo() {
        JPanel caja = new JPanel();
        caja.setOpaque(false);
        caja.setLayout(new BoxLayout(caja, BoxLayout.Y_AXIS));
        caja.setBorder(new EmptyBorder(6, 12, 6, 12));

        FiltrosReporteDTO filtros = reporte != null ? reporte.getFiltros() : null;

        String desde = "—";
        String hasta = "—";

        if (filtros != null && filtros.getFechaInicio() != null) {
            desde = filtros.getFechaInicio().format(FMT_FECHA);
        }

        if (filtros != null && filtros.getFechaFin() != null) {
            hasta = filtros.getFechaFin().format(FMT_FECHA);
        }

        JLabel lblPeriodo = new JLabel("Periodo");
        lblPeriodo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblPeriodo.setForeground(new Color(55, 55, 55));

        JLabel lblDesde = new JLabel("Desde: " + desde);
        lblDesde.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblDesde.setForeground(new Color(85, 85, 85));

        JLabel lblHasta = new JLabel("Hasta: " + hasta);
        lblHasta.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblHasta.setForeground(new Color(85, 85, 85));

        caja.add(lblPeriodo);
        caja.add(Box.createVerticalStrut(4));
        caja.add(lblDesde);
        caja.add(lblHasta);

        return caja;
    }

    private JPanel crearContenido() {
        JPanel contenido = new JPanel();
        contenido.setOpaque(false);
        contenido.setLayout(new BorderLayout(0, 18));

        contenido.add(crearSeccionFiltros(), BorderLayout.NORTH);
        contenido.add(crearSeccionPrincipal(), BorderLayout.CENTER);
        contenido.add(crearSeccionIndicadores(), BorderLayout.SOUTH);

        return contenido;
    }

    private JPanel crearSeccionFiltros() {
        JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(12, 0, 8, 0));

        FiltrosReporteDTO f = reporte != null ? reporte.getFiltros() : null;

        panel.add(crearDatoFiltro("Sucursal", f != null ? f.getSucursal() : ""));
        panel.add(crearDatoFiltro("Membresía", f != null ? f.getTipoMembresia() : ""));
        panel.add(crearDatoFiltro("Entrenador", f != null ? f.getEntrenador() : ""));
        panel.add(crearDatoFiltro("Amenidad", f != null ? f.getAmenidad() : ""));
        panel.add(crearDatoFiltro("Tipo de reporte", obtenerNombreReporte()));
        panel.add(crearDatoFiltro("Estado", reporte != null && reporte.isTieneDatos() ? "Con datos" : "Sin datos"));

        return panel;
    }

    private JPanel crearDatoFiltro(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(245, 245, 250));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(8, 10, 8, 10));

        JLabel lblEtiqueta = new JLabel(etiqueta.toUpperCase());
        lblEtiqueta.setFont(new Font("Segoe UI", Font.BOLD, 9));
        lblEtiqueta.setForeground(new Color(115, 115, 115));

        JLabel lblValor = new JLabel(valor == null || valor.isBlank() ? "Todos" : valor);
        lblValor.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblValor.setForeground(new Color(40, 40, 40));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(4));
        card.add(lblValor);

        return card;
    }

    private JPanel crearSeccionPrincipal() {
        JPanel panel = new JPanel(new BorderLayout(18, 0));
        panel.setOpaque(false);

        panel.add(crearResumenMetricas(), BorderLayout.WEST);
        //panel.add(crearPanelGrafica(), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearResumenMetricas() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 12));
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(190, 0));

        MetricasReporteDTO m = obtenerMetricas();

        panel.add(crearMetricaGrande("Ingresos totales", "$" + m.getTotalIngresos()));
        panel.add(crearMetricaGrande("Membresías vendidas", String.valueOf(m.getMembresiasVendidas())));
        panel.add(crearMetricaGrande("Nuevos socios", String.valueOf(m.getNuevosSocios())));
        panel.add(crearMetricaGrande("Renovaciones", String.valueOf(m.getRenovaciones())));

        return panel;
    }

    private JPanel crearMetricaGrande(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(238, 238, 248));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEtiqueta.setForeground(new Color(80, 80, 80));

        JLabel lblValor = new JLabel(valor == null ? "—" : valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblValor.setForeground(new Color(45, 45, 45));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(8));
        card.add(lblValor);

        return card;
    }

//    private JPanel crearPanelGrafica() {
//        JPanel panel = new JPanel(new BorderLayout(0, 12));
//        panel.setOpaque(false);
//
//        JLabel titulo = new JLabel("Comparación de ingresos por periodo");
//        titulo.setFont(new Font("Segoe UI", Font.BOLD, 13));
//        titulo.setForeground(new Color(45, 45, 45));
//
//        PanelGraficaBarras grafica = new PanelGraficaBarras();
//
//        panel.add(titulo, BorderLayout.NORTH);
//        panel.add(grafica, BorderLayout.CENTER);
//
//        return panel;
//    }

    private JPanel crearSeccionIndicadores() {
        JPanel panel = new JPanel(new BorderLayout(0, 14));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Indicadores destacados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setForeground(new Color(45, 45, 45));

        JPanel indicadores = new JPanel(new GridLayout(2, 2, 12, 12));
        indicadores.setOpaque(false);

        MetricasReporteDTO m = obtenerMetricas();

        indicadores.add(crearIndicador("Sucursal con más ventas", m.getSucursalConMasVentas()));
        indicadores.add(crearIndicador("Entrenador con más clientes", m.getEntrenadorConMasClientes()));
        indicadores.add(crearIndicador("Amenidad más solicitada", m.getAmenidadMasSolicitada()));
        indicadores.add(crearIndicador("Membresía más vendida", m.getTipoMembresiaMasVendida()));

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(indicadores, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearIndicador(String etiqueta, String valor) {
        JPanel card = new JPanel();
        card.setBackground(new Color(250, 250, 252));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(10, 12, 10, 12));

        JLabel lblEtiqueta = new JLabel(etiqueta);
        lblEtiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEtiqueta.setForeground(new Color(90, 90, 90));

        JLabel lblValor = new JLabel(valor == null || valor.isBlank() ? "—" : valor);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblValor.setForeground(new Color(45, 45, 45));

        card.add(lblEtiqueta);
        card.add(Box.createVerticalStrut(6));
        card.add(lblValor);

        return card;
    }

    private JPanel crearPiePagina() {
        JPanel pie = new JPanel(new BorderLayout());
        pie.setOpaque(false);
        pie.setBorder(new EmptyBorder(18, 0, 0, 0));

        JLabel izquierda = new JLabel("Reporte generado por SteelCore Fitness");
        izquierda.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        izquierda.setForeground(new Color(120, 120, 120));

        JLabel derecha = new JLabel("Página 1 de 1");
        derecha.setFont(new Font("Segoe UI", Font.PLAIN, 9));
        derecha.setForeground(new Color(120, 120, 120));

        pie.add(izquierda, BorderLayout.WEST);
        pie.add(derecha, BorderLayout.EAST);

        return pie;
    }

    private MetricasReporteDTO obtenerMetricas() {
        if (reporte != null && reporte.getMetricas() != null) {
            return reporte.getMetricas();
        }

        return new MetricasReporteDTO();
    }

    private String obtenerNombreReporte() {
        if (reporte == null || reporte.getTipoReporte() == null) {
            return "Reporte General";
        }

        TipoReporteDTO tipo = reporte.getTipoReporte();

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

    /*
     * Gráfica simple dibujada
     * Despues los valores se van a reemplazar
     */
    private static class PanelGraficaBarras extends JPanel {

        private final int[] valores = {80, 120, 95, 150, 130};
        private final String[] etiquetas = {"Sem 1", "Sem 2", "Sem 3", "Sem 4", "Sem 5"};

        public PanelGraficaBarras() {
            setOpaque(false);
            setPreferredSize(new Dimension(0, 230));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();

            int margenIzq = 38;
            int margenDer = 20;
            int margenSup = 20;
            int margenInf = 36;

            int areaAncho = ancho - margenIzq - margenDer;
            int areaAlto = alto - margenSup - margenInf;

            g2.setColor(new Color(245, 245, 248));
            g2.fillRoundRect(0, 0, ancho, alto, 12, 12);

            g2.setColor(new Color(210, 210, 220));
            g2.setStroke(new BasicStroke(1f));

            // Eje vertical
            g2.drawLine(margenIzq, margenSup, margenIzq, margenSup + areaAlto);

            // Eje horizontal
            g2.drawLine(margenIzq, margenSup + areaAlto, margenIzq + areaAncho, margenSup + areaAlto);

            int max = 1;
            for (int v : valores) {
                if (v > max) {
                    max = v;
                }
            }

            int cantidad = valores.length;
            int espacio = areaAncho / cantidad;
            int anchoBarra = Math.max(28, espacio / 2);

            for (int i = 0; i < cantidad; i++) {
                int valor = valores[i];

                int barraAlto = (int) ((valor / (double) max) * (areaAlto - 20));
                int x = margenIzq + (i * espacio) + (espacio - anchoBarra) / 2;
                int y = margenSup + areaAlto - barraAlto;

                g2.setColor(new Color(120, 105, 190));
                g2.fillRoundRect(x, y, anchoBarra, barraAlto, 8, 8);

                g2.setColor(new Color(70, 70, 70));
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 9));
                g2.drawString(etiquetas[i], x - 2, margenSup + areaAlto + 18);

                g2.setFont(new Font("Segoe UI", Font.BOLD, 9));
                g2.drawString(String.valueOf(valor), x + 4, y - 5);
            }

            g2.dispose();
        }
    }
}
