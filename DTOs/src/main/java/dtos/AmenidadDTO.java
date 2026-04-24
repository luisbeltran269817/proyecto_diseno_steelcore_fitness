/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Gael Galaviz
 */
public class AmenidadDTO {
    private String idAmenidad;
    private String nombre;
    private String descripcion;
    private TipoAmenidad tipo;      
    private Double costo;
    
    public enum TipoAmenidad {
        BASICA,
        EXTRA
    }
    public AmenidadDTO() {
    }

    public AmenidadDTO(String idAmenidad, String nombre, String descripcion, TipoAmenidad tipo, Double costo) {
        this.idAmenidad = idAmenidad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.costo = costo;
    }

    public String getIdAmenidad() {
        return idAmenidad;
    }

    public void setIdAmenidad(String idAmenidad) {
        this.idAmenidad = idAmenidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoAmenidad getTipo() {
        return tipo;
    }

    public void setTipo(TipoAmenidad tipo) {
        this.tipo = tipo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    
}
