/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Gael Galaviz
 */
public class CitaDTO {
    private String idCita;
    private String idCliente;    
    private String idEntrenador; 
    private String idSucursal;
    private LocalDateTime fechaHora;
    private String estado;       
    private String tipoCita;    
    private String notas;

    public CitaDTO(String idCita, String idCliente, String idEntrenador, String idSucursal, LocalDateTime fechaHora, String estado, String tipoCita, String notas) {
        this.idCita = idCita;
        this.idCliente = idCliente;
        this.idEntrenador = idEntrenador;
        this.idSucursal = idSucursal;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.tipoCita = tipoCita;
        this.notas = notas;
    }

    public String getIdCita() {
        return idCita;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    
    
}
