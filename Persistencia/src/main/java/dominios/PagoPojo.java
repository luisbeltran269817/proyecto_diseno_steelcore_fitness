/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class PagoPojo {
    private String idPago;
    private String idCliente;

    private Double monto;

    private String metodoPago;

    private EstadoPagoPojo estado;

    private LocalDateTime fecha;
    
    public enum EstadoPagoPojo {
        PENDIENTE,
        COMPLETADO,
        FALLIDO
    }

    public PagoPojo() {
    }
    
    public PagoPojo(String idPago, String idCliente, Double monto, String metodoPago, EstadoPagoPojo estado, LocalDateTime fecha) {
        this.idPago = idPago;
        this.idCliente = idCliente;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fecha = fecha;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPagoPojo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPagoPojo estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
    
}
