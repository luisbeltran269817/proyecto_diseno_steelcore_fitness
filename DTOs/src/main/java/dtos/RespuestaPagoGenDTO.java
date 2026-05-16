/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 * clase DTO para la respuesta a la peticion del pago
 * @author Tungs
 */ 
public class RespuestaPagoGenDTO {
    private boolean exitoso;
    private String mensaje;
    private String idTransaccion;

    public RespuestaPagoGenDTO() {
    }

    public RespuestaPagoGenDTO(boolean exitoso, String mensaje, String idTransaccion) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
        this.idTransaccion = idTransaccion;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    
    
}
