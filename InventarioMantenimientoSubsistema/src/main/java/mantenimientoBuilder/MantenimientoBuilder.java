/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mantenimientoBuilder;

import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class MantenimientoBuilder {
    
    private String idMantenimiento;
    private String idTecnico;
    private String idMaquina;
    private String descripcion;
    private LocalDateTime fechaProgramada;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private MantenimientoDTO.EstadoMantenimientoDTO estado;
    private List<MantenimientoPiezaDTO> piezas;

    public MantenimientoBuilder setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
        return this;
    }

    public MantenimientoBuilder setTecnico(TecnicoDTO tecnico) {
        if (tecnico != null) {
            this.idTecnico = tecnico.getIdTecnico();
        }
        return this;
    }

    public MantenimientoBuilder setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
        return this;
    }

    public MantenimientoBuilder setMaquina(MaquinaDTO maquina) {
        if (maquina != null) {
            this.idMaquina = maquina.getIdMaquina();
        }
        return this;
    }

    public MantenimientoBuilder setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
        return this;
    }

    public MantenimientoBuilder setDescripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public MantenimientoBuilder setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
        return this;
    }

    public MantenimientoBuilder setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
        return this;
    }

    public MantenimientoBuilder setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
        return this;
    }

    public MantenimientoBuilder setEstado(MantenimientoDTO.EstadoMantenimientoDTO estado) {
        this.estado = estado;
        return this;
    }

    public MantenimientoBuilder setPiezas(List<MantenimientoPiezaDTO> piezas) {
        this.piezas = piezas;
        return this;
    }
    
    public MantenimientoBuilder agregarPieza(MantenimientoPiezaDTO pieza) {
        if (this.piezas == null) {
            this.piezas = new ArrayList<>();
        }
        if (pieza != null) {
            this.piezas.add(pieza);
        }
        return this;
    }

    /**
     * Método que construye la solicitud de mantenimiento
     * @return 
     */
    public MantenimientoDTO build() {
        MantenimientoDTO mantenimiento = new MantenimientoDTO();

        mantenimiento.setIdMantenimiento(idMantenimiento);
        mantenimiento.setIdTecnico(idTecnico);
        mantenimiento.setIdMaquina(idMaquina);
        mantenimiento.setDescripcion(descripcion);
        mantenimiento.setFechaProgramada(fechaProgramada);
        mantenimiento.setFechaInicio(fechaInicio);
        mantenimiento.setFechaFin(fechaFin);
        mantenimiento.setEstado(estado);
        mantenimiento.setPiezas(piezas != null ? piezas : new ArrayList<>());

        return mantenimiento;
    }
}
