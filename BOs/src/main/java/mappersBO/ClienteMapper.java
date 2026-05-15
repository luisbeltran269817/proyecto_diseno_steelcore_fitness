/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import dominios.MembresiaPojo.EstadoMembresiaPojo;
import dominios.UsuarioPojo;
import dominios.UsuarioPojo.RolUsuarioPojo;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.UsuarioDTO;
import java.util.ArrayList;

/**
 *
 * @author Tungs
 */
public class ClienteMapper {
    public static ClienteDTO toDTO(ClientePojo pojo) {

        if (pojo == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setIdCliente(pojo.getIdCliente());
        if (pojo.getUsuario() != null) {
            dto.setCorreo(pojo.getUsuario().getCorreo());

            dto.setNombre(pojo.getUsuario().getNombre());

            dto.setContraseña(pojo.getUsuario().getContraseña());

            if (pojo.getUsuario().getRol() != null) {
                dto.setRol(UsuarioDTO.Rol.valueOf(pojo.getUsuario().getRol().name()));
            }
        }
        dto.setApellidoPaterno(pojo.getApellidoPaterno());
        dto.setApellidoMaterno(pojo.getApellidoMaterno());
        dto.setFechaNacimiento(pojo.getFechaNacimiento());
        dto.setCurp(pojo.getCurp());

        dto.setMembresias(new ArrayList<>());

        if (pojo.getCitaBienvenida() != null) {
            dto.setCitaBienvenida(CitaMapper.toDTO(pojo.getCitaBienvenida()));
        }

        return dto;
    }

    public static ClientePojo toPojo(ClienteDTO dto) {

        if (dto == null) {
            return null;
        }

        ClientePojo pojo = new ClientePojo();
        pojo.setIdCliente(dto.getIdCliente());

        UsuarioPojo usuario = new UsuarioPojo();

        usuario.setCorreo(dto.getCorreo());
        usuario.setNombre(dto.getNombre());
        usuario.setContraseña(dto.getContraseña());

        if (dto.getRol() != null) {
            usuario.setRol(RolUsuarioPojo.valueOf(dto.getRol().name()));
        }

        pojo.setUsuario(usuario);

        pojo.setApellidoPaterno(dto.getApellidoPaterno());

        pojo.setApellidoMaterno(dto.getApellidoMaterno());

        pojo.setFechaNacimiento(dto.getFechaNacimiento());

        pojo.setCurp(dto.getCurp());
        if (dto.getMembresias() != null && !dto.getMembresias().isEmpty()) {
            MembresiaDTO activa = null;
            for (MembresiaDTO membresia : dto.getMembresias()) {
                if (membresia.getEstado()== MembresiaDTO.EstadoMembresia.ACTIVA) {
                    activa = membresia;
                    break;
                }
            }
            if (activa != null) {
                MembresiaActivaPojo snapshot =new MembresiaActivaPojo();
                snapshot.setIdMembresia(activa.getIdMembresia());
                snapshot.setIdPlan(activa.getIdPlan());
                snapshot.setFechaCaducidad(activa.getFechaCaducidad());
                snapshot.setEstado(EstadoMembresiaPojo.valueOf(activa.getEstado().name()));
                pojo.setMembresiaActiva(snapshot);
            }
        }
        if (dto.getCitaBienvenida() != null) {
            pojo.setCitaBienvenida(CitaMapper.toPojo(dto.getCitaBienvenida()));
        }
        return pojo;
    }
    
}
