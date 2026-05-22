/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasRutina;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaMensaje extends PantallaBase {

    public PantallaMensaje(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Crear Rutina");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {

        // Panel superior - título
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(48, 0, 0, 0));

        JLabel lblTitulo = new JLabel("¿Algún objetivo en mente?");
        lblTitulo.setFont(Colores.FUENTE_TITULO);
        lblTitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        panelSuperior.add(lblTitulo);

        // Panel central - botones de objetivo
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 0, 12, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        Boton btnCardio = crearBoton("Cardio", Boton.Variante.SECUNDARIO);
        Boton btnPerderPeso = crearBoton("Perder Peso", Boton.Variante.SECUNDARIO);
        Boton btnCuerpoCompleto = crearBoton("Cuerpo completo", Boton.Variante.SECUNDARIO);
        
        btnCardio.addActionListener(e -> {
            try { controlador.irAEditorConPlantilla("Cardio"); } 
            catch (NegocioException ex) { JOptionPane.showMessageDialog(this, "No se pudo cargar la plantilla.", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnPerderPeso.addActionListener(e -> {
            try { controlador.irAEditorConPlantilla("Perder peso"); } 
            catch (NegocioException ex) { JOptionPane.showMessageDialog(this, "No se pudo cargar la plantilla.", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        btnCuerpoCompleto.addActionListener(e -> {
            try { controlador.irAEditorConPlantilla("Cuerpo completo"); } 
            catch (NegocioException ex) { JOptionPane.showMessageDialog(this, "No se pudo cargar la plantilla.", "Error", JOptionPane.ERROR_MESSAGE); }
        });

        gbc.gridy = 0; panelCentro.add(btnCardio, gbc);
        gbc.gridy = 1; panelCentro.add(btnPerderPeso, gbc);
        gbc.gridy = 2; panelCentro.add(btnCuerpoCompleto, gbc);
        gbc.gridy = 3;
        gbc.insets = new Insets(28, 0, 12, 0);
        panelCentro.add(crearBotonOmitir(), gbc);

        // Panel inferior - botón volver
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(16, 32, 32, 32));

        Boton btnVolver = crearBoton("Volver", Boton.Variante.SECUNDARIO);
        panelInferior.add(btnVolver, BorderLayout.WEST);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        btnVolver.addActionListener(e -> {
            controlador.irAVistaRutina();
        });
    }

    private JButton crearBotonOmitir() {
        Color colorBase  = Color.decode("#8B3A3A");
        Color colorHover = Color.decode("#A04444");
        Color colorPress = Color.decode("#6E2E2E");
        Color[] colorActual = {colorBase};

        JButton btn = new JButton("Omitir") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorActual[0]);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(colorBase.darker());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(Colores.FUENTE_BOTON);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(220, 50));

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { colorActual[0] = colorHover; btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)   { colorActual[0] = colorBase;  btn.repaint(); }
            @Override public void mousePressed(MouseEvent e)  { colorActual[0] = colorPress; btn.repaint(); }
            @Override public void mouseReleased(MouseEvent e) { colorActual[0] = colorHover; btn.repaint(); }
        });
        
        btn.addActionListener(e -> {
            controlador.irAEditorRutinaNueva();
        });

        return btn;
    }
}