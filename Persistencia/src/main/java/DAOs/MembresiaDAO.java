/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios.MembresiaPojo;
import excepciones.PersistenciaException;
import interfaces.IMembresiaDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersPersistencia.MembresiaPersistenciaMapper;
import org.bson.Document;

/**
 * Clase DAO para membresia
 * @author luiscarlosbeltran
 */
public class MembresiaDAO implements IMembresiaDAO{
    private static final Logger logger = Logger.getLogger(MembresiaDAO.class.getName());
     private final MongoCollection<Document> coleccion;
    public MembresiaDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("membresias");
    }
    
    /**
     * Método que guarda una membresía en la BD
     * @param membresia la membresia a guardar
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void guardar(MembresiaPojo membresia) throws PersistenciaException {
        try {
            if (membresia == null) {
                throw new PersistenciaException("La membresia es nula");
            }
            Document doc = MembresiaPersistenciaMapper.toDocument(membresia);
            coleccion.insertOne(doc);
            logger.log(Level.INFO, "Membresía guardada correctamente");
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al guardar la membresía", e);
            throw new PersistenciaException("Error al guardar la membresía");
        }
    }
    /**
     * Método que busca una membresia por su id
     * @param idMembresia el id de la membresía a buscar
     * @return la membresiaPojo encontrada
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public MembresiaPojo buscarPorId(String idMembresia) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("_id", idMembresia)).first();
            if (doc == null) {
                return null;
            }
            return MembresiaPersistenciaMapper.toPojo(doc);
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al buscar membresía", e);
            throw new PersistenciaException("Error al buscar membresía");
        }
    }
    /**
     * Metodo que busca una membresia por codigoQR
     * @param codigoQR un string del codigoQR
     * @return MembresiaPojo si se encontro, null en caso contrario
     * @throws PersistenciaException 
     */
    @Override
    public MembresiaPojo buscarPorCodigoQR(String codigoQR) throws PersistenciaException {
        try {
            if (codigoQR == null || codigoQR.isBlank()) return null;
            Document doc = coleccion.find(Filters.eq("codigoQR", codigoQR)).first();
            return (doc != null) ? MembresiaPersistenciaMapper.toPojo(doc) : null;
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al buscar membresia por QR", e);
            throw new PersistenciaException("Error al buscar membresia por QR");
        }
    }

    /**
     * Método que actualiza los datos de una membresia
     * @param membresia la membresia nueva a actualizar
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void actualizar(MembresiaPojo membresia) throws PersistenciaException {
        try {
            if (membresia == null) {
                throw new PersistenciaException("La membresía no puede ser null");
            }
            Document doc = MembresiaPersistenciaMapper.toDocument(membresia);
            coleccion.replaceOne(Filters.eq("_id", membresia.getIdMembresia()),doc);
            logger.log(Level.INFO, "Membresía actualizada correctamente");
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al actualizar la membresía", e);
            throw new PersistenciaException("Error al actualizar la membresía");
        }
    }
}
