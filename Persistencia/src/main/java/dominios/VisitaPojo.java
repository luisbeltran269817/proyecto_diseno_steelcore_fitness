/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class VisitaPojo {
     private String idVisita;

    private String idCliente;

    private String idSucursal;

    private LocalDateTime fechaHora;

    public VisitaPojo() {
    }
    
    public VisitaPojo(String idVisita, String idCliente, String idSucursal, LocalDateTime fechaHora) {
        this.idVisita = idVisita;
        this.idCliente = idCliente;
        this.idSucursal = idSucursal;
        this.fechaHora = fechaHora;
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
    
    
}
