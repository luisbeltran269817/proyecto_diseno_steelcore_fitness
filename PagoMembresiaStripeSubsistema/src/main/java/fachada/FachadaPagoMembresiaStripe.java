/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import control.ControlPagoMembresiaStripe;
import dtos.PeticionPagoGenDTO;
import dtos.RespuestaPagoGenDTO;
import dtosInfraestructura.PeticionPagoDTO;
import dtosInfraestructura.RespuestaPagoDTO;

/**
 *
 * @author Tungs
 */
public class FachadaPagoMembresiaStripe implements IPagoMembresiaStripe{
    private final ControlPagoMembresiaStripe controlPago;

    public FachadaPagoMembresiaStripe() {
        this.controlPago = new ControlPagoMembresiaStripe();
    }
    
    @Override
    public RespuestaPagoGenDTO procesarPago(PeticionPagoGenDTO solicitud) {
        return controlPago.ejecutarCobro(solicitud);
    }
}
