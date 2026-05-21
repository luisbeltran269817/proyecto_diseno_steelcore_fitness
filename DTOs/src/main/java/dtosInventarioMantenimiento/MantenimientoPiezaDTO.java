/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

/**
 *
 * @author Tungs
 */
public class MantenimientoPiezaDTO {
    private String idMantenimientoPiezaDTO;
    private String idMantenimiento;
    private String idPieza;
    private int cantidad;

    public MantenimientoPiezaDTO(String idMantenimientoPiezaDTO, String idMantenimiento, String idPieza, int cantidad) {
        this.idMantenimientoPiezaDTO = idMantenimientoPiezaDTO;
        this.idMantenimiento = idMantenimiento;
        this.idPieza = idPieza;
        this.cantidad = cantidad;
    }

    public MantenimientoPiezaDTO() {
    }

    public String getIdMantenimientoPiezaDTO() {
        return idMantenimientoPiezaDTO;
    }

    public void setIdMantenimientoPiezaDTO(String idMantenimientoPiezaDTO) {
        this.idMantenimientoPiezaDTO = idMantenimientoPiezaDTO;
    }

    public String getIdMantenimiento() {
        return idMantenimiento;
    }

    public void setIdMantenimiento(String idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    public String getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(String idPieza) {
        this.idPieza = idPieza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
