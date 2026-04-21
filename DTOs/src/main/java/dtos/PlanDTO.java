/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author julian izaguirre
 */
public class PlanDTO {
    private String idPlan;
    private String nombre;
    private double precio;
    private String descripcion;
    private boolean incluyeEntrenador;
 
    public PlanDTO(String idPlan, String nombre, double precio,
                   String descripcion, boolean incluyeEntrenador) {
        this.idPlan = idPlan;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.incluyeEntrenador = incluyeEntrenador;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isIncluyeEntrenador() {
        return incluyeEntrenador;
    }

    public void setIncluyeEntrenador(boolean incluyeEntrenador) {
        this.incluyeEntrenador = incluyeEntrenador;
    }
    
    
    
    
}
