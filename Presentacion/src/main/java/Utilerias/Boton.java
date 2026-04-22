/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Tungs
 */
public class Boton extends JButton {
    public enum Variante { PRIMARIO, SECUNDARIO }
 
    private Color colorBase;
    private Color colorHover;
    private Color colorPress;
    private Color colorActual;
    
    public Boton(String texto, Variante variante) {
        super(texto);
        asignarColores(variante);
        colorActual = colorBase;
 
        setForeground(Colores.TEXTO_PRINCIPAL);
        setFont(Colores.FUENTE_BOTON);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(220, 50));
 
        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { colorActual = colorHover; repaint(); }
            @Override public void mouseExited(MouseEvent e)   { colorActual = colorBase;  repaint(); }
            @Override public void mousePressed(MouseEvent e)  { colorActual = colorPress; repaint(); }
            @Override public void mouseReleased(MouseEvent e) { colorActual = colorHover; repaint(); }
        });
    }
 
    private void asignarColores(Variante v) {
        if (v == Variante.PRIMARIO) {
            colorBase  = Colores.BTN_PRIMARIO;
            colorHover = Colores.BTN_PRIMARIO_H;
            colorPress = Colores.BTN_PRIMARIO_P;
        } else {
            colorBase  = Colores.BTN_SECUNDARIO;
            colorHover = Colores.BTN_SECUNDARIO_H;
            colorPress = Colores.BTN_SECUNDARIO_P;
        }
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorActual);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
 
        g2.setColor(Colores.ACENTO.brighter());
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
 
        g2.dispose();
        super.paintComponent(g);
    }
}
