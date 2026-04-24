/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class VisitaDTO {
    private String idVisita;
    private String gimnasio;
    private LocalDateTime fechaHora;
    private String calle;
    private String colonia;
    private String ciudad;

    public VisitaDTO(String idVisita, String gimnasio, LocalDateTime fechaHora, String calle, String colonia, String ciudad) {
        this.idVisita = idVisita;
        this.gimnasio = gimnasio;
        this.fechaHora = fechaHora;
        this.calle = calle;
        this.colonia = colonia;
        this.ciudad = ciudad;
    }
    
    public String getGimnasio() {
        return gimnasio;
    }
    
    public void setGimnasio(String gimnasio) {
        this.gimnasio = gimnasio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public VisitaDTO() {
    }
    
    
}
