/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.DetalleRutinaPojo;
import dominios.RutinaPojo;
import dtos.DetalleRutinaDTO;
import dtos.RutinaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase mapper de rutinas de la capa de negocio
 * @author luiscarlosbeltran
 */
public class RutinaMapper {
    /**
     * conveirte un rutina de su pojo a dto
     * @param pojo
     * @return 
     */
    public static RutinaDTO toDTO(RutinaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        RutinaDTO dto = new RutinaDTO();
        dto.setIdEntrenador(pojo.getIdEntrenador());
        dto.setNombre(pojo.getNombre());
        dto.setFechaCreacion(pojo.getFechaCreacion());
        if (pojo.getDetalles() != null) {
            List<DetalleRutinaDTO> detallesDTO = new ArrayList<>();
            for (DetalleRutinaPojo detalle : pojo.getDetalles()) {
                detallesDTO.add(DetalleRutinaMapper.toDTO(detalle));
            }
            dto.setDetalles(detallesDTO);
        } else {
            dto.setDetalles(new ArrayList<>());
        }
        return dto;
    }
    /**
     * convierte rutina de su dto a pojo
     * @param dto
     * @return 
     */
    public static RutinaPojo toPojo(RutinaDTO dto) {
        if (dto == null) {
            return null;
        }
        RutinaPojo pojo = new RutinaPojo();
        pojo.setIdEntrenador(dto.getIdEntrenador());
        pojo.setNombre(dto.getNombre());
        pojo.setFechaCreacion(dto.getFechaCreacion());
        if (dto.getDetalles() != null) {
            List<DetalleRutinaPojo> detallesPojo = new ArrayList<>();
            for (DetalleRutinaDTO detalle: dto.getDetalles()) {
                detallesPojo.add(DetalleRutinaMapper.toPojo(detalle));
            }
            pojo.setDetalles(detallesPojo);
        } else {
            pojo.setDetalles(new ArrayList<>());
        }
        return pojo;
    }
}
