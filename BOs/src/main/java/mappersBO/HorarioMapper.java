/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.HorarioPojo;
import dtos.HorarioDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author luiscarlosbeltran
 */
public class HorarioMapper {
    private static final Logger logger =Logger.getLogger(HorarioMapper.class.getName());
    public static HorarioPojo toPojo(HorarioDTO dto) {
        if (dto == null) {
            return null;
        }
        HorarioPojo pojo =new HorarioPojo();

        pojo.setIdHorario(dto.getIdHorario());
        pojo.setNombreDia(dto.getNombreDia());
        pojo.setHoraInicio(dto.getHoraInicio());
        pojo.setHoraFin(dto.getHoraFin());
        pojo.setDisponible(dto.isDisponible());
        logger.info("HorarioDTO convertido a HorarioPojo");
        return pojo;
    }

    public static HorarioDTO toDTO(HorarioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        HorarioDTO dto =new HorarioDTO();
        dto.setIdHorario(pojo.getIdHorario());

        dto.setNombreDia(pojo.getNombreDia());

        dto.setHoraInicio(pojo.getHoraInicio());

        dto.setHoraFin(pojo.getHoraFin());

        dto.setDisponible(pojo.isDisponible());

        logger.info("HorarioPojo convertido a HorarioDTO");

        return dto;
    }

    public static List<HorarioDTO>toDTOList(List<HorarioPojo> pojos) {
        List<HorarioDTO> lista =new ArrayList<>();
        if (pojos != null) {
            for (HorarioPojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }

        return lista;
    }
    
    public static List<HorarioPojo>toPojoList(List<HorarioDTO> dtos) {
        List<HorarioPojo> lista =new ArrayList<>();
        if (dtos != null) {
            for (HorarioDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }

        return lista;
    }
}