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
 * @author julian izaguirre
 */
public class PantallaTransaccionFallida extends PantallaBase {
     private final String causa;
 
    public PantallaTransaccionFallida(IControladorAplicacion controlador, String causa) {
        super(controlador);
        this.causa = (causa != null && !causa.isBlank()) ? causa : "Error desconocido.";
        setTitle("Transacción Rechazada - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(500, 420);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(48, 52, 48, 52));

        JLabel ico = new JLabel("✕", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.BOLD, 52));
        ico.setForeground(new Color(220, 80, 80));
        ico.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel titulo = new JLabel("Transacción Rechazada", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblDescripcion = new JLabel(
            "<html><center>No pudimos procesar tu pago.<br>Causa: "
            + causa + "</center></html>",
            SwingConstants.CENTER);
        lblDescripcion.setFont(Colores.FUENTE_LABEL);
        lblDescripcion.setForeground(Colores.TEXTO_SECUNDARIO);
        lblDescripcion.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel lblConsejo = new JLabel(
            "<html><center>Intenta con otra tarjeta o revisa tus datos.</center></html>",
            SwingConstants.CENTER);
        lblConsejo.setFont(Colores.FUENTE_LABEL);
        lblConsejo.setForeground(Colores.TEXTO_SECUNDARIO);
        lblConsejo.setAlignmentX(CENTER_ALIGNMENT);
 
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnCancelar  = crearBoton("Cancelar",        Boton.Variante.SECUNDARIO);
        Boton btnReintentar= crearBoton("Intentar otra vez", Boton.Variante.PRIMARIO);
 
        btnCancelar.addActionListener(e -> controlador.irABienvenida());

        btnReintentar.addActionListener(e -> controlador.irADatosBancarios());
 
        panelBtns.add(btnCancelar);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnReintentar);

        card.add(ico);
        card.add(Box.createVerticalStrut(12));
        card.add(titulo);
        card.add(Box.createVerticalStrut(20));
        card.add(lblDescripcion);
        card.add(Box.createVerticalStrut(12));
        card.add(lblConsejo);
        card.add(Box.createVerticalGlue());
        card.add(panelBtns);
 
        fondo.add(card);
    }
}
