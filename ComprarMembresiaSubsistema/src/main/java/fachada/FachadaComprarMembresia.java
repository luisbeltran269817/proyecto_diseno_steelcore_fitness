/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import Clase_Control.ControlComprarMembresia;
import dtos.CompraDTO;
import dtos.PlanDTO;
import dtos.ResultadoDTO;
import dtos.SucursalDTO;
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
    private final ControlComprarMembresia control;
    
    public FachadaComprarMembresia() {
        this.control = new ControlComprarMembresia();
    }

    @Override
    public UsuarioDTO obtenerPerfil(String correo) {
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
    public List<PlanDTO> obtenerPlanes() {
        return control.obtenerPlanes();
    }
 
    @Override
    public PlanDTO obtenerDetallePlan(String idPlan) {
        return control.obtenerDetallePlan(idPlan);
    }
 
    @Override
    public List<SucursalDTO> obtenerSucursales() {
        return control.obtenerSucursales();
    }
 
    @Override
    public ResultadoDTO generarContrato(CompraDTO dto) {
        return control.generarContrato(dto);
    }
 
    @Override
    public ResultadoDTO confirmarCompra(CompraDTO dto) {
        return control.confirmarCompra(dto);
    }
}
