/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controladores;

import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.InicioSesionDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;

import fachada.IMapaSucursal;
import java.util.List;
import javax.swing.JComponent;

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

    /** Abre PantallaQR — el socio ve su código para mostrar en recepción. */
    void irAQR();

    /** Abre BC_PantallaEspera — el módulo de recepción que escanea el QR. */
    void irAModuloRecepcion();

    void irATransaccionFallida(String causa);

    void irATransaccionExitosa();

    void iniciarSesion(String correo, String contrasena) throws Exception;

    void cerrarSesion();

    boolean tieneCitaBienvenida();

    CitaDTO obtenerCitaBienvenida();

    boolean tieneMembresiaActiva();

    MembresiaDTO obtenerMembresiaActiva();

    /**
     * Devuelve la membresia comprada en ESTA sesion, o null si no hay ninguna.
     * PantallaQR la usa para mostrar el QR correcto de inmediato tras la compra,
     * evitando que el mock M001 tape la membresia real recien creada.
     */
    MembresiaDTO getMembresiaRecienCreada();

    List<VisitaDTO> obtenerHistorial();

    void cancelarMembresia();

    List<SucursalDTO> obtenerSucursales();

    List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal);

    List<AmenidadDTO> obtenerAmenidadesExtra();

    List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal);

    List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador);

    public void confirmarCompra();

    public double calcularTotal();

    void confirmarCitaBienvenida();

    public void setTokenTarjeta(String token);

    public String getTokenTarjeta();

    // Mapa — Presentación solo ve JComponent y el listener propio de controlMapaSucursal
    JComponent getComponenteMapa();
    List<SucursalDTO> iniciarMapa();
    SucursalDTO onMarcadorClickeado(String idSucursal);
    void actualizarUbicacion(double lat, double lng);
    void centrarMapaEn(double lat, double lng);
    void setOnMarcadorClickListener(IMapaSucursal.OnMarcadorSucursalClickListener listener);
    byte[] generarQRMembresia(String idMembresia);

    String iniciarServidorQR(byte[] qrPng);

    /** Apaga el servidor HTTP. Lo llama ControladorAplicacion al salir de PantallaQR. */
    void detenerServidorQR();
    void ubicarUsuarioAutomaticamente();
}