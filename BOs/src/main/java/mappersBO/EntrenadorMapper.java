/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.EntrenadorPojo;
import dtos.EntrenadorDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorMapper {
    
    private static final Logger logger =Logger.getLogger(EntrenadorMapper.class.getName());

    public static EntrenadorPojo toPojo(EntrenadorDTO dto) {
        if (dto == null) {
            return null;
        }
        EntrenadorPojo pojo =new EntrenadorPojo();

        pojo.setIdEntrenador(dto.getIdEntrenador());

        pojo.setNombre(dto.getNombre());

        pojo.setIdSucursal(dto.getIdSucursal());

        pojo.setHorarios(HorarioMapper.toPojoList(dto.getHorarios()));
        logger.info("EntrenadorDTO convertido a EntrenadorPojo");
        return pojo;
    }

    public static EntrenadorDTO toDTO(EntrenadorPojo pojo) {
        if (pojo == null) {
            return null;
        }
        EntrenadorDTO dto =new EntrenadorDTO();
        dto.setIdEntrenador(pojo.getIdEntrenador());
        dto.setNombre(pojo.getNombre());
        dto.setIdSucursal(pojo.getIdSucursal());
        dto.setHorarios(HorarioMapper.toDTOList(pojo.getHorarios()));
        logger.info("EntrenadorPojo convertido a EntrenadorDTO");
        return dto;
    }

    public static List<EntrenadorDTO>toDTOList(List<EntrenadorPojo> pojos) {
        List<EntrenadorDTO> lista =new ArrayList<>();
        if (pojos != null) {
            for (EntrenadorPojo pojo: pojos) {
                lista.add(toDTO(pojo));
            }
        }

        return lista;
    }
}