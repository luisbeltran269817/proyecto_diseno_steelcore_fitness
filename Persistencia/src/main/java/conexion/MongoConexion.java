/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Clase que crea la conexión con el Mongo
 * @author Tungs
 */
public class MongoConexion {
    
    //private static final String URL ="mongodb+srv://urogallo27:NorbkJmXDpsc9JeF@menchacabd.oepyw00.mongodb.net/?appName=MenchacaBD";
    private static final String URL ="mongodb://localhost:27017";
    
    private static final String NOMBRE_BASE_DATOS ="steelcorefitness";

    private static MongoClient cliente;

    private MongoConexion() {
    }

    public static MongoClient obtenerCliente() {

        if (cliente == null) {

            cliente = MongoClients.create(URL);
        }

        return cliente;
    }

    public static MongoDatabase obtenerBaseDatos() {
        return obtenerCliente().getDatabase(NOMBRE_BASE_DATOS);
    }
}
