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
import dominios.EjercicioPojo;
import excepciones.PersistenciaException;
import interfaces.IEjercicioDAO;
import java.util.ArrayList;
import java.util.List;
import mappersPersistencia.EjercicioPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class EjercicioDAO implements IEjercicioDAO {
    private final MongoCollection<Document> coleccion;

    public EjercicioDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("ejercicios");
    }
    
    /**
     * Este metodo se usa para obtener una lista de ejercicioPojo a partir de los ids
     * es parte del flujo para meterlos a detallerutinadto como ejerciciodto y no como id
     * @param idsEjercicios
     * @return
     * @throws PersistenciaException 
     */
    public List<EjercicioPojo> obtenerPorListaIds(List<String> idsEjercicios) throws PersistenciaException {
        try {
            List<EjercicioPojo> ejercicios = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find(Filters.in("_id", idsEjercicios));
            for (Document doc : docs) {
                ejercicios.add(EjercicioPersistenciaMapper.toPojo(doc));
            }
            return ejercicios;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener ejercicios");
        }
    }
    
    public List<EjercicioPojo> recuperarEjercicios(String grupoMuscular) throws PersistenciaException {
        try {
            List<EjercicioPojo> ejercicios = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find(Filters.eq("grupoMuscular", grupoMuscular));
            for (Document doc : docs) {
                ejercicios.add(EjercicioPersistenciaMapper.toPojo(doc));
            }
            return ejercicios;
        } catch (MongoException e) {
            throw new PersistenciaException("Error al recuperar ejercicios por grupo muscular");
        }
    }
}
