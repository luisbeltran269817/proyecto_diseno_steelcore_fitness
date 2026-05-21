/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clase_Control.GestorEstadosCompra;
import ControlDeAcceso.BC_PantallaEspera;
import ControladoresReportes.ControlReportes;
import Excepciones.NegocioException;
import Fachada.FachadaControlAcceso;
import Fachada.FachadaInicioSesion;
import Fachada.FachadaPlanearRutina;
import Fachada.IInicioSesion;
import Fachada.IPlanearRutina;
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
import PantallasInicioSesion.PantallaInicioSesionSocios;
import PantallasInventarioMantenimiento.PantallaAgregarModificarInventario;
import PantallasInventarioMantenimiento.PantallaEliminarInventario;
import PantallasInventarioMantenimiento.PantallaInventarioMantenimiento;
import PantallasInventarioMantenimiento.PantallaInventarioMaquinas;
import PantallasInventarioMantenimiento.PantallaProgramarMantenimiento;
import PantallasInventarioMantenimiento.PantallaSeleccionPiezasMantenimiento;
import PantallasRutina.PantallaBusquedaEntrenador;
import PantallasRutina.PantallaEditorRutina;
import PantallasRutina.PantallaMensaje;
import PantallasRutina.PantallaSeleccionEjercicios;
import PantallasRutina.PantallaVistaRutina;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.EjercicioDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.RutinaDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import dtosInventarioMantenimiento.AdminMantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.InventarioMantenimientoException;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import infraestructura.PresentacionInfra;
import inventarioMantenimiento.FachadaInventarioMantenimiento;
import inventarioMantenimiento.GestorEstadosMantenimiento;
import inventarioMantenimiento.IInventarioMantenimiento;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.Consumer;
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

    // COSAS DE INVENTARIO MANTENIMIENTO
    private final IInventarioMantenimiento inventarioMantenimientoFachada;
    private PantallaInventarioMantenimiento pantallaInventarioMantenimiento;
    private PantallaProgramarMantenimiento pantallaProgramarMantenimiento;
    private PantallaSeleccionPiezasMantenimiento pantallaSeleccionPiezasMantenimiento;
    private PantallaAgregarModificarInventario pantallaAgregarModificarInventario;
    private PantallaEliminarInventario pantallaEliminarInventario;
    private final GestorEstadosMantenimiento gestorMantenimiento;
    private PantallaInventarioMaquinas pantallaInventarioMaquinas;

    // De rutina
    private final IPlanearRutina rutinaFachada;
    private RutinaDTO rutinaSeleccionada;

    private PantallaBienvenida pantallaBienvenida;
    private PantallaInicioSesion pantallaInicioSesion;
    private PantallaInicioSesionSocios pantallaInicioSesionRecepcion;
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

    // Pantallas rutina
    private PantallaVistaRutina pantallaVistaRutina;
    private PantallaMensaje pantallaMensaje;
    private PantallaEditorRutina pantallaEditorRutina;
    private PantallaBusquedaEntrenador pantallaBusquedaEntrenador;

    private final ControlReportes controlReportes;

    private ControladorAplicacion() {
        this.inicioSesionFachada = new FachadaInicioSesion();
        this.compraFachada = new FachadaComprarMembresia();
        this.infraMapa = new PresentacionInfra();
        this.gestor = new GestorEstadosCompra();

        this.inventarioMantenimientoFachada = new FachadaInventarioMantenimiento();
        this.gestorMantenimiento = new GestorEstadosMantenimiento();

        this.rutinaFachada = new FachadaPlanearRutina();
        this.controlReportes = new ControlReportes(this);
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

    // --- Navegación ---
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
     */
    @Override
    public void irAAgendarCitaDesdePerfil() {
        gestor.setEntrenadorSeleccionado(null);
        gestor.setHorarioSeleccionado(null);

        if (gestor.getSucursalSeleccionada() == null) {
            try {
                MembresiaDTO membresia = compraFachada.obtenerMembresiaActiva(
                        gestor.getUsuarioActual().getCorreo()
                );

                if (membresia != null && membresia.getIdSucursal() != null) {
                    SucursalDTO sucursal = null;

                    try {
                        for (SucursalDTO s : compraFachada.obtenerSucursales()) {
                            if (s.getIdSucursal().equals(membresia.getIdSucursal())) {
                                sucursal = s;
                                break;
                            }
                        }
                    } catch (NegocioException ignored) {
                    }

                    if (sucursal == null) {
                        sucursal = new SucursalDTO();
                        sucursal.setIdSucursal(membresia.getIdSucursal());
                    }

                    gestor.setSucursalSeleccionada(sucursal);
                }
            } catch (NegocioException e) {
                // Continuamos aunque no se pudo cargar la membresía.
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
    public void irAQRDesdeRecepcion() {
        cerrarPantallas();
        pantallaQRSocio = new PantallaQR(this, true);
        pantallaQRSocio.setVisible(true);
    }

    @Override
    public void irAInicioSesionRecepcion() {
        cerrarPantallas();
        pantallaInicioSesionRecepcion = new PantallaInicioSesionSocios(this);
        pantallaInicioSesionRecepcion.setVisible(true);
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

    // --- Sesión ---
    @Override
    public void iniciarSesion(String correo, String contrasena) throws NegocioException {
        UsuarioDTO usuario;

        try {
            usuario = inicioSesionFachada.iniciarSesion(correo, contrasena);
        } catch (Exception ex) {
            throw new NegocioException(
                    ex.getMessage() != null
                    ? ex.getMessage()
                    : "Correo o contraseña incorrectos."
            );
        }

        gestor.setUsuarioActual(usuario);

        if (usuario instanceof AdminMantenimientoDTO) {
            irAInventarioMantenimiento();
            return;
        }

        if (esAdminReportes(usuario)) {
            irAAdministrarReportes();
            return;
        }

        if (usuario instanceof ClienteDTO) {
            try {
                CitaDTO cita = compraFachada.obtenerCitaBienvenida(usuario.getCorreo());
                gestor.setCitaBienvenida(cita);
            } catch (NegocioException ex) {
                // No bloquear inicio de sesión si falla la consulta de la cita.
            }

            irAPerfilUsuario();
            return;
        }

        irAPerfilUsuario();
    }

    @Override
    public void cerrarSesion() {
        gestor.limpiarSesion();
        irABienvenida();
    }

    // --- Consultas de negocio ---
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

        return plan == null
                ? 0.0
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

    // --- Mapa ---
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
            // Si falla la búsqueda simplemente se regresa null.
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
     * @param json cadena JSON sin parsear
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

    // --- Métodos de inventario mantenimiento ---
    @Override
    public void irAInventarioMantenimiento() {
        cerrarPantallas();
        pantallaInventarioMantenimiento = new PantallaInventarioMantenimiento(this);
        pantallaInventarioMantenimiento.setVisible(true);
    }

    @Override
    public void irAProgramarMantenimiento() {
        cerrarPantallas();
        pantallaProgramarMantenimiento = new PantallaProgramarMantenimiento(this);
        pantallaProgramarMantenimiento.setVisible(true);
    }

    @Override
    public List<MaquinaDTO> obtenerMaquinasMantenimiento() throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.obtenerMaquinas();
    }

    @Override
    public void setMaquinaSeleccionada(MaquinaDTO maquina) {
        gestorMantenimiento.setMaquinaSeleccionada(maquina);
    }

    @Override
    public MaquinaDTO getMaquinaSeleccionada() {
        return gestorMantenimiento.getMaquinaSeleccionada();
    }

    @Override
    public List<TecnicoDTO> obtenerTecnicosMantenimiento() throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.obtenerTecnicos();
    }

    @Override
    public boolean tecnicoTieneHorarioDisponible(String idTecnico, LocalDateTime fechaProgramada)
            throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.tecnicoTieneHorarioDisponible(idTecnico, fechaProgramada);
    }

    @Override
    public void guardarDatosProgramacionMantenimiento(
            String descripcion,
            LocalDateTime fechaProgramada,
            TecnicoDTO tecnico
    ) {
        gestorMantenimiento.setDescripcionMantenimiento(descripcion);
        gestorMantenimiento.setFechaProgramadaMantenimiento(fechaProgramada);
        gestorMantenimiento.setTecnicoSeleccionado(tecnico);
    }

    @Override
    public String getDescripcionMantenimiento() {
        return gestorMantenimiento.getDescripcionMantenimiento();
    }

    @Override
    public LocalDateTime getFechaProgramadaMantenimiento() {
        return gestorMantenimiento.getFechaProgramadaMantenimiento();
    }

    @Override
    public TecnicoDTO getTecnicoSeleccionadoMantenimiento() {
        return gestorMantenimiento.getTecnicoSeleccionado();
    }

    @Override
    public void irASeleccionPiezasMantenimiento() {
        cerrarPantallas();
        pantallaSeleccionPiezasMantenimiento = new PantallaSeleccionPiezasMantenimiento(this);
        pantallaSeleccionPiezasMantenimiento.setVisible(true);
    }

    @Override
    public boolean hayStockSuficientePieza(String idPieza, int cantidad)
            throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.hayStockSuficiente(idPieza, cantidad);
    }

    @Override
    public void agregarPiezaSeleccionadaMantenimiento(PiezaDTO pieza, int cantidad)
            throws InventarioMantenimientoException {
        if (cantidad <= 0) {
            throw new InventarioMantenimientoException("La cantidad debe ser mayor a cero");
        }

        List<MantenimientoPiezaDTO> piezas = gestorMantenimiento.getPiezasSeleccionadas();
        int cantidadTotalSolicitada = cantidad;

        for (MantenimientoPiezaDTO mp : piezas) {
            if (mp.getIdPieza() != null && mp.getIdPieza().equals(pieza.getIdPieza())) {
                cantidadTotalSolicitada += mp.getCantidad();
                break;
            }
        }

        boolean suficiente = inventarioMantenimientoFachada.hayStockSuficiente(
                pieza.getIdPieza(),
                cantidadTotalSolicitada
        );

        if (!suficiente) {
            throw new InventarioMantenimientoException("No hay stock suficiente para la cantidad solicitada");
        }

        for (MantenimientoPiezaDTO mp : piezas) {
            if (mp.getIdPieza() != null && mp.getIdPieza().equals(pieza.getIdPieza())) {
                mp.setCantidad(mp.getCantidad() + cantidad);
                return;
            }
        }

        MantenimientoPiezaDTO nueva = new MantenimientoPiezaDTO();
        nueva.setIdMantenimientoPiezaDTO(UUID.randomUUID().toString());
        nueva.setIdPieza(pieza.getIdPieza());
        nueva.setCantidad(cantidad);

        piezas.add(nueva);
    }

    @Override
    public MantenimientoDTO confirmarSolicitudMantenimiento()
            throws InventarioMantenimientoException {
        MaquinaDTO maquina = gestorMantenimiento.getMaquinaSeleccionada();
        TecnicoDTO tecnico = gestorMantenimiento.getTecnicoSeleccionado();
        String descripcion = gestorMantenimiento.getDescripcionMantenimiento();
        LocalDateTime fecha = gestorMantenimiento.getFechaProgramadaMantenimiento();
        List<MantenimientoPiezaDTO> piezas = gestorMantenimiento.getPiezasSeleccionadas();

        MantenimientoDTO mantenimiento = inventarioMantenimientoFachada.registrarSolicitudMantenimiento(
                maquina.getIdMaquina(),
                tecnico.getIdTecnico(),
                descripcion,
                fecha,
                piezas
        );

        gestorMantenimiento.limpiar();

        return mantenimiento;
    }

    @Override
    public List<PiezaDTO> obtenerPiezasMantenimiento() throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.obtenerPiezas();
    }

    @Override
    public List<MantenimientoPiezaDTO> obtenerPiezasSeleccionadasMantenimiento() {
        return gestorMantenimiento.getPiezasSeleccionadas();
    }

    @Override
    public void limpiarPiezasSeleccionadasMantenimiento() {
        gestorMantenimiento.limpiarPiezasSeleccionadas();
    }

    @Override
    public List<MaquinaDTO> filtrarMaquinasMantenimiento(
            String idSucursal,
            MaquinaDTO.EstadoMaquinaDTO estado
    ) throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.filtrarMaquinas(idSucursal, estado);
    }

    @Override
    public void prepararProgramacionMantenimiento() throws InventarioMantenimientoException {
        MaquinaDTO maquina = gestorMantenimiento.getMaquinaSeleccionada();

        if (maquina == null) {
            throw new InventarioMantenimientoException(
                    "Selecciona una máquina de la tabla antes de programar mantenimiento"
            );
        }

        inventarioMantenimientoFachada.validarMaquinaParaProgramarMantenimiento(
                maquina.getIdMaquina()
        );

        irAProgramarMantenimiento();
    }

    @Override
    public MaquinaDTO registrarMaquinaInventario(
            String idSucursal,
            String modelo,
            String tipo,
            MaquinaDTO.EstadoMaquinaDTO estado
    ) throws InventarioMantenimientoException {
        return inventarioMantenimientoFachada.registrarMaquina(
                idSucursal,
                modelo,
                tipo,
                estado
        );
    }

    @Override
    public void actualizarMaquinaInventario(
            String idMaquina,
            String idSucursal,
            String modelo,
            String tipo,
            MaquinaDTO.EstadoMaquinaDTO estado
    ) throws InventarioMantenimientoException {
        inventarioMantenimientoFachada.actualizarMaquina(
                idMaquina,
                idSucursal,
                modelo,
                tipo,
                estado
        );
    }

    @Override
    public void darBajaMaquinaInventario(String idMaquina, String motivo)
            throws InventarioMantenimientoException {
        inventarioMantenimientoFachada.darBajaMaquina(idMaquina, motivo);
    }

    @Override
    public void irAAgregarInventario() {
        cerrarPantallas();
        pantallaAgregarModificarInventario = new PantallaAgregarModificarInventario(this, false);
        pantallaAgregarModificarInventario.setVisible(true);
    }

    @Override
    public void irAModificarInventario() {
        cerrarPantallas();
        pantallaAgregarModificarInventario = new PantallaAgregarModificarInventario(this, true);
        pantallaAgregarModificarInventario.setVisible(true);
    }

    @Override
    public void irAEliminarInventario() {
        cerrarPantallas();
        pantallaEliminarInventario = new PantallaEliminarInventario(this);
        pantallaEliminarInventario.setVisible(true);
    }

    @Override
    public void irAInventarioMaquinas() {
        cerrarPantallas();
        pantallaInventarioMaquinas = new PantallaInventarioMaquinas(this);
        pantallaInventarioMaquinas.setVisible(true);
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

        if (pantallaInicioSesionRecepcion != null) {
            pantallaInicioSesionRecepcion.dispose();
            pantallaInicioSesionRecepcion = null;
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

        if (pantallaInventarioMantenimiento != null) {
            pantallaInventarioMantenimiento.dispose();
            pantallaInventarioMantenimiento = null;
        }

        if (pantallaProgramarMantenimiento != null) {
            pantallaProgramarMantenimiento.dispose();
            pantallaProgramarMantenimiento = null;
        }

        if (pantallaSeleccionPiezasMantenimiento != null) {
            pantallaSeleccionPiezasMantenimiento.dispose();
            pantallaSeleccionPiezasMantenimiento = null;
        }

        if (pantallaAgregarModificarInventario != null) {
            pantallaAgregarModificarInventario.dispose();
            pantallaAgregarModificarInventario = null;
        }

        if (pantallaEliminarInventario != null) {
            pantallaEliminarInventario.dispose();
            pantallaEliminarInventario = null;
        }

        if (pantallaInventarioMaquinas != null) {
            pantallaInventarioMaquinas.dispose();
            pantallaInventarioMaquinas = null;
        }

        if (pantallaVistaRutina != null) {
            pantallaVistaRutina.dispose();
            pantallaVistaRutina = null;
        }

        if (pantallaMensaje != null) {
            pantallaMensaje.dispose();
            pantallaMensaje = null;
        }

        if (pantallaEditorRutina != null) {
            pantallaEditorRutina.dispose();
            pantallaEditorRutina = null;
        }

        if (pantallaBusquedaEntrenador != null) {
            pantallaBusquedaEntrenador.dispose();
            pantallaBusquedaEntrenador = null;
        }
    }

    // --- Métodos del caso planear rutina ---
    @Override
    public void irAVistaRutina() {
        cerrarPantallas();
        pantallaVistaRutina = new PantallaVistaRutina(this);
        pantallaVistaRutina.setVisible(true);
    }

    @Override
    public void irAMensaje() {
        cerrarPantallas();
        pantallaMensaje = new PantallaMensaje(this);
        pantallaMensaje.setVisible(true);
    }

    @Override
    public List<RutinaDTO> obtenerRutinas() throws NegocioException {
        return rutinaFachada.obtenerRutinas(getUsuarioActual().getCorreo());
    }

    @Override
    public void irAEditorRutinaNueva() {
        cerrarPantallas();
        rutinaSeleccionada = null;
        pantallaEditorRutina = new PantallaEditorRutina(this);
        pantallaEditorRutina.setVisible(true);
    }

    @Override
    public void irAEditorRutinaExistente(RutinaDTO rutina) {
        cerrarPantallas();
        rutinaSeleccionada = rutina;
        pantallaEditorRutina = new PantallaEditorRutina(this);
        pantallaEditorRutina.setVisible(true);
    }

    @Override
    public RutinaDTO getRutinaSeleccionada() {
        return rutinaSeleccionada;
    }

    @Override
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular)
            throws NegocioException {
        return rutinaFachada.recuperarEjercicios(grupoMuscular);
    }

    @Override
    public void abrirSeleccionEjercicios(
            String nombreDia,
            Consumer<String> callbackGrupo,
            Consumer<List<EjercicioDTO>> callbackEjercicios
    ) {
        new PantallaSeleccionEjercicios(
                pantallaEditorRutina,
                this,
                nombreDia,
                callbackGrupo,
                callbackEjercicios
        );
    }

    @Override
    public RutinaDTO guardarRutina(RutinaDTO rutina) throws NegocioException {
        return rutinaFachada.guardarRutina(getUsuarioActual().getCorreo(), rutina);
    }

    @Override
    public boolean borrarRutina(String nombre) throws NegocioException {
        return rutinaFachada.borrarRutina(getUsuarioActual().getCorreo(), nombre);
    }

    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDeSucursalActual()
            throws NegocioException {
        String idSucursal = rutinaFachada.obtenerIdSucursalMembresiaActiva(
                getUsuarioActual().getCorreo()
        );

        return obtenerEntrenadoresDeSucursal(idSucursal);
    }

    @Override
    public void irABusquedaEntrenador(
            RutinaDTO rutina,
            Consumer<String> callbackIdEntrenador
    ) {
        cerrarPantallas();
        pantallaBusquedaEntrenador = new PantallaBusquedaEntrenador(
                this,
                rutina,
                callbackIdEntrenador
        );
        pantallaBusquedaEntrenador.setVisible(true);
    }

    @Override
    public EntrenadorDTO obtenerEntrenadorPorId(String id) throws NegocioException {
        return rutinaFachada.obtenerEntrenadorPorId(id);
    }

    @Override
    public void irAEditorConPlantilla(String nombrePlantilla) throws NegocioException {
        rutinaSeleccionada = rutinaFachada.obtenerPlantilla(nombrePlantilla);
        cerrarPantallas();
        pantallaEditorRutina = new PantallaEditorRutina(this);
        pantallaEditorRutina.setVisible(true);
    }

    @Override
    public void irAAdministrarReportes() {
        controlReportes.iniciarAdministrarReportes();
    }

    /**
     * Verifica si el usuario autenticado corresponde al administrador de
     * reportes.
     *
     * @param usuario usuario que inició sesión.
     * @return true si debe entrar al módulo de reportes.
     */
    private boolean esAdminReportes(UsuarioDTO usuario) {
        if (usuario == null || usuario.getCorreo() == null) {
            return false;
        }

        return usuario.getCorreo().equalsIgnoreCase("reportes@gmail.com");
    }
}
