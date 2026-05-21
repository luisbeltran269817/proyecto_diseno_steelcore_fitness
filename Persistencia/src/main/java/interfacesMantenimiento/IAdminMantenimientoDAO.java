/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import dominios_mantenimiento.AdminMantenimientoPojo;
import excepciones.PersistenciaException;

/**
 *
 * @author Tungs
 */
public interface IAdminMantenimientoDAO {
    
    AdminMantenimientoPojo buscarPorCorreo(String correo) throws PersistenciaException;

}
