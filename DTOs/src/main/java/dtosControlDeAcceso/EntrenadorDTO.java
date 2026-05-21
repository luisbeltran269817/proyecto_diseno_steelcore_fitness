/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dtosControlDeAcceso;

/**
 * DTO de entrenador para el módulo de control de acceso.
 * Muestra disponibilidad en tiempo real para la pantalla de selección.
 *
 * @author julian izaguirre
 */
public class EntrenadorDTO {

    private String idEntrenador;
    private String nombre;
    private EstadoEntrenador estado;
    private String idSucursal;

    public EntrenadorDTO() {
    }

    public EntrenadorDTO(String idEntrenador, String nombre, EstadoEntrenador estado, String idSucursal) {
        this.idEntrenador = idEntrenador;
        this.nombre = nombre;
        this.estado = estado;
        this.idSucursal = idSucursal;
    }
    
    public boolean estaDisponible() {
        return estado == EstadoEntrenador.LIBRE;
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

    public EstadoEntrenador getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntrenador estado) {
        this.estado = estado;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    @Override
    public String toString() {
        return "EntrenadorDTO{" + "idEntrenador=" + idEntrenador + ", nombre=" + nombre + ", estado=" + estado + ", idSucursal=" + idSucursal + '}';
    }
}
