/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import dominios_mantenimiento.MantenimientoPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IMantenimientoDAO {
    
    public void registrarMantenimiento(MantenimientoPojo mantenimiento) throws PersistenciaException;
    MantenimientoPojo obtenerMantenimiento(String idMantenimiento) throws PersistenciaException;

    List<MantenimientoPojo> obtenerMantenimientos() throws PersistenciaException;

    boolean tieneMantenimientoActivo(String idMaquina) throws PersistenciaException;
}
