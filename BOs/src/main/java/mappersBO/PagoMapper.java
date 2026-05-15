/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.PagoPojo;
import dominios.PagoPojo.EstadoPagoPojo;
import dtos.PagoDTO;

/**
 *
 * @author Tungs
 */
public class PagoMapper {
    
    //NO HAY TIEMPO DE LOGGERS LUEGO
    public static PagoPojo toPojo(PagoDTO dto) {

        if (dto == null) {
            return null;
        }
        PagoPojo pojo = new PagoPojo();

        pojo.setIdPago(dto.getIdPago());
        pojo.setIdCliente(dto.getIdCliente());
        pojo.setMonto(dto.getMonto());
        pojo.setMetodoPago(dto.getMetodoPago());
        pojo.setEstado(EstadoPagoPojo.valueOf(dto.getEstado().name()));
        pojo.setFecha(dto.getFecha());

        return pojo;
    }

    public static PagoDTO toDTO(PagoPojo pojo) {
        if (pojo == null) {
            return null;
        }
        PagoDTO dto = new PagoDTO();
        dto.setIdPago(pojo.getIdPago());
        dto.setIdCliente(pojo.getIdCliente());
        dto.setMonto(pojo.getMonto());
        dto.setMetodoPago(pojo.getMetodoPago());
        dto.setEstado(PagoDTO.EstadoPago.valueOf(pojo.getEstado().name()));
        dto.setFecha(pojo.getFecha());

        return dto;
    }
}
