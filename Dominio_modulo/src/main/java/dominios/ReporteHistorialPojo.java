/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * POJO de dominio/persistencia que representa un reporte generado guardado en
 * el historial.
 *
 * Esta clase se utiliza para guardar en MongoDB un resumen del reporte
 * generado. No almacena el PDF, únicamente los datos principales que permiten
 * consultar reportes anteriores.
 *
 * @author Noelia E.N.
 */
public class ReporteHistorialPojo {

    private String idReporte;
    private String tipoReporte;

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
    public ReporteHistorialPojo() {
    }

    public String getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(String idReporte) {
        this.idReporte = idReporte;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
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
