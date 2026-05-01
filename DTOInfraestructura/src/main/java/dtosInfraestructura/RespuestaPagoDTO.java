/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInfraestructura;

/** 
 * Clase que representa la respuesta que le da la API de Stripe al cliente después de recibir su petición
 * @author Tungs
 */
public class RespuestaPagoDTO {
    
    private boolean exitoso;
    private String idTransaccion;
    private String mensajeError;

    public RespuestaPagoDTO() {
    }

    public RespuestaPagoDTO(boolean exitoso, String idTransaccion, String mensajeError) {
        this.exitoso = exitoso;
        this.idTransaccion = idTransaccion;
        this.mensajeError = mensajeError;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }
    
    
}
