/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class RutinaDTO {
    //referencia al entrenador que puede ser null
    private String idEntrenador;
    
    private String nombre;
    private LocalDateTime fechaCreacion;
    //lista de detalles que representa los dias de la semana
    private List<DetalleRutinaDTO> detalles;

    public RutinaDTO() {
    }

    public RutinaDTO(String idEntrenador, String nombre, LocalDateTime fechaCreacion, List<DetalleRutinaDTO> detalles) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.detalles = detalles;
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<DetalleRutinaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRutinaDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}
