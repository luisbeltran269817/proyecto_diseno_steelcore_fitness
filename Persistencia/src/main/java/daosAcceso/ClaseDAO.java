/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * DAO que maneja las operaciones de las clases del gimnasio
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
     * Trae las clases de una sucursal filtrando por el plan del socio
     *
     * @param idSucursal La sucursal donde esta el socio
     * @param idPlan El plan que tiene pagado
     * @return Lista con las clases que el socio puede tomar
     * @throws PersistenciaException Si ocurre un error al buscar
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

    /** 
     * Busca los datos completos de una clase usando su ID
     * 
     * @param idClase Identificador unico de la clase
     * @return El objeto de la clase o null si no se encuentra
     * @throws PersistenciaException Si falla la consulta a Mongo
     */
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
     * Metodo para inscribir un socio a una clase validando espacios
     *
     * @param idClase Clase a la que quiere entrar
     * @param idCliente Socio que se va a inscribir
     * @throws PersistenciaException Si la clase esta llena o el socio ya esta registrado
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
                        "Lo sentimos, el cupo para esta clase ya esta lleno\n"
                        + "Por favor selecciona otro horario");
            }
            if (clase.estaInscrito(idCliente)) {
                throw new PersistenciaException("El socio ya esta inscrito en esta clase");
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