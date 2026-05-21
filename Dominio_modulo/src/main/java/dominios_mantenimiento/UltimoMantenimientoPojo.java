/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class UltimoMantenimientoPojo {
    
    private String idMantenimiento;
    private LocalDateTime fecha;
    private EstadoMantenimientoSnapshot estado;
    
    public enum EstadoMantenimientoSnapshot {
        PENDIENTE,
        REALIZADO,
        ESPERA
    }

    public UltimoMantenimientoPojo() {
    }

    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoMantenimientoSnapshot getEstado() {
        return estado;
    }

    public void setEstado(EstadoMantenimientoSnapshot estado) {
        this.estado = estado;
    }

}
