/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gael Galaviz
 */
public class ClienteDTO extends UsuarioDTO {
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaNacimiento;
    private String curp;

    private List<MembresiaDTO> membresias;
    private List<VisitaDTO> historialVisitas;

    //esto noni
    private boolean tieneCitaBienvenidaAgendada;
    private String idCitaBienvenida;

    public ClienteDTO(String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String curp, List<MembresiaDTO> membresias, List<VisitaDTO> historialVisitas, boolean tieneCitaBienvenidaAgendada, String idCitaBienvenida, String correo, String nombre, String contraseña, Rol rol) {
        super(correo, nombre, contraseña, rol);
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.membresias = membresias;
        this.historialVisitas = historialVisitas;
        this.tieneCitaBienvenidaAgendada = tieneCitaBienvenidaAgendada;
        this.idCitaBienvenida = idCitaBienvenida;
    }

    public ClienteDTO(String apellidoPaterno, String apellidoMaterno, LocalDate fechaNacimiento, String curp, List<MembresiaDTO> membresias, List<VisitaDTO> historialVisitas, boolean tieneCitaBienvenidaAgendada, String idCitaBienvenida) {
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaNacimiento = fechaNacimiento;
        this.curp = curp;
        this.membresias = membresias;
        this.historialVisitas = historialVisitas;
        this.tieneCitaBienvenidaAgendada = tieneCitaBienvenidaAgendada;
        this.idCitaBienvenida = idCitaBienvenida;
    }

    public ClienteDTO() {
        
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

    public boolean isTieneCitaBienvenidaAgendada() {
        return tieneCitaBienvenidaAgendada;
    }

    public void setTieneCitaBienvenidaAgendada(boolean tieneCitaBienvenidaAgendada) {
        this.tieneCitaBienvenidaAgendada = tieneCitaBienvenidaAgendada;
    }

    public String getIdCitaBienvenida() {
        return idCitaBienvenida;
    }

    public void setIdCitaBienvenida(String idCitaBienvenida) {
        this.idCitaBienvenida = idCitaBienvenida;
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
