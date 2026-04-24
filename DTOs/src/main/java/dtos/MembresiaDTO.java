/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaDTO {    
    private String idMembresia;
    private String idCliente;
    private String idPlan;
    private String idSucursal;

    private List<AmenidadDTO> amenidadesExtra;

    private String codigoQR;
    private Double montoPagado;

    private LocalDateTime fechaTramite;
    private LocalDateTime fechaCaducidad;

    private EstadoMembresia estado;

    private String idPago;

    public enum EstadoMembresia {
        ACTIVA,
        VENCIDA,
        CANCELADA
    }

    public MembresiaDTO(String idMembresia, String idCliente, String idPlan, String idSucursal, List<AmenidadDTO> amenidadesExtra, String codigoQR, Double montoPagado, LocalDateTime fechaTramite, LocalDateTime fechaCaducidad, EstadoMembresia estado, String idPago) {
        this.idMembresia = idMembresia;
        this.idCliente = idCliente;
        this.idPlan = idPlan;
        this.idSucursal = idSucursal;
        this.amenidadesExtra = amenidadesExtra;
        this.codigoQR = codigoQR;
        this.montoPagado = montoPagado;
        this.fechaTramite = fechaTramite;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
        this.idPago = idPago;
    }

    public MembresiaDTO() {
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

    public List<AmenidadDTO> getAmenidadesExtra() {
        return amenidadesExtra;
    }

    public void setAmenidadesExtra(List<AmenidadDTO> amenidadesExtra) {
        this.amenidadesExtra = amenidadesExtra;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
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

    public EstadoMembresia getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresia estado) {
        this.estado = estado;
    }

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    
    
    
}
