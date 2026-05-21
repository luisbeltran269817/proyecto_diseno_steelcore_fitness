/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.MantenimientoDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IMantenimientoBO {
    
    public void registrarMantenimiento(MantenimientoDTO mantenimiento) throws NegocioException;
    public MantenimientoDTO obtenerMantenimiento(String idMantenimiento) throws NegocioException;
    public List<MantenimientoDTO> obtenerMantenimientos() throws NegocioException;
    public boolean tieneMantenimientoActivo(String idMaquina) throws NegocioException;
}
