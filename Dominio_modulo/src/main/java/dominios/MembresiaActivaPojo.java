/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import dominios.MembresiaPojo.EstadoMembresiaPojo;
import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class MembresiaActivaPojo {
    private String idMembresia;
    private String idPlan;
    private LocalDateTime fechaCaducidad;
    private EstadoMembresiaPojo estado;

    public MembresiaActivaPojo(String idMembresia, String idPlan, LocalDateTime fechaCaducidad, EstadoMembresiaPojo estado) {
        this.idMembresia = idMembresia;
        this.idPlan = idPlan;
        this.fechaCaducidad = fechaCaducidad;
        this.estado = estado;
    }

    public MembresiaActivaPojo() {
    }
    
    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public LocalDateTime getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDateTime fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public EstadoMembresiaPojo getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresiaPojo estado) {
        this.estado = estado;
    }
    
    
}
