/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dominioAcceso;

import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class ClasePojo {

    private String idClase;
    private String nombre;
    private String idSucursal;
    private String idPlan;
    private String diaSemana;
    private LocalTime horaInicio;
    private int cupoMaximo;
    private int cupoActual;
    private List<String> inscritos;

    public ClasePojo() {
    }

    public ClasePojo(String idClase, String nombre, String idSucursal, String idPlan, String diaSemana, LocalTime horaInicio, int cupoMaximo, int cupoActual, List<String> inscritos) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.idSucursal = idSucursal;
        this.idPlan = idPlan;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.cupoMaximo = cupoMaximo;
        this.cupoActual = cupoActual;
        this.inscritos = inscritos;
    }

    public boolean estaLlena() {
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

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
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
