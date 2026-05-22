/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.HorarioDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import java.util.List;

/**
 * Contrato principal de la fachada para las pantallas de acceso.
 * La fachada es responsable de mantener el contexto de la visita activa
 * (idVisita, idCliente, idPlan, etc.) — el coordinador no guarda nada.
 *
 * @author julian izaguirre
 */
public interface Icontrolacceso {
    String getIdVisitaActiva();

    String getIdClienteActivo();

    String getIdPlanActivo();

    boolean isPlanIncluyeEntrenador();

    boolean isPlanIncluyeClases();

    /**
     * Revisa si el codigo escaneado es valido para darle entrada al socio.
     * Internamente guarda el contexto de la visita (idVisita, idCliente, idPlan…).
     *
     * @param codigoQR Texto del QR escaneado
     * @return Los datos de la visita para mostrar en pantalla
     * @throws AccesoDenegadoException Si el acceso es rechazado por alguna regla
     */
    ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException;

    /**
     * Anota que el socio va a andar por su cuenta en el area comun de pesas.
     * Usa internamente el idVisita que guardo al procesar el QR.
     *
     * @throws AccesoDenegadoException Si hay problema al guardar el registro
     */
    void registrarAreaGeneral() throws AccesoDenegadoException;

    /**
     * Trae a los entrenadores que todavia tienen algun hueco en su agenda hoy.
     *
     * @param idSucursal Sucursal a revisar
     * @return Lista de entrenadores listos para mandar a la pantalla
     * @throws AccesoDenegadoException Si falla la comunicacion con la base
     */
    List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException;

    /**
     * Trae los horarios disponibles de un entrenador especifico.
     * Solo devuelve los que aun no estan ocupados.
     *
     * @param idEntrenador Entrenador a consultar
     * @return Lista de horarios libres listos para mostrar en pantalla
     * @throws AccesoDenegadoException Si falla la consulta
     */
    List<HorarioDTO> obtenerHorariosEntrenador(String idEntrenador)
            throws AccesoDenegadoException;

    /**
     * Separa de forma segura un horario con el entrenador seleccionado.
     * Usa internamente el idVisita e idCliente guardados al procesar el QR.
     *
     * @param idEntrenador Entrenador elegido por el socio
     * @param idHorario    Horario que se ocupara
     * @throws AccesoDenegadoException Si alguien mas se lo gano o hay algun error
     */
    void asignarEntrenador(String idEntrenador, String idHorario)
            throws AccesoDenegadoException;

    /**
     * Muestra las clases que aplican para el nivel de membresia que tiene el socio.
     * Usa internamente el idPlan e idCliente guardados al procesar el QR.
     *
     * @param idSucursal Sucursal a consultar
     * @return Lista de clases a las que se puede inscribir
     * @throws AccesoDenegadoException Si su plan no incluye clases
     */
    List<ClaseDTO> obtenerClasesPorPlan(String idSucursal) throws AccesoDenegadoException;

    /**
     * Apunta al socio en la lista de asistencia de la clase que escogio.
     * Usa internamente el idVisita e idCliente guardados al procesar el QR.
     *
     * @param idClase Clase a la que quiere entrar
     * @throws AccesoDenegadoException Si ya no cabe nadie mas o ya estaba registrado
     */
    void inscribirClase(String idClase) throws AccesoDenegadoException;

    /**
     * Excepcion personalizada para cuando rebotamos a alguien en la entrada.
     */
    class AccesoDenegadoException extends Exception {
        private final String motivo;

        public AccesoDenegadoException(String motivo) {
            super(motivo);
            this.motivo = motivo;
        }

        public String getMotivo() { return motivo; }
    }
}