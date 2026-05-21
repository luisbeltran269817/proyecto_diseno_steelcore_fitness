/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarioMantenimiento;

import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class GestorEstadosMantenimiento {
    
     private MaquinaDTO maquinaSeleccionada;
    private TecnicoDTO tecnicoSeleccionado;
    private MantenimientoDTO mantenimientoSeleccionado;
    private PiezaDTO piezaSeleccionada;

    private String descripcionMantenimiento;
    private LocalDateTime fechaProgramadaMantenimiento;

    private List<MantenimientoPiezaDTO> piezasSeleccionadas = new ArrayList<>();

    public MaquinaDTO getMaquinaSeleccionada() {
        return maquinaSeleccionada;
    }

    public void setMaquinaSeleccionada(MaquinaDTO maquinaSeleccionada) {
        this.maquinaSeleccionada = maquinaSeleccionada;
    }

    public TecnicoDTO getTecnicoSeleccionado() {
        return tecnicoSeleccionado;
    }

    public void setTecnicoSeleccionado(TecnicoDTO tecnicoSeleccionado) {
        this.tecnicoSeleccionado = tecnicoSeleccionado;
    }

    public MantenimientoDTO getMantenimientoSeleccionado() {
        return mantenimientoSeleccionado;
    }

    public void setMantenimientoSeleccionado(MantenimientoDTO mantenimientoSeleccionado) {
        this.mantenimientoSeleccionado = mantenimientoSeleccionado;
    }

    public PiezaDTO getPiezaSeleccionada() {
        return piezaSeleccionada;
    }

    public void setPiezaSeleccionada(PiezaDTO piezaSeleccionada) {
        this.piezaSeleccionada = piezaSeleccionada;
    }

    public String getDescripcionMantenimiento() {
        return descripcionMantenimiento;
    }

    public void setDescripcionMantenimiento(String descripcionMantenimiento) {
        this.descripcionMantenimiento = descripcionMantenimiento;
    }

    public LocalDateTime getFechaProgramadaMantenimiento() {
        return fechaProgramadaMantenimiento;
    }

    public void setFechaProgramadaMantenimiento(LocalDateTime fechaProgramadaMantenimiento) {
        this.fechaProgramadaMantenimiento = fechaProgramadaMantenimiento;
    }

    public List<MantenimientoPiezaDTO> getPiezasSeleccionadas() {
        return piezasSeleccionadas;
    }

    public void setPiezasSeleccionadas(List<MantenimientoPiezaDTO> piezasSeleccionadas) {
        this.piezasSeleccionadas = piezasSeleccionadas;
    }

    public void limpiarPiezasSeleccionadas() {
        this.piezasSeleccionadas = new ArrayList<>();
    }

    public void limpiar() {
        maquinaSeleccionada = null;
        tecnicoSeleccionado = null;
        mantenimientoSeleccionado = null;
        piezaSeleccionada = null;
        descripcionMantenimiento = null;
        fechaProgramadaMantenimiento = null;
        piezasSeleccionadas = new ArrayList<>();
    }
}
