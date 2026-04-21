/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author julian izaguirre
 */
public class CompraDTO {
    private String idSocio;
    private String idPlan;
    private String idSucursal;
    private String idInstructor;
    private String idHorario;
    private String metodoPago;
    private double monto;
 
    public CompraDTO(String idSocio, String idPlan, String idSucursal,
                     String idInstructor, String idHorario,
                     String metodoPago, double monto) {
        this.idSocio = idSocio;
        this.idPlan = idPlan;
        this.idSucursal = idSucursal;
        this.idInstructor = idInstructor;
        this.idHorario = idHorario;
        this.metodoPago = metodoPago;
        this.monto = monto;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(String idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(String idHorario) {
        this.idHorario = idHorario;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
}
