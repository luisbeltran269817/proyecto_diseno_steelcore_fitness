/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clase_Control.GestorEstadosCompra;
import Fachada.FachadaControlAcceso;
import ControlDeAcceso.BC_PantallaEspera;
import Excepciones.NegocioException;
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
import PantallasComprarMembresia.PantallaTransaccionExitosa;
import PantallasComprarMembresia.PantallaTransaccionFallida;
import PantallasInicioSesion.PantallaInicioSesion;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import infraestructura.PresentacionInfra;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 *
 * @author luiscarlosbeltran
 */
public class ControladorAplicacion implements IControladorAplicacion {

    private static ControladorAplicacion instancia;
 
    private final IInicioSesion inicioSesionFachada;
    private final IComprarMembresia compraFachada;
    private final PresentacionInfra infraMapa;
    private final GestorEstadosCompra gestor;
 
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
    private BC_PantallaEspera pantallaRecepcion;
    private PantallaQR pantallaQRSocio;
    private PantallaTransaccionFallida pantallaFallida;
    private PantallaTransaccionExitosa pantallaExitosa;
 
    private ControladorAplicacion() {
        this.inicioSesionFachada = new FachadaInicioSesion();
        this.compraFachada = new FachadaComprarMembresia();
        this.infraMapa = new PresentacionInfra();
        this.gestor = new GestorEstadosCompra();
    }
 
    /**
     * Regresa la única instancia del controlador (singleton).
     *
     * @return instancia del controlador
     */
    public static ControladorAplicacion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorAplicacion();
        }
        return instancia;
    }
 
    /**
     * Arranca la aplicación mostrando la pantalla de bienvenida.
     */
    public void iniciar() {
        irABienvenida();
    }

    @Override
    public UsuarioDTO getUsuarioActual() {
        return gestor.getUsuarioActual();
    }
 
    @Override
    public void setSucursalSeleccionada(SucursalDTO sucursal) {
        gestor.setSucursalSeleccionada(sucursal);
    }
 
    @Override
    public SucursalDTO getSucursalSeleccionada() {
        return gestor.getSucursalSeleccionada();
    }
 
    @Override
    public void setPlanSeleccionado(PlanDTO plan) {
        gestor.setPlanSeleccionado(plan);
    }
 
    @Override
    public PlanDTO getPlanSeleccionado() {
        return gestor.getPlanSeleccionado();
    }
 
    @Override
    public void setExtrasSeleccionados(List<AmenidadDTO> extras) {
        gestor.setExtrasSeleccionados(extras);
    }
 
    @Override
    public List<AmenidadDTO> getExtrasSeleccionados() {
        return gestor.getExtrasSeleccionados();
    }
 
    @Override
    public void setEntrenadorSeleccionado(EntrenadorDTO entrenador) {
        gestor.setEntrenadorSeleccionado(entrenador);
    }
 
    @Override
    public EntrenadorDTO getEntrenadorSeleccionado() {
        return gestor.getEntrenadorSeleccionado();
    }
 
    @Override
    public void setHorarioSeleccionado(HorarioDTO horario) {
        gestor.setHorarioSeleccionado(horario);
    }
 
    @Override
    public HorarioDTO getHorarioSeleccionado() {
        return gestor.getHorarioSeleccionado();
    }
 
    @Override
    public MembresiaDTO getMembresiaRecienCreada() {
        return gestor.getMembresiaRecienCreada();
    }
 
    @Override
    public void setTokenTarjeta(String token) {
        gestor.setTokenTarjeta(token);
    }
 
    @Override
    public String getTokenTarjeta() {
        return gestor.getTokenTarjeta();
    }
 
    // --- navegación ---
 
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
        // Limpiar TODO el estado de compra antes de un flujo nuevo
        gestor.limpiar();
        irASeleccionSucursal();
    }

    @Override
    public void irASeleccionSucursal() {
        cerrarPantallas();
        pantallaSucursal = new PantallaSeleccionSucursal(this);
        pantallaSucursal.setVisible(true);
    }

    /**
     * Inicia el flujo de agendar cita desde el perfil de usuario.
     * El cliente ya tiene membresia, asi que NO se llama a limpiar().
     * Solo se resetean entrenador y horario; la sucursal se toma de
     * la membresia activa si el gestor no la tiene ya guardada.
     */
    @Override
    public void irAAgendarCitaDesdePerfil() {
        // Solo limpiamos las selecciones de cita, no todo el estado
        gestor.setEntrenadorSeleccionado(null);
        gestor.setHorarioSeleccionado(null);

        // Si no hay sucursal en el gestor la obtenemos de la membresia activa
        if (gestor.getSucursalSeleccionada() == null) {
            try {
                MembresiaDTO m = compraFachada.obtenerMembresiaActiva(
                        gestor.getUsuarioActual().getCorreo());
                if (m != null && m.getIdSucursal() != null) {
                    SucursalDTO sucursal = null;
                    // Intentar encontrar el objeto SucursalDTO completo
                    try {
                        for (SucursalDTO s : compraFachada.obtenerSucursales()) {
                            if (s.getIdSucursal().equals(m.getIdSucursal())) {
                                sucursal = s;
                                break;
                            }
                        }
                    } catch (NegocioException ignored) { }
                    // Fallback: construir con solo el id si no encontramos el objeto completo
                    if (sucursal == null) {
                        sucursal = new SucursalDTO();
                        sucursal.setIdSucursal(m.getIdSucursal());
                    }
                    gestor.setSucursalSeleccionada(sucursal);
                }
            } catch (NegocioException e) {
                // Continuamos aunque no pudimos cargar la membresia;
                // PantallaSeleccionInstructor mostrara "no hay instructores"
            }
        }
        irASeleccionInstructor();
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
    public void irASeleccionHorario() throws NegocioException {
        cerrarPantallas();
        pantallaHorario = new PantallaSeleccionHorario(this);
        pantallaHorario.setVisible(true);
    }
 
    @Override
    public void irAQR() {
        cerrarPantallas();
        pantallaQRSocio = new PantallaQR(this);
        pantallaQRSocio.setVisible(true);
    }
 
    @Override
    public void irAModuloRecepcion() {
        cerrarPantallas();
        pantallaRecepcion = new BC_PantallaEspera(this);
        pantallaRecepcion.setVisible(true);
    }
 
    @Override
    public void irATransaccionFallida(String causa) {
        cerrarPantallas();
        pantallaFallida = new PantallaTransaccionFallida(this, causa);
        pantallaFallida.setVisible(true);
    }
 
    @Override
    public void irATransaccionExitosa() {
        cerrarPantallas();
        pantallaExitosa = new PantallaTransaccionExitosa(this);
        pantallaExitosa.setVisible(true);
    }
 
    // --- sesión ---
 
    @Override
    public void iniciarSesion(String correo, String contrasena) throws NegocioException {
        UsuarioDTO usuario;
        try {
            usuario = inicioSesionFachada.iniciarSesion(correo, contrasena);
        } catch (Exception ex) {
            throw new NegocioException(ex.getMessage() != null ? ex.getMessage() : "Correo o contrasena incorrectos.");
        }
        gestor.setUsuarioActual(usuario);
        try {
            CitaDTO cita = compraFachada.obtenerCitaBienvenida(usuario.getCorreo());
            gestor.setCitaBienvenida(cita);
        } catch (NegocioException ex) {
            // No bloquear el login si falla la consulta de cita
        }
        irAPerfilUsuario();
    }
 
    @Override
    public void cerrarSesion() {
        gestor.limpiarSesion();
        irABienvenida();
    }
 
    // --- consultas de negocio ---
 
    @Override
    public boolean tieneMembresiaActiva() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        return usuario != null && compraFachada.tieneMembresiaActiva(usuario.getCorreo());
    }
 
    @Override
    public boolean tieneCitaBienvenida() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        return usuario != null
                && compraFachada.obtenerCitaBienvenida(usuario.getCorreo()) != null;
    }
 
    @Override
    public MembresiaDTO obtenerMembresiaActiva() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        return usuario == null ? null : compraFachada.obtenerMembresiaActiva(usuario.getCorreo());
    }
 
    @Override
    public CitaDTO obtenerCitaBienvenida() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        return usuario == null ? null : compraFachada.obtenerCitaBienvenida(usuario.getCorreo());
    }
 
    @Override
    public List<VisitaDTO> obtenerHistorial() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        return usuario == null ? new ArrayList<>() : compraFachada.obtenerHistorial(usuario.getCorreo());
    }
 
    @Override
    public void cancelarMembresia() throws NegocioException {
        UsuarioDTO usuario = gestor.getUsuarioActual();
        if (usuario != null) {
            compraFachada.cancelarMembresia(usuario.getCorreo());
        }
    }
 
    @Override
    public List<SucursalDTO> obtenerSucursales() throws NegocioException {
        return compraFachada.obtenerSucursales();
    }
 
    @Override
    public List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal) throws NegocioException {
        SucursalDTO tmp = new SucursalDTO();
        tmp.setIdSucursal(idSucursal);
        return compraFachada.obtenerPlanes(tmp);
    }
 
    @Override
    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return compraFachada.obtenerAmenidadesExtra();
    }
 
    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal) throws NegocioException {
        SucursalDTO tmp = new SucursalDTO();
        tmp.setIdSucursal(idSucursal);
        return compraFachada.obtenerEntrenadores(tmp);
    }
 
    @Override
    public List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador) throws NegocioException {
        EntrenadorDTO tmp = new EntrenadorDTO();
        tmp.setIdEntrenador(idEntrenador);
        return compraFachada.obtenerHorarios(tmp);
    }
 
    @Override
    public void confirmarCompra() {
        try {
            UsuarioDTO usuario = gestor.getUsuarioActual();
            MembresiaDTO dto = new MembresiaDTO();
            dto.setIdCliente(usuario.getCorreo());
            dto.setIdPlan(gestor.getPlanSeleccionado().getIdPlan());
            dto.setIdSucursal(gestor.getSucursalSeleccionada().getIdSucursal());
            dto.setAmenidadesExtra(gestor.getExtrasSeleccionados());
            MembresiaDTO creada = compraFachada.comprarMembresia(dto, gestor.getTokenTarjeta());
            gestor.setMembresiaRecienCreada(creada);
            gestor.setTokenTarjeta(null);
            irATransaccionExitosa();
        } catch (NegocioException e) {
            irATransaccionFallida(e.getMessage());
        }
    }
 
    @Override
    public double calcularTotal() throws NegocioException {
        PlanDTO plan = gestor.getPlanSeleccionado();
        return plan == null ? 0.0
                : compraFachada.calcularTotal(plan.getIdPlan(), gestor.getExtrasSeleccionados());
    }
 
    @Override
    public void confirmarCitaBienvenida() throws NegocioException {
        EntrenadorDTO entrenador = gestor.getEntrenadorSeleccionado();
        HorarioDTO horario = gestor.getHorarioSeleccionado();
        if (entrenador == null || horario == null) {
            throw new NegocioException("Debe seleccionar un instructor y un horario");
        }
        SucursalDTO sucursal = gestor.getSucursalSeleccionada();
        if (sucursal == null) {
            throw new NegocioException("No se encontro la sucursal; intente agendar la cita de nuevo");
        }
        UsuarioDTO usuario = gestor.getUsuarioActual();
        CitaDTO dto = new CitaDTO();
        dto.setIdCliente(usuario.getCorreo());
        dto.setIdEntrenador(entrenador.getIdEntrenador());
        dto.setIdSucursal(sucursal.getIdSucursal());
        dto.setIdHorario(horario.getIdHorario());
        CitaDTO agendada = compraFachada.agendarCita(dto);
        gestor.setCitaBienvenida(agendada);
    }
 
    // --- mapa ---
 
    @Override
    public JComponent getComponenteMapa() {
        return infraMapa.getComponenteMapa();
    }
 
    @Override
    public List<SucursalDTO> iniciarMapa() throws NegocioException {
        List<SucursalDTO> sucursales = compraFachada.obtenerSucursales();
        infraMapa.colocarMarcadores(sucursales);
        return sucursales;
    }
 
    @Override
    public SucursalDTO onMarcadorClickeado(String idSucursal) {
        infraMapa.resaltarMarcador(idSucursal);
        try {
            for (SucursalDTO s : compraFachada.obtenerSucursales()) {
                if (s.getIdSucursal().equals(idSucursal)) {
                    return s;
                }
            }
        } catch (NegocioException ignorada) {
            // si falla la búsqueda simplemente se regresa null
        }
        return null;
    }
 
    @Override
    public void actualizarUbicacion(double lat, double lng) {
        infraMapa.actualizarUbicacion(lat, lng);
    }
 
    @Override
    public void centrarMapaEn(double lat, double lng) {
        infraMapa.centrarMapaEn(lat, lng);
    }
 
    @Override
    public void setOnMarcadorClickListener(IControladorAplicacion.OnMarcadorClickListener listener) {
        infraMapa.setOnMarcadorClickListener(listener::onMarcadorClick);
    }
 
    @Override
    public void ubicarUsuarioAutomaticamente() {
        Thread hilo = new Thread(() -> {
            try {
                URL url = new URL("http://ip-api.com/json/?fields=lat,lon");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(4_000);
                con.setReadTimeout(4_000);
                StringBuilder sb = new StringBuilder();
                try (Scanner sc = new Scanner(con.getInputStream())) {
                    while (sc.hasNextLine()) {
                        sb.append(sc.nextLine());
                    }
                }
                double lat = extraerDouble(sb.toString(), "lat");
                double lon = extraerDouble(sb.toString(), "lon");
                if (lat != 0) {
                    SwingUtilities.invokeLater(() -> actualizarUbicacion(lat, lon));
                }
            } catch (Exception e) {
                System.out.println("[geo] no se pudo obtener ubicación: " + e.getMessage());
            }
        }, "hilo-geo");
        hilo.setDaemon(true);
        hilo.start();
    }
 
    /**
     * Extrae un valor double de un JSON simple buscando la clave indicada.
     *
     * @param json  cadena JSON sin parsear
     * @param clave nombre del campo a extraer
     * @return valor del campo, o 0 si no se encontró o falló el parseo
     */
    private double extraerDouble(String json, String clave) {
        try {
            int idx = json.indexOf("\"" + clave + "\":");
            if (idx < 0) {
                return 0;
            }
            int inicio = idx + clave.length() + 3;
            int fin = json.indexOf(",", inicio);
            if (fin < 0) {
                fin = json.indexOf("}", inicio);
            }
            return Double.parseDouble(json.substring(inicio, fin).trim());
        } catch (Exception ex) {
            return 0;
        }
    }
 
    @Override
    public byte[] generarQRMembresia(String idMembresia) throws NegocioException {
        return compraFachada.generarQRMembresia(idMembresia);
    }
 
    @Override
    public String iniciarServidorQR(byte[] qrPng) {
        return FachadaControlAcceso.getInstancia().iniciarServidorQR(qrPng);
    }
 
    @Override
    public void detenerServidorQR() {
        FachadaControlAcceso.getInstancia().detenerServidorQR();
    }
 
    /**
     * Cierra todas las pantallas que estén abiertas antes de mostrar una nueva.
     */
    private void cerrarPantallas() {
        if (pantallaBienvenida != null) {
            pantallaBienvenida.dispose();
            pantallaBienvenida = null;
        }
        if (pantallaInicioSesion != null) {
            pantallaInicioSesion.dispose();
            pantallaInicioSesion = null;
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
        if (pantallaQRSocio != null) {
            pantallaQRSocio.dispose();
            pantallaQRSocio = null;
        }
        if (pantallaRecepcion != null) {
            pantallaRecepcion.dispose();
            pantallaRecepcion = null;
        }
        if (pantallaFallida != null) {
            pantallaFallida.dispose();
            pantallaFallida = null;
        }
        if (pantallaExitosa != null) {
            pantallaExitosa.dispose();
            pantallaExitosa = null;
        }
    }
}