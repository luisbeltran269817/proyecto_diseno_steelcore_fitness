package interfacesAcceso;

import dominioAcceso.VisitaAccesoPojo;
import excepciones.PersistenciaException;

/**
 * Contrato de acceso a datos para visitas enriquecidas del caso individual.
 *
 * @author julian izaguirre
 */
public interface IVisitaAccesoDAO {

    /**
     * Guarda la visita con todos los datos de acceso.
     */
    VisitaAccesoPojo guardar(VisitaAccesoPojo visita) throws PersistenciaException;

    /**
     * Actualiza el tipo de servicio y el recurso asignado de una visita ya registrada.
     *
     * @param tipoServicio      "AREA_GENERAL", "CLASE" o "ENTRENADOR"
     * @param idRecursoAsignado id de la clase o entrenador; null si es AREA_GENERAL
     */
    void actualizarServicio(String idVisita, String tipoServicio,
                             String idRecursoAsignado) throws PersistenciaException;
}