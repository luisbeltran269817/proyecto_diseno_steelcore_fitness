/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalDateTime;

/**
 * Clase DTO para visitas
 * @author Tungs
 */
public class VisitaDTO {
    private String idVisita;
    private String idSocio;  
    private String idCliente;
    private LocalDateTime fechaHora;
    private String idSucursal;
    private String tipoServicio;
    private String idRecursoAsignado;

    public VisitaDTO(String idVisita, String idSocio, String idCliente, LocalDateTime fechaHora, String idSucursal, String tipoServicio, String idRecursoAsignado) {
        this.idVisita = idVisita;
        this.idSocio = idSocio;
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.idSucursal = idSucursal;
        this.tipoServicio = tipoServicio;
        this.idRecursoAsignado = idRecursoAsignado;
    }

    public VisitaDTO() {
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdRecursoAsignado() {
        return idRecursoAsignado;
    }

    public void setIdRecursoAsignado(String idRecursoAsignado) {
        this.idRecursoAsignado = idRecursoAsignado;
    }
    
    
}
