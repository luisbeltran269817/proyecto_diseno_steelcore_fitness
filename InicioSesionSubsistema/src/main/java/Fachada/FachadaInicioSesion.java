/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import dtos.InicioSesionDTO;
import dtos.UsuarioDTO;
import dtos.UsuarioDTO.Rol;

/**
 *
 * @author Tungs
 */
public class FachadaInicioSesion implements IInicioSesion {

    public FachadaInicioSesion() {
    }
    
    //Los métodos de las fachadas van a ser así por ahora, retornando valores simples
    //hmmm
    @Override
    public UsuarioDTO iniciarSesion(InicioSesionDTO dto) {
        if (dto.getCorreo().equals("admin@mail.com")) {
            UsuarioDTO admin = new UsuarioDTO();
            admin.setCorreo(dto.getCorreo());
            admin.setNombre("Admin");
            admin.setRol(Rol.ADMIN);
            return admin;
        }
        if (dto.getCorreo().equals("cliente@mail.com")) {
            UsuarioDTO cliente = new UsuarioDTO();
            cliente.setCorreo(dto.getCorreo());
            cliente.setNombre("Julian Menchaca");
            cliente.setRol(Rol.CLIENTE);
            cliente.setMembresiaActiva(true);
            cliente.setNombreMembresia("ChacaYunk Deluxe");
            return cliente;
        }
        return null;
    }
    
}
