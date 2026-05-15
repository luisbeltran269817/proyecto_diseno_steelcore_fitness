/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import EstadoMembresia.EstadoMembresiaDTO;
import dominios.MembresiaPojo;
import dominios.MembresiaPojo.EstadoMembresiaPojo;
import dtos.MembresiaDTO;

/**
 *
 * @author Tungs
 */
public class MembresiaMapper {
    
    public static MembresiaPojo toPojo(MembresiaDTO dto) {
        if (dto == null) {
            return null;
        }
        MembresiaPojo pojo = new MembresiaPojo();
        pojo.setIdMembresia(dto.getIdMembresia());
        pojo.setIdCliente(dto.getIdCliente());
        pojo.setIdPlan(dto.getIdPlan());
        pojo.setIdSucursal(dto.getIdSucursal());
        pojo.setAmenidadesExtra(AmenidadMapper.toPojoList(dto.getAmenidadesExtra()));
        pojo.setMetodoPago(dto.getMetodoPago());
        pojo.setMontoPagado(dto.getMontoPagado());
        pojo.setCodigoQR(dto.getCodigoQR());
        pojo.setFechaTramite(dto.getFechaTramite());
        pojo.setFechaCaducidad(dto.getFechaCaducidad());
        pojo.setEstado(EstadoMembresiaPojo.valueOf(dto.getEstado().name()));

        pojo.setPago(PagoMapper.toPojo(dto.getPago()));
        return pojo;
    }

    public static MembresiaDTO toDTO(MembresiaPojo pojo) {

        if (pojo == null) {
            return null;
        }

        MembresiaDTO dto = new MembresiaDTO();

        dto.setIdMembresia(pojo.getIdMembresia());
        dto.setIdCliente(pojo.getIdCliente());
        dto.setIdPlan(pojo.getIdPlan());
        dto.setIdSucursal(pojo.getIdSucursal());

        dto.setAmenidadesExtra(AmenidadMapper.toDTOList(pojo.getAmenidadesExtra()));

        dto.setMetodoPago(pojo.getMetodoPago());
        dto.setMontoPagado(pojo.getMontoPagado());

        dto.setCodigoQR(pojo.getCodigoQR());

        dto.setFechaTramite(pojo.getFechaTramite());
        dto.setFechaCaducidad(pojo.getFechaCaducidad());

        dto.setEstado(MembresiaDTO.EstadoMembresia.valueOf(pojo.getEstado().name()));

        dto.setPago(PagoMapper.toDTO(pojo.getPago()));

        return dto;
    }
}
