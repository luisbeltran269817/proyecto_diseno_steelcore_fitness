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
import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.InventarioMantenimientoException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author Tungs
 */
public interface IControladorAplicacion {

    /**
     * Regresa el usuario que tiene sesión activa.
     *
     * @return usuario actual
     */
    UsuarioDTO getUsuarioActual();
 
    /**
     * Guarda la sucursal que el usuario eligió.
     *
     * @param sucursal sucursal seleccionada en el mapa
     */
    void setSucursalSeleccionada(SucursalDTO sucursal);
 
    /**
     * Regresa la sucursal elegida.
     *
     * @return sucursal seleccionada
     */
    SucursalDTO getSucursalSeleccionada();
 
    /**
     * Guarda el plan de membresía elegido.
     *
     * @param plan plan elegido
     */
    void setPlanSeleccionado(PlanDTO plan);
 
    /**
     * Regresa el plan elegido.
     *
     * @return plan seleccionado
     */
    PlanDTO getPlanSeleccionado();
 
    /**
     * Guarda los extras que el usuario agregó a su membresía.
     *
     * @param extras lista de amenidades extra
     */
    void setExtrasSeleccionados(List<AmenidadDTO> extras);
 
    /**
     * Regresa los extras seleccionados.
     *
     * @return lista de amenidades extra
     */
    List<AmenidadDTO> getExtrasSeleccionados();
 
    /**
     * Guarda el entrenador elegido para la cita de bienvenida.
     *
     * @param entrenador entrenador seleccionado
     */
    void setEntrenadorSeleccionado(EntrenadorDTO entrenador);
 
    /**
     * Regresa el entrenador elegido.
     *
     * @return entrenador seleccionado
     */
    EntrenadorDTO getEntrenadorSeleccionado();
 
    /**
     * Guarda el horario elegido para la cita de bienvenida.
     *
     * @param horario horario seleccionado
     */
    void setHorarioSeleccionado(HorarioDTO horario);
 
    /**
     * Regresa el horario elegido.
     *
     * @return horario seleccionado
     */
    HorarioDTO getHorarioSeleccionado();
 
    /**
     * Regresa la membresía creada en esta sesión justo después de pagar.
     * PantallaQR la usa para mostrar el código correcto de inmediato.
     *
     * @return membresía recién creada, o null si todavía no se compró
     */
    MembresiaDTO getMembresiaRecienCreada();
 
    /**
     * Guarda el token de tarjeta generado por Stripe.
     *
     * @param token token de pago
     */
    void setTokenTarjeta(String token);
 
    /**
     * Regresa el token de tarjeta.
     *
     * @return token de pago
     */
    String getTokenTarjeta();
 
    // --- navegación entre pantallas ---
 
    /** Muestra la pantalla de bienvenida. */
    void irABienvenida();
 
    /** Muestra la pantalla de inicio de sesión. */
    void irAInicioSesion();
 
    /** Muestra el perfil del usuario. */
    void irAPerfilUsuario();
 
    /**
     * Inicia el flujo de compra limpiando cualquier selección anterior
     * y llevando al usuario a elegir sucursal.
     */
    void iniciarCompraMembresia();
 
    /** Muestra la pantalla de selección de sucursal con el mapa. */
    void irASeleccionSucursal();
 
    /** Muestra la pantalla de selección de plan. */
    void irASeleccionPlan();
 
    /** Muestra el detalle del plan elegido. */
    void irADetallePlan();
 
    /** Muestra los términos y condiciones. */
    void irATerminosCondiciones();
 
    /** Muestra la pantalla para capturar datos bancarios. */
    void irADatosBancarios();
 
    /** Muestra la pantalla de selección de instructor. */
    void irASeleccionInstructor();
    /**
     * Inicia el flujo de agendar cita desde el perfil (cliente ya tiene membresia).
     * Recupera la sucursal de la membresia activa antes de ir a seleccion de instructor.
     */
    void irAAgendarCitaDesdePerfil();
 
    /**
     * Muestra la pantalla de selección de horario.
     *
     * @throws NegocioException si no se pueden cargar los horarios
     */
    void irASeleccionHorario() throws NegocioException;
 
    /** Muestra la pantalla con el código QR del socio. */
    void irAQR();
 
    /** Abre el módulo de recepción que escanea QR. */
    void irAModuloRecepcion();
 
    /**
     * Muestra la pantalla de transacción fallida con el motivo.
     *
     * @param causa descripción del error
     */
    void irATransaccionFallida(String causa);
 
