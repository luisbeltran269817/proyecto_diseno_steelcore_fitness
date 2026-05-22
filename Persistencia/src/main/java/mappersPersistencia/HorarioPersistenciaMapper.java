/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.HorarioPojo;
import java.time.LocalTime;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class HorarioPersistenciaMapper {

    public static Document toDocument(HorarioPojo pojo) {
        Document doc = new Document();

        doc.append("idHorario",pojo.getIdHorario());
        doc.append("nombreDia",pojo.getNombreDia());

        doc.append("horaInicio",pojo.getHoraInicio().toString());

        doc.append("horaFin",pojo.getHoraFin().toString());

        doc.append("disponible",pojo.isDisponible());

        return doc;
    }
    
    public static HorarioPojo toPojo(Document doc) {
        
        HorarioPojo pojo = new HorarioPojo();
        pojo.setIdHorario(doc.getString("idHorario"));

        pojo.setNombreDia(doc.getString("nombreDia"));
        
        pojo.setHoraInicio(LocalTime.parse(doc.getString("horaInicio")));

        pojo.setHoraFin(LocalTime.parse(doc.getString("horaFin")));

        pojo.setDisponible(doc.getBoolean("disponible", true)); // true = libre si no existe el campo

        return pojo;
    }
    
    
}