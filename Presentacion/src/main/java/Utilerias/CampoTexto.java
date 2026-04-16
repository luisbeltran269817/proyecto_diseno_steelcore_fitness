/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class CampoTexto extends JPanel {
    private JTextField campo;
    private String placeholder;
    private boolean estaEnfocado = false;

    public CampoTexto(String labelTexto, String placeholder) {
        this.placeholder = placeholder;
        setLayout(new BorderLayout(0, 6));
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        JLabel label = new JLabel(labelTexto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_SECUNDARIO);

        campo = new JTextField(placeholder) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        campo.setFont(Colores.FUENTE_CAMPO);
        campo.setForeground(Colores.TEXTO_PLACEHOLDER);
        campo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        campo.setOpaque(false);
        campo.setBorder(new EmptyBorder(10, 16, 10, 16));
        campo.setPreferredSize(new Dimension(0, 48));

        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                estaEnfocado = true;
                if (campo.getText().equals(CampoTexto.this.placeholder)) {
                    campo.setText("");
                    campo.setForeground(Colores.TEXTO_PRINCIPAL);
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                estaEnfocado = false;
                if (campo.getText().isEmpty()) {
                    campo.setText(CampoTexto.this.placeholder);
                    campo.setForeground(Colores.TEXTO_PLACEHOLDER);
                }
                repaint();
            }
        });

        add(label, BorderLayout.NORTH);
        add(campo, BorderLayout.CENTER);
    }

    public String getValor() {
        String val = campo.getText().trim();
        return val.equals(placeholder) ? "" : val;
    }

    public void limpiar() {
        campo.setText(placeholder);
        campo.setForeground(Colores.TEXTO_PLACEHOLDER);
    }
}
