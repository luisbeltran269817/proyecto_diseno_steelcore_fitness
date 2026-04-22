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
    private String especialidad;
    private List<String> idSucursalesAsignadas;
    private List<LocalDateTime> horariosDisponibles;

    public EntrenadorDTO(String idEntrenador, String nombre, String especialidad, List<String> idSucursalesAsignadas, List<LocalDateTime> horariosDisponibles) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.idSucursalesAsignadas = idSucursalesAsignadas;
        this.horariosDisponibles = horariosDisponibles;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public List<String> getIdSucursalesAsignadas() {
        return idSucursalesAsignadas;
    }

    public void setIdSucursalesAsignadas(List<String> idSucursalesAsignadas) {
        this.idSucursalesAsignadas = idSucursalesAsignadas;
    }

    public List<LocalDateTime> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(List<LocalDateTime> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }
    
    
}
