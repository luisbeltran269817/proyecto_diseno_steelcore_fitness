/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import dtos.UsuarioDTO;
import objetosnegocios.UsuarioBO;

/**
 *
 * @author julian izaguirre
 */
public class ControlInicioSesion {
    private final UsuarioBO usuarioBO;

    public ControlInicioSesion(UsuarioBO usuarioBO) {
        this.usuarioBO = usuarioBO;
    }
    
    public UsuarioDTO iniciarSesion(String correo, String contraseña) throws Exception {
        UsuarioDTO usuario = usuarioBO.obtenerUsuarioPorCorreo(correo);
        if (usuario == null) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
        if (!usuario.getContraseña().equals(contraseña)) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
        return usuario;
    }
}
