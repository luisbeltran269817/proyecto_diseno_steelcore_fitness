/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import conexion.MongoConexion;
import dominios.ReporteHistorialPojo;
import excepciones.PersistenciaException;
import interfaces.IReporteHistorialDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersPersistencia.ReporteHistorialPersistenciaMapper;
import org.bson.Document;

/**
 * DAO para el historial de reportes generados.
 *
 * Esta clase se encarga de guardar y consultar reportes generados dentro de la
 * colección "reportesGenerados" de MongoDB.
 *
 * @author Noelia E.N.
 */
public class ReporteHistorialDAO implements IReporteHistorialDAO {

    private static final Logger logger = Logger.getLogger(ReporteHistorialDAO.class.getName());
    private final MongoCollection<Document> coleccion;

    /**
     * Constructor del DAO.
     *
     * Obtiene la colección de reportes generados desde MongoDB.
     */
    public ReporteHistorialDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("reportesGenerados");
    }

    /**
     * Guarda un reporte generado en MongoDB.
     *
     * @param reporte reporte a guardar.
     * @throws PersistenciaException si ocurre un error al guardar.
     */
    @Override
    public void guardar(ReporteHistorialPojo reporte) throws PersistenciaException {
        try {
            if (reporte == null) {
                throw new PersistenciaException("El reporte historial no puede ser null.");
            }

            Document doc = ReporteHistorialPersistenciaMapper.toDocument(reporte);
            coleccion.insertOne(doc);

            logger.log(Level.INFO, "Reporte guardado en historial correctamente.");

        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al guardar reporte historial", e);
            throw new PersistenciaException("Error al guardar reporte historial.");
        }
    }

    /**
     * Obtiene los últimos reportes generados.
     *
     * @param limite cantidad máxima de reportes a consultar.
     * @return lista de reportes recientes.
     * @throws PersistenciaException si ocurre un error al consultar.
     */
    @Override
    public List<ReporteHistorialPojo> obtenerUltimos(int limite) throws PersistenciaException {
        try {
            List<ReporteHistorialPojo> reportes = new ArrayList<>();

            int limiteSeguro = limite <= 0 ? 10 : limite;

            FindIterable<Document> docs = coleccion.find()
                    .sort(Sorts.descending("fechaGeneracion"))
                    .limit(limiteSeguro);

            for (Document doc : docs) {
                reportes.add(ReporteHistorialPersistenciaMapper.toPojo(doc));
            }

            logger.log(Level.INFO, "Últimos reportes consultados correctamente.");
            return reportes;

        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al consultar últimos reportes", e);
            throw new PersistenciaException("Error al consultar últimos reportes.");
        }
    }

    /**
     * Busca un reporte histórico por id.
     *
     * @param idReporte id del reporte.
     * @return reporte encontrado o null si no existe.
     * @throws PersistenciaException si ocurre un error al buscar.
     */
    @Override
    public ReporteHistorialPojo buscarPorId(String idReporte) throws PersistenciaException {
        try {
            if (idReporte == null || idReporte.isBlank()) {
                return null;
            }

            Document doc = coleccion.find(Filters.eq("_id", idReporte)).first();

            if (doc == null) {
                return null;
            }

            return ReporteHistorialPersistenciaMapper.toPojo(doc);

        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al buscar reporte historial", e);
            throw new PersistenciaException("Error al buscar reporte historial.");
        }
    }
}
