/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.AmenidadPojo;
import dominios.PlanPojo;
import dtos.AmenidadDTO;
import dtos.PlanDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Tungs
 */
public class PlanMapper {
      private static final Logger logger =Logger.getLogger(PlanMapper.class.getName());

    public static PlanPojo toPojo(PlanDTO dto) {
        if (dto == null) {
            return null;
        }
        PlanPojo pojo = new PlanPojo();

        pojo.setIdPlan(dto.getIdPlan());
        pojo.setNombre(dto.getNombre());
        pojo.setPrecio(dto.getPrecio());
        pojo.setDescripcion(dto.getDescripcion());
        pojo.setMesesDuracion(dto.getMesesDuracion());

        List<AmenidadPojo> amenidades =new ArrayList<>();
        if (dto.getAmenidades() != null) {
            for (AmenidadDTO amenidad: dto.getAmenidades()) {
                amenidades.add(AmenidadMapper.toPojo(amenidad)
                );
            }
        }
        pojo.setAmenidades(amenidades);
        logger.info("PlanDTO convertido a PlanPojo");
        return pojo;
    }

    public static PlanDTO toDTO(PlanPojo pojo) {
        if (pojo == null) {
            return null;
        }

        PlanDTO dto = new PlanDTO();

        dto.setIdPlan(pojo.getIdPlan());
        dto.setNombre(pojo.getNombre());
        dto.setPrecio(pojo.getPrecio());
        dto.setDescripcion(pojo.getDescripcion());
        dto.setMesesDuracion(pojo.getMesesDuracion());
        List<AmenidadDTO> amenidades =new ArrayList<>();
        if (pojo.getAmenidades() != null) {
            for (AmenidadPojo amenidad: pojo.getAmenidades()) {
                amenidades.add(AmenidadMapper.toDTO(amenidad));
            }
        }
        dto.setAmenidades(amenidades);
        logger.info("PlanPojo convertido a PlanDTO");
        return dto;
    }
    
    public static List<PlanDTO> toDTOList(List<PlanPojo> pojos) {
        List<PlanDTO> lista=new ArrayList<>();
        for (PlanPojo pojo : pojos) {
            lista.add(toDTO(pojo));
        }
        return lista;
    }
}
