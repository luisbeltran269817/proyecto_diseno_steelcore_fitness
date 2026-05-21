/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author luiscarlosbeltran
 */
public class EjercicioDTO {
    private String idEjercicio;
    private String grupoMuscular;
    private String nombre;
    private String descripcion;

    public EjercicioDTO() {
    }

    public EjercicioDTO(String idEjercicio, String grupoMuscular, String nombre, String descripcion) {
        this.idEjercicio = idEjercicio;
        this.grupoMuscular = grupoMuscular;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(String idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMusculaar) {
        this.grupoMuscular = grupoMusculaar;
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
    
    
}
