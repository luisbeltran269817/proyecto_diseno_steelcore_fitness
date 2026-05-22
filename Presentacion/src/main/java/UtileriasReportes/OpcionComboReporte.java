/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtileriasReportes;

/**
 * Representa una opción seleccionable dentro de un ComboBox de reportes.
 *
 * Permite mostrar un texto amigable al usuario, pero conservar internamente un
 * valor real, normalmente un id de base de datos.
 *
 * Ejemplo: texto visible: "Centro Hermosillo" valor interno: "S001"
 *
 * @author Noelia E.N.
 */
public class OpcionComboReporte {

    private String valor;
    private String texto;

    /**
     * Constructor de la opción del combo.
     *
     * @param valor valor interno que se usará en filtros o consultas.
     * @param texto texto visible para el usuario.
     */
    public OpcionComboReporte(String valor, String texto) {
        this.valor = valor;
        this.texto = texto;
    }

    /**
     * Obtiene el valor interno de la opción.
     *
     * @return valor interno.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Obtiene el texto visible de la opción.
     *
     * @return texto visible.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Devuelve el texto visible para que JComboBox lo muestre automáticamente.
     *
     * @return texto visible de la opción.
     */
    @Override
    public String toString() {
        return texto;
    }
}
