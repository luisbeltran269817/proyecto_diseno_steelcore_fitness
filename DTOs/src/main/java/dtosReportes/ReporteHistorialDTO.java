/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosReportes;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO que representa un reporte generado guardado en el historial.
 *
 * Esta clase se utiliza para transportar información resumida de reportes
 * generados entre las capas del sistema. No guarda el archivo PDF, solo los
 * datos principales necesarios para consultar los últimos reportes realizados.
 *
 * @author Noelia E.N.
 */
public class ReporteHistorialDTO {

    private String idReporte;
    private TipoReporteDTO tipoReporte;

    private LocalDateTime fechaGeneracion;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    private String sucursal;
    private String tipoMembresia;
    private String entrenador;
    private String amenidad;

    private double totalIngresos;
    private int membresiasVendidas;
    private int renovaciones;
    private int nuevosSocios;

    private String sucursalConMasVentas;
    private String entrenadorConMasClientes;
    private String amenidadMasSolicitada;
    private String tipoMembresiaMasVendida;

    /**
     * Constructor vacío.
     */
    public ReporteHistorialDTO() {
    }

    /**
     * Obtiene el identificador del reporte guardado.
     *
     * @return id del reporte.
     */
    public String getIdReporte() {
        return idReporte;
    }

    /**
     * Establece el identificador del reporte guardado.
     *
     * @param idReporte id del reporte.
     */
    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    /**
     * Obtiene el tipo de reporte generado.
     *
     * @return tipo de reporte.
     */
    public TipoReporteDTO getTipoReporte() {
        return tipoReporte;
    }

    /**
     * Establece el tipo de reporte generado.
     *
     * @param tipoReporte tipo de reporte.
     */
    public void setTipoReporte(TipoReporteDTO tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    /**
     * Obtiene la fecha y hora en que se generó el reporte.
     *
     * @return fecha de generación.
     */
    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    /**
     * Establece la fecha y hora en que se generó el reporte.
     *
     * @param fechaGeneracion fecha de generación.
     */
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    /**
     * Obtiene la fecha inicial del periodo reportado.
     *
     * @return fecha inicial.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha inicial del periodo reportado.
     *
     * @param fechaInicio fecha inicial.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha final del periodo reportado.
     *
     * @return fecha final.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha final del periodo reportado.
     *
     * @param fechaFin fecha final.
     */
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

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public int getMembresiasVendidas() {
        return membresiasVendidas;
    }

    public void setMembresiasVendidas(int membresiasVendidas) {
        this.membresiasVendidas = membresiasVendidas;
    }

    public int getRenovaciones() {
        return renovaciones;
    }

    public void setRenovaciones(int renovaciones) {
        this.renovaciones = renovaciones;
    }

    public int getNuevosSocios() {
        return nuevosSocios;
    }

    public void setNuevosSocios(int nuevosSocios) {
        this.nuevosSocios = nuevosSocios;
    }

    public String getSucursalConMasVentas() {
        return sucursalConMasVentas;
    }

    public void setSucursalConMasVentas(String sucursalConMasVentas) {
        this.sucursalConMasVentas = sucursalConMasVentas;
    }

    public String getEntrenadorConMasClientes() {
        return entrenadorConMasClientes;
    }

    public void setEntrenadorConMasClientes(String entrenadorConMasClientes) {
        this.entrenadorConMasClientes = entrenadorConMasClientes;
    }

    public String getAmenidadMasSolicitada() {
        return amenidadMasSolicitada;
    }

    public void setAmenidadMasSolicitada(String amenidadMasSolicitada) {
        this.amenidadMasSolicitada = amenidadMasSolicitada;
    }

    public String getTipoMembresiaMasVendida() {
        return tipoMembresiaMasVendida;
    }

    public void setTipoMembresiaMasVendida(String tipoMembresiaMasVendida) {
        this.tipoMembresiaMasVendida = tipoMembresiaMasVendida;
    }
}
