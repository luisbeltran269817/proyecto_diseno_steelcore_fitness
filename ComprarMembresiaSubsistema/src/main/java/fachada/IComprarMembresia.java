/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;


import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;

import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IComprarMembresia {
    
  public List<SucursalDTO> obtenerSucursales();
  public List<PlanDTO> obtenerPlanes(SucursalDTO sucursal);
  public List<AmenidadDTO> obtenerAmenidadesPlan(PlanDTO plan);
  public List<AmenidadDTO> obtenerAmenidadesExtra();
  public MembresiaDTO crearMembresia(MembresiaDTO dto);
  public List<EntrenadorDTO> obtenerEntrenadores(SucursalDTO sucursal);
  public List<HorarioDTO> obtenerHorarios(EntrenadorDTO entrenador);
  public CitaDTO agendarCita(CitaDTO dto);
  public boolean hayHorarios(EntrenadorDTO entrenador);
  public boolean tieneMembresiaActiva(String idCliente);
  public MembresiaDTO obtenerMembresiaActiva(String idCliente);
  public List<VisitaDTO> obtenerHistorial(String idCliente);
   public void cancelarMembresia(String idCliente);
}
