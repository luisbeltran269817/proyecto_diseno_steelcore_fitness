/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorMapper {
    /**
     * convierte un pojo de entrenador a su dto
     * @param pojo
     * @return 
     */
    public static EntrenadorDTO toDTO(EntrenadorPojo pojo) {
        if (pojo == null) {
            return null;
        }
        
        EntrenadorDTO dto = new EntrenadorDTO();
        dto.setIdEntrenador(pojo.getIdEntrenador());
        dto.setNombre(pojo.getNombre());
        dto.setIdSucursal(pojo.getIdSucursal());

        List<HorarioDTO> horariosDTO = new ArrayList<>();
        if (pojo.getHorarios() != null) {
            for (HorarioPojo horarioPojo : pojo.getHorarios()) {
                horariosDTO.add(HorarioMapper.toDTO(horarioPojo));
            }
        }
        dto.setHorarios(horariosDTO);
        return dto;
    }
    
    /**
     * convierte un dto de entrenador a su pojo
     * @param dto
     * @return 
     */
    public static EntrenadorPojo toPojo(EntrenadorDTO dto) {
        if (dto == null) {
            return null;
        }

        EntrenadorPojo pojo = new EntrenadorPojo();
        pojo.setIdEntrenador(dto.getIdEntrenador());
        pojo.setNombre(dto.getNombre());
        pojo.setIdSucursal(dto.getIdSucursal());

        List<HorarioPojo> horariosPojo = new ArrayList<>();
        if (dto.getHorarios() != null) {
            for (HorarioDTO horarioDTO : dto.getHorarios()) {
                horariosPojo.add(HorarioMapper.toPojo(horarioDTO));
            }
        }
        pojo.setHorarios(horariosPojo);
        return pojo;
    }
}