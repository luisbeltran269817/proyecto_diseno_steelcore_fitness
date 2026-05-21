package daosAcceso;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import conexion.MongoConexion;
import dominioAcceso.VisitaAccesoPojo;
import excepciones.PersistenciaException;
import interfacesAcceso.IVisitaAccesoDAO;
import java.util.logging.Logger;
import mappersAcceso.VisitaAccesoPersistenciaMapper;
import org.bson.Document;

/**
 * DAO de visitas enriquecidas para el caso individual de Control de Acceso.
 * Usa la misma colección "visitas" que VisitaDAO.
 *
 * @author julian izaguirre
 */
public class VisitaAccesoDAO implements IVisitaAccesoDAO {

    private static final Logger LOG = Logger.getLogger(VisitaAccesoDAO.class.getName());
    private final MongoCollection<Document> coleccion;

    public VisitaAccesoDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("visitas");
    }

    /** {@inheritDoc} */
    @Override
    public VisitaAccesoPojo guardar(VisitaAccesoPojo visita) throws PersistenciaException {
        try {
            if (visita == null) {
                throw new PersistenciaException("La visita no puede ser null");
            }
            Document doc = VisitaAccesoPersistenciaMapper.toDocument(visita);
            coleccion.insertOne(doc);
            LOG.info("VisitaAcceso guardada: id=" + visita.getIdVisita());
            return visita;
        } catch (MongoException ex) {
            LOG.severe("Error al guardar visita de acceso: " + ex.getMessage());
            throw new PersistenciaException("Error al registrar la visita de acceso");
        }
    }

    /** {@inheritDoc} */
    @Override
    public void actualizarServicio(String idVisita, String tipoServicio,
                                    String idRecursoAsignado)
            throws PersistenciaException {
        try {
            coleccion.updateOne(
                    Filters.eq("_id", idVisita),
                    Updates.combine(
                            Updates.set("tipoServicio",      tipoServicio),
                            Updates.set("idRecursoAsignado", idRecursoAsignado)
                    )
            );
            LOG.info("Servicio actualizado en visita " + idVisita
                    + " tipo=" + tipoServicio
                    + " recurso=" + idRecursoAsignado);
        } catch (MongoException ex) {
            LOG.severe("Error al actualizar servicio en visita: " + ex.getMessage());
            throw new PersistenciaException("Error al actualizar el servicio de la visita");
        }
    }
}