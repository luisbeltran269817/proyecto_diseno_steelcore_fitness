/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import dtos.UsuarioDTO;
import dtos.UsuarioDTO.Rol;
import dtos.VisitaDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class FachadaComprarMembresia implements IComprarMembresia {
    
    @Override
    public UsuarioDTO obtenerPerfil(String correo) {
        //Esto lo moveremos a los BOs, en el BO habrá un método que regrese todos los registros del arreglo
        UsuarioDTO u = new UsuarioDTO();
        u.setCorreo(correo);
        u.setNombre("Julian Menchaca");
        u.setRol(Rol.CLIENTE);
        u.setMembresiaActiva(true);
        u.setNombreMembresia("ChacaYunk Deluxe");

        return u;
    }

    @Override
    public List<VisitaDTO> obtenerHistorial(String correo) {
        List<VisitaDTO> lista = new ArrayList<>();
        VisitaDTO v = new VisitaDTO();
        v.setGimnasio("SteelCore Centro");
        v.setCalle("Zacatecas");
        v.setColonia("Las cortinas");
        v.setCiudad("Obregón");
        v.setFechaHora(LocalDateTime.now());

        lista.add(v);

        return lista;
    }
    @Override
    public void cancelarMembresia(String correo) {
        //aún no
    }

    @Override
    public void adquirirMembresia(String correo) {
        //aún no
    }
}