    /** Muestra la pantalla de transacción exitosa. */
    void irATransaccionExitosa();
 
    // --- sesión ---
 
    /**
     * Autentica al usuario y lleva al perfil.
     *
     * @param correo    correo del usuario
     * @param contrasena contraseña del usuario
     * @throws NegocioException
     */
    void iniciarSesion(String correo, String contrasena) throws NegocioException;
 
    /** Cierra la sesión y regresa a la pantalla de bienvenida. */
    void cerrarSesion();
 
    // --- consultas de negocio ---
 
    /**
     * Indica si el usuario actual tiene una membresía activa.
     *
     * @return true si tiene membresía activa
     * @throws NegocioException si falla la consulta
     */
    boolean tieneMembresiaActiva() throws NegocioException;
 
    /**
     * Indica si el usuario actual tiene una cita de bienvenida agendada.
     *
     * @return true si tiene cita
     * @throws NegocioException si falla la consulta
     */
    boolean tieneCitaBienvenida() throws NegocioException;
 
    /**
     * Regresa la membresía activa del usuario actual.
     *
     * @return membresía activa, o null si no tiene
     * @throws NegocioException si falla la consulta
     */
    MembresiaDTO obtenerMembresiaActiva() throws NegocioException;
 
    /**
     * Regresa la cita de bienvenida del usuario actual.
     *
     * @return cita de bienvenida, o null si no tiene
     * @throws NegocioException si falla la consulta
     */
    CitaDTO obtenerCitaBienvenida() throws NegocioException;
 
    /**
     * Regresa el historial de visitas del usuario actual.
     *
     * @return lista de visitas
     * @throws NegocioException si falla la consulta
     */
    List<VisitaDTO> obtenerHistorial() throws NegocioException;
 
    /**
     * Cancela la membresía activa del usuario actual.
     *
     * @throws NegocioException si falla la operación
     */
    void cancelarMembresia() throws NegocioException;
 
    /**
     * Regresa todas las sucursales disponibles.
     *
     * @return lista de sucursales
     * @throws NegocioException si falla la consulta
     */
    List<SucursalDTO> obtenerSucursales() throws NegocioException;
 
    /**
     * Regresa los planes disponibles en una sucursal.
     *
     * @param idSucursal id de la sucursal
     * @return lista de planes
     * @throws NegocioException si falla la consulta
     */
    List<PlanDTO> obtenerPlanesDeSucursal(String idSucursal) throws NegocioException;
 
    /**
     * Regresa las amenidades extra disponibles.
     *
     * @return lista de amenidades extra
     */
    List<AmenidadDTO> obtenerAmenidadesExtra();
 
    /**
     * Regresa los entrenadores de una sucursal.
     *
     * @param idSucursal id de la sucursal
     * @return lista de entrenadores
     * @throws NegocioException si falla la consulta
     */
    List<EntrenadorDTO> obtenerEntrenadoresDeSucursal(String idSucursal) throws NegocioException;
 
    /**
     * Regresa los horarios disponibles de un entrenador.
     *
     * @param idEntrenador id del entrenador
     * @return lista de horarios
     * @throws NegocioException si falla la consulta
     */
    List<HorarioDTO> obtenerHorariosDeEntrenador(String idEntrenador) throws NegocioException;
 
    /**
     * Procesa el pago y crea la membresía con los datos guardados en el gestor.
     * Si el pago es exitoso navega a la pantalla de transacción exitosa,
     * si falla navega a la de transacción fallida.
     */
    void confirmarCompra();
 
    /**
     * Calcula el total a pagar con el plan y los extras elegidos.
     *
     * @return total en pesos
     * @throws NegocioException si falla el cálculo
     */
    double calcularTotal() throws NegocioException;
 
    /**
     * Agenda la cita de bienvenida con el entrenador y horario elegidos.
     *
     * @throws NegocioException si falla la operación
     */
    void confirmarCitaBienvenida() throws NegocioException;
 
    // --- mapa ---
 
    /**
     * Interfaz funcional para recibir el id de la sucursal cuando el
     * usuario hace clic en un marcador del mapa.
     */
    @FunctionalInterface
    interface OnMarcadorClickListener {
        /**
         * Se dispara cuando el usuario hace clic en un marcador.
         *
         * @param idSucursal id de la sucursal clickeada
         */
        void onMarcadorClick(String idSucursal);
    }
 
