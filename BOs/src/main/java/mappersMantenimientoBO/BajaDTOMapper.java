/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.BajaPojo;
import dtosInventarioMantenimiento.BajasInventarioDTO;

/**
 *
 * @author Tungs
 */
public class BajaDTOMapper {
    
    
     /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static BajaPojo toPojo(BajasInventarioDTO dto) {
        if (dto == null) {
            return null;
        }

        BajaPojo pojo = new BajaPojo();

        pojo.setIdBaja(dto.getIdBaja());
        pojo.setMotivo(dto.getMotivo());
        pojo.setFechaBaja(dto.getFechaBaja());
        pojo.setTipo(dto.getTipo());

        return pojo;
    }
    
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static BajasInventarioDTO toDTO(BajaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        BajasInventarioDTO dto = new BajasInventarioDTO();
        dto.setIdBaja(pojo.getIdBaja());
        dto.setMotivo(pojo.getMotivo());
        dto.setFechaBaja(pojo.getFechaBaja());
        dto.setTipo(pojo.getTipo());

        return dto;
    }
}
