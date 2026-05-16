/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Guarda y expone el estado del flujo de compra de membresía.
 *
 * El controlador de aplicación actúa como mediador entre las pantallas,
 * pero no debería cargar con la responsabilidad de recordar qué eligió
 * el usuario en cada paso. Eso es trabajo de esta clase.
 *
 * Cada vez que el usuario inicia una compra nueva se llama a {@link #limpiar()}
 * para resetear todo a cero.
 *
 * @author julian izaguirre
 */
public class GestorEstadosCompra {

    private UsuarioDTO usuarioActual;
    private SucursalDTO sucursalSeleccionada;
    private PlanDTO planSeleccionado;
    private List<AmenidadDTO> extrasSeleccionados;
    private EntrenadorDTO entrenadorSeleccionado;
    private HorarioDTO horarioSeleccionado;
    private MembresiaDTO membresiaRecienCreada;
    private CitaDTO citaBienvenida;
    private String tokenTarjeta;

    /**
     * Crea el gestor con la lista de extras vacía.
     */
    public GestorEstadosCompra() {
        this.extrasSeleccionados = new ArrayList<>();
    }

    /**
     * Limpia todas las selecciones del flujo de compra.
     * Hay que llamar esto antes de iniciar una compra nueva para que
     * no queden datos de la sesión anterior.
     */
    public void limpiar() {
        sucursalSeleccionada = null;
        planSeleccionado = null;
        extrasSeleccionados.clear();
        entrenadorSeleccionado = null;
        horarioSeleccionado = null;
        membresiaRecienCreada = null;
        tokenTarjeta = null;
    }

    /**
     * Limpia todo lo que tiene que ver con la sesión del usuario.
     * Se usa al cerrar sesión.
     */
    public void limpiarSesion() {
        limpiar();
        usuarioActual = null;
        citaBienvenida = null;
    }

    // --- getters y setters ---

    /**
     * Regresa el usuario que inició sesión.
     *
     * @return usuario actual, o null si no hay sesión activa
     */
    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Guarda el usuario que acaba de iniciar sesión.
     *
     * @param usuario usuario autenticado
     */
    public void setUsuarioActual(UsuarioDTO usuario) {
        this.usuarioActual = usuario;
    }

    /**
     * Regresa la sucursal que el usuario eligió en el mapa.
     *
     * @return sucursal seleccionada, o null si todavía no eligió
     */
    public SucursalDTO getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    /**
     * Guarda la sucursal elegida.
     *
     * @param sucursal sucursal que el usuario seleccionó
     */
    public void setSucursalSeleccionada(SucursalDTO sucursal) {
        this.sucursalSeleccionada = sucursal;
    }

    /**
     * Regresa el plan de membresía elegido.
     *
     * @return plan seleccionado, o null si todavía no eligió
     */
    public PlanDTO getPlanSeleccionado() {
        return planSeleccionado;
    }

    /**
     * Guarda el plan elegido.
     *
     * @param plan plan de membresía que el usuario seleccionó
     */
    public void setPlanSeleccionado(PlanDTO plan) {
        this.planSeleccionado = plan;
    }

    /**
     * Regresa la lista de amenidades extra que el usuario agregó.
     *
     * @return lista de extras, nunca null (puede estar vacía)
     */
    public List<AmenidadDTO> getExtrasSeleccionados() {
        return extrasSeleccionados;
    }

    /**
     * Reemplaza la lista de extras seleccionados.
     *
     * @param extras nueva lista de amenidades extra
     */
    public void setExtrasSeleccionados(List<AmenidadDTO> extras) {
        this.extrasSeleccionados = extras != null ? extras : new ArrayList<>();
    }

    /**
     * Regresa el entrenador elegido para la cita de bienvenida.
     *
     * @return entrenador seleccionado, o null si todavía no eligió
     */
    public EntrenadorDTO getEntrenadorSeleccionado() {
        return entrenadorSeleccionado;
    }

    /**
     * Guarda el entrenador elegido.
     *
     * @param entrenador entrenador que el usuario seleccionó
     */
    public void setEntrenadorSeleccionado(EntrenadorDTO entrenador) {
        this.entrenadorSeleccionado = entrenador;
    }

    /**
     * Regresa el horario elegido para la cita de bienvenida.
     *
     * @return horario seleccionado, o null si todavía no eligió
     */
    public HorarioDTO getHorarioSeleccionado() {
        return horarioSeleccionado;
    }

    /**
     * Guarda el horario elegido.
     *
     * @param horario horario que el usuario seleccionó
     */
    public void setHorarioSeleccionado(HorarioDTO horario) {
        this.horarioSeleccionado = horario;
    }

    /**
     * Regresa la membresía que se creó en esta sesión después de pagar.
     * PantallaQR la usa para mostrar el QR correcto sin tener que buscarlo
     * en la base de datos.
     *
     * @return membresía recién creada, o null si todavía no se compró
     */
    public MembresiaDTO getMembresiaRecienCreada() {
        return membresiaRecienCreada;
    }

    /**
     * Guarda la membresía que se acaba de crear.
     *
     * @param membresia membresía generada después del pago exitoso
     */
    public void setMembresiaRecienCreada(MembresiaDTO membresia) {
        this.membresiaRecienCreada = membresia;
    }

    /**
     * Regresa la cita de bienvenida del usuario actual.
     *
     * @return cita de bienvenida, o null si no tiene
     */
    public CitaDTO getCitaBienvenida() {
        return citaBienvenida;
    }

    /**
     * Guarda la cita de bienvenida.
     *
     * @param cita cita agendada
     */
    public void setCitaBienvenida(CitaDTO cita) {
        this.citaBienvenida = cita;
    }

    /**
     * Regresa el token de tarjeta generado por Stripe para esta compra.
     *
     * @return token de pago, o null si todavía no se capturó
     */
    public String getTokenTarjeta() {
        return tokenTarjeta;
    }

    /**
     * Guarda el token de tarjeta.
     *
     * @param token token generado por Stripe
     */
    public void setTokenTarjeta(String token) {
        this.tokenTarjeta = token;
    }
}