    /**
     * Regresa el componente visual del mapa para incrustarlo en una pantalla.
     *
     * @return componente Swing del mapa
     */
    JComponent getComponenteMapa();
 
    /**
     * Coloca los marcadores de las sucursales en el mapa.
     *
     * @return lista de sucursales con marcadores colocados
     * @throws NegocioException si falla la carga de sucursales
     */
    List<SucursalDTO> iniciarMapa() throws NegocioException;
 
    /**
     * Resalta el marcador de la sucursal clickeada y regresa sus datos.
     *
     * @param idSucursal id de la sucursal que se clickeó
     * @return datos de la sucursal, o null si no se encontró
     */
    SucursalDTO onMarcadorClickeado(String idSucursal);
 
    /**
     * Actualiza la posición del usuario en el mapa y centra la vista ahí.
     *
     * @param lat latitud
     * @param lng longitud
     */
    void actualizarUbicacion(double lat, double lng);
 
    /**
     * Centra el mapa en las coordenadas indicadas.
     *
     * @param lat latitud
     * @param lng longitud
     */
    void centrarMapaEn(double lat, double lng);
 
    /**
     * Registra el listener que se llama cuando el usuario hace clic en un marcador.
     *
     * @param listener listener a registrar
     */
    void setOnMarcadorClickListener(OnMarcadorClickListener listener);
 
    /**
     * Genera los bytes PNG del código QR para una membresía.
     *
     * @param idMembresia id de la membresía
     * @return bytes PNG del QR
     * @throws NegocioException si falla la generación
     */
    byte[] generarQRMembresia(String idMembresia) throws NegocioException;
 
    /**
     * Levanta un servidor HTTP temporal para servir el QR como imagen.
     *
     * @param qrPng bytes PNG del código QR
     * @return URL donde se sirve el QR
     */
    String iniciarServidorQR(byte[] qrPng);
 
    /**
     * Apaga el servidor HTTP del QR. Hay que llamar esto al salir de PantallaQR.
     */
    void detenerServidorQR();
 
    /**
     * Lanza un hilo que consulta la API de geolocalización por IP y centra
     * el mapa en la ubicación aproximada del usuario.
     */
    void ubicarUsuarioAutomaticamente();
    
    
    //COSAS DE INVENTARIO MANTENIMIENTO
        /**
         * Método que viaja a la pantalla de inventarioMantenimiento
         */
        public void irAInventarioMantenimiento();
        /**
         * Método que viaja a la ventana de programación de mantenimiento
         */
        public void irAProgramarMantenimiento();

        /**
         * Método que viaja a la pantala de obtenerMaquinasMantenimiento
         * @return una lista de máquinasDTO
         * @throws InventarioMantenimientoException si ocurre un error
         */
        public List<MaquinaDTO> obtenerMaquinasMantenimiento() throws InventarioMantenimientoException;
        /**
         * Método que cambia la máquina seleccionada (estado)
         * @param maquina la máquina seleccionada
         */
        public void setMaquinaSeleccionada(MaquinaDTO maquina);
        /**
         * Método que obtiene la máquina seleccionada
         * @return la máquina que se seleccionó
         */
        public MaquinaDTO getMaquinaSeleccionada();

        /**
         * Método que obtiene los técnicos del sistema
         * @return una lista con todos los técnicos
         * @throws InventarioMantenimientoException si ocurre un error
         */
        List<TecnicoDTO> obtenerTecnicosMantenimiento() throws InventarioMantenimientoException;

        /**
         * Método que pregunta a la base de datos si un ténico tiene horario disponible
         * @param idTecnico el id del técnico a validar
         * @param fechaProgramada el horario a avalidar
         * @return verdadero si tiene horario disponible, falso en caso contrario
         * @throws InventarioMantenimientoException si ocurre un error
         */
        boolean tecnicoTieneHorarioDisponible(String idTecnico, LocalDateTime fechaProgramada) throws InventarioMantenimientoException;

        /**
         * Método que guarda los datos de una programación de mantenimiento
         * @param descripcion la descripción del mantenimiento
         * @param fechaProgramada la fecha programada
         * @param tecnico el técnico asignado al mantenimiento
         */
        void guardarDatosProgramacionMantenimiento(String descripcion, LocalDateTime fechaProgramada, TecnicoDTO tecnico);

