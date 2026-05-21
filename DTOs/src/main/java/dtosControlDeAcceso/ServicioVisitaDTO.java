/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;


import java.time.LocalDateTime;

/**
 * DTO que registra un servicio específico usado durante una visita.
 * Puede ser área general, una clase o un entrenador asignado.
 *
 * idReferencia apunta al id de la clase o entrenador según tipoServicio;
 * para AREA_GENERAL queda vacío.
 *
 * @author julian izaguirre
 */
public class ServicioVisitaDTO {

    private String idServicio;
    private String idVisita;
    private TipoServicio  tipoServicio;
    private String idReferencia;   
    private LocalDateTime fechaHora;

    public ServicioVisitaDTO() {
    }

    public ServicioVisitaDTO(String idServicio, String idVisita, TipoServicio tipoServicio, String idReferencia, LocalDateTime fechaHora) {
        this.idServicio = idServicio;
        this.idVisita = idVisita;
        this.tipoServicio = tipoServicio;
        this.idReferencia = idReferencia;
        this.fechaHora = fechaHora;
    }

    public String getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(String idServicio) {
        this.idServicio = idServicio;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(String idReferencia) {
        this.idReferencia = idReferencia;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "ServicioVisitaDTO{" + "idServicio=" + idServicio + ", idVisita=" + idVisita + ", tipoServicio=" + tipoServicio + ", idReferencia=" + idReferencia + ", fechaHora=" + fechaHora + '}';
    }
}
