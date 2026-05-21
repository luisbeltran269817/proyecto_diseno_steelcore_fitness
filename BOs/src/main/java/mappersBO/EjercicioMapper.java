/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.EjercicioPojo;
import dtos.EjercicioDTO;

/**
 * Clase mapper de ejercicios de la capa de negocio
 * @author luiscarlosbeltran
 */
public class EjercicioMapper {
    /**
     * convierte un ejerciciopojo en su dto
     * @param pojo
     * @return 
     */
    public static EjercicioDTO toDTO(EjercicioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        EjercicioDTO dto = new EjercicioDTO();
        dto.setIdEjercicio(pojo.getIdEjercicio());
        dto.setNombre(pojo.getNombre());
        dto.setGrupoMuscular(pojo.getGrupoMuscular());
        dto.setDescripcion(pojo.getDescripcion());
        return dto;
    }
    /**
     * convierte un ejerciciodto a su pojo
     * @param dto
     * @return 
     */
    public static EjercicioPojo toPojo(EjercicioDTO dto) {
        if (dto == null) {
            return null;
        }
        EjercicioPojo pojo = new EjercicioPojo();
        pojo.setIdEjercicio(dto.getIdEjercicio());
        pojo.setNombre(dto.getNombre());
        pojo.setGrupoMuscular(dto.getGrupoMuscular());
        pojo.setDescripcion(dto.getDescripcion());
        return pojo;
    }
}
