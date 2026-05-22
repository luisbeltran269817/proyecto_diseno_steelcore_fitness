/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import Controladores.IControladorAplicacion;
import ControladoresReportes.IControlReportes;
import Utilerias.Boton;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtosReportes.ReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class PantallaImprimirReporte extends PantallaBase {

    private final IControlReportes controlReportes;
    private final ReporteDTO reporte;

    private PantallaVistaPreviaReporte panelHojaReporte;

    private CampoTexto campoDestino;
    private CampoTexto campoPaginas;
    private CampoTexto campoCopias;

    public PantallaImprimirReporte(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion,
            ReporteDTO reporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.reporte = reporte;
        setTitle("Imprimir Reporte");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearScrollReporte(), BorderLayout.CENTER);
        root.add(crearPanelImpresion(), BorderLayout.EAST);

        add(root);
    }

    private JScrollPane crearScrollReporte() {
        panelHojaReporte = new PantallaVistaPreviaReporte(reporte);

        JScrollPane scroll = new JScrollPane(panelHojaReporte);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(760, 620));
        scroll.getViewport().setBackground(Colores.FONDO_PRINCIPAL);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        return scroll;
    }

    private JPanel crearPanelImpresion() {
        JPanel panel = crearCard(360, 0);
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(30, 28, 30, 28));

//        JLabel titulo = new JLabel("Opciones de impresión");
//        titulo.setFont(Colores.FUENTE_SUBTITULO);
//        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

//        JPanel campos = new JPanel(new GridLayout(3, 1, 0, 18));
//        campos.setOpaque(false);
//
//        campoDestino = new CampoTexto("Destino", "Impresora principal");
//        campoPaginas = new CampoTexto("Páginas", "Todas");
//        campoCopias = new CampoTexto("Copias", "1");
//
//        campos.add(campoDestino);
//        campos.add(campoPaginas);
//        campos.add(campoCopias);

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));

        Boton btnImprimir = crearBoton("Imprimir", Boton.Variante.PRIMARIO);
        btnImprimir.setPreferredSize(new Dimension(220, 50));
        btnImprimir.addActionListener(e -> controlReportes.imprimir());

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.setPreferredSize(new Dimension(220, 50));
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaOpcionesReporte());

        botones.add(btnImprimir);
        botones.add(Box.createVerticalStrut(14));
        botones.add(btnCancelar);

//        panel.add(titulo, BorderLayout.NORTH);
//        panel.add(campos, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }
}
