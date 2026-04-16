/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Tungs
 */
public class InicioSesionDTO {
    private String correo;
    private String contrasena;
    

    public InicioSesionDTO(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    public InicioSesionDTO() {
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    
}
