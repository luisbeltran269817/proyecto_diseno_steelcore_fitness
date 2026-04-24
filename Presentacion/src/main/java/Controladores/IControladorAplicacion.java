/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controladores;

import dtos.InicioSesionDTO;
import dtos.MembresiaDTO;
import dtos.SucursalDTO;
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

    public void iniciarSesion(String correo, String contrasena) throws Exception;
    
    public UsuarioDTO getUsuarioActual();
    
    public void cerrarSesion();
    
    public boolean tieneMembresiaActiva();
    
    public void iniciarCompraMembresia();
    
    public MembresiaDTO obtenerMembresiaActiva();
    
    List<VisitaDTO> obtenerHistorial();
    
 
    public void cancelarMembresia();
    
    public List<SucursalDTO> obtenerSucursales();
    
    public void seleccionarSucursal(SucursalDTO sucursal);
    
    public void irASeleccionSucursal();

}
