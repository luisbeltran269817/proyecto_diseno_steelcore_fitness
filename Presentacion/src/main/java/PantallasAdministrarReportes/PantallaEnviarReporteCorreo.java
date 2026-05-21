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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PantallaEnviarReporteCorreo extends PantallaBase {

    private final IControlReportes controlReportes;
    private final ReporteDTO reporte;

    private CampoTexto campoCorreo;

    public PantallaEnviarReporteCorreo(IControlReportes controlReportes,
                                       IControladorAplicacion controladorAplicacion,
                                       ReporteDTO reporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.reporte = reporte;
        setTitle("Enviar Reporte por Correo");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(28, 28));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearVistaPrevia(), BorderLayout.CENTER);
        root.add(crearPanelCorreo(), BorderLayout.EAST);

        add(root);
    }

    private JPanel crearVistaPrevia() {
        JPanel card = crearCard(760, 620);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(18, 18, 18, 18));
        card.add(new PantallaVistaPreviaReporte(reporte), BorderLayout.CENTER);
        return card;
    }

    private JPanel crearPanelCorreo() {
        JPanel panel = crearCard(390, 0);
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(new EmptyBorder(34, 30, 34, 30));

        JLabel titulo = new JLabel("Enviar por correo");
        titulo.setFont(Colores.FUENTE_SUBTITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

        campoCorreo = new CampoTexto("Correo destinatario", "correo@ejemplo.com");

        JPanel botones = new JPanel();
        botones.setOpaque(false);
        botones.setLayout(new BoxLayout(botones, BoxLayout.Y_AXIS));

        Boton btnEnviar = crearBoton("Enviar", Boton.Variante.PRIMARIO);
        btnEnviar.setPreferredSize(new Dimension(220, 50));
        btnEnviar.addActionListener(e -> controlReportes.enviar(campoCorreo.getValor()));

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.setPreferredSize(new Dimension(220, 50));
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaOpcionesReporte());

        botones.add(btnEnviar);
        botones.add(Box.createVerticalStrut(14));
        botones.add(btnCancelar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(campoCorreo, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }
}
