/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class UltimoMantenimientoDTO {
    
    private String idMantenimiento;
    private LocalDateTime fecha;
    private MantenimientoDTO.EstadoMantenimientoDTO estado;
    
    public UltimoMantenimientoDTO() {
    }

    public UltimoMantenimientoDTO(String idMantenimiento, LocalDateTime fecha, MantenimientoDTO.EstadoMantenimientoDTO estado) {
        this.idMantenimiento = idMantenimiento;
        this.fecha = fecha;
        this.estado = estado;
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

    public MantenimientoDTO.EstadoMantenimientoDTO getEstado() {
        return estado;
    }

    public void setEstado(MantenimientoDTO.EstadoMantenimientoDTO estado) {
        this.estado = estado;
    }
}
