/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoBO;

import dominios.UsuarioPojo;
import dominios.UsuarioPojo.RolUsuarioPojo;
import dominios_mantenimiento.AdminMantenimientoPojo;
import dtos.UsuarioDTO;
import dtosInventarioMantenimiento.AdminMantenimientoDTO;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoDTOMapper {
    
     public static AdminMantenimientoPojo toPojo(AdminMantenimientoDTO dto) {

        if (dto == null) {
            return null;
        }

        AdminMantenimientoPojo pojo = new AdminMantenimientoPojo();

        pojo.setIdAdminMantenimiento(dto.getIdAdminMantenimiento());

        UsuarioPojo usuario = new UsuarioPojo();

        usuario.setCorreo(dto.getCorreo());
        usuario.setNombre(dto.getNombre());
        usuario.setContraseña(dto.getContraseña());

        if (dto.getRol() != null) {
            usuario.setRol(RolUsuarioPojo.valueOf(dto.getRol().name()));
        }

        pojo.setUsuario(usuario);

        return pojo;
    }

    public static AdminMantenimientoDTO toDTO(AdminMantenimientoPojo pojo) {

        if (pojo == null) {
            return null;
        }
        
        AdminMantenimientoDTO dto = new AdminMantenimientoDTO();

        dto.setIdAdminMantenimiento(pojo.getIdAdminMantenimiento());

        if (pojo.getUsuario() != null) {
            dto.setCorreo(pojo.getUsuario().getCorreo());
            dto.setNombre(pojo.getUsuario().getNombre());
            dto.setContraseña(pojo.getUsuario().getContraseña());

            if (pojo.getUsuario().getRol() != null) {
                dto.setRol(UsuarioDTO.Rol.valueOf(pojo.getUsuario().getRol().name()));
            }
        }

        return dto;
    }
}
