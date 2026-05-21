/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.MaquinaPojo;
import dtosInventarioMantenimiento.MaquinaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class MaquinaDTOMapper {
    
    /**
    * Método que convierte un dto a un pojo
    * @param dto el dto a convertir
    * @return  el pojo convertido
    */
    public static MaquinaPojo toPojo(MaquinaDTO dto) {
        if (dto == null) {
            return null;
        }

        MaquinaPojo pojo = new MaquinaPojo();

        pojo.setIdMaquina(dto.getIdMaquina());
        pojo.setIdSucursal(dto.getIdSucursal());
        pojo.setModelo(dto.getModelo());
        pojo.setTipo(dto.getTipo());

        if (dto.getEstado() != null) {
            pojo.setEstado(MaquinaPojo.EstadoMaquina.valueOf(dto.getEstado().name()));
        }

        pojo.setUltimoMantenimiento(UltimoMantenimientoDTOMapper.toPojo(dto.getUltimoMantenimiento()));

        return pojo;
    }
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static MaquinaDTO toDTO(MaquinaPojo pojo) {
        if (pojo == null) {
            return null;
        }

        MaquinaDTO dto = new MaquinaDTO();

        dto.setIdMaquina(pojo.getIdMaquina());
        dto.setIdSucursal(pojo.getIdSucursal());
        dto.setModelo(pojo.getModelo());
        dto.setTipo(pojo.getTipo());

        if (pojo.getEstado() != null) {
            dto.setEstado(MaquinaDTO.EstadoMaquinaDTO.valueOf(pojo.getEstado().name()));
        }

        dto.setUltimoMantenimiento(UltimoMantenimientoDTOMapper.toDTO(pojo.getUltimoMantenimiento()));

        return dto;
    }
    /**
     * Método que convierte una lista de pojos a dtos
     * @param pojos la lista a convertir
     * @return la lista de dtos convertida
     */
    public static List<MaquinaDTO> toDTOList(List<MaquinaPojo> pojos) {
        List<MaquinaDTO> lista = new ArrayList<>();

        if (pojos != null) {
            for (MaquinaPojo pojo : pojos) {
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
    public static List<MaquinaPojo> toPojoList(List<MaquinaDTO> dtos) {
        List<MaquinaPojo> lista = new ArrayList<>();

        if (dtos != null) {
            for (MaquinaDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }

        return lista;
    }

}
