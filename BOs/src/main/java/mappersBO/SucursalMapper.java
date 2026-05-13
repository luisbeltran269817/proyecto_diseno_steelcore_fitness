/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.AmenidadPojo;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import dtos.AmenidadDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Tungs
 */
public class SucursalMapper {
    private static final Logger logger =Logger.getLogger(SucursalMapper.class.getName());

    public static SucursalPojo toPojo(SucursalDTO dto) {
        if (dto == null) {
            return null;
        }
        SucursalPojo pojo =new SucursalPojo();
        pojo.setIdSucursal(dto.getIdSucursal());
        pojo.setNombre(dto.getNombre());
        pojo.setCalle(dto.getCalle());
        pojo.setColonia(dto.getColonia());
        pojo.setCiudad(dto.getCiudad());
        pojo.setCodigoPostal(dto.getCodigoPostal());
        pojo.setLatitud(dto.getLatitud());
        pojo.setLongitud(dto.getLongitud());

        List<PlanPojo> planes =new ArrayList<>();
        if (dto.getPlanes() != null) {
            for (PlanDTO plan: dto.getPlanes()) {
                planes.add(PlanMapper.toPojo(plan)
                );
            }
        }

        pojo.setPlanes(planes);

        List<AmenidadPojo> amenidades =new ArrayList<>();
        if (dto.getAmenidadesSucursal()!= null) {
            for (AmenidadDTO amenidad: dto.getAmenidadesSucursal()) {
                amenidades.add(AmenidadMapper.toPojo(amenidad));
            }
        }
        pojo.setAmenidadesSucursal(amenidades);
        logger.info("SucursalDTO convertido a SucursalPojo");
        return pojo;
    }

    public static SucursalDTO toDTO(SucursalPojo pojo) {
        if (pojo == null) {
            return null;
        }
        SucursalDTO dto =new SucursalDTO();
        dto.setIdSucursal(pojo.getIdSucursal());

        dto.setNombre(pojo.getNombre());
        dto.setCalle(pojo.getCalle());
        dto.setColonia(pojo.getColonia());
        dto.setCiudad(pojo.getCiudad());
        dto.setCodigoPostal(pojo.getCodigoPostal());
        dto.setLatitud(pojo.getLatitud());
        dto.setLongitud(pojo.getLongitud());
        dto.setPlanes(PlanMapper.toDTOList(pojo.getPlanes()));

        List<AmenidadDTO> amenidades =new ArrayList<>();

        if (pojo.getAmenidadesSucursal()!= null) {
            for (AmenidadPojo amenidad : pojo.getAmenidadesSucursal()) {
                amenidades.add(AmenidadMapper.toDTO(amenidad));
            }
        }

        dto.setAmenidadesSucursal(amenidades);
        logger.info("SucursalPojo convertido a SucursalDTO");
        return dto;
    }
    
    public static List<SucursalDTO> toDTOList(
        List<SucursalPojo> pojos) {
        List<SucursalDTO> lista =new ArrayList<>();
        for (SucursalPojo pojo : pojos) {
            lista.add(toDTO(pojo));
        }
        return lista;
    }
}
