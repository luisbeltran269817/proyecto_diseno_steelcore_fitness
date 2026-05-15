/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import dtos.AmenidadDTO;
import interfaces.IAmenidadDAO;
import java.util.ArrayList;
import java.util.List;
import mappersPersistencia.AmenidadPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class AmenidadDAO implements IAmenidadDAO{
    private MongoCollection<Document> coleccion;
    
    public AmenidadDAO() {
        this.coleccion =MongoConexion.obtenerBaseDatos().getCollection("amenidades");
    }
    
    
    //Método para el nuevo pobladorBD;
    public void agregar(
        AmenidadPojo amenidad) {
        Document doc =
            AmenidadPersistenciaMapper.toDocument(
                    amenidad
            );
        coleccion.insertOne(doc);
    }
    //Metodo nuevo supremo del mongo
    @Override
    public List<AmenidadPojo> ConsultarTodas(){
        List<AmenidadPojo> lista = new ArrayList<>();
        FindIterable<Document> docs = coleccion.find();
        for (Document doc : docs) {
            AmenidadPojo pojo = AmenidadPersistenciaMapper.toPojo(doc);
            lista.add(pojo);
        }
            return lista;
        }
}
