/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOsMantenimiento;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominios_mantenimiento.PiezaPojo;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IPiezaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoPersistencia.PiezaPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class PiezaDAO implements IPiezaDAO {
    private static final Logger logger = Logger.getLogger(PiezaDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public PiezaDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("piezas");
    }

    /**
     * Método que obtiene una pieza de la BD
     * @param idPieza
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public PiezaPojo obtenerPieza(String idPieza) throws PersistenciaException {
        try {
            if (idPieza == null || idPieza.isBlank()) {
                throw new PersistenciaException("El id de la pieza es inválido");
            }

            Document doc = coleccion.find(Filters.eq("_id", idPieza)).first();

            if (doc == null) {
                logger.log(Level.WARNING, "No se encontró la pieza con id: {0}", idPieza);
                return null;
            }

            logger.log(Level.INFO, "Pieza obtenida correctamente: {0}", idPieza);
            return PiezaPersistenciaMapper.toPojo(doc);

        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener pieza", ex);
            throw new PersistenciaException("Error al obtener pieza");
        }
    }
    /**
     * Método que actualiza el stock de una pieza
     * @param idPieza la pieza a actualizar
     * @param stock el nuevo stock
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void actualizarStock(String idPieza, int stock) throws PersistenciaException {
        try {
            PiezaPojo piezaObtenida= obtenerPieza(idPieza);
            if(piezaObtenida == null){
                logger.log(Level.WARNING, "No se encontró la pieza con id: {0}", idPieza);
                throw new PersistenciaException("No se encontró la pieza para actualizar stock");
            }
            if (stock < 0) {
                throw new PersistenciaException("El stock no puede ser negativo");
            }
            coleccion.updateOne(Filters.eq("_id", idPieza), Updates.set("stock", stock));
            logger.log(Level.INFO, "Stock actualizado correctamente para la pieza: ", idPieza);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al actualizar stock de pieza", ex);
            throw new PersistenciaException("Error al actualizar stock de pieza");
        }
    }

    /**
     * Método que muestra las piezas
     * @return una lista de piezas
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public List<PiezaPojo> mostrarPiezas() throws PersistenciaException {
        try {
            List<PiezaPojo> piezas = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find();
            for (Document doc : docs) {
                piezas.add(PiezaPersistenciaMapper.toPojo(doc));
            }
            logger.log(Level.INFO, "Piezas obtenidas correctamente. Total: ", piezas.size());
            return piezas;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener piezas", ex);
            throw new PersistenciaException("Error al obtener piezas");
        }
    }
    
    /**
     * Método que le pregunta a la BD si hay stock suficiente
     * @param idPieza el id de la pieza a buscar
     * @param cantidad la cantidad a validar
     * @return verdadero si hay stock suficiente, falso en caso contrario
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public boolean hayStockSuficiente(String idPieza, int cantidad) throws PersistenciaException {
        try {
            PiezaPojo piezaObtenida= obtenerPieza(idPieza);
            if(piezaObtenida == null){
                logger.log(Level.WARNING, "No se encontró la pieza con id: {0}", idPieza);
                throw new PersistenciaException("No se encontró la pieza para actualizar stock");
            }
            if (cantidad <= 0) {
                throw new PersistenciaException("La cantidad debe ser mayor a cero");
            }
            Document doc = coleccion.find(Filters.and(Filters.eq("_id", idPieza), Filters.gte("stock", cantidad), Filters.eq("estado", PiezaPojo.EstadoPieza.ACTIVO.name()))).first();
            boolean suficiente = doc != null;
            return suficiente;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al validar stock de pieza", ex);
            throw new PersistenciaException("Error al validar stock de pieza");
        }
    }
    
    
}
