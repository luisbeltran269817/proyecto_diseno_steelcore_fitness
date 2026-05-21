/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.PiezaPojo;
import dtosInventarioMantenimiento.PiezaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class PiezaDTOMapper {
    
    /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static PiezaPojo toPojo(PiezaDTO dto) {
        if (dto == null) {
            return null;
        }

        PiezaPojo pojo = new PiezaPojo();

        pojo.setIdPieza(dto.getIdPieza());
        pojo.setNombre(dto.getNombre());
        pojo.setStock(dto.getStock());

        if (dto.getEstado() != null) {
            pojo.setEstado(PiezaPojo.EstadoPieza.valueOf(dto.getEstado().name()));
        }

        return pojo;
    }
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static PiezaDTO toDTO(PiezaPojo pojo) {
        if (pojo == null) {
            return null;
        }

        PiezaDTO dto = new PiezaDTO();

        dto.setIdPieza(pojo.getIdPieza());
        dto.setNombre(pojo.getNombre());
        dto.setStock(pojo.getStock());

        if (pojo.getEstado() != null) {
            dto.setEstado(PiezaDTO.EstadoPiezaDTO.valueOf(pojo.getEstado().name()));
        }

        return dto;
    }
   
    /**
     * Método que convierte una lista de pojos a dtos
     * @param pojos la lista a convertir
     * @return la lista de dtos convertida
     */
    public static List<PiezaDTO> toDTOList(List<PiezaPojo> pojos) {
        List<PiezaDTO> lista = new ArrayList<>();

        if (pojos != null) {
            for (PiezaPojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }

        return lista;
    }
    
    /**
     * Método que convierte una lista de dtos a pojos
     * @param dtos la lista a convertir
     * @return  la lista de pojos convertida
     */
    public static List<PiezaPojo> toPojoList(List<PiezaDTO> dtos) {
        List<PiezaPojo> lista = new ArrayList<>();

        if (dtos != null) {
            for (PiezaDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }
        return lista;
    }
}
