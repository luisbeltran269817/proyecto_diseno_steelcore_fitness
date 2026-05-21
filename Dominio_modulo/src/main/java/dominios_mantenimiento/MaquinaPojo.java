/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

/**
 *
 * @author Tungs
 */
public class MaquinaPojo {

    private String idMaquina;
    private String idSucursal;
    private String modelo;
    private String tipo;
    private EstadoMaquina estado;
    private UltimoMantenimientoPojo ultimoMantenimiento;
    private BajaPojo baja;

    public enum EstadoMaquina {
        BUENAS_CONDICIONES,
        MANTENIMIENTO_PREVENTVO,
        MANTENIMIENTO_URGENTE,
        INACTIVO
    }

    public MaquinaPojo() {
    }

    public String getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(String idMaquina) {
        this.idMaquina = idMaquina;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public EstadoMaquina getEstado() {
        return estado;
    }

    public void setEstado(EstadoMaquina estado) {
        this.estado = estado;
    }

    public UltimoMantenimientoPojo getUltimoMantenimiento() {
        return ultimoMantenimiento;
    }

    public void setUltimoMantenimiento(UltimoMantenimientoPojo ultimoMantenimiento) {
        this.ultimoMantenimiento = ultimoMantenimiento;
    }

    public BajaPojo getBaja() {
        return baja;
    }

    public void setBaja(BajaPojo baja) {
        this.baja = baja;
    }

}
