/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gael Galaviz
 */
public class EntrenadorDTO {
    private String idEntrenador;
    private String nombre;
    private List<SucursalDTO> sucursales;
    private List<HorarioDTO> horarios;

    public EntrenadorDTO(String idEntrenador, String nombre, List<SucursalDTO> sucursales, List<HorarioDTO> horarios) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.sucursales = sucursales;
        this.horarios = horarios;
    }

    public EntrenadorDTO() {
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

    public List<SucursalDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<SucursalDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public List<HorarioDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioDTO> horarios) {
        this.horarios = horarios;
    }
    
    
    
    
}
