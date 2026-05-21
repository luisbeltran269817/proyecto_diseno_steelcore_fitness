/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

/**
 *
 * @author Tungs
 */
public class MantenimientoPiezaPojo {
    private String idMantenimientoPieza;
    private String idMantenimiento;
    private String idPieza;
    private int cantidad;

    public MantenimientoPiezaPojo() {
    }

    public String getIdMantenimientoPieza() {
        return idMantenimientoPieza;
    }

    public void setIdMantenimientoPieza(String idMantenimientoPieza) {
        this.idMantenimientoPieza = idMantenimientoPieza;
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
