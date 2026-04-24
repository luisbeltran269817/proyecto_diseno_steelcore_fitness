/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PlanDTO {
    private String idPlan;
    private String nombre;
    private Double precio;
    private String descripcion;
    private int mesesDuracion;
     private List<AmenidadDTO> amenidades;

    public PlanDTO() {
    }

    public PlanDTO(String idPlan, String nombre, Double precio, String descripcion, int mesesDuracion, List<AmenidadDTO> amenidades) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.mesesDuracion = mesesDuracion;
        this.amenidades = amenidades;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getMesesDuracion() {
        return mesesDuracion;
    }

    public void setMesesDuracion(int mesesDuracion) {
        this.mesesDuracion = mesesDuracion;
    }

    public List<AmenidadDTO> getAmenidades() {
        return amenidades;
    }

    public void setAmenidades(List<AmenidadDTO> amenidades) {
        this.amenidades = amenidades;
    }

    
 
    
}
