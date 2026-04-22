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
        return control.obtenerPerfil(correo);
    }

    @Override
    public List<VisitaDTO> obtenerHistorial(String correo) {
        return control.obtenerHistorial(correo);
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
