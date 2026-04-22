/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import java.time.LocalDate;
import java.util.UUID;
import dtos.InicioSesionDTO;

/**
 *
 * @author julian izaguirre
 */
public class SocioBO {
    
    
    //Lo mismo aqui, solo definir el arreglo statico y los metodos de acceso
    private String idSocio;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String codigoQR;
    private LocalDate fechaNacimiento;
    private String idMembresia;
    private boolean activo;
 
    private String tokenSesion;
 
    public SocioBO(String idSocio, String nombre, String apellido,
                   String email, String telefono, LocalDate fechaNacimiento) {
        this.idSocio = idSocio;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = false;
    }
 
    public InicioSesionDTO iniciarSesion(String usuario, String password) {
        if (usuario == null || usuario.isBlank() ||
            password == null || password.isBlank()) {
            return null;
        }
 
        if (usuario.equals("admin@gmail.com") && password.equals("1234")) {
            this.tokenSesion = UUID.randomUUID().toString();
            this.activo = true;
            return new InicioSesionDTO(idSocio, nombre, tokenSesion, true);
        }
 
        return null; // credenciales incorrectas
    }
 
    public boolean cerrarSesion(String token) {
        if (token == null || token.isBlank()) return false;
        if (token.equals(this.tokenSesion)) {
            this.tokenSesion = null;
            this.activo = false;
            return true;
        }
        return false;
    }
 
    public boolean validarToken(String token) {
        if (token == null || token.isBlank()) return false;
        return token.equals(this.tokenSesion);
    }
 
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    
}
