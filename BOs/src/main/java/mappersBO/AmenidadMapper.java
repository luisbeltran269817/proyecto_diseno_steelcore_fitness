/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.AmenidadPojo;
import dtos.AmenidadDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class AmenidadMapper {
    
    
    public static AmenidadPojo toPojo(AmenidadDTO dto) {
        AmenidadPojo pojo =new AmenidadPojo();

        pojo.setIdAmenidad(dto.getIdAmenidad());

        pojo.setNombre(dto.getNombre());
        pojo.setDescripcion(dto.getDescripcion());
        pojo.setTipo(AmenidadPojo.TipoAmenidad.valueOf(
                        dto.getTipo().name()
                )
        );
        pojo.setCosto(dto.getCosto());

        return pojo;
    }
    
    public static AmenidadDTO toDTO(AmenidadPojo pojo) {
        AmenidadDTO dto =new AmenidadDTO();
        dto.setIdAmenidad(pojo.getIdAmenidad());
        dto.setNombre(pojo.getNombre());
        dto.setDescripcion(pojo.getDescripcion());
        dto.setTipo(AmenidadDTO.TipoAmenidad.valueOf(pojo.getTipo().name()));
        dto.setCosto(pojo.getCosto());
        return dto;
    }
    
    public static List<AmenidadDTO> toDTOList(List<AmenidadPojo> pojos) {
        List<AmenidadDTO> lista = new ArrayList<>();
        for (AmenidadPojo pojo : pojos) {
            lista.add(toDTO(pojo)
            );
        }
        return lista;
    }
    
    public static List<AmenidadPojo> toPojoList(List<AmenidadDTO> dtos) {
        List<AmenidadPojo> lista= new ArrayList<>();
        for (AmenidadDTO dto : dtos) {
            lista.add(toPojo(dto));
        }
        return lista;
    }
    
    
}
