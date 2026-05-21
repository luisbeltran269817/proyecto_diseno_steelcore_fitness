/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtileriasReportes;

import Utilerias.Colores;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Noelia E.N
 */
public class CampoFechaReporte extends JPanel {

    private JFormattedTextField campo;

    public CampoFechaReporte(String labelTexto) {
        setLayout(new BorderLayout(0, 6));
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        JLabel label = new JLabel(labelTexto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_SECUNDARIO);

        try {
            MaskFormatter mascara = new MaskFormatter("####-##-##");
            mascara.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(mascara);
        } catch (ParseException ex) {
            campo = new JFormattedTextField();
        }

        campo.setFont(Colores.FUENTE_CAMPO);
        campo.setForeground(Colores.TEXTO_PRINCIPAL);
        campo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        campo.setBackground(Colores.FONDO_CAMPO);
        campo.setBorder(new EmptyBorder(10, 16, 10, 16));
        campo.setPreferredSize(new Dimension(0, 48));

        add(label, BorderLayout.NORTH);
        add(campo, BorderLayout.CENTER);
    }

    public String getValor() {
        String texto = campo.getText().trim();
        return texto.contains("_") ? "" : texto;
    }

    public void limpiar() {
        campo.setValue(null);
        campo.setText("");
    }
}
