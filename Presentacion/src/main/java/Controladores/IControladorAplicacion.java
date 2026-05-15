/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Controladores;

import Excepciones.NegocioException;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
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

    void irASeleccionHorario()throws NegocioException;

    /** Abre PantallaQR — el socio ve su código para mostrar en recepción. */
    void irAQR();

    /** Abre BC_PantallaEspera — el módulo de recepción que escanea el QR. */
    void irAModuloRecepcion();

    void irATransaccionFallida(String causa);

    void irATransaccionExitosa();

    void iniciarSesion(String correo, String contrasena) throws Exception;

    void cerrarSesion();

    boolean tieneCitaBienvenida() throws NegocioException;

    CitaDTO obtenerCitaBienvenida() throws NegocioException;

    boolean tieneMembresiaActiva() throws NegocioException;

    MembresiaDTO obtenerMembresiaActiva() throws NegocioException;

    /**
     * Devuelve la membresia comprada en ESTA sesion, o null si no hay ninguna.
     * PantallaQR la usa para mostrar el QR correcto de inmediato tras la compra,
     * evitando que el mock M001 tape la membresia real recien creada.
     */
    MembresiaDTO getMembresiaRecienCreada();

    List<VisitaDTO> obtenerHistorial()throws NegocioException;

    void cancelarMembresia() throws NegocioException;

    List<SucursalDTO> obtenerSucursales() throws NegocioException;

    List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal) throws NegocioException;

    List<AmenidadDTO> obtenerAmenidadesExtra() throws NegocioException;

    List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal) throws NegocioException;

    List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador) throws NegocioException;

    public void confirmarCompra();

    public double calcularTotal() throws NegocioException;

    void confirmarCitaBienvenida() throws NegocioException;

    public void setTokenTarjeta(String token);

    public String getTokenTarjeta();

    // Mapa — Presentación solo ve JComponent y el listener propio de controlMapaSucursal
    JComponent getComponenteMapa();
    List<SucursalDTO> iniciarMapa() throws NegocioException;
    SucursalDTO onMarcadorClickeado(String idSucursal);
    void actualizarUbicacion(double lat, double lng);
    void centrarMapaEn(double lat, double lng);
    void setOnMarcadorClickListener(IMapaSucursal.OnMarcadorSucursalClickListener listener);
    byte[] generarQRMembresia(String idMembresia) throws NegocioException;

    String iniciarServidorQR(byte[] qrPng);

    /** Apaga el servidor HTTP. Lo llama ControladorAplicacion al salir de PantallaQR. */
    void detenerServidorQR();
    void ubicarUsuarioAutomaticamente();
}