/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominioAcceso;

import java.time.LocalDateTime;

/**
 *
 * @author julian izaguirre
 */
public class VisitaAccesoPojo {
    private String idVisita;
    private String idCliente;
    private String idSucursal;
    private LocalDateTime fechaHora;
    private String tipoServicio;
    private String idRecursoAsignado;

    public VisitaAccesoPojo() {
    }

    public VisitaAccesoPojo(String idVisita, String idCliente, String idSucursal, LocalDateTime fechaHora, String tipoServicio, String idRecursoAsignado) {
        this.idVisita = idVisita;
        this.idCliente = idCliente;
        this.idSucursal = idSucursal;
        this.fechaHora = fechaHora;
        this.tipoServicio = tipoServicio;
        this.idRecursoAsignado = idRecursoAsignado;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public String getIdRecursoAsignado() {
        return idRecursoAsignado;
    }

    public void setIdRecursoAsignado(String idRecursoAsignado) {
        this.idRecursoAsignado = idRecursoAsignado;
    }

    @Override
    public String toString() {
        return "VisitaAccesoPojo{" + "idVisita=" + idVisita + ", idCliente=" + idCliente + ", idSucursal=" + idSucursal + ", fechaHora=" + fechaHora + ", tipoServicio=" + tipoServicio + ", idRecursoAsignado=" + idRecursoAsignado + '}';
    }
}
