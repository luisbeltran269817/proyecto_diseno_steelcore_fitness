/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOsMantenimiento;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominios_mantenimiento.BajaPojo;
import dominios_mantenimiento.MaquinaPojo;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IMaquinaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoPersistencia.BajaPersistenciaMapper;
import mappersMantenimientoPersistencia.MaquinaPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 *
 * @author Tungs
 */
public class MaquinaDAO implements IMaquinaDAO {
    
    private static final Logger logger = Logger.getLogger(MaquinaDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public MaquinaDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("maquinas");
    }

    /**
     * Método que registra una maquina en la BD
     * @param maquina la maquina a insertar
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void registrarMaquina(MaquinaPojo maquina) throws PersistenciaException {
        try {
            if (maquina == null) {
                throw new PersistenciaException("La máquina no puede ser null");
            }
            Document doc = MaquinaPersistenciaMapper.toDocument(maquina);
            coleccion.insertOne(doc);

            logger.log(Level.INFO, "Máquina registrada correctamente: {0}", maquina.getIdMaquina());
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al registrar máquina", ex);
            throw new PersistenciaException("Error al registrar máquina");
        }
    }
    /**
     * Método que obtiene una Máquina de la BD
     * @param idMaquina el id de la Máquina a obtener
     * @return la maquina encontrada
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public MaquinaPojo obtenerMaquina(String idMaquina) throws PersistenciaException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new PersistenciaException("El id de la máquina es inválido");
            }

            Document doc = coleccion.find(Filters.eq("_id", idMaquina)).first();

            if (doc == null) {
                logger.log(Level.WARNING, "No se encontró la máquina con id: ", idMaquina);
                return null;
            }

            logger.log(Level.INFO, "Máquina obtenida correctamente: ", idMaquina);
            return MaquinaPersistenciaMapper.toPojo(doc);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener máquina", ex);
            throw new PersistenciaException("Error al obtener máquina");
        }
    }
    
    /**
     * Método que obtiene todas las máquinas de la BD
     * @return una lista de máquinas
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public List<MaquinaPojo> obtenerMaquinas() throws PersistenciaException {
        try {
            List<MaquinaPojo> maquinas = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find();

            for (Document doc : docs) {
                maquinas.add(MaquinaPersistenciaMapper.toPojo(doc));
            }

            logger.log(Level.INFO, "Máquinas obtenidas correctamente. Total: ", maquinas.size());
            return maquinas;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener máquinas", ex);
            throw new PersistenciaException("Error al obtener máquinas");
        }
    }

    /**
     * Método que actualiza los datos de una máquina
     * @param maquina la Maquina que reemplazará a la anterior
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void actualizarMaquina(MaquinaPojo maquina) throws PersistenciaException {
        try {
            if (maquina == null) {
                throw new PersistenciaException("La máquina no puede ser null");
            }

            if (maquina.getIdMaquina() == null || maquina.getIdMaquina().isBlank()) {
                throw new PersistenciaException("El id de la máquina es inválido");
            }

            Document doc = MaquinaPersistenciaMapper.toDocument(maquina);

            UpdateResult resultado = coleccion.replaceOne(Filters.eq("_id", maquina.getIdMaquina()), doc);

            if (resultado.getMatchedCount() == 0) {
                logger.log(Level.WARNING, "No se encontró la máquina para actualizar: ", maquina.getIdMaquina());
                throw new PersistenciaException("No se encontró la máquina para actualizar");
            }

            logger.log(Level.INFO, "Máquina actualizada correctamente: ", maquina.getIdMaquina());
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al actualizar máquina", ex);
            throw new PersistenciaException("Error al actualizar máquina");
        }
    }
    
    /**
     * Método que cambia el estado de una máquina
     * @param idMaquina la maquina a cambiarle el estado
     * @param estado el nuevo estado de la máquina
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void cambiarEstado(String idMaquina, MaquinaPojo.EstadoMaquina estado) throws PersistenciaException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new PersistenciaException("El id de la máquina es inválido");
            }
            if (estado == null) {
                throw new PersistenciaException("El estado de la máquina no puede ser null");
            }

            UpdateResult resultado = coleccion.updateOne(Filters.eq("_id", idMaquina), Updates.set("estado", estado.name()));

            if (resultado.getMatchedCount() == 0) {
                logger.log(Level.WARNING, "No se encontró la máquina para cambiar estado: ", idMaquina);
                throw new PersistenciaException("No se encontró la máquina para cambiar estado");
            }
            logger.log(Level.INFO, "Estado de máquina actualizado correctamente: {0}", idMaquina);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al cambiar estado de máquina", ex);
            throw new PersistenciaException("Error al cambiar estado de máquina");
        }
    }
    
    /**
     * Método que registra una baja para una maquina
     * @param idMaquina el id de la máquina a dar de baja
     * @param baja la baja a registrar
     * @throws PersistenciaException si ocurre un error 
     */
    @Override
    public void registrarBaja(String idMaquina, BajaPojo baja) throws PersistenciaException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new PersistenciaException("El id de la máquina es inválido");
            }

            if (baja == null) {
                throw new PersistenciaException("La baja no puede ser null");
            }

            Document bajaDoc = BajaPersistenciaMapper.toDocument(baja);

            UpdateResult resultado = coleccion.updateOne(Filters.eq("_id", idMaquina),Updates.combine(Updates.set("estado", MaquinaPojo.EstadoMaquina.INACTIVO.name()),Updates.set("baja", bajaDoc))
            );
            if (resultado.getMatchedCount() == 0) {
                logger.log(Level.WARNING, "No se encontró la máquina para registrar baja: ", idMaquina);
                throw new PersistenciaException("No se encontró la máquina para registrar baja");
            }
            logger.log(Level.INFO, "Baja de máquina registrada correctamente:", idMaquina);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al registrar baja de máquina", ex);
            throw new PersistenciaException("Error al registrar baja de máquina");
        }
    }
   
    /**
     * Método que regresa una lista de máquinas usando la función agregate del mongo para realizar filtros
     * @param idSucursal el id de la sucursal a filtrar (si se recibe)
     * @param estado el estado de la maquina a filtrar (también si se recibe)
     * @return una lista de Maquinas Pojo con los resultados
     * @throws PersistenciaException si ocurre un error 
     */
    @Override
    public List<MaquinaPojo> filtrarMaquinas(String idSucursal, MaquinaPojo.EstadoMaquina estado) throws PersistenciaException {
        try {
            List<Bson> filtros = new ArrayList<>();
            if (idSucursal != null && !idSucursal.isBlank()) {
                filtros.add(Filters.eq("idSucursal", idSucursal));
            }
            if (estado != null) {
                filtros.add(Filters.eq("estado", estado.name()));
            }
            //Es bastante práctico el uso del agreggate para los filtros, más sencillo que el JPQL
            List<Bson> pipeline = new ArrayList<>();
            if (!filtros.isEmpty()) {
                pipeline.add(Aggregates.match(Filters.and(filtros)));
            }
            List<MaquinaPojo> maquinas = new ArrayList<>();

            AggregateIterable<Document> docs = coleccion.aggregate(pipeline);

            for (Document doc : docs) {
                maquinas.add(MaquinaPersistenciaMapper.toPojo(doc));
            }
            logger.log(Level.INFO, "Máquinas filtradas correctamente. Total: ", maquinas.size());
            return maquinas;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al filtrar máquinas", ex);
            throw new PersistenciaException("Error al filtrar máquinas");
        }
    }

}
