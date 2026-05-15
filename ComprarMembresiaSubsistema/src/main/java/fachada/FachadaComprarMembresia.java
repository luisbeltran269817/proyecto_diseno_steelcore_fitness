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
    public List<SucursalDTO> obtenerSucursales() throws NegocioException {
        return control.obtenerSucursales();
    }

    @Override
    public List<PlanDTO> obtenerPlanes(SucursalDTO sucursal) throws NegocioException {
        return control.obtenerPlanes(sucursal.getIdSucursal());
    }

    @Override
    public List<AmenidadDTO> obtenerAmenidadesPlan(PlanDTO plan) throws NegocioException {
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
    public List<EntrenadorDTO> obtenerEntrenadores(SucursalDTO sucursal) throws NegocioException {
        return control.obtenerEntrenadores(sucursal.getIdSucursal());
    }

    @Override
    public List<HorarioDTO> obtenerHorarios(EntrenadorDTO entrenador) throws NegocioException {
        return control.obtenerHorarios(entrenador.getIdEntrenador());
    }

    @Override
    public CitaDTO agendarCita(CitaDTO dto) throws NegocioException {
        return control.agendarCitaBienvenida(
                dto.getIdCliente(),
                dto.getIdEntrenador(),
                dto.getIdSucursal(),
                dto.getIdHorario()
        );
    }

    @Override
    public boolean hayHorarios(EntrenadorDTO entrenador) throws NegocioException {
        return control.hayHorariosDisponibles(entrenador.getIdEntrenador());
    }

    @Override
    public boolean tieneMembresiaActiva(String idCliente) throws NegocioException {
        return control.tieneMembresiaActiva(idCliente);
    }

    @Override
    public MembresiaDTO obtenerMembresiaActiva(String idCliente) throws NegocioException {
        return control.obtenerMembresiaActiva(idCliente);
    }

    @Override
    public CitaDTO obtenerCitaBienvenida(String idCliente) throws NegocioException {
        return control.obtenerCitaBienvenida(idCliente);
    }

    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) throws NegocioException {
        return control.obtenerHistorial(idCliente);
    }

    @Override
    public void cancelarMembresia(String idCliente) throws NegocioException {
        control.cancelarMembresia(idCliente);
    }

    @Override
    public double calcularTotal(String idPlan, List<AmenidadDTO> extras) throws NegocioException {
        return control.calcularTotal(idPlan, extras);
    }
    
    @Override
    public byte[] generarQRMembresia(String idMembresia) throws NegocioException {
        return control.generarQRMembresia(idMembresia);
    }
}
