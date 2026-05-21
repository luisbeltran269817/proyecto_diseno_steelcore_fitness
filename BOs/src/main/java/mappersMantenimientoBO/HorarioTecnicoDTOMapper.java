/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.HorarioTecnicoPojo;
import dtosInventarioMantenimiento.HorarioTecnicoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class HorarioTecnicoDTOMapper {
    
    /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static HorarioTecnicoPojo toPojo(HorarioTecnicoDTO dto) {
        if (dto == null) {
            return null;
        }
        HorarioTecnicoPojo pojo = new HorarioTecnicoPojo();
        pojo.setIdHorario(dto.getIdHorario());
        pojo.setNombreDia(dto.getNombreDia());
        pojo.setHoraInicio(dto.getHoraInicio());
        pojo.setHoraFin(dto.getHoraFin());
        pojo.setDisponible(dto.isDisponible());

        return pojo;
    }

    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static HorarioTecnicoDTO toDTO(HorarioTecnicoPojo pojo) {
        if (pojo == null) {
            return null;
        }

        HorarioTecnicoDTO dto = new HorarioTecnicoDTO();

        dto.setIdHorario(pojo.getIdHorario());
        dto.setNombreDia(pojo.getNombreDia());
        dto.setHoraInicio(pojo.getHoraInicio());
        dto.setHoraFin(pojo.getHoraFin());
        dto.setDisponible(pojo.isDisponible());

        return dto;
    }

    public static List<HorarioTecnicoPojo> toPojoList(List<HorarioTecnicoDTO> dtos) {
        List<HorarioTecnicoPojo> lista = new ArrayList<>();

        if (dtos != null) {
            for (HorarioTecnicoDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }

        return lista;
    }

    public static List<HorarioTecnicoDTO> toDTOList(List<HorarioTecnicoPojo> pojos) {
        List<HorarioTecnicoDTO> lista = new ArrayList<>();

        if (pojos != null) {
            for (HorarioTecnicoPojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }

        return lista;
    }
}
