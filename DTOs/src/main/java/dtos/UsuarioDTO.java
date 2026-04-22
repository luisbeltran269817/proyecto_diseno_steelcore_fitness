/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Tungs
 */
public class UsuarioDTO {
    private String nombre;
    private String correo;
    
    public enum Rol {
        ADMIN,
        CLIENTE,
    }
    
    private Rol rol;
    private boolean membresiaActiva;
    private String nombreMembresia;

    public UsuarioDTO(String nombre, String correo, Rol rol, boolean membresiaActiva, String nombreMembresia) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
        this.membresiaActiva = membresiaActiva;
        this.nombreMembresia = nombreMembresia;
    }

    public UsuarioDTO() {
    }
    
    public UsuarioDTO(String nombre, String correo, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol = rol;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean getMembresiaActiva() {
        return membresiaActiva;
    }

    public void setMembresiaActiva(boolean membresiaActiva) {
        this.membresiaActiva = membresiaActiva;
    }

    public String getNombreMembresia() {
        return nombreMembresia;
    }

    public void setNombreMembresia(String nombreMembresia) {
        this.nombreMembresia = nombreMembresia;
    }
    
    
}
