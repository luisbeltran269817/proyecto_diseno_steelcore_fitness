/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Clase_Control.ClaseControlInicioSesion;
import dtos.InicioSesionDTO;

/**
 *
 * @author Tungs
 */
public class FachadaInicioSesion implements IInicioSesion {

    private ClaseControlInicioSesion ctrl;
 
    public FachadaInicioSesion() {
        this.ctrl = new ClaseControlInicioSesion();
    }
    
    @Override
    public InicioSesionDTO iniciarSesion(String usuario, String password) {
        return ctrl.procesarInicioSesion(usuario, password);
        
    }
 
    @Override
    public void cerrarSesion(String token) {
        ctrl.procesarCierreSesion(token);
    }
 
    @Override
    public boolean validarToken(String token) {
        return ctrl.procesarValidacionToken(token);
    }

    
}
