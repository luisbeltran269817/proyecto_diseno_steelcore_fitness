/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import Clase_Control.ControlComprarMembresia;
import Excepciones.NegocioException;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
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
    
    
    //Este método ya está Mongosificado
    @Override
    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return control.obtenerAmenidadesExtra();
    }

    @Override
    public MembresiaDTO comprarMembresia(MembresiaDTO dto, String token) throws NegocioException  {
        return control.comprarMembresia(
                dto.getIdCliente(),
                dto.getIdPlan(),
                dto.getIdSucursal(),
                dto.getAmenidadesExtra(),
                token
        );
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
        return control.agendarCitaBienvenida(
                dto.getIdCliente(),
                dto.getIdEntrenador(),
                dto.getIdSucursal(),
                dto.getIdHorario()
        );
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
    public CitaDTO obtenerCitaBienvenida(String idCliente) {
        return control.obtenerCitaBienvenida(idCliente);
    }

    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        return control.obtenerHistorial(idCliente);
    }

    @Override
    public void cancelarMembresia(String idCliente) {
        control.cancelarMembresia(idCliente);
    }

    @Override
    public double calcularTotal(String idPlan, List<AmenidadDTO> extras) {
        return control.calcularTotal(idPlan, extras);
    }

    @Override
    public byte[] generarQRMembresia(String idMembresia) {
        return control.generarQRMembresia(idMembresia);
    }
}
