/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author julian izaguirre
 */
public class ControlAccesoException extends Exception {
    
    public ControlAccesoException() {
        
    }
    
    public ControlAccesoException(String msj) {
        super(msj);
    }
    // causa como en peru xdddd
    public ControlAccesoException(String msj, Throwable causa) {
        super(msj, causa);
    }
    
}
