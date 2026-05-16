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

    private String idSucursal;

    private List<HorarioPojo> horarios;

    public EntrenadorPojo() {
    }
    
    public EntrenadorPojo(String idEntrenador, String nombre, String idSucursal, List<HorarioPojo> horarios) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
        this.horarios = horarios;
    }

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
    
    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public List<HorarioPojo> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioPojo> horarios) {
        this.horarios = horarios;
    }
    
    
}
