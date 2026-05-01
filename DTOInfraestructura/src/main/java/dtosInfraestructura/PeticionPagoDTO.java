/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInfraestructura;

/**
 * Clase que representa una petición que se le hace el servidor de la API de Stripe
 * @author Tungs
 */
public class PeticionPagoDTO {
    private double monto;
    private String descripcion;
    private String tokenTarjeta;

    public PeticionPagoDTO(double monto, String descripcion, String tokenTarjeta) {
        this.monto = monto;
        this.descripcion = descripcion;
        this.tokenTarjeta = tokenTarjeta;
    }

    public PeticionPagoDTO() {
    }
    
    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTokenTarjeta() {
        return tokenTarjeta;
    }

    public void setTokenTarjeta(String tokenTarjeta) {
        this.tokenTarjeta = tokenTarjeta;
    }
    
    
}
