/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import dtos.InicioSesionDTO;
import java.util.UUID;

/**
 *
 * @author julian izaguirre
 */
public class ClaseControlInicioSesion {
    
    public InicioSesionDTO procesarInicioSesion(String usuario, String password) {
        if (usuario == null || usuario.isEmpty() ||
            password == null || password.isEmpty()) {
            return null;
        }
        // contra
        if (usuario.equals("admin") && password.equals("1234")) {
            String token = UUID.randomUUID().toString();
            return new InicioSesionDTO("USR-001", "Administrador", token, true);
        }
        return null;
    }
 
    public boolean procesarCierreSesion(String token) {
        if (token == null || token.isEmpty()) return false;
        return true;
    }
 
    public boolean procesarValidacionToken(String token) {
        if (token == null || token.isEmpty()) return false;
        return !token.isEmpty();
    }
    
    
}
