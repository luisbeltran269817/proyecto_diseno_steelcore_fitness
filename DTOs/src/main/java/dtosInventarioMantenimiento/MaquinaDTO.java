/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

/**
 *
 * @author Tungs
 */
public class MaquinaDTO {
    private String idMaquina;
    private String idSucursal;
    private String modelo;
    private String tipo;
    
    private UltimoMantenimientoDTO ultimoMantenimiento;
    
    public enum EstadoMaquinaDTO{
        BUENAS_CONDICIONES,
        MANTENIMIENTO_PREVENTVO,
        MANTENIMIENTO_URGENTE,
        INACTIVO
    }
    private EstadoMaquinaDTO estado;

    public MaquinaDTO(String idMaquina, String idSucursal, String modelo, String tipo, UltimoMantenimientoDTO ultimoMantenimiento, EstadoMaquinaDTO estado) {
        this.idMaquina = idMaquina;
        this.idSucursal = idSucursal;
        this.modelo = modelo;
        this.tipo = tipo;
        this.ultimoMantenimiento = ultimoMantenimiento;
        this.estado = estado;
    }

    

    public MaquinaDTO() {
    }
    
    
    public MaquinaDTO(String idMaquina, String idSucursal, String modelo, String tipo, EstadoMaquinaDTO estado) {
        this.idMaquina = idMaquina;
        this.idSucursal = idSucursal;
        this.modelo = modelo;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getIdMaquina() {
        return idMaquina;
    }

    public UltimoMantenimientoDTO getUltimoMantenimiento() {
        return ultimoMantenimiento;
    }

    public void setUltimoMantenimiento(UltimoMantenimientoDTO ultimoMantenimiento) {
        this.ultimoMantenimiento = ultimoMantenimiento;
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

    public EstadoMaquinaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoMaquinaDTO estado) {
        this.estado = estado;
    }
    
}
