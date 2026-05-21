/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class RutinaPojo {
    String nombre;
    LocalDateTime fechaCreacion;
    List<DetalleRutinaPojo> detalles;
    String idEntrenador;

    public RutinaPojo() {
    }

    public RutinaPojo(String nombre, LocalDateTime fechaCreacion, List<DetalleRutinaPojo> detalles, String idEntrenador) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.detalles = detalles;
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

    public List<DetalleRutinaPojo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleRutinaPojo> detalles) {
        this.detalles = detalles;
    }

    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }
    
    
}
