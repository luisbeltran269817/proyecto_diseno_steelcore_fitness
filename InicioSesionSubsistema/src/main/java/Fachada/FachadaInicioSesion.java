/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Clase_Control.ControlInicioSesion;
import dtos.UsuarioDTO;

/**
 *
 * @author Tungs
 */
public class FachadaInicioSesion implements IInicioSesion {
    
    private final ControlInicioSesion controlInicioSesion;

    public FachadaInicioSesion() {
//      AlmacenComprarMembresiaMock almacen = AlmacenComprarMembresiaMock.getInstancia();
        this.controlInicioSesion = new ControlInicioSesion();
    }
    
    @Override
    public UsuarioDTO iniciarSesion(String correo, String contraseña) throws Exception {
        return controlInicioSesion.iniciarSesion(correo, contraseña);
    }
    
    
}
