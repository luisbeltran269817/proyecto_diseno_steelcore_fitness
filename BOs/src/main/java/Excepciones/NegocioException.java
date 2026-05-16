/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package Excepciones;

/**
 *
 * @author Tungs
 */
public class NegocioException extends Exception {

    /**
     * Creates a new instance of <code>NegocioException</code> without detail
     * message.
     */
    public NegocioException() {
    }

    /**
     * Constructs an instance of <code>NegocioException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NegocioException(String msg) {
        super(msg);
    }
    
    /**
     * Constructor con causa. Permite encadenar la excepcion original
     * para que no se pierda el stack trace al envolver PersistenciaException.
     *
     * @param msg   mensaje descriptivo del error de negocio
     * @param cause excepcion original (p.ej. PersistenciaException)
     */
    public NegocioException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
