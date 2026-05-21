/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import conexion.MongoConexion;
import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import excepciones.PersistenciaException;
import interfaces.IEntrenadorDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappersPersistencia.EntrenadorPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Clase DAO para entrenadores
 * @author luiscarlosbeltran
 */
public class EntrenadorDAO implements IEntrenadorDAO {

    private MongoCollection<Document> coleccion;
    private static final Logger LOG = Logger.getLogger(EntrenadorDAO.class.getName());
   
    public EntrenadorDAO() {

        //la coleccion de entrenadores en la bd de mongo debe llamarse "entrenadores"
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("entrenadores");
    }
    
    /**
     * Metodo para buscar un entrenador por su id
     * @param idEntrenador el id para buscar al entrenador
     * @return EntrenadorPojo si se encontro, null en caso contrario
     * @throws PersistenciaException 
     */
    @Override
    public EntrenadorPojo buscarPorId(String idEntrenador) throws PersistenciaException {
        try{
            Document doc =coleccion.find(Filters.eq("_id",idEntrenador)).first();
            if (doc == null) {
                Logger.getLogger(EntrenadorDAO.class.getName()).warning("No se encontró el entrenador con id: "+ idEntrenador);
                return null;
            }
            Logger.getLogger(EntrenadorDAO.class.getName()).info("Entrenador encontrado correctamente");
            return EntrenadorPersistenciaMapper.toPojo(doc);
        } catch (MongoException ex) {
            Logger.getLogger(EntrenadorDAO.class.getName()).severe("Error al buscar entrenador");
            throw new PersistenciaException("Error al buscar entrenador");
        }
    }    
    /**
     * METODO CONVERTIDO A MONGO PORQUE LO USA EL CASO BASE
     * obtiene todos los entrenadores de una sucursal
     * @param idSucursal la sucursal en la que se buscaran los entrenadores
     * @return List<EntrenadorPojo> lista de entrenadores de la sucursal
     */
    @Override
    public List<EntrenadorPojo> obtenerPorSucursal(String idSucursal) throws PersistenciaException {
        try{
            List<EntrenadorPojo> lista = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find(Filters.eq("idSucursal", idSucursal));
            for (Document doc : docs) {
                EntrenadorPojo pojo = EntrenadorPersistenciaMapper.toPojo(doc);
                lista.add(pojo);
            }
            return lista;
        }catch(MongoException ex){
            Logger.getLogger(EntrenadorDAO.class.getName()).severe("Error al obtener los entrenadores por sucursal");
            throw new PersistenciaException("Ocurrió un error al intentar obtener a los entrenadores por sucursal");
        }
    }
    /**
     * Metodo para obtener los horarios de un entrenador
     * @param idEntrenador el entrenador del que se quiere saber sus horarios
     * @return List<HorarioPojo> los horarios del entrenados
     * @throws PersistenciaException 
     */
    @Override
    public List<HorarioPojo>obtenerHorariosEntrenador(String idEntrenador)throws PersistenciaException {
        try {
            EntrenadorPojo entrenador = buscarPorId(idEntrenador);
            if (entrenador == null) {
                Logger.getLogger(EntrenadorDAO.class.getName()).warning("No existe el entrenador");
                return new ArrayList<>();
            }
            Logger.getLogger(EntrenadorDAO.class.getName()).info("Horarios obtenidos correctamente");
            return entrenador.getHorarios();
        } catch (PersistenciaException ex) {
            Logger.getLogger(EntrenadorDAO.class.getName()).severe("Error al obtener horarios");
            throw new PersistenciaException("Error al intentar obtener horarios");
        }
    }
    
    /**
     * Actualiza el campo disponible de un horario especifico dentro del documento
     * del entrenador usando arrayFilters de MongoDB.
     *
     * @param idEntrenador id del entrenador
     * @param idHorario id del horario a modificar
     * @param disponible nuevo valor
     * @throws PersistenciaException si falla la operacion
     */
    @Override
    public void actualizarDisponibilidadHorario(String idEntrenador,
                                                 String idHorario,
                                                 boolean disponible)
            throws PersistenciaException {
        try {
            Bson filtro    = Filters.eq("_id", idEntrenador);
            Bson actualizacion = Updates.set("horarios.$[h].disponible", disponible);
 
            // arrayFilter: solo actualiza el sub-documento cuyo idHorario coincida
            Document arrayFilter = new Document("h.idHorario", idHorario);
            UpdateOptions opciones = new UpdateOptions()
                    .arrayFilters(List.of(arrayFilter));
 
            long modificados = coleccion.updateOne(filtro, actualizacion, opciones)
                    .getModifiedCount();
 
            if (modificados == 0) {
                LOG.warning("No se modifico ningun horario. "
                        + "idEntrenador=" + idEntrenador
                        + " idHorario=" + idHorario);
                // puede que ya estuviera en ese estado
            } else {
                LOG.info("Horario " + idHorario
                        + " del entrenador " + idEntrenador
                        + " actualizado a disponible=" + disponible);
            }
        } catch (MongoException ex) {
            LOG.severe("Error al actualizar disponibilidad: " + ex.getMessage());
            throw new PersistenciaException("Error al actualizar disponibilidad del horario");
        }
    }
}
