/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase DTO para entrenadores
 * @author Gael Galaviz
 */
public class EntrenadorDTO {
    private String idEntrenador;
    private String nombre;
    private String idSucursal;
    private List<HorarioDTO> horarios;

    public EntrenadorDTO() {
    }

    public EntrenadorDTO(String idEntrenador, String nombre, String idSucursal, List<HorarioDTO> horarios) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
        this.horarios = horarios;
    }

    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
}
