/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Fachada;

import dtos.InicioSesionDTO;
import dtos.UsuarioDTO;

/**
 *
 * @author Tungs
 */
public interface IInicioSesion {
    
    public InicioSesionDTO iniciarSesion(String usuario, String password);
    public void cerrarSesion(String token);
    public boolean validarToken(String token);
}
