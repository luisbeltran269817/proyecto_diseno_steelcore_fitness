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

    private String idHorario;

    private LocalDateTime fechaHora;

    private EstadoCita estado;

    private String notas;

    public enum EstadoCita {
        PENDIENTE,
        CONFIRMADA,
        CANCELADA,
        COMPLETADA
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

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public CitaDTO() {
    }

    public CitaDTO(String idCita, String idCliente, String idEntrenador, String idSucursal, String idHorario, LocalDateTime fechaHora, EstadoCita estado, String notas) {
        this.idCita = idCita;
        this.idCliente = idCliente;
        this.idEntrenador = idEntrenador;
        this.idSucursal = idSucursal;
        this.idHorario = idHorario;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.notas = notas;
    }
    
}
