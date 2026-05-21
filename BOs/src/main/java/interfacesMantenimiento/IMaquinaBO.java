/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.BajasInventarioDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IMaquinaBO {
    public void registrarMaquina(MaquinaDTO maquina) throws NegocioException;
    public MaquinaDTO obtenerMaquina(String idMaquina) throws NegocioException;
    public List<MaquinaDTO> obtenerMaquinas() throws NegocioException;
    public void actualizarMaquina(MaquinaDTO maquina) throws NegocioException;
    public void cambiarEstado(String idMaquina, MaquinaDTO.EstadoMaquinaDTO estado) throws NegocioException;
    public void registrarBaja(String idMaquina, BajasInventarioDTO baja) throws NegocioException;
    public List<MaquinaDTO> filtrarMaquinas(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws NegocioException;
}
