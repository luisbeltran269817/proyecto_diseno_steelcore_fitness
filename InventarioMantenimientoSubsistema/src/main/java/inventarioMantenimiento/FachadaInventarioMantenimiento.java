/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarioMantenimiento;

import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.InventarioMantenimientoException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class FachadaInventarioMantenimiento implements IInventarioMantenimiento {
     private final ControlInventarioMantenimiento control;

    public FachadaInventarioMantenimiento() {
        this.control = new ControlInventarioMantenimiento();
    }

    @Override
    public List<MaquinaDTO> obtenerMaquinas() throws InventarioMantenimientoException {
        
        return control.obtenerMaquinas();
    }

    @Override
    public List<PiezaDTO> obtenerPiezas() throws InventarioMantenimientoException {
        return control.obtenerPiezas();
    }

    @Override
    public List<TecnicoDTO> obtenerTecnicos() throws InventarioMantenimientoException {
        return control.obtenerTecnicos();
    }

    @Override
    public List<MantenimientoDTO> obtenerMantenimientos() throws InventarioMantenimientoException {
        return control.obtenerMantenimientos();
    }

    @Override
    public MantenimientoDTO registrarSolicitudMantenimiento(String idMaquina, String idTecnico, String descripcion, LocalDateTime fechaProgramada, List<MantenimientoPiezaDTO> piezas) throws InventarioMantenimientoException {
        return control.registrarSolicitudMantenimiento(idMaquina, idTecnico, descripcion, fechaProgramada, piezas);
    }

    @Override
    public MaquinaDTO registrarMaquina(String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        return control.registrarMaquina(idSucursal,modelo,tipo,estado);
    }

    @Override
    public void actualizarMaquina(String idMaquina,String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        control.actualizarMaquina(idMaquina,idSucursal,modelo,tipo,estado);
    }

    @Override
    public void darBajaMaquina(String idMaquina,String motivo) throws InventarioMantenimientoException {
        control.darBajaMaquina(idMaquina,motivo);
    }
    
    @Override
    public boolean tecnicoTieneHorarioDisponible(String idTecnico, LocalDateTime fechaProgramada) throws InventarioMantenimientoException {
        return control.tecnicoTieneHorarioDisponible(idTecnico, fechaProgramada);
    }
    
    @Override
    public boolean hayStockSuficiente(String idPieza, int cantidad) throws InventarioMantenimientoException {
        return control.hayStockSuficiente(idPieza, cantidad);
    }
    
    @Override
    public List<MaquinaDTO> filtrarMaquinas(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        return control.filtrarMaquinas(idSucursal, estado);
    }

    @Override
    public void validarMaquinaParaProgramarMantenimiento(String idMaquina) throws InventarioMantenimientoException {
        control.validarMaquinaParaProgramarMantenimiento(idMaquina);
    }
}
