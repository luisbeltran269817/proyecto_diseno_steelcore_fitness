/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.CitaPojo;
import dominios.CitaPojo.EstadoCitaPojo;
import dtos.CitaDTO;

/**
 *
 * @author Tungs
 */
public class CitaMapper {
    
    public static CitaDTO toDTO(CitaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        CitaDTO dto = new CitaDTO();

        dto.setIdCita(pojo.getIdCita());
        dto.setIdCliente(pojo.getIdCliente());
        dto.setIdEntrenador(pojo.getIdEntrenador());
        dto.setIdSucursal(pojo.getIdSucursal());
        dto.setIdHorario(pojo.getIdHorario());
        dto.setFechaHora(pojo.getFechaHora());
        dto.setNotas(pojo.getNotas());

        if (pojo.getEstado() != null) {dto.setEstado(CitaDTO.EstadoCita.valueOf(pojo.getEstado().name()));
        }
        return dto;
    }

    public static CitaPojo toPojo(CitaDTO dto) {
        if (dto == null) {
            return null;
        }
        CitaPojo pojo = new CitaPojo();
        pojo.setIdCita(dto.getIdCita());
        pojo.setIdCliente(dto.getIdCliente());
        pojo.setIdEntrenador(dto.getIdEntrenador());
        pojo.setIdSucursal(dto.getIdSucursal());
        pojo.setIdHorario(dto.getIdHorario());
        pojo.setFechaHora(dto.getFechaHora());
        pojo.setNotas(dto.getNotas());
        if (dto.getEstado() != null) {
            pojo.setEstado(EstadoCitaPojo.valueOf(dto.getEstado().name()));
        }
        return pojo;
    }
}
