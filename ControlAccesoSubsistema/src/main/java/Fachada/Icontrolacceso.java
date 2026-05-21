/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import java.util.List;

/**
 * Contrato principal de la fachada para las pantallas de acceso
 *
 * @author julian izaguirre
 */
public interface Icontrolacceso {

    /**
     * Revisa si el codigo escaneado es valido para darle entrada al socio
     *
     * @param codigoQR Texto del QR escaneado
     * @return Los datos de la visita para la pantalla
     * @throws AccesoDenegadoException Si el acceso es rechazado por alguna regla
     */
    ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException;

    /**
     * Anota que el socio va a andar por su cuenta en el area comun de pesas
     *
     * @param idVisita Identificador de la visita que esta corriendo
     * @throws AccesoDenegadoException Si hay problema al guardar el registro
     */
    void registrarAreaGeneral(String idVisita) throws AccesoDenegadoException;

    /**
     * Trae a los entrenadores que todavia tienen algun hueco en su agenda hoy
     *
     * @param idSucursal Sucursal a revisar
     * @return Lista de entrenadores listos para mandar a la pantalla
     * @throws AccesoDenegadoException Si falla la comunicacion con la base
     */
    List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException;

    /**
     * Separa de forma segura un horario con el entrenador seleccionado
     *
     * @param idVisita Identificador de la visita
     * @param idCliente Identificador del socio
     * @param idEntrenador Entrenador elegido por el socio
     * @param idHorario Horario que se ocupara
     * @throws AccesoDenegadoException Si alguien mas se lo gano o hay algun error
     */
    void asignarEntrenador(String idVisita, String idCliente,
                            String idEntrenador, String idHorario)
            throws AccesoDenegadoException;

    /**
     * Muestra las clases que aplican para el nivel de membresia que tiene el socio
     *
     * @param idSucursal Sucursal a consultar
     * @param idPlan Plan que paga el socio
     * @param idCliente Identificador del socio para ver a cuales ya entro
     * @param incluyeClases Permiso para tomar clase segun su membresia
     * @return Lista de clases a las que se puede inscribir
     * @throws AccesoDenegadoException Si su plan no incluye clases
     */
    List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                         String idCliente, boolean incluyeClases)
            throws AccesoDenegadoException;

    /**
     * Apunta al socio en la lista de asistencia de la clase que escogio
     *
     * @param idVisita Visita activa
     * @param idClase Clase a la que quiere entrar
     * @param idCliente Socio a inscribir
     * @throws AccesoDenegadoException Si ya no cabe nadie mas o ya estaba registrado
     */
    void inscribirClase(String idVisita, String idClase, String idCliente)
            throws AccesoDenegadoException;

    /**
     * Excepcion personalizada para cuando rebotamos a alguien en la entrada
     */
    class AccesoDenegadoException extends Exception {
        private final String motivo;

        /**
         * Crea el error con el mensaje de rechazo
         * 
         * @param motivo La razon del porque no pasa
         */
        public AccesoDenegadoException(String motivo) {
            super(motivo);
            this.motivo = motivo;
        }

        /**
         * Te regresa el texto del motivo
         * 
         * @return El mensaje explicando el rechazo
         */
        public String getMotivo() { return motivo; }
    }
}