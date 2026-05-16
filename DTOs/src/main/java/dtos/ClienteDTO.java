/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DTO para clientes
 * @author Gael Galaviz
 */
public class ClienteDTO extends UsuarioDTO {
    private String idCliente;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String curp;

    private List<MembresiaDTO> membresias;
    private List<VisitaDTO> historialVisitas;
    private CitaDTO citaBienvenida;

    public ClienteDTO() {
    }
    
    public ClienteDTO(String idCliente, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String curp, List<MembresiaDTO> membresias, List<VisitaDTO> historialVisitas, CitaDTO citaBienvenida, String correo, String nombre, String contraseña, Rol rol) {
        super(correo, nombre, contraseña, rol);
        this.idCliente = idCliente;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.membresias = membresias;
        this.historialVisitas = historialVisitas;
        this.citaBienvenida = citaBienvenida;
    }

    public ClienteDTO(String idCliente, String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String curp, List<MembresiaDTO> membresias, List<VisitaDTO> historialVisitas, CitaDTO citaBienvenida) {
        this.idCliente = idCliente;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.membresias = membresias;
        this.historialVisitas = historialVisitas;
        this.citaBienvenida = citaBienvenida;
    }
    
    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public List<MembresiaDTO> getMembresias() {
        return membresias;
    }

    public void setMembresias(List<MembresiaDTO> membresias) {
        this.membresias = membresias;
    }

    public List<VisitaDTO> getHistorialVisitas() {
        return historialVisitas;
    }

    public void setHistorialVisitas(List<VisitaDTO> historialVisitas) {
        this.historialVisitas = historialVisitas;
    }

    public CitaDTO getCitaBienvenida() {
        return citaBienvenida;
    }

    public void setCitaBienvenida(CitaDTO citaBienvenida) {
        this.citaBienvenida = citaBienvenida;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    
}
