/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalTime;

/**
 * DTO de una clase ofrecida en la sucursal.
 * Solo incluye los campos necesarios para la pantalla de selección.
 *
 * @author julian izaguirre
 */
public class ClaseDTO {

    private String idClase;
    private String nombre;
    private LocalTime horario;
    private int cupoDisponible;
    private String idSucursal;
    private String diaSemana;
    private int cupoMaximo;

    public ClaseDTO() {
    }

    public ClaseDTO(String idClase, String nombre, LocalTime horario, int cupoDisponible, String idSucursal, String diaSemana, int cupoMaximo) {
        this.idClase = idClase;
        this.nombre = nombre;
        this.horario = horario;
        this.cupoDisponible = cupoDisponible;
        this.idSucursal = idSucursal;
        this.diaSemana = diaSemana;
        this.cupoMaximo = cupoMaximo;
    }
    
    public boolean estaLlena() {
        return cupoDisponible <= 0;
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

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public int getCupoDisponible() {
        return cupoDisponible;
    }

    public void setCupoDisponible(int cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }

    @Override
    public String toString() {
        return "ClaseDTO{" + "idClase=" + idClase + ", nombre=" + nombre + ", horario=" + horario + ", cupoDisponible=" + cupoDisponible + ", idSucursal=" + idSucursal + ", diaSemana=" + diaSemana + ", cupoMaximo=" + cupoMaximo + '}';
    }
}
