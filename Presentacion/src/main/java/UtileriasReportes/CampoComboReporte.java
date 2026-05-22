/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtileriasReportes;

import Utilerias.Colores;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Campo reutilizable para mostrar un JComboBox dentro de las pantallas de
 * reportes.
 *
 * El combo muestra nombres al usuario, pero puede regresar ids internos para
 * que los filtros funcionen correctamente.
 *
 * @author Noelia E.N
 */
public class CampoComboReporte extends JPanel {

    private JComboBox<OpcionComboReporte> combo;

    /**
     * Constructor del campo combo.
     *
     * @param labelTexto texto que se mostrará como etiqueta del campo.
     */
    public CampoComboReporte(String labelTexto) {
        setLayout(new BorderLayout(0, 6));
        setOpaque(false);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        JLabel label = new JLabel(labelTexto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_SECUNDARIO);

        combo = new JComboBox<>();
        combo.setFont(Colores.FUENTE_CAMPO);
        combo.setBackground(Colores.FONDO_CAMPO);
        combo.setForeground(Colores.TEXTO_PRINCIPAL);
        combo.setBorder(new EmptyBorder(8, 12, 8, 12));
        combo.setPreferredSize(new Dimension(0, 48));

        add(label, BorderLayout.NORTH);
        add(combo, BorderLayout.CENTER);
    }

    /**
     * Agrega una opción al combo.
     *
     * @param valor valor interno de la opción.
     * @param texto texto visible para el usuario.
     */
    public void agregarOpcion(String valor, String texto) {
        combo.addItem(new OpcionComboReporte(valor, texto));
    }

    /**
     * Agrega una opción genérica como "Todos" o "Todas".
     *
     * @param texto texto visible.
     */
    public void agregarOpcionTodos(String texto) {
        combo.addItem(new OpcionComboReporte("", texto));
    }

    /**
     * Limpia todas las opciones del combo.
     */
    public void limpiarOpciones() {
        combo.removeAllItems();
    }

    /**
     * Obtiene el valor interno seleccionado.
     *
     * @return valor interno seleccionado.
     */
    public String getValor() {
        OpcionComboReporte opcion = (OpcionComboReporte) combo.getSelectedItem();

        if (opcion == null || opcion.getValor() == null) {
            return "";
        }

        return opcion.getValor();
    }

    /**
     * Obtiene el texto visible seleccionado.
     *
     * @return texto visible seleccionado.
     */
    public String getTextoSeleccionado() {
        OpcionComboReporte opcion = (OpcionComboReporte) combo.getSelectedItem();

        if (opcion == null || opcion.getTexto() == null) {
            return "";
        }

        return opcion.getTexto();
    }

    /**
     * Permite acceder al combo interno para agregar listeners.
     *
     * @return combo interno.
     */
    public JComboBox<OpcionComboReporte> getCombo() {
        return combo;
    }
}
