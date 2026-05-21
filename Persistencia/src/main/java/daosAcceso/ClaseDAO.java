package daosAcceso;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import conexion.MongoConexion;
import dominioAcceso.ClasePojo;
import excepciones.PersistenciaException;
import interfacesAcceso.IClaseDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappersAcceso.ClasePersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * DAO para la colección "clases".
 *
 * Reglas de negocio aplicadas en persistencia:
 *   - inscribirSocio() es atómico: si cupo lleno o ya inscrito lanza excepción.
 *   - obtenerPorSucursalYPlan() incluye clases sin restricción de plan (idPlan null).
 *
 * @author julian izaguirre
 */
public class ClaseDAO implements IClaseDAO {

    private static final Logger LOG = Logger.getLogger(ClaseDAO.class.getName());
    private final MongoCollection<Document> coleccion;

    public ClaseDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("clases");
    }

    /**
     * {@inheritDoc}
     *
     * Filtra clases donde idSucursal coincide Y
     * (idPlan == idPlan del socio OR idPlan no existe / es null en el documento).
     */
    @Override
    public List<ClasePojo> obtenerPorSucursalYPlan(String idSucursal, String idPlan)
            throws PersistenciaException {
        try {
            Bson filtroPlan = Filters.or(
                    Filters.eq("idPlan", idPlan),
                    Filters.exists("idPlan", false),
                    Filters.eq("idPlan", null)
            );
            Bson filtroFinal = Filters.and(
                    Filters.eq("idSucursal", idSucursal),
                    filtroPlan
            );

            List<ClasePojo> resultado = new ArrayList<>();
            for (Document doc : coleccion.find(filtroFinal)) {
                resultado.add(ClasePersistenciaMapper.toPojo(doc));
            }
            LOG.info("Clases obtenidas para sucursal=" + idSucursal
                    + " plan=" + idPlan + " total=" + resultado.size());
            return resultado;

        } catch (MongoException ex) {
            LOG.severe("Error al obtener clases: " + ex.getMessage());
            throw new PersistenciaException("Error al consultar las clases disponibles");
        }
    }

    /** {@inheritDoc} */
    @Override
    public ClasePojo buscarPorId(String idClase) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", idClase)).first();
            if (doc == null) {
                LOG.warning("Clase no encontrada: " + idClase);
                return null;
            }
            return ClasePersistenciaMapper.toPojo(doc);
        } catch (MongoException ex) {
            LOG.severe("Error al buscar clase: " + ex.getMessage());
            throw new PersistenciaException("Error al buscar la clase");
        }
    }

    /**
     * {@inheritDoc}
     *
     * Verificación de cupo y duplicados ANTES de la actualización atómica.
     * Lanza PersistenciaException con mensaje específico para cada caso.
     */
    @Override
    public void inscribirSocio(String idClase, String idCliente)
            throws PersistenciaException {
        try {
            ClasePojo clase = buscarPorId(idClase);
            if (clase == null) {
                throw new PersistenciaException("La clase no existe en el sistema.");
            }
            if (clase.estaLlena()) {
                throw new PersistenciaException(
                        "Lo sentimos, el cupo para esta clase ya está lleno.\n"
                        + "Por favor selecciona otro horario.");
            }
            if (clase.estaInscrito(idCliente)) {
                throw new PersistenciaException("El socio ya está inscrito en esta clase.");
            }

            coleccion.updateOne(
                    Filters.eq("_id", idClase),
                    Updates.combine(
                            Updates.inc("cupoActual", 1),
                            Updates.push("inscritos", idCliente)
                    )
            );
            LOG.info("Socio " + idCliente + " inscrito en clase " + idClase);

        } catch (PersistenciaException ex) {
            throw ex;
        } catch (MongoException ex) {
            LOG.severe("Error al inscribir socio: " + ex.getMessage());
            throw new PersistenciaException("Error al inscribir al socio en la clase");
        }
    }
}