/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.MantenimientoPojo;
import dtosInventarioMantenimiento.MantenimientoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class MantenimientoDTOMapper {
    
    
    /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static MantenimientoPojo toPojo(MantenimientoDTO dto) {
        if (dto == null) {
            return null;
        }
        MantenimientoPojo pojo = new MantenimientoPojo();
        pojo.setIdMantenimiento(dto.getIdMantenimiento());
        pojo.setIdTecnico(dto.getIdTecnico());
        pojo.setIdMaquina(dto.getIdMaquina());
        pojo.setDescripcion(dto.getDescripcion());
        pojo.setFechaProgramada(dto.getFechaProgramada());
        pojo.setFechaInicio(dto.getFechaInicio());
        pojo.setFechaFin(dto.getFechaFin());
        if (dto.getEstado() != null) {
            pojo.setEstado(MantenimientoPojo.EstadoMantenimiento.valueOf(dto.getEstado().name()));
        }
        pojo.setPiezas(MantenimientoPiezaDTOMapper.toPojoList(dto.getPiezas()));
        return pojo;
    }
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static MantenimientoDTO toDTO(MantenimientoPojo pojo) {
        if (pojo == null) {
            return null;
        }

        MantenimientoDTO dto = new MantenimientoDTO();

        dto.setIdMantenimiento(pojo.getIdMantenimiento());
        dto.setIdTecnico(pojo.getIdTecnico());
        dto.setIdMaquina(pojo.getIdMaquina());
        dto.setDescripcion(pojo.getDescripcion());
        dto.setFechaProgramada(pojo.getFechaProgramada());
        dto.setFechaInicio(pojo.getFechaInicio());
        dto.setFechaFin(pojo.getFechaFin());

        if (pojo.getEstado() != null) {
            dto.setEstado(MantenimientoDTO.EstadoMantenimientoDTO.valueOf(pojo.getEstado().name()));
        }

        dto.setPiezas(MantenimientoPiezaDTOMapper.toDTOList(pojo.getPiezas()));

        return dto;
    }
    
    /**
     * Método que convierte una lista de pojos a dtos
     * @param pojos la lista a convertir
     * @return la lista de dtos convertida
     */
    public static List<MantenimientoDTO> toDTOList(List<MantenimientoPojo> pojos) {
        List<MantenimientoDTO> lista = new ArrayList<>();

        if (pojos != null) {
            for (MantenimientoPojo pojo : pojos) {
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
    public static List<MantenimientoPojo> toPojoList(List<MantenimientoDTO> dtos) {
        List<MantenimientoPojo> lista = new ArrayList<>();
        if (dtos != null) {
            for (MantenimientoDTO dto : dtos) {
                lista.add(toPojo(dto));
            }
        }
        return lista;
    }
}
