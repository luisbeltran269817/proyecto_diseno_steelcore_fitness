/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalTime;

/**
 * DTO de horario de un entrenador para el módulo de control de acceso.
 *
 * @author julian izaguirre
 */
public class HorarioDTO {

    private String idHorario;
    private String idEntrenador;
    private String idSucursal;
    private boolean disponible;
    private LocalTime horaInicio;

    public HorarioDTO(String idHorario, String idEntrenador, String idSucursal, boolean disponible, LocalTime horaInicio, LocalTime horaFin, String diaSemana) {
        this.idHorario = idHorario;
        this.idEntrenador = idEntrenador;
        this.idSucursal = idSucursal;
        this.disponible = disponible;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.diaSemana = diaSemana;
    }
    private LocalTime horaFin;
    private String diaSemana;

    public HorarioDTO() {
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    @Override
    public String toString() {
        return "HorarioDTO{" + "idHorario=" + idHorario + ", idEntrenador=" + idEntrenador + ", idSucursal=" + idSucursal + ", disponible=" + disponible + ", horaInicio=" + horaInicio + ", horaFin=" + horaFin + ", diaSemana=" + diaSemana + '}';
    }
}
