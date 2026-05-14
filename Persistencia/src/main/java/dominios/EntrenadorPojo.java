/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorPojo {
    private String idEntrenador;
    private String nombre;
    private List<HorarioPojo> horarios;
    private String idSucursal;
    
    //constructores
    public EntrenadorPojo() {
    }

    public EntrenadorPojo(String idEntrenador, String nombre, List<HorarioPojo> horarios, String idSucursal) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.horarios = horarios;
        this.idSucursal = idSucursal;
    }

    //get y set
    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<HorarioPojo> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioPojo> horarios) {
        this.horarios = horarios;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }
    
    
}
