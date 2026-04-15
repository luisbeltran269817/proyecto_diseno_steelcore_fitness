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
import javax.swing.JButton;

/**
 *
 * @author luiscarlosbeltran
 */
public class BotonUtileria {

    /**
     * metodo que crea un boton con diseño generico
     * @param texto
     * @return 
     */
    public static JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Monospaced", Font.PLAIN, 22));
        boton.setBackground(new Color(235, 215, 170));
        boton.setFocusPainted(false);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(260, 70));
        boton.setBorder(BorderFactory.createLineBorder(new Color(230, 170, 50), 2));
        return boton;
    }
}