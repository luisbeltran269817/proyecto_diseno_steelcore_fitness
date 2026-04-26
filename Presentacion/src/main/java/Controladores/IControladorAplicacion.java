/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controladores;

import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.InicioSesionDTO;
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
public interface IControladorAplicacion {
    UsuarioDTO getUsuarioActual();
 
    void setSucursalSeleccionada(SucursalDTO sucursal);
    SucursalDTO getSucursalSeleccionada();
 
    void setPlanSeleccionado(PlanDTO plan);
    PlanDTO getPlanSeleccionado();
 
    void setExtrasSeleccionados(List<AmenidadDTO> extras);
    List<AmenidadDTO> getExtrasSeleccionados();
 
    void setEntrenadorSeleccionado(EntrenadorDTO entrenador);
    EntrenadorDTO getEntrenadorSeleccionado();
 
    void setHorarioSeleccionado(HorarioDTO horario);
    HorarioDTO getHorarioSeleccionado();
    
    void irABienvenida();
    void irAInicioSesion();
    void irAPerfilUsuario();
 
    // Flujo de compra
    void iniciarCompraMembresia();
    void irASeleccionSucursal();
    void irASeleccionPlan();
    void irADetallePlan();
    void irATerminosCondiciones();
    void irADatosBancarios();
    void irASeleccionInstructor();
    void irASeleccionHorario();
    void irAQR();
    void irATransaccionFallida(String causa);

    void iniciarSesion(String correo, String contrasena) throws Exception;
    void cerrarSesion();

    boolean tieneMembresiaActiva();
    MembresiaDTO obtenerMembresiaActiva();
    List<VisitaDTO> obtenerHistorial();
    void cancelarMembresia();
 
    List<SucursalDTO> obtenerSucursales();
    List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal);
    List<AmenidadDTO> obtenerAmenidadesExtra();
 
    List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal);
    List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador);
 
    /** Ejecuta el flujo completo de compra con los datos acumulados en el estado */
    MembresiaDTO confirmarCompra();
 
    /** Agenda la cita de bienvenida con los datos acumulados */
    void confirmarCitaBienvenida();
}
