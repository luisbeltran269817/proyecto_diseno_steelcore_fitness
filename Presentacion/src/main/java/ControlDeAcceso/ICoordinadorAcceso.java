package ControlDeAcceso;

import Controladores.IControladorAplicacion;

import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;

import java.util.List;

/**
 * Interfaz del coordinador de acceso del caso individual.
 *
 * Extiende IControladorAplicacion del caso base (hereda login, navegacion
 * compartida, sucursal, etc.) y agrega los metodos propios del control de acceso.
 *
 * Todos los metodos que antes retornaban "ResultadoDTO" (clase inexistente)
 * ahora retornan ResultadoAccesoDTO:
 *  - accesoConcedido=true  → operacion exitosa
 *  - accesoConcedido=false → error, revisar getMensaje() para el motivo
 *
 * @author julian izaguirre
 */
public interface ICoordinadorAcceso extends IControladorAplicacion {

    // ── Navegacion ──────────────────────────────────────────────────────────

    void iniciarPantallaEspera();
    void iniciarPantallaExpediente(ResultadoAccesoDTO resultado);
    void iniciarPantallaEntrenadores();
    void iniciarPantallaClases();

    // ── QR / Acceso ─────────────────────────────────────────────────────────

    /**
     * Procesa el QR escaneado y guarda el contexto de la visita.
     * @return ResultadoAccesoDTO, o null si el QR no es valido
     */
    ResultadoAccesoDTO procesarQR(String codigoQR);

    /** Registra que el socio eligio el area general (pesas). */
    void seleccionarAreaGeneral();

    // ── Entrenadores ────────────────────────────────────────────────────────

    boolean planIncluyeEntrenador();

    List<EntrenadorDTO> obtenerEntrenadoresDisponibles();

    /**
     * Asigna el entrenador al socio.
     * @return ResultadoAccesoDTO; isAccesoConcedido()==true si fue exitoso
     */
    ResultadoAccesoDTO asignarEntrenador(String idEntrenador, String idHorario);

    // ── Clases ──────────────────────────────────────────────────────────────

    boolean planIncluyeClases();

    List<ClaseDTO> obtenerClasesPlan();

    /**
     * Inscribe al socio en la clase seleccionada.
     * @return ResultadoAccesoDTO; isAccesoConcedido()==true si fue exitoso
     */
    ResultadoAccesoDTO inscribirClase(String idClase);
}