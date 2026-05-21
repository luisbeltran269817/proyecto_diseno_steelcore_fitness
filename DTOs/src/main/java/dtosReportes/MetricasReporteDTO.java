/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosReportes;

/**
 * Clase DTO para la vista rápida de reportes y en la pantalla del reporte
 * @author Noelia E.N
 */
public class MetricasReporteDTO {

    private double totalIngresos;
    private int membresiasVendidas;
    private int renovaciones;
    private int nuevosSocios;
    private String sucursalConMasVentas;
    private String entrenadorConMasClientes;
    private String amenidadMasSolicitada;
    private String tipoMembresiaMasVendida;

    public MetricasReporteDTO() {
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
