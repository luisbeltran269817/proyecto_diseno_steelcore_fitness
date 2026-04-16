/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tungs
 */
public abstract class ComponenteBase extends JPanel {
    protected Color colorPrimario = new Color(83, 74, 183);   
    protected Color colorFondo    = new Color(30, 20, 80);   
    protected Color colorTexto    = Color.WHITE;

    public ComponenteBase() {
        setBackground(colorFondo);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }
    
    protected JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(colorTexto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        return label;
    }
    
}
