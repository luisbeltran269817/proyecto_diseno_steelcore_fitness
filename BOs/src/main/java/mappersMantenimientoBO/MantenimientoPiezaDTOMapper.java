/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios_mantenimiento.MantenimientoPiezaPojo;
import java.util.ArrayList;
import java.util.List;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
/**
 *
 * @author Tungs
 */
public class MantenimientoPiezaDTOMapper {
    
    
     /**
     * Método que convierte un dto a un pojo
     * @param dto el dto a convertir
     * @return  el pojo convertido
     */
    public static MantenimientoPiezaPojo toPojo(MantenimientoPiezaDTO dto) {
        if (dto == null) {
            return null;
        }
        MantenimientoPiezaPojo pojo = new MantenimientoPiezaPojo();

        pojo.setIdMantenimientoPieza(dto.getIdMantenimientoPiezaDTO());
        pojo.setIdMantenimiento(dto.getIdMantenimiento());
        pojo.setIdPieza(dto.getIdPieza());
        pojo.setCantidad(dto.getCantidad());

        return pojo;
    }
    /**
     * Método que convierte un pojo a un dto
     * @param pojo el pojo a convertir
     * @return el dto convertido
     */
    public static MantenimientoPiezaDTO toDTO(MantenimientoPiezaPojo pojo) {
        if (pojo == null) {
            return null;
        }

        MantenimientoPiezaDTO dto = new MantenimientoPiezaDTO();

        dto.setIdMantenimientoPiezaDTO(pojo.getIdMantenimientoPieza());
        dto.setIdMantenimiento(pojo.getIdMantenimiento());
        dto.setIdPieza(pojo.getIdPieza());
        dto.setCantidad(pojo.getCantidad());

        return dto;
    }
    /**
     * Método que convierte una lista de dtos a pojos
     * @param dtos la lista a convertir
     * @return  la lista de pojos convertida
     */
    public static List<MantenimientoPiezaPojo> toPojoList(List<MantenimientoPiezaDTO> dtos) {
        List<MantenimientoPiezaPojo> lista = new ArrayList<>();

        if (dtos != null) {
            for (MantenimientoPiezaDTO dto : dtos) {
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
    public static List<MantenimientoPiezaDTO> toDTOList(List<MantenimientoPiezaPojo> pojos) {
        List<MantenimientoPiezaDTO> lista = new ArrayList<>();

        if (pojos != null) {
            for (MantenimientoPiezaPojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }

        return lista;
    }
}
