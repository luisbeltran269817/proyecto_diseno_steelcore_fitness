/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import excepciones.PersistenciaException;
import interfaces.IPlantillaRutinaDAO;
import mappersPersistencia.RutinaPersistenciaMapper;
import org.bson.Document;
import dominios.RutinaPojo;

/**
 *
 * @author luiscarlosbeltran
 */
public class PlantillaRutinaDAO implements IPlantillaRutinaDAO {
    private final MongoCollection<Document> coleccion;
    
    public PlantillaRutinaDAO(){
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("plantillaRutina");
    }
    
    @Override
    public RutinaPojo obtenerPlantilla(String nombre) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("nombre", nombre)).first();
            if (doc == null) return null;
            return RutinaPersistenciaMapper.toPojo(doc);
        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener plantilla");
        }
    }
}
