package Fachada;

import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.MembresiaDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import dtosControlDeAcceso.VisitaDTO;
import java.util.List;

/**
 * Interfaz del subsistema Control de Acceso.
 *
 * Todos los métodos usan exclusivamente DTOs del paquete dtosControlDeAcceso.*
 * La capa de presentación solo interactúa con esta interfaz.
 *
 * @author julian izaguirre
 */
public interface Icontrolacceso {

    /**
     * Procesa el QR escaneado: valida membresía, registra visita y devuelve
     * los datos del expediente para pintarlos en pantalla.
     *
     * @param codigoQR string del QR escaneado (URL o id directo)
     * @return ResultadoAccesoDTO con nombre, estado membresía, hora de entrada e idVisita
     * @throws AccesoDenegadoException si la membresía no es válida
     */
    ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException;

    /**
     * Registra que el socio eligió ingresar únicamente al área general (pesas).
     * Actualiza el tipoServicio de la visita a AREA_GENERAL.
     *
     * @param idVisita id de la visita activa
     * @throws AccesoDenegadoException si falla la actualización
     */
    void registrarAreaGeneral(String idVisita) throws AccesoDenegadoException;

    /**
     * Devuelve los entrenadores de la sucursal con su disponibilidad en tiempo real.
     * Si todos están OCUPADO la pantalla muestra el mensaje de saturación.
     *
     * @param idSucursal id de la sucursal; puede ser null si ya se configuró en la fachada
     * @return lista de EntrenadorDTO; puede ser vacía pero nunca null
     * @throws AccesoDenegadoException si falla la consulta
     */
    List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException;

    /**
     * Asigna un entrenador al socio para el horario elegido.
     * Marca el horario como OCUPADO en Mongo y actualiza la visita.
     *
     * @param idVisita     id de la visita activa
     * @param idCliente    id del socio
     * @param idEntrenador id del entrenador seleccionado
     * @param idHorario    id del horario elegido
     * @throws AccesoDenegadoException si el horario ya está ocupado o hay error
     */
    void asignarEntrenador(String idVisita, String idCliente,
                            String idEntrenador, String idHorario)
            throws AccesoDenegadoException;

    /**
     * Obtiene las clases disponibles para el plan del socio.
     * Lanza excepción si el plan no incluye clases (la pantalla muestra el aviso).
     *
     * @param idSucursal    sucursal donde opera la recepción
     * @param idPlan        id del plan de la membresía
     * @param idCliente     id del socio (para excluir clases ya inscritas)
     * @param incluyeClases flag del plan — si false, acceso bloqueado
     * @return lista de ClaseDTO; puede estar vacía si la sucursal no tiene clases del plan
     * @throws AccesoDenegadoException si plan sin clases, sucursal sin clases, o error
     */
    List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                         String idCliente, boolean incluyeClases)
            throws AccesoDenegadoException;

    /**
     * Inscribe al socio en la clase elegida y actualiza la visita.
     *
     * @param idVisita  id de la visita activa
     * @param idClase   id de la clase seleccionada
     * @param idCliente id del socio
     * @throws AccesoDenegadoException si cupo lleno, ya inscrito, o error
     */
    void inscribirClase(String idVisita, String idClase, String idCliente)
            throws AccesoDenegadoException;

    // --- Clases internas de excepción ---

    class AccesoDenegadoException extends Exception {
        private final String motivo;

        public AccesoDenegadoException(String motivo) {
            super(motivo);
            this.motivo = motivo;
        }

        public String getMotivo() { return motivo; }
    }
}