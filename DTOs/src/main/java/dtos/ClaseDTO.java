/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 * esto pa mi
 * 
 * @author julian izaguirre
 */
public class ClaseDTO {
    private String idClase;
    private String nombre;
    private String idSucursal;
    private String idPlan;
    private String horario;
    private int cupoMaximo;
    private int cupoActual;
    private java.util.List<String> inscritos;

    public ClaseDTO() {
    }

    public ClaseDTO(String idClase, String nombre, String idSucursal, String idPlan, String horario, int cupoMaximo, int cupoActual, List<String> inscritos) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
        this.idPlan = idPlan;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.cupoActual = cupoActual;
        this.inscritos = inscritos;
    }
    
    public boolean estaLlena() { // se escucha bien en mi mente
        return cupoActual >= cupoMaximo; 
    }
    
    public boolean estaInscrito(String idCliente) {
        return inscritos != null && inscritos.contains(idCliente);
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
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

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    public int getCupoActual() {
        return cupoActual;
    }

    public void setCupoActual(int cupoActual) {
        this.cupoActual = cupoActual;
    }

    public List<String> getInscritos() {
        return inscritos;
    }

    public void setInscritos(List<String> inscritos) {
        this.inscritos = inscritos;
    }
    
    
}
