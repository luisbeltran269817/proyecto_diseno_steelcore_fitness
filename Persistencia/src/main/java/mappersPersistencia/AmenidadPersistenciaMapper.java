/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.AmenidadPojo;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class AmenidadPersistenciaMapper {
    
    public static Document toDocument(AmenidadPojo amenidad) {
        Document doc = new Document();
        doc.append("_id", amenidad.getIdAmenidad());
        doc.append("nombre", amenidad.getNombre());
        doc.append("descripcion", amenidad.getDescripcion());
        doc.append("tipo",amenidad.getTipo().name());
        doc.append("costo", amenidad.getCosto());
        return doc;
    }
    
    public static AmenidadPojo toPojo(Document doc) {
        AmenidadPojo pojo= new AmenidadPojo();
        pojo.setIdAmenidad(doc.getString("_id"));
        pojo.setNombre(doc.getString("nombre"));
        pojo.setDescripcion(doc.getString("descripcion"));
        pojo.setTipo(AmenidadPojo.TipoAmenidad.valueOf(doc.getString("tipo")));
        pojo.setCosto(doc.getDouble("costo"));
        return pojo;
    }
}
