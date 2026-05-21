/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.AdminMantenimientoDTO;

/**
 *
 * @author Tungs
 */
public interface IAdminMantenimientoBO {
    
     AdminMantenimientoDTO buscarPorCorreo(String correo) throws NegocioException;
}
