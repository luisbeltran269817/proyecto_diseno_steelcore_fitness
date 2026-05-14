/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDate;

/**
 *
 * @author luiscarlosbeltran
 */
public class MembresiaPojo {
    //atributos que se supone necesitamos para el caso base, despues se pueden agregar mas segun los requisitos
    private String idPlan;
    private String idSucursal;
    private String codigoQR;
    private Double montoPagado;
    private LocalDate fechaTramite;
    private LocalDate fechaCaducidad;
    private String estadoMembresia;

    public MembresiaPojo() {
    }

    public MembresiaPojo(String idPlan, String idSucursal, String codigoQR, Double montoPagado, LocalDate fechaTramite, LocalDate fechaCaducidad, String estadoMembresia) {
        this.idPlan = idPlan;
        this.idSucursal = idSucursal;
        this.codigoQR = codigoQR;
        this.montoPagado = montoPagado;
        this.fechaTramite = fechaTramite;
        this.fechaCaducidad = fechaCaducidad;
        this.estadoMembresia = estadoMembresia;
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

    public LocalDate getFechaTramite() {
        return fechaTramite;
    }

    public void setFechaTramite(LocalDate fechaTramite) {
        this.fechaTramite = fechaTramite;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(String estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }
    
    
}
