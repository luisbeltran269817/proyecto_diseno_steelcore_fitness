/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
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
public interface IInventarioMantenimiento {
    
    public List<MaquinaDTO> obtenerMaquinas() throws InventarioMantenimientoException;

    public List<PiezaDTO> obtenerPiezas() throws InventarioMantenimientoException;

    public List<TecnicoDTO> obtenerTecnicos() throws InventarioMantenimientoException;

    public List<MantenimientoDTO> obtenerMantenimientos() throws InventarioMantenimientoException;

    public MantenimientoDTO registrarSolicitudMantenimiento(String idMaquina, String idTecnico, String descripcion, LocalDateTime fechaProgramada, List<MantenimientoPiezaDTO> piezas) throws InventarioMantenimientoException;

    MaquinaDTO registrarMaquina(String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;
    void actualizarMaquina(String idMaquina,String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;
    void darBajaMaquina(String idMaquina,String motivo) throws InventarioMantenimientoException;
    
    boolean tecnicoTieneHorarioDisponible(String idTecnico, LocalDateTime fechaProgramada) throws InventarioMantenimientoException;
    
    
    boolean hayStockSuficiente(String idPieza, int cantidad) throws InventarioMantenimientoException;
    
    List<MaquinaDTO> filtrarMaquinas(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;

    void validarMaquinaParaProgramarMantenimiento(String idMaquina) throws InventarioMantenimientoException;
}
    
   
