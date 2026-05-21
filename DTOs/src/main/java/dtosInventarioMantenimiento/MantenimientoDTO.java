/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class MantenimientoDTO {
    private String idMantenimiento;
    private String idTecnico;
    private String idMaquina;
    private String Descripcion;
    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    public enum EstadoMantenimientoDTO{
        PENDIENTE, REALIZADO, ESPERA
    }
    private EstadoMantenimientoDTO estado;
    
    private List<MantenimientoPiezaDTO> piezas;

    public MantenimientoDTO() {
    }
    
    public MantenimientoDTO(String idMantenimiento, String idTecnico, String idMaquina, String Descripcion, LocalDateTime fechaProgramada, LocalDateTime fechaInicio, LocalDateTime fechaFin, EstadoMantenimientoDTO estado, List<MantenimientoPiezaDTO> piezas) {
        this.idMantenimiento = idMantenimiento;
        this.idTecnico = idTecnico;
        this.idMaquina = idMaquina;
        this.Descripcion = Descripcion;
        this.fechaProgramada = fechaProgramada;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.piezas = piezas;
    }
    
    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public LocalDateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoMantenimientoDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoMantenimientoDTO estado) {
        this.estado = estado;
    }

    public List<MantenimientoPiezaDTO> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<MantenimientoPiezaDTO> piezas) {
        this.piezas = piezas;
    }
    
}
