/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

/**
 *
 * @author Tungs
 */
public class PiezaPojo {
    private String idPieza;
    private String nombre;
    private int stock;

    private EstadoPieza estado;

    private BajaPojo baja;

    public enum EstadoPieza {
        ACTIVO,
        INACTIVO
    }

    public PiezaPojo() {
    }

    public String getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(String idPieza) {
        this.idPieza = idPieza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public EstadoPieza getEstado() {
        return estado;
    }

    public void setEstado(EstadoPieza estado) {
        this.estado = estado;
    }

    public BajaPojo getBaja() {
        return baja;
    }
    
    public void setBaja(BajaPojo baja) {
        this.baja = baja;
    }
}
