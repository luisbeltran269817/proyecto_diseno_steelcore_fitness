/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class CampoContrasena extends JPanel {
    
    private JPasswordField campo;
    private JButton btnToggle;
    private boolean visible = false;
    private static final String PLACEHOLDER = "••••••••";

    public CampoContrasena(String labelTexto) {
        setLayout(new BorderLayout(0, 6));
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        JLabel label = new JLabel(labelTexto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_SECUNDARIO);

        JPanel contenedor = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
            }
        };
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(0, 48));

        campo = new JPasswordField();
        campo.setEchoChar('●');
        campo.setFont(Colores.FUENTE_CAMPO);
        campo.setForeground(Colores.TEXTO_PRINCIPAL);
        campo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        campo.setOpaque(false);
        campo.setBorder(new EmptyBorder(10, 16, 10, 8));

        btnToggle = new JButton("Ver");
        btnToggle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnToggle.setForeground(Colores.TEXTO_PLACEHOLDER);
        btnToggle.setContentAreaFilled(false);
        btnToggle.setBorderPainted(false);
        btnToggle.setFocusPainted(false);
        btnToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnToggle.setPreferredSize(new Dimension(60, 44));
        btnToggle.addActionListener(e -> toggleVisibilidad());

        contenedor.add(campo, BorderLayout.CENTER);
        contenedor.add(btnToggle, BorderLayout.EAST);

        add(label, BorderLayout.NORTH);
        add(contenedor, BorderLayout.CENTER);
    }

    private void toggleVisibilidad() {
        visible = !visible;
        campo.setEchoChar(visible ? (char) 0 : '●');
        btnToggle.setText(visible ? "Ocultar" : "Ver");
        btnToggle.setForeground(visible ? Colores.ACENTO : Colores.TEXTO_PLACEHOLDER);
    }

    public String getValor() {
        return new String(campo.getPassword());
    }

    public void limpiar() {
        campo.setText("");
    }

}