        /**
         * Método que obtiene la descripción de un mantenimiento escrita (estado)
         * @return 
         */
        String getDescripcionMantenimiento();
        /**
         * Método que obtiene la fecha programada de un mantenimiento (estado)
         * @return la fecha programada de un mantenimiento
         */
        LocalDateTime getFechaProgramadaMantenimiento();

        /**
         * Método que obtiene el técnico seleccionado para un mantenimiento
         * @return el técnico seleccionado
         */
        TecnicoDTO getTecnicoSeleccionadoMantenimiento();

        /**
         * Método que viaja a la pantalla de selección de plan
         */
        void irASeleccionPiezasMantenimiento();
        
        /**
         * Método que obtiene las piezas de la BD
         * @return una lista con todas las piezas obtenidas
         * @throws InventarioMantenimientoException si ocurre un error
         */
        List<PiezaDTO> obtenerPiezasMantenimiento() throws InventarioMantenimientoException;

        /**
         * Método que agrega una pieza seleccionada a un mantenimiento
         * @param pieza las piezas seleccionadas
         * @param cantidad la cantidad de la 
         * @throws InventarioMantenimientoException si ocurre un error
         */
        void agregarPiezaSeleccionadaMantenimiento(PiezaDTO pieza, int cantidad) throws InventarioMantenimientoException;

        /**
         * Método que obtiene las piezas seleccionadas de un mantenimiento (Estado)
         * @return 
         */
        List<MantenimientoPiezaDTO> obtenerPiezasSeleccionadasMantenimiento();

        /**
         * Método que valida que haya stock suficiente para una 
         * @param idPieza el id de la pieza a buscar
         * @param cantidad la cantidad de la pieza a validar
         * @return verdadero si hay stock suficiente, falso en caso contrario
         * @throws InventarioMantenimientoException si ocurre un error
         */
        boolean hayStockSuficientePieza(String idPieza, int cantidad) throws InventarioMantenimientoException;
        
        /**
         * Método que confirma una solititud de mantenimiento
         * @return el mantenimientoDTO generado
         * @throws InventarioMantenimientoException si ocurre un erro
         */
        public MantenimientoDTO confirmarSolicitudMantenimiento() throws InventarioMantenimientoException;
        
        /**
         * Método que limpia el estado de piezas seleccionadas
         */
        void limpiarPiezasSeleccionadasMantenimiento();
        
        /**
         * Método que obtiene todas las máquinas de la BD según filtros
         * @param idSucursal el id de la sucursal a filtrar
         * @param estado el estado de la máquina a filtrar
         * @return una lista de máquinas DTO
         * @throws InventarioMantenimientoException si ocurre un errors
         */
        List<MaquinaDTO> filtrarMaquinasMantenimiento(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;

        /**
         * Método que prepara una solicitud de mantenimiento
         * @throws InventarioMantenimientoException si ocurre un error 
         */
        void prepararProgramacionMantenimiento() throws InventarioMantenimientoException;

        /**
         * Método que viaja a la pantalla de inventario de máquinas
         */
        void irAInventarioMaquinas();
        
        /**
         * Método que registra una máquina en la bd
         * @param idSucursal la sucursal a la que pertenece
         * @param modelo el modelo de la máquina
         * @param tipo el tipo de la máquina
         * @param estado el estado de la máquina
         * @return  la MaquinaDTO registrada
         * @throws InventarioMantenimientoException si ocurre un error
         */
        MaquinaDTO registrarMaquinaInventario(String idSucursal,String modelo, String tipo, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;

        /**
         * Método que actualiza una máquina de la BD
         * @param idMaquina la máquina a a insertar
         * @param idSucursal el id de la sucursal de la máquina
         * @param modelo el modelo de la máquina
         * @param tipo el tipo de la máquina
         * @param estado el estado de la máquina
         * @throws InventarioMantenimientoException  si ocurre un error
         */
        void actualizarMaquinaInventario(String idMaquina,String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException;

        /**
         * Método que da de baja una máquina en la Base de Datos
         * @param idMaquina el id de la máquina a dar de baja
         * @param motivo el motivo de la baja
         * @throws InventarioMantenimientoException si ocurre un error
         */
        void darBajaMaquinaInventario(String idMaquina,String motivo) throws InventarioMantenimientoException;
        
        /**
         * Método que viaja a la pantalla de agregarInventario
         */
        void irAAgregarInventario();

        /**
         * Método que viaja a la pantalla de modificar inventario
         */
        void irAModificarInventario();

        /**
         * Método que viaja a la pantalla de eliminar inventario
         */
        void irAEliminarInventario();
}