/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Fachada.FachadaInicioSesion;
import Fachada.IInicioSesion;
import PantallasComprarMembresia.DatosBancarios;
import PantallasComprarMembresia.PantallaBienvenida;
import PantallasComprarMembresia.PantallaDetallePlan;
import PantallasComprarMembresia.PantallaPerfilUsuario;
import PantallasComprarMembresia.PantallaQR;
import PantallasComprarMembresia.PantallaSeleccionHorario;
import PantallasComprarMembresia.PantallaSeleccionInstructor;
import PantallasComprarMembresia.PantallaSeleccionPlan;
import PantallasComprarMembresia.PantallaSeleccionSucursal;
import PantallasComprarMembresia.PantallaTerminosCondiciones;
import PantallasComprarMembresia.PantallaTransaccionFallida;
import PantallasComprarMembresia.PantallaVerPerfil;
import PantallasInicioSesion.PantallaInicioSesion;
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
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author luiscarlosbeltran
 */
public class ControladorAplicacion implements IControladorAplicacion {
    private static ControladorAplicacion instancia;

    private final IInicioSesion inicioSesionFachada;
    private final IComprarMembresia compraFachada;
 
    // pantallas
    private PantallaBienvenida pantallaBienvenida;
    private PantallaInicioSesion pantallaInicioSesion;
    private PantallaPerfilUsuario pantallaPerfil;
    private PantallaSeleccionSucursal pantallaSucursal;
    private PantallaSeleccionPlan pantallaPlan;
    private PantallaDetallePlan pantallaDetalle;
    private PantallaTerminosCondiciones pantallaTerminos;
    private DatosBancarios pantallaBancarios;
    private PantallaSeleccionInstructor pantallaInstructor;
    private PantallaSeleccionHorario pantallaHorario;
    private PantallaQR pantallaQR;
    private PantallaTransaccionFallida pantallaFallida;
 
    // los estados compartidos entre las pantallas tolano
    private UsuarioDTO usuarioActual;
    private SucursalDTO sucursalSeleccionada;
    private PlanDTO planSeleccionado;
    private List<AmenidadDTO> extrasSeleccionados;
    private EntrenadorDTO entrenadorSeleccionado;
    private HorarioDTO horarioSeleccionado;
    private MembresiaDTO membresiaRecienCreada;
 
    private ControladorAplicacion() {
        this.inicioSesionFachada = new FachadaInicioSesion();
        this.compraFachada = new FachadaComprarMembresia();
        this.extrasSeleccionados = new ArrayList<>();
    }
 
    public static ControladorAplicacion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorAplicacion();
        }
        return instancia;
    }
 
    public void iniciar() {
        irABienvenida();
    }
 
    @Override public UsuarioDTO getUsuarioActual() { return usuarioActual; }
 
    @Override public void setSucursalSeleccionada(SucursalDTO s) { this.sucursalSeleccionada = s; }
    @Override public SucursalDTO getSucursalSeleccionada() { return sucursalSeleccionada; }
 
    @Override public void setPlanSeleccionado(PlanDTO p) { this.planSeleccionado = p; }
    @Override public PlanDTO getPlanSeleccionado() { return planSeleccionado; }
 
    @Override public void setExtrasSeleccionados(List<AmenidadDTO> e) { this.extrasSeleccionados = e; }
    @Override public List<AmenidadDTO> getExtrasSeleccionados() { return extrasSeleccionados; }
 
    @Override public void setEntrenadorSeleccionado(EntrenadorDTO e) { this.entrenadorSeleccionado = e; }
    @Override public EntrenadorDTO getEntrenadorSeleccionado() { return entrenadorSeleccionado; }
 
    @Override public void setHorarioSeleccionado(HorarioDTO h) { this.horarioSeleccionado = h; }
    @Override public HorarioDTO getHorarioSeleccionado() { return horarioSeleccionado; }
 
    public MembresiaDTO getMembresiaRecienCreada() { return membresiaRecienCreada; }

    @Override
    public void irABienvenida() {
        cerrarPantallas();
        pantallaBienvenida = new PantallaBienvenida(this);
        pantallaBienvenida.setVisible(true);
    }
 
    @Override
    public void irAInicioSesion() {
        cerrarPantallas();
        pantallaInicioSesion = new PantallaInicioSesion(this);
        pantallaInicioSesion.setVisible(true);
    }
 
    @Override
    public void irAPerfilUsuario() {
        cerrarPantallas();
        pantallaPerfil = new PantallaPerfilUsuario(this);
        pantallaPerfil.setVisible(true);
    }
 
    @Override
    public void iniciarCompraMembresia() {
        sucursalSeleccionada = null;
        planSeleccionado = null;
        extrasSeleccionados.clear();
        entrenadorSeleccionado = null;
        horarioSeleccionado = null;
        membresiaRecienCreada = null;
        irASeleccionSucursal();
    }
 
    @Override
    public void irASeleccionSucursal() {
        cerrarPantallas();
        pantallaSucursal = new PantallaSeleccionSucursal(this);
        pantallaSucursal.setVisible(true);
    }
 
    @Override
    public void irASeleccionPlan() {
        cerrarPantallas();
        pantallaPlan = new PantallaSeleccionPlan(this);
        pantallaPlan.setVisible(true);
    }
 
    @Override
    public void irADetallePlan() {
        cerrarPantallas();
        pantallaDetalle = new PantallaDetallePlan(this);
        pantallaDetalle.setVisible(true);
    }
 
    @Override
    public void irATerminosCondiciones() {
        cerrarPantallas();
        pantallaTerminos = new PantallaTerminosCondiciones(this);
        pantallaTerminos.setVisible(true);
    }
 
    @Override
    public void irADatosBancarios() {
        cerrarPantallas();
        pantallaBancarios = new DatosBancarios(this);
        pantallaBancarios.setVisible(true);
    }
 
    @Override
    public void irASeleccionInstructor() {
        cerrarPantallas();
        pantallaInstructor = new PantallaSeleccionInstructor(this);
        pantallaInstructor.setVisible(true);
    }
 
    @Override
    public void irASeleccionHorario() {
        cerrarPantallas();
        pantallaHorario = new PantallaSeleccionHorario(this);
        pantallaHorario.setVisible(true);
    }
 
    @Override
    public void irAQR() {
        cerrarPantallas();
        pantallaQR = new PantallaQR(this);
        pantallaQR.setVisible(true);
    }
 
    @Override
    public void irATransaccionFallida(String causa) {
        cerrarPantallas();
        pantallaFallida = new PantallaTransaccionFallida(this, causa);
        pantallaFallida.setVisible(true);
    }
 
    @Override
    public void iniciarSesion(String correo, String contrasena) throws Exception {
        this.usuarioActual = inicioSesionFachada.iniciarSesion(correo, contrasena);
        irAPerfilUsuario();
    }
 
    @Override
    public void cerrarSesion() {
        usuarioActual = null;
        sucursalSeleccionada = null;
        planSeleccionado = null;
        extrasSeleccionados.clear();
        entrenadorSeleccionado = null;
        horarioSeleccionado = null;
        membresiaRecienCreada = null;
        irABienvenida();
    }
 
    @Override
    public boolean tieneMembresiaActiva() {
        if (usuarioActual == null) return false;
        return compraFachada.tieneMembresiaActiva(usuarioActual.getCorreo());
    }
 
    @Override
    public MembresiaDTO obtenerMembresiaActiva() {
        if (usuarioActual == null) return null;
        return compraFachada.obtenerMembresiaActiva(usuarioActual.getCorreo());
    }
 
    @Override
    public List<VisitaDTO> obtenerHistorial() {
        if (usuarioActual == null) return new ArrayList<>();
        return compraFachada.obtenerHistorial(usuarioActual.getCorreo());
    }
 
    @Override
    public void cancelarMembresia() {
        if (usuarioActual != null) {
            compraFachada.cancelarMembresia(usuarioActual.getCorreo());
        }
    }
 
    @Override
    public List<SucursalDTO> obtenerSucursales() {
        return compraFachada.obtenerSucursales();
    }
 
    @Override
    public List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal) {
        SucursalDTO temp = new SucursalDTO();
        temp.setIdSucursal(idSucursal);
        return compraFachada.obtenerPlanes(temp);
    }
 
    @Override
    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return compraFachada.obtenerAmenidadesExtra();
    }
 
    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal) {
        SucursalDTO temp = new SucursalDTO();
        temp.setIdSucursal(idSucursal);
        return compraFachada.obtenerEntrenadores(temp);
    }
 
    @Override
    public List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador) {
        EntrenadorDTO temp = new EntrenadorDTO();
        temp.setIdEntrenador(idEntrenador);
        return compraFachada.obtenerHorarios(temp);
    }
 
    @Override
    public MembresiaDTO confirmarCompra() {
        MembresiaDTO dto = new MembresiaDTO();
        dto.setIdCliente(usuarioActual.getCorreo());
        dto.setIdPlan(planSeleccionado.getIdPlan());
        dto.setIdSucursal(sucursalSeleccionada.getIdSucursal());
        dto.setAmenidadesExtra(extrasSeleccionados);
        this.membresiaRecienCreada = compraFachada.crearMembresia(dto);
        return membresiaRecienCreada;
    }
 
    @Override
    public void confirmarCitaBienvenida() {
        if (entrenadorSeleccionado == null || horarioSeleccionado == null) return;
        CitaDTO dto = new CitaDTO();
        dto.setIdCliente(usuarioActual.getCorreo());
        dto.setIdEntrenador(entrenadorSeleccionado.getIdEntrenador());
        dto.setIdSucursal(sucursalSeleccionada.getIdSucursal());
        dto.setIdHorario(horarioSeleccionado.getIdHorario());
        compraFachada.agendarCita(dto);
    }
 
 
    private void cerrarPantallas() {
        if (pantallaBienvenida  != null) { 
            pantallaBienvenida.dispose();  
            pantallaBienvenida  = null; 
        }
        if (pantallaInicioSesion!= null) { 
            pantallaInicioSesion.dispose();
            pantallaInicioSesion= null; 
        }
        if (pantallaPerfil != null) { 
            pantallaPerfil.dispose();      
            pantallaPerfil = null; 
        }
        if (pantallaSucursal != null) { 
            pantallaSucursal.dispose();    
            pantallaSucursal = null; 
        }
        if (pantallaPlan != null) { 
            pantallaPlan.dispose();        
            pantallaPlan = null; 
        }
        if (pantallaDetalle != null) { 
            pantallaDetalle.dispose();     
            pantallaDetalle = null; 
        }
        if (pantallaTerminos != null) { 
            pantallaTerminos.dispose();    
            pantallaTerminos = null; 
        }
        if (pantallaBancarios != null) { 
            pantallaBancarios.dispose();   
            pantallaBancarios = null; 
        }
        if (pantallaInstructor != null) { 
            pantallaInstructor.dispose();  
            pantallaInstructor = null; 
        }
        if (pantallaHorario != null) { 
            pantallaHorario.dispose();     
            pantallaHorario = null; 
        }
        if (pantallaQR != null) { 
            pantallaQR.dispose();          
            pantallaQR = null; 
        }
        if (pantallaFallida != null) { 
            pantallaFallida.dispose();     
            pantallaFallida = null; 
        }
    }
}