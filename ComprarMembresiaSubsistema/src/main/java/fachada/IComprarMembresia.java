/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.CompraDTO;
import dtos.PlanDTO;
import dtos.ResultadoDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IComprarMembresia {
    
    public UsuarioDTO obtenerPerfil(String correo);
    
    List<VisitaDTO> obtenerHistorial(String correo);
    
    public List<PlanDTO> obtenerPlanes();
    
    public PlanDTO obtenerDetallePlan(String idPlan);
    
    public List<SucursalDTO> obtenerSucursales();
    
    public ResultadoDTO generarContrato(CompraDTO dto);
    
    public ResultadoDTO confirmarCompra(CompraDTO dto);
}
