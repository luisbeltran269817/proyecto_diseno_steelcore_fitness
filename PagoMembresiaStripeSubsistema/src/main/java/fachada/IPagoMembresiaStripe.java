/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.PeticionPagoGenDTO;
import dtos.RespuestaPagoGenDTO;
import dtosInfraestructura.PeticionPagoDTO;
import dtosInfraestructura.RespuestaPagoDTO;

/**
 *
 * @author Tungs
 */
public interface IPagoMembresiaStripe {
    RespuestaPagoGenDTO procesarPago(PeticionPagoGenDTO peticionGen);
}
