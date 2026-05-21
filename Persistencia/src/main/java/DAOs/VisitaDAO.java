/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios.VisitaPojo;
import excepciones.PersistenciaException;
import interfaces.IVisitaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersPersistencia.VisitaPersistenciaMapper;
import org.bson.Document;

/**
 * Clase DAO para visitas
 * @author julian izaguirre
 */
public class VisitaDAO implements IVisitaDAO {
 
    private static final Logger logger = Logger.getLogger(VisitaDAO.class.getName());
    private final MongoCollection<Document> coleccion;
    public VisitaDAO() {
          this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("visitas");
    }
    /**
     * metodo para guardar una visita
     * @param idCliente el id del cliente que realizo la visita
     * @param visita objeto VisitaPojo a guardar
     * @return VisitaPojo
     * @throws PersistenciaException 
     */
    @Override
    public VisitaPojo guardar(String idCliente,VisitaPojo visita) throws PersistenciaException {
        try {
            if (idCliente == null || idCliente.isBlank()) {
                throw new PersistenciaException( "El idCliente es inválido");
            }
            if (visita == null) {
                throw new PersistenciaException("La visita no puede ser null");
            }
            visita.setIdCliente(idCliente);
            Document doc =VisitaPersistenciaMapper.toDocument(visita);
            coleccion.insertOne(doc);
            logger.log(Level.INFO, "Visita guardada correctamente");
            VisitaPojo visitaReturn = VisitaPersistenciaMapper.toPojo(doc);
            return visitaReturn;
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al guardar visita", e);
            throw new PersistenciaException("Error al guardar visita");
        }
    }

    /**
     * Metodo para obtener visitas de un cliente
     * @param idCliente el cliente del que se quieren obtener las visitas
     * @return List VisitaPojo las visitas del cliente
     * @throws PersistenciaException 
     */
    @Override
    public List<VisitaPojo> obtenerPorCliente(String idCliente)throws PersistenciaException {
        try {
            List<VisitaPojo> visitas= new ArrayList<>();
            FindIterable<Document> docs= coleccion.find(Filters.eq("idCliente", idCliente));
            for (Document doc : docs) {
                VisitaPojo pojo= VisitaPersistenciaMapper.toPojo(doc);
                visitas.add(pojo);
            }
            logger.log(Level.INFO,"Visitas obtenidas correctamente");
            return visitas;
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al obtener visitas", e);
            throw new PersistenciaException( "Error al obtener visitas");
        }
    }
}
