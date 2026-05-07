/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Noelia E.N
 */
public class PantallaTransaccionExitosa extends PantallaBase {

    public PantallaTransaccionExitosa(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Pago Exitoso - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);

        setContentPane(fondo);

        JPanel card = crearCard(560, 460);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(48, 52, 48, 52));

        JLabel ico = new JLabel("✓", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.BOLD, 56));
        ico.setForeground(new Color(94, 220, 153));
        ico.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Pago realizado con éxito", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(
                "<html><center>"
                + "Tu membresía ya se encuentra activa.<br>"
                + "Ahora puedes agendar tu cita de bienvenida."
                + "</center></html>",
                SwingConstants.CENTER);

        lblDescripcion.setFont(Colores.FUENTE_LABEL);
        lblDescripcion.setForeground(Colores.TEXTO_SECUNDARIO);
        lblDescripcion.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblPregunta = new JLabel(
                "<html><center>"
                + "¿Deseas agendar tu cita de bienvenida ahora?"
                + "</center></html>",
                SwingConstants.CENTER);

        lblPregunta.setFont(Colores.FUENTE_LABEL);
        lblPregunta.setForeground(Colores.TEXTO_PRINCIPAL);
        lblPregunta.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);

        Boton btnDespues = crearBoton(
                "En otro momento",
                Boton.Variante.SECUNDARIO
        );

        Boton btnAgendar = crearBoton(
                "Agendar ahora",
                Boton.Variante.PRIMARIO
        );

        btnDespues.addActionListener(e -> {
            dispose();
            controlador.irAPerfilUsuario();
        });

        btnAgendar.addActionListener(e -> {
            dispose();
            controlador.irASeleccionInstructor();
        });

        panelBtns.add(btnDespues);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnAgendar);

        card.add(ico);
        card.add(Box.createVerticalStrut(16));

        card.add(titulo);
        card.add(Box.createVerticalStrut(24));

        card.add(lblDescripcion);
        card.add(Box.createVerticalStrut(22));

        card.add(lblPregunta);

        card.add(Box.createVerticalGlue());

        card.add(panelBtns);

        fondo.add(card);
    }
}
