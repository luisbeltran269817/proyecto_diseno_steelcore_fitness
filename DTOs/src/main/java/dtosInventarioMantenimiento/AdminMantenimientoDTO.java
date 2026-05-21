/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

import dtos.UsuarioDTO;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoDTO extends UsuarioDTO {
    private String idAdminMantenimiento;

    public AdminMantenimientoDTO() {
    }
    
    public String getIdAdminMantenimiento() {
        return idAdminMantenimiento;
    }

    public void setIdAdminMantenimiento(String idAdminMantenimiento) {
        this.idAdminMantenimiento = idAdminMantenimiento;
    }
}
