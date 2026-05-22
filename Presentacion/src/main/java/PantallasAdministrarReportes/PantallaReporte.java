/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import Controladores.IControladorAplicacion;
import ControladoresReportes.IControlReportes;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtosReportes.ReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla donde se muestra la vista previa completa del reporte generado.
 *
 * Esta pantalla presenta el reporte dentro de un área con scroll para permitir
 * visualizar una hoja completa en tamaño amplio y legible.
 *
 * @author Noelia E.N.
 */
public class PantallaReporte extends PantallaBase {

    private final IControlReportes controlReportes;
    private final ReporteDTO reporte;
    private PantallaVistaPreviaReporte panelHojaReporte;

    /**
     * Constructor de la pantalla del reporte.
     *
     * @param controlReportes controlador del módulo de reportes.
     * @param controladorAplicacion controlador principal de la aplicación.
     * @param reporte reporte generado que se mostrará en pantalla.
     */
    public PantallaReporte(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion,
            ReporteDTO reporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.reporte = reporte;
        setTitle("Reporte Completo");
        inicializarComponentes();
        setVisible(true);
    }

    /**
     * Inicializa los componentes principales de la pantalla.
     */
    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(18, 18));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(20, 28, 20, 28));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearCentro(), BorderLayout.CENTER);
        root.add(crearAcciones(), BorderLayout.SOUTH);

        add(root);
    }

    /**
     * Crea el encabezado de la pantalla.
     *
     * @return panel de encabezado.
     */
    private JPanel crearHeader() {
        JPanel header = crearCard(0, 70);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(12, 28, 12, 28));

        JLabel titulo = new JLabel("REPORTE COMPLETO");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        header.add(titulo, BorderLayout.CENTER);

        return header;
    }

    /**
     * Crea la sección central donde se muestra la hoja del reporte.
     *
     * @return panel central con scroll.
     */
    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);

        panelHojaReporte = new PantallaVistaPreviaReporte(reporte);

        /*
         * Tamaño grande tipo hoja carta en pantalla.
         * Si la hoja se ve demasiado grande o pequeña, ajusta estos valores.
         */
        panelHojaReporte.setPreferredSize(new Dimension(900, 1200));
        panelHojaReporte.setMinimumSize(new Dimension(900, 1200));

        JScrollPane scroll = new JScrollPane(panelHojaReporte);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getViewport().setBackground(Colores.FONDO_PRINCIPAL);

        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel contenedorHoja = new JPanel(new BorderLayout());
        contenedorHoja.setOpaque(false);
        contenedorHoja.setBorder(new EmptyBorder(0, 0, 0, 0));
        contenedorHoja.add(scroll, BorderLayout.CENTER);

        centro.add(contenedorHoja, BorderLayout.CENTER);

        return centro;
    }

    /**
     * Crea los botones inferiores de la pantalla.
     *
     * @return panel de acciones.
     */
    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaFiltros());

        Boton btnConfirmar = crearBoton("Confirmar reporte", Boton.Variante.PRIMARIO);
        btnConfirmar.setPreferredSize(new Dimension(240, 50));
        btnConfirmar.addActionListener(e -> controlReportes.irAPantallaOpcionesReporte());

        acciones.add(btnCancelar, BorderLayout.WEST);
        acciones.add(btnConfirmar, BorderLayout.EAST);

        return acciones;
    }
}
