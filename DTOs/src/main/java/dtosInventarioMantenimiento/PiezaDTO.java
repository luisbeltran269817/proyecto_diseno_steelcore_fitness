/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

/**
 *
 * @author Tungs
 */
public class PiezaDTO {
    private String idPieza;
    private String nombre;
    
    public  enum EstadoPiezaDTO {
        ACTIVO, INACTIVO
    }
    private EstadoPiezaDTO estado;
    
    private int Stock;

    public PiezaDTO() {
    }

    public PiezaDTO(String idPieza, String nombre, EstadoPiezaDTO estado, int Stock) {
        this.idPieza = idPieza;
        this.nombre = nombre;
        this.estado = estado;
        this.Stock = Stock;
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

    public EstadoPiezaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoPiezaDTO estado) {
        this.estado = estado;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    
}
