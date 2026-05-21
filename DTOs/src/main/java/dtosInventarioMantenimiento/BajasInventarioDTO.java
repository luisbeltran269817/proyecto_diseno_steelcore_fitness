/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class BajasInventarioDTO {
    private String idBaja;
    private String motivo;
    private LocalDateTime fechaBaja;
    private String tipo;
    private String idMaquina;
    private String idPieza;

    public BajasInventarioDTO() {
    }

    public BajasInventarioDTO(String idBaja, String motivo, LocalDateTime fechaBaja, String tipo, String idMaquina, String idPieza) {
        this.idBaja = idBaja;
        this.motivo = motivo;
        this.fechaBaja = fechaBaja;
        this.tipo = tipo;
        this.idMaquina = idMaquina;
        this.idPieza = idPieza;
    }
    
    public String getIdBaja() {
        return idBaja;
    }

    public void setIdBaja(String idBaja) {
        this.idBaja = idBaja;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDateTime fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(String idPieza) {
        this.idPieza = idPieza;
    }
    
    
}
