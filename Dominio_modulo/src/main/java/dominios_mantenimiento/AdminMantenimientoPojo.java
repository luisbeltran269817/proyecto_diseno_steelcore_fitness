/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

import dominios.UsuarioPojo;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoPojo {
    
    private String idAdminMantenimiento;
    private UsuarioPojo usuario;

    public AdminMantenimientoPojo() {
    }

    public String getIdAdminMantenimiento() {
        return idAdminMantenimiento;
    }

    public void setIdAdminMantenimiento(String idAdminMantenimiento) {
        this.idAdminMantenimiento = idAdminMantenimiento;
    }

    public UsuarioPojo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPojo usuario) {
        this.usuario = usuario;
    }
}
