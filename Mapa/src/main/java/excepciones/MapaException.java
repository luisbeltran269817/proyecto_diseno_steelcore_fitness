/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author julian izaguirre
 */
public class MapaException extends Exception{
    /**
     * Construye una MapaException con un mensaje descriptivo.
     *
     * @param msj Descripción del error ocurrido.
     */
    public MapaException(String msj) {
        super(msj);
    }
    
    /**
     * Construye una MapaException con un mensaje y la causa original.
     *
     * @param msj Descripción del error ocurrido.
     * @param causa Excepción original que provocó este error.
     */
    public MapaException(String msj, Throwable causa) {
        super(msj, causa);
    }
}
