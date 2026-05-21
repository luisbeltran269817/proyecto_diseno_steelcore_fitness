/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalDateTime;

/**
 * DTO de membresía del caso individual de control de acceso.
 * Contiene los campos relevantes para validar el acceso del socio.
 *
 * @author julian izaguirre
 */
public class MembresiaDTO {

    private String idMembresia;
    private String tipoPlan;
    private EstadoMembresia estado;
    private boolean incluyeEntrenador;
    private boolean incluyeClases;
    private LocalDateTime fechaCaducidad;

    public MembresiaDTO() {
    }

    public MembresiaDTO(String idMembresia, String tipoPlan, EstadoMembresia estado, boolean incluyeEntrenador, boolean incluyeClases, LocalDateTime fechaCaducidad) {
        this.idMembresia = idMembresia;
        this.tipoPlan = tipoPlan;
        this.estado = estado;
        this.incluyeEntrenador = incluyeEntrenador;
        this.incluyeClases = incluyeClases;
        this.fechaCaducidad = fechaCaducidad;
    }
    
    public boolean estaActiva() {
        return estado == EstadoMembresia.ACTIVA
                && (fechaCaducidad == null || !LocalDateTime.now().isAfter(fechaCaducidad));
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public EstadoMembresia getEstado() {
        return estado;
    }

    public void setEstado(EstadoMembresia estado) {
        this.estado = estado;
    }

    public boolean isIncluyeEntrenador() {
        return incluyeEntrenador;
    }

    public void setIncluyeEntrenador(boolean incluyeEntrenador) {
        this.incluyeEntrenador = incluyeEntrenador;
    }

    public boolean isIncluyeClases() {
        return incluyeClases;
    }

    public void setIncluyeClases(boolean incluyeClases) {
        this.incluyeClases = incluyeClases;
    }

    public LocalDateTime getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDateTime fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String toString() {
        return "MembresiaDTO{" + "idMembresia=" + idMembresia + ", tipoPlan=" + tipoPlan + ", estado=" + estado + ", incluyeEntrenador=" + incluyeEntrenador + ", incluyeClases=" + incluyeClases + ", fechaCaducidad=" + fechaCaducidad + '}';
    }
}
