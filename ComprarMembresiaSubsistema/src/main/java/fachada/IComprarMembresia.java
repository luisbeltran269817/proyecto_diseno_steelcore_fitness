/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;


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
public interface IComprarMembresia {
    
  public List<SucursalDTO> obtenerSucursales() throws NegocioException;
  public List<PlanDTO> obtenerPlanes(SucursalDTO sucursal)throws NegocioException;
  public List<AmenidadDTO> obtenerAmenidadesPlan(PlanDTO plan) throws NegocioException;
  public List<AmenidadDTO> obtenerAmenidadesExtra();
  public MembresiaDTO comprarMembresia(MembresiaDTO dto, String token) throws NegocioException;
  public List<EntrenadorDTO> obtenerEntrenadores(SucursalDTO sucursal) throws NegocioException;
  public List<HorarioDTO> obtenerHorarios(EntrenadorDTO entrenador) throws NegocioException;
  public CitaDTO agendarCita(CitaDTO dto) throws NegocioException;
  public boolean hayHorarios(EntrenadorDTO entrenador) throws NegocioException;
  public boolean tieneMembresiaActiva(String idCliente) throws NegocioException;
  public MembresiaDTO obtenerMembresiaActiva(String idCliente) throws NegocioException;
  public CitaDTO obtenerCitaBienvenida(String idCliente)throws NegocioException;
  public List<VisitaDTO> obtenerHistorial(String idCliente) throws NegocioException;
  public void cancelarMembresia(String idCliente)throws NegocioException;
   public double calcularTotal(String idPlan, List<AmenidadDTO> extras) throws NegocioException;
   
   
   byte[] generarQRMembresia(String idMembresia) throws NegocioException;
   
}
