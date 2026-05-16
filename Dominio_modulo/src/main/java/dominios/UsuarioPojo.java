/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

/**
 *
 * @author Tungs
 */
public class UsuarioPojo {
    private String correo;
    private String nombre;
    private String contraseña;
    private RolUsuarioPojo rol;
    public enum RolUsuarioPojo {
        ADMIN,
        CLIENTE
    }

    public UsuarioPojo() {
    }

    public UsuarioPojo(String correo, String nombre, String contraseña, RolUsuarioPojo rol) {
        this.correo = correo;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public RolUsuarioPojo getRol() {
        return rol;
    }

    public void setRol(RolUsuarioPojo rol) {
        this.rol = rol;
    }
    
    
}
