/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.TecnicoPojo;
import dtosInventarioMantenimiento.TecnicoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class TecnicoDTOMapper {
    
     /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static TecnicoPojo toPojo(TecnicoDTO dto) {
        if (dto == null) {
            return null;
        }
        TecnicoPojo pojo = new TecnicoPojo();
        pojo.setIdTecnico(dto.getIdTecnico());
        pojo.setNombre(dto.getNombre());
        pojo.setHorarios(HorarioTecnicoDTOMapper.toPojoList(dto.getHorarios()));

        return pojo;
    }
    
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static TecnicoDTO toDTO(TecnicoPojo pojo) {
        if (pojo == null) {
            return null;
        }
        TecnicoDTO dto = new TecnicoDTO();

        dto.setIdTecnico(pojo.getIdTecnico());
        dto.setNombre(pojo.getNombre());
        dto.setHorarios(HorarioTecnicoDTOMapper.toDTOList(pojo.getHorarios()));

        return dto;
    }
    /**
     * Método que convierte una lista de dtos a pojos
     * @param dtos la lista a convertir
     * @return  la lista de pojos convertida
     */
    public static List<TecnicoPojo> toPojoList(List<TecnicoDTO> dtos) {
        List<TecnicoPojo> lista = new ArrayList<>();
        if (dtos != null) {
            for (TecnicoDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }

        return lista;
    }

    /**
     * Método que convierte una lista de pojos a dtos
     * @param pojos la lista a convertir
     * @return la lista de dtos convertida
     */
    public static List<TecnicoDTO> toDTOList(List<TecnicoPojo> pojos) {
        List<TecnicoDTO> lista = new ArrayList<>();
        if (pojos != null) {
            for (TecnicoPojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }
        return lista;
    }
}
