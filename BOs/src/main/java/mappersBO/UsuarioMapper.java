/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.UsuarioPojo;
import dominios.UsuarioPojo.RolUsuarioPojo;
import dtos.UsuarioDTO;

/**
 *
 * @author Tungs
 */
public class UsuarioMapper {
    
    public static UsuarioDTO toDTO(UsuarioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        UsuarioDTO dto = new UsuarioDTO();
        dto.setCorreo(pojo.getCorreo());
        dto.setNombre(pojo.getNombre());
        dto.setContraseña(pojo.getContraseña());
        if (pojo.getRol() != null) {
            dto.setRol(UsuarioDTO.Rol.valueOf(pojo.getRol().name()));
        }
        return dto;
    }

    public static UsuarioPojo toPojo(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }
        UsuarioPojo pojo = new UsuarioPojo();
        pojo.setCorreo(dto.getCorreo());
        pojo.setNombre(dto.getNombre());
        pojo.setContraseña(dto.getContraseña());
        if (dto.getRol() != null) {pojo.setRol(RolUsuarioPojo.valueOf(dto.getRol().name()));
        }

        return pojo;
    }
}
