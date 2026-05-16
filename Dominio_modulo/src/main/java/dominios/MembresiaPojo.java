/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class MembresiaPojo {
    private String idMembresia;
    private String idCliente;
    private String idPlan;
    private String idSucursal;

    private List<AmenidadPojo> amenidadesExtra;

    private String metodoPago;
    private Double montoPagado;

    private String codigoQR;

    private LocalDateTime fechaTramite;
    private LocalDateTime fechaCaducidad;

    private  EstadoMembresiaPojo estado;

    private PagoPojo pago;
    
    public enum EstadoMembresiaPojo {
        ACTIVA,
        CANCELADA,
        VENCIDA
    }

    public MembresiaPojo() {
    }

    public MembresiaPojo(String idMembresia, String idCliente, String idPlan, String idSucursal, List<AmenidadPojo> amenidadesExtra, String metodoPago, Double montoPagado, String codigoQR, LocalDateTime fechaTramite, LocalDateTime fechaCaducidad, EstadoMembresiaPojo estado, PagoPojo pago) {
        this.idMembresia = idMembresia;
        this.idCliente = idCliente;
        this.idPlan = idPlan;
        this.idSucursal = idSucursal;
        this.amenidadesExtra = amenidadesExtra;
        this.metodoPago = metodoPago;
        this.montoPagado = montoPagado;
        this.codigoQR = codigoQR;
        this.fechaTramite = fechaTramite;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
        this.pago = pago;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<AmenidadPojo> getAmenidadesExtra() {
        return amenidadesExtra;
    }

    public void setAmenidadesExtra(List<AmenidadPojo> amenidadesExtra) {
        this.amenidadesExtra = amenidadesExtra;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public LocalDateTime getFechaTramite() {
        return fechaTramite;
    }

    public void setFechaTramite(LocalDateTime fechaTramite) {
        this.fechaTramite = fechaTramite;
    }

    public LocalDateTime getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDateTime fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public EstadoMembresiaPojo getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresiaPojo estado) {
        this.estado = estado;
    }

    public PagoPojo getPago() {
        return pago;
    }

    public void setPago(PagoPojo pago) {
        this.pago = pago;
    }
    
}
