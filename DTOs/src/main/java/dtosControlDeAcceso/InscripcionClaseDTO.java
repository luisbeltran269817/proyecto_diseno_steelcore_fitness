/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalDateTime;

/**
 * DTO que representa la inscripción de un socio a una clase durante su visita.
 *
 * @author julian izaguirre
 */
public class InscripcionClaseDTO {

    private String idClase;
    private String idSocio;
    private String idVisita;
    private LocalDateTime fechaHora;

    public InscripcionClaseDTO() {
    }

    public InscripcionClaseDTO(String idClase, String idSocio, String idVisita, LocalDateTime fechaHora) {
        this.idClase = idClase;
        this.idSocio = idSocio;
        this.idVisita = idVisita;
        this.fechaHora = fechaHora;
    }

    public String getIdClase() {
        return idClase;
    }

    public void setIdClase(String idClase) {
        this.idClase = idClase;
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

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "InscripcionClaseDTO{" + "idClase=" + idClase + ", idSocio=" + idSocio + ", idVisita=" + idVisita + ", fechaHora=" + fechaHora + '}';
    }
}
