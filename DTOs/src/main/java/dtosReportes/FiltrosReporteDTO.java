/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosReportes;

import java.time.LocalDate;

/**
 * Clase DTO con los filtros para un reporte
 * @author Noelia E.N
 */
public class FiltrosReporteDTO {

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String sucursal;
    private String tipoMembresia;
    private String entrenador;
    private String amenidad;

    public FiltrosReporteDTO() {
    }

    public FiltrosReporteDTO(LocalDate fechaInicio, LocalDate fechaFin, String sucursal,
            String tipoMembresia, String entrenador, String amenidad) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.sucursal = sucursal;
        this.tipoMembresia = tipoMembresia;
        this.entrenador = entrenador;
        this.amenidad = amenidad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getTipoMembresia() {
        return tipoMembresia;
    }

    public void setTipoMembresia(String tipoMembresia) {
        this.tipoMembresia = tipoMembresia;
    }

    public String getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(String entrenador) {
        this.entrenador = entrenador;
    }

    public String getAmenidad() {
        return amenidad;
    }

    public void setAmenidad(String amenidad) {
        this.amenidad = amenidad;
    }
}
