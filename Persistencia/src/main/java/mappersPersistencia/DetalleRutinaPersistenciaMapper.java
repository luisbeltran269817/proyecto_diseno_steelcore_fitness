/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.DetalleRutinaPojo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class DetalleRutinaPersistenciaMapper {
    public static Document toDocument(DetalleRutinaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("nombreDia", pojo.getNombreDia());
        doc.append("grupoMuscular", pojo.getGrupoMuscular());
        //validacion proq los dias de descanso no traen ejercicios 
        if (pojo.getIdsEjercicios() != null) {
            doc.append("ejercicios", pojo.getIdsEjercicios());
        } else {
            doc.append("ejercicios", new ArrayList<>());
        }
        return doc;
    }

    public static DetalleRutinaPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        DetalleRutinaPojo pojo = new DetalleRutinaPojo();
        pojo.setNombreDia(doc.getString("nombreDia"));
        pojo.setGrupoMuscular(doc.getString("grupoMuscular"));
        List<String> idsEjercicios = (List<String>) doc.get("ejercicios");
        //si pues aqui tambien validacion por lo mismo de los descansos
        if (idsEjercicios != null) {
            pojo.setIdsEjercicios(idsEjercicios);
        } else {
            pojo.setIdsEjercicios(new ArrayList<>());
        }
        return pojo;
    }
}
