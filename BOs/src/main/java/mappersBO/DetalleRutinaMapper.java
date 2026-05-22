/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.DetalleRutinaPojo;
import dtos.DetalleRutinaDTO;
import dtos.EjercicioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase mapper de detallerutina de la capa de negocio
 * @author luiscarlosbeltran
 */
public class DetalleRutinaMapper {
    /**
     * convierte un detallerutina de su pojo a dto
     * @param pojo
     * @return 
     */
    public static DetalleRutinaDTO toDTO(DetalleRutinaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        DetalleRutinaDTO dto = new DetalleRutinaDTO();
        dto.setNombreDia(pojo.getNombreDia());
        dto.setGrupoMuscular(pojo.getGrupoMuscular());
        //la lista se mete primero vacia poruqe despues en BO se llena con los EjercicioDTO
        //y para eso se usa otro metodo de consulta, porque en presentacion nimodo que decirle 
        //al cliente que sus ejercicios son id, id, id
        dto.setEjercicios(new ArrayList<>());
        return dto;
    }
    /**
     * convierte un detallerutina de dto a pojo
     * @param dto
     * @return 
     */
    public static DetalleRutinaPojo toPojo(DetalleRutinaDTO dto) {
        if (dto == null) {
            return null;
        }
        DetalleRutinaPojo pojo = new DetalleRutinaPojo();
        pojo.setNombreDia(dto.getNombreDia());
        pojo.setGrupoMuscular(dto.getGrupoMuscular());
        //solo se guardan los id porque asi es en la bd
        if (dto.getEjercicios() != null) {
            List<String> ids = new ArrayList<>();
            for (EjercicioDTO ejercicio : dto.getEjercicios()) {
                ids.add(ejercicio.getIdEjercicio());
            }
            pojo.setIdsEjercicios(ids);
        } else {
            pojo.setIdsEjercicios(new ArrayList<>());
        }
        return pojo;
    }
}
