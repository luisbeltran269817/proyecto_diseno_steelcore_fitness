/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import EstadoMembresia.EstadoMembresiaDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaDTO {    
    private String idMembresia;
    private String idPlan;
    private String idSucursal;
    private List<AmenidadDTO> amenidadesContratadas;
    private String codigoQR;
    private Double montoPagado;
    private LocalDateTime fechaTramite;
    private LocalDateTime fechaCaducidad;
    private EstadoMembresiaDTO estado;

    public MembresiaDTO(String idMembresia, String idPlan, String idSucursal, List<AmenidadDTO> amenidadesContratadas, String codigoQR, Double montoPagado, LocalDateTime fechaTramite, LocalDateTime fechaCaducidad, EstadoMembresiaDTO estado) {
        this.idMembresia = idMembresia;
        this.idPlan = idPlan;
        this.idSucursal = idSucursal;
        this.amenidadesContratadas = amenidadesContratadas;
        this.codigoQR = codigoQR;
        this.montoPagado = montoPagado;
        this.fechaTramite = fechaTramite;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
    }

    public MembresiaDTO() {
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
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

    public List<AmenidadDTO> getAmenidadesContratadas() {
        return amenidadesContratadas;
    }

    public void setAmenidadesContratadas(List<AmenidadDTO> amenidadesContratadas) {
        this.amenidadesContratadas = amenidadesContratadas;
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

    public EstadoMembresiaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresiaDTO estado) {
        this.estado = estado;
    }
    
    
}
