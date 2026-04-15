/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author luiscarlosbeltran
 */
public class LabelUtileria {

    /**
     * metodo para crear un JLabel generico
     * @param texto
     * @return 
     */
    public static JLabel crearTitulo(String texto) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(235, 215, 170));
        label.setForeground(new Color(50, 50, 50));
        label.setFont(new Font("Monospaced", Font.BOLD, 22));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(300, 80));
        label.setBorder(BorderFactory.createLineBorder(new Color(230, 170, 50), 2));
        return label;
    }
}