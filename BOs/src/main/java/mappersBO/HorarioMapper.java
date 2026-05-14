/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.HorarioPojo;
import dtos.HorarioDTO;

/**
 *
 * @author luiscarlosbeltran
 */
public class HorarioMapper {
    /**
     * metodo que convierte un horariopojo a un horariodto
     * @param pojo
     * @return 
     */
    public static HorarioDTO toDTO(HorarioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        
        HorarioDTO dto = new HorarioDTO();
        dto.setNombreDia(pojo.getNombreDia());
        dto.setHoraInicio(pojo.getInicio());
        dto.setHoraFin(pojo.getFin());
        dto.setDisponible(pojo.isDisponible());
        return dto;
    }
    
    /**
     * metodo que convierte un horariodto a un horariopojo
     * @param dto
     * @return 
     */
    public static HorarioPojo toPojo(HorarioDTO dto) {
        if (dto == null) {
            return null;
        }

        HorarioPojo pojo = new HorarioPojo();
        pojo.setNombreDia(dto.getNombreDia());
        pojo.setInicio(dto.getHoraInicio());
        pojo.setFin(dto.getHoraFin());
        pojo.setDisponible(dto.isDisponible());
        return pojo;
    }
}