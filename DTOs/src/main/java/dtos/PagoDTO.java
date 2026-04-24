/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class PagoDTO {
    private String idPago;
    private String idCliente;

    private Double monto;

    private EstadoPago estado;

    private LocalDateTime fecha;

    public enum EstadoPago {
        PENDIENTE,
        COMPLETADO,
        FALLIDO
    }

    public PagoDTO(String idPago, String idCliente, Double monto, EstadoPago estado, LocalDateTime fecha) {
        this.idPago = idPago;
        this.idCliente = idCliente;
        this.monto = monto;
        this.estado = estado;
        this.fecha = fecha;
    }

    public PagoDTO() {
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

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    
}
