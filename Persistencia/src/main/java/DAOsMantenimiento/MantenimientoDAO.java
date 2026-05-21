/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOsMantenimiento;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios_mantenimiento.MantenimientoPojo;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IMantenimientoDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoPersistencia.MantenimientoPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MantenimientoDAO implements IMantenimientoDAO {
    
     private static final Logger logger = Logger.getLogger(MantenimientoDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public MantenimientoDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("mantenimientos");
    }
    
    /**
     * Método que registra un mantenimiento en la BD
     * @param mantenimiento el mantenimiento a registrar
     * @throws PersistenciaException 
     */
    @Override
    public void registrarMantenimiento(MantenimientoPojo mantenimiento) throws PersistenciaException {
        try {
            if (mantenimiento == null) {
                throw new PersistenciaException("El mantenimiento no puede ser null");
            }

            Document doc = MantenimientoPersistenciaMapper.toDocument(mantenimiento);
            coleccion.insertOne(doc);

            logger.log(Level.INFO, "Mantenimiento registrado correctamente: ", mantenimiento.getIdMantenimiento());
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al registrar mantenimiento", ex);
            throw new PersistenciaException("Error al registrar mantenimiento");
        }
    }

    /**
     * Método que obtiene un mantenimiento de la BD
     * @param idMantenimiento el id el mantenimiento a obtener
     * @return el mantenimineto objtenido
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public MantenimientoPojo obtenerMantenimiento(String idMantenimiento) throws PersistenciaException {
        try {
            if (idMantenimiento == null || idMantenimiento.isBlank()) {
                throw new PersistenciaException("El id del mantenimiento es inválido");
            }
            Document doc = coleccion.find(Filters.eq("_id", idMantenimiento)).first();
            if (doc == null) {
                logger.log(Level.WARNING, "No se encontró el mantenimiento con id: ", idMantenimiento);
                return null;
            }
            logger.log(Level.INFO, "Mantenimiento obtenido correctamente: ", idMantenimiento);
            return MantenimientoPersistenciaMapper.toPojo(doc);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener mantenimiento", ex);
            throw new PersistenciaException("Error al obtener mantenimiento");
        }
    }

    /**
     * Método que obtiene todos los mantenimientos
     * @return una lista con todos los Mantenimientos
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public List<MantenimientoPojo> obtenerMantenimientos() throws PersistenciaException {
        try {
            List<MantenimientoPojo> mantenimientos = new ArrayList<>();

            FindIterable<Document> docs = coleccion.find();

            for (Document doc : docs) {
                mantenimientos.add(MantenimientoPersistenciaMapper.toPojo(doc));
            }
            logger.log(Level.INFO, "Mantenimientos obtenidos correctamente. Total: ", mantenimientos.size());
            return mantenimientos;

        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener mantenimientos", ex);
            throw new PersistenciaException("Error al obtener mantenimientos");
        }
    }

    /**
     * Método que pregunta a la BD si una máquina tiene mantenimiento activo
     * @param idMaquina
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public boolean tieneMantenimientoActivo(String idMaquina) throws PersistenciaException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new PersistenciaException("El id de la máquina no puede ser nulo");
            }
            Document doc = coleccion.find(Filters.and(Filters.eq("idMaquina", idMaquina), Filters.in("estado", MantenimientoPojo.EstadoMantenimiento.PENDIENTE.name(), MantenimientoPojo.EstadoMantenimiento.ESPERA.name()))).first();
            boolean tieneActivo = doc != null;
            return tieneActivo;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al validar mantenimiento activo", ex);
            throw new PersistenciaException("Error al validar mantenimiento activo");
        }
    }
}
