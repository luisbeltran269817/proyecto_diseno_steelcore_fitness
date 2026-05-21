/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author Tungs
 */
public class ClientePojo {
    private String idCliente;

    private UsuarioPojo usuario;

    private String apellidoPaterno;
    private String apellidoMaterno;
    
    private LocalDate fechaNacimiento;

    private String curp;

    private MembresiaActivaPojo membresiaActiva;

    private CitaPojo citaBienvenida;
    
    //atributo de mi caso de uso rutinas
    private List<RutinaPojo> rutinas;

    public ClientePojo() {
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public UsuarioPojo getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPojo usuario) {
        this.usuario = usuario;
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

    public MembresiaActivaPojo getMembresiaActiva() {
        return membresiaActiva;
    }

    public void setMembresiaActiva(MembresiaActivaPojo membresiaActiva) {
        this.membresiaActiva = membresiaActiva;
    }

    public CitaPojo getCitaBienvenida() {
        return citaBienvenida;
    }

    public void setCitaBienvenida(CitaPojo citaBienvenida) {
        this.citaBienvenida = citaBienvenida;
    }

    public List<RutinaPojo> getRutinas() {
        return rutinas;
    }

    public void setRutinas(List<RutinaPojo> rutinas) {
        this.rutinas = rutinas;
    }

    
    
}
