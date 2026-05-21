/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalDateTime;

/**
 * DTO que registra la asignación de un entrenador a un socio durante su visita.
 *
 * @author julian izaguirre
 */
public class AsignacionEntrenadorDTO {

    private String idEntrenador;
    private String idSocio;
    private String idVisita;
    private LocalDateTime horaAsignacion;
    private TipoServicio tipoServicio; 

    public AsignacionEntrenadorDTO() {
    }

    public AsignacionEntrenadorDTO(String idEntrenador, String idSocio, String idVisita, LocalDateTime horaAsignacion, TipoServicio tipoServicio) {
        this.idEntrenador = idEntrenador;
        this.idSocio = idSocio;
        this.idVisita = idVisita;
        this.horaAsignacion = horaAsignacion;
        this.tipoServicio = tipoServicio;
    }

    public String getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public LocalDateTime getHoraAsignacion() {
        return horaAsignacion;
    }

    public void setHoraAsignacion(LocalDateTime horaAsignacion) {
        this.horaAsignacion = horaAsignacion;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @Override
    public String toString() {
        return "AsignacionEntrenadorDTO{" + "idEntrenador=" + idEntrenador + ", idSocio=" + idSocio + ", idVisita=" + idVisita + ", horaAsignacion=" + horaAsignacion + ", tipoServicio=" + tipoServicio + '}';
    }
}
