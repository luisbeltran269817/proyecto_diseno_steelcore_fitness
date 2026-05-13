/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author Tungs
 */
public class HorarioDTO {
    private String idHorario;
    private String idEntrenador;
    //Estos son los viejos, nononono
    private LocalDateTime inicio;
    private LocalDateTime fin;
    //Nuevos
    
    private LocalTime horaInicio;

    private LocalTime horaFin;
    
    private boolean disponible;

    public HorarioDTO(String idHorario, String idEntrenador, LocalDateTime inicio, LocalDateTime fin, boolean disponible) {
        this.idHorario = idHorario;
        this.idEntrenador = idEntrenador;
        this.inicio = inicio;
        this.fin = fin;
        this.disponible = disponible;
    }
    
    public HorarioDTO(String idHorario, String idEntrenador, LocalTime horaInicio, LocalTime horaFin, boolean disponible) {
        this.idHorario = idHorario;
        this.idEntrenador = idEntrenador;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
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

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
}
