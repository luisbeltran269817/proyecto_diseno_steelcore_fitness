/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import Clase_Control.ControlComprarMembresia;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;

import dtos.PlanDTO;

import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import objetosnegocios.AlmacenComprarMembresiaMock;

/**
 *
 * @author Tungs
 */
public class FachadaComprarMembresia implements IComprarMembresia {
    private final ControlComprarMembresia control;

    public FachadaComprarMembresia() {
        AlmacenComprarMembresiaMock almacen = AlmacenComprarMembresiaMock.getInstancia();
        this.control = new ControlComprarMembresia();
    }
    
    @Override
    public List<SucursalDTO> obtenerSucursales() {
        return control.obtenerSucursales();
    }
    @Override
    public List<PlanDTO> obtenerPlanes(SucursalDTO sucursal) {
        return control.obtenerPlanes(sucursal.getIdSucursal());
    }
    @Override
    public List<AmenidadDTO> obtenerAmenidadesPlan(PlanDTO plan) {
        return control.obtenerAmenidadesDePlan(plan.getIdPlan());
    }
    @Override
    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return control.obtenerAmenidadesExtra();
    }
    @Override
    public double calcularTotal(PlanDTO plan, List<AmenidadDTO> extras) {
        return control.calcularTotal(plan.getIdPlan(), extras);
    }
    @Override
    public MembresiaDTO crearMembresia(MembresiaDTO dto) {
        return control.crearMembresia(dto.getIdCliente(),dto.getIdPlan(),dto.getIdSucursal(),dto.getAmenidadesExtra());
    }
    @Override
    public List<EntrenadorDTO> obtenerEntrenadores(SucursalDTO sucursal) {
        return control.obtenerEntrenadores(sucursal.getIdSucursal());
    }
    @Override
    public List<HorarioDTO> obtenerHorarios(EntrenadorDTO entrenador) {
        return control.obtenerHorarios(entrenador.getIdEntrenador());
    }
    @Override
    public CitaDTO agendarCita(CitaDTO dto) {
        return control.agendarCitaBienvenida(dto.getIdCliente(),dto.getIdEntrenador(),dto.getIdSucursal(),dto.getIdHorario());
    }
    @Override
    public boolean hayHorarios(EntrenadorDTO entrenador) {
        return control.hayHorariosDisponibles(entrenador.getIdEntrenador());
    }
    @Override
    public boolean tieneMembresiaActiva(String idCliente) {
        return control.tieneMembresiaActiva(idCliente);
    }
    @Override
    public MembresiaDTO obtenerMembresiaActiva(String idCliente) {
        return control.obtenerMembresiaActiva(idCliente);
    }
    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        return control.obtenerHistorial(idCliente);
    }
    @Override
    public void cancelarMembresia(String idCliente) {
        control.cancelarMembresia(idCliente);
    }
}
