/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Clase_Control.ControlInicioSesion;
import objetosnegocios.ClienteBO;
import dtos.InicioSesionDTO;
import dtos.UsuarioDTO;
import java.time.LocalDate;
import objetosnegocios.AlmacenComprarMembresiaMock;
import objetosnegocios.UsuarioBO;

/**
 *
 * @author Tungs
 */
public class FachadaInicioSesion implements IInicioSesion {
    
    private final ControlInicioSesion controlInicioSesion;

    public FachadaInicioSesion() {
        AlmacenComprarMembresiaMock almacen = AlmacenComprarMembresiaMock.getInstancia();
        UsuarioBO usuarioBO = new UsuarioBO();
        this.controlInicioSesion = new ControlInicioSesion(usuarioBO);
    }
    
    @Override
    public UsuarioDTO iniciarSesion(String correo, String contraseña) throws Exception {
        return controlInicioSesion.iniciarSesion(correo, contraseña);
    }
    
    
}
