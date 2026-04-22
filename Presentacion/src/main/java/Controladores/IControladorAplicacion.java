/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controladores;

import dtos.InicioSesionDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IControladorAplicacion {
    
    void irABienvenida();

    void irAInicioSesion();

    void irAPerfilUsuario();

    void iniciarSesion(InicioSesionDTO dto);
    
    public UsuarioDTO obtenerPerfil();
    
    public List<VisitaDTO> obtenerHistorial();
    
    void verPerfil();
    
    public void SeleccionSucursal();
    
    void SeleccionPlan(dtos.SucursalDTO sucursal);
    
    void ResumenCompra(dtos.SucursalDTO sucursal, dtos.PlanDTO plan);
    
    void PantallaExito(dtos.ResultadoDTO resultado);
}
