/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.EjercicioPojo;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class EjercicioPersistenciaMapper {
    public static Document toDocument(EjercicioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("_id", pojo.getIdEjercicio());
        doc.append("nombre", pojo.getNombre());
        doc.append("grupoMuscular", pojo.getGrupoMuscular());
        doc.append("descripcion", pojo.getDescripcion());
        return doc;
    }
    
    public static EjercicioPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        EjercicioPojo pojo = new EjercicioPojo();
        pojo.setIdEjercicio(doc.getString("_id"));
        pojo.setNombre(doc.getString("nombre"));
        pojo.setGrupoMuscular(doc.getString("grupoMuscular"));
        pojo.setDescripcion(doc.getString("descripcion"));
        return pojo;
    }
}
