/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

/**
 *
 * @author luiscarlosbeltran
 */
public class EjercicioPojo {
    String idEjercicio;
    String grupoMuscular;
    String nombre;
    String descripcion;

    public EjercicioPojo() {
    }

    public EjercicioPojo(String idEjercicio, String grupoMuscular, String nombre, String descripcion) {
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

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
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
