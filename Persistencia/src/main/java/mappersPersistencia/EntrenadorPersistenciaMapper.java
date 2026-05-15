/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorPersistenciaMapper {
    
    public static Document toDocument(EntrenadorPojo pojo) {

        List<Document> horariosDocs =new ArrayList<>();

        if (pojo.getHorarios() != null) {
            for (HorarioPojo horario: pojo.getHorarios()) {
                horariosDocs.add(HorarioPersistenciaMapper.toDocument(horario));
            }
        }

        Document doc = new Document();

        doc.append("_id",pojo.getIdEntrenador());

        doc.append("nombre",pojo.getNombre());

        doc.append("idSucursal",pojo.getIdSucursal());

        doc.append("horarios",horariosDocs);

        return doc;
    }
    
    public static EntrenadorPojo toPojo(Document doc) {

        EntrenadorPojo pojo = new EntrenadorPojo();

        pojo.setIdEntrenador(doc.getString("_id"));

        pojo.setNombre(doc.getString("nombre"));

        pojo.setIdSucursal(doc.getString("idSucursal"));

        List<Document> horariosDocs =(List<Document>)doc.get("horarios");

        List<HorarioPojo> horarios =new ArrayList<>();

        if (horariosDocs != null) {
            for (Document horarioDoc: horariosDocs) {
                horarios.add(HorarioPersistenciaMapper.toPojo(horarioDoc));
            }
        }

        pojo.setHorarios(horarios);

        return pojo;
    }
}
