/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.UltimoMantenimientoPojo;
import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.UltimoMantenimientoDTO;

/**
 *
 * @author Tungs
 */
public class UltimoMantenimientoDTOMapper {
    
    /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static UltimoMantenimientoPojo toPojo(UltimoMantenimientoDTO dto) {
        
        if (dto == null) {
            return null;
        }
        UltimoMantenimientoPojo pojo = new UltimoMantenimientoPojo();
        pojo.setIdMantenimiento(dto.getIdMantenimiento());
        pojo.setFecha(dto.getFecha());

        if (dto.getEstado() != null) {
            pojo.setEstado(UltimoMantenimientoPojo.EstadoMantenimientoSnapshot.valueOf(dto.getEstado().name()));
        }
        return pojo;
    }
    
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static UltimoMantenimientoDTO toDTO(UltimoMantenimientoPojo pojo) {
        if (pojo == null) {
            return null;
        }

        UltimoMantenimientoDTO dto = new UltimoMantenimientoDTO();
        
        dto.setIdMantenimiento(pojo.getIdMantenimiento());
        dto.setFecha(pojo.getFecha());

        if (pojo.getEstado() != null) {
            dto.setEstado(MantenimientoDTO.EstadoMantenimientoDTO.valueOf(pojo.getEstado().name()));
        }
        return dto;
    }
}
