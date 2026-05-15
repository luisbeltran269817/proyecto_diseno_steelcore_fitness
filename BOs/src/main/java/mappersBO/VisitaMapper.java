/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.VisitaPojo;
import dtos.SucursalDTO;
import dtos.VisitaDTO;

/**
 *
 * @author Tungs
 */
public class VisitaMapper {
     public static VisitaDTO toDTO(VisitaPojo pojo,SucursalDTO sucursal) {
        if (pojo == null) {
            return null;
        }
        VisitaDTO dto = new VisitaDTO();
        dto.setIdVisita( pojo.getIdVisita());
        dto.setFechaHora( pojo.getFechaHora());
        if (sucursal != null) {
            dto.setGimnasio( sucursal.getNombre());
            dto.setCalle(sucursal.getCalle());
            dto.setColonia( sucursal.getColonia());
            dto.setCiudad(sucursal.getCiudad());
        }

        return dto;
    }

    public static VisitaPojo toPojo(String idCliente,String idSucursal, VisitaDTO dto) {
        if (dto == null) {
            return null;
        }
        VisitaPojo pojo = new VisitaPojo();
        pojo.setIdVisita( dto.getIdVisita());
        pojo.setIdCliente(idCliente);
        pojo.setIdSucursal(idSucursal);
        pojo.setFechaHora( dto.getFechaHora());
        return pojo;
    }
}
