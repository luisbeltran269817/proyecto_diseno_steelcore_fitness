/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios.EntrenadorPojo;
import dtos.EntrenadorDTO;
import dtos.SucursalDTO;
import interfaces.IEntrenadorDAO;
import java.util.ArrayList;
import java.util.List;
import mappersPersistencia.EntrenadorPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorDAO implements IEntrenadorDAO {
    private final AlmacenComprarMembresiaMock almacen;
    private MongoCollection<Document> coleccion;

    public EntrenadorDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
        //la coleccion de entrenadores en la bd de mongo debe llamarse "entrenadores"
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("entrenadores");
    }
    
    @Override
    public List<EntrenadorDTO> obtenerTodos() {
        return new ArrayList<>(almacen.getEntrenadores().values());
    }
    
    @Override
    public EntrenadorDTO buscarPorId(String id) {
        return almacen.getEntrenadores().get(id);
    }
    
    /**
     * METODO CONVERTIDO A MONGO PORQUE LO USA EL CASO BASE
     * obtiene todos los entrenadores de una sucursal
     * @param idSucursal
     * @return 
     */
    @Override
    public List<EntrenadorPojo> obtenerPorSucursal(String idSucursal) {
        List<EntrenadorPojo> lista = new ArrayList<>();
        FindIterable<Document> docs = coleccion.find(Filters.eq("idSucursal", idSucursal));
        
        for (Document doc : docs) {
            EntrenadorPojo pojo = EntrenadorPersistenciaMapper.toPojo(doc);
            lista.add(pojo);
        }
        return lista;
    }
}
