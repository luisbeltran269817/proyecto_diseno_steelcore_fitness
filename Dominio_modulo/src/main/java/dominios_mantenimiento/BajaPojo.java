/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

import java.time.LocalDateTime;

/**
 *
 * @author Tungs
 */
public class BajaPojo {
     private String idBaja;
    private String motivo;
    private LocalDateTime fechaBaja;
    private String tipo;

    public BajaPojo() {
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
}
