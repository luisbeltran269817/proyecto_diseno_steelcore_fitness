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

    /**
     * recibe un HorarioPojo y lo convierte a document
     * @param horario
     * @return 
     */
    public static Document toDocument(HorarioPojo horario) {
        Document doc = new Document();
        
        doc.append("nombreDia", horario.getNombreDia());
        doc.append("inicio", horario.getInicio().toString());
        doc.append("fin", horario.getFin().toString());
        doc.append("disponible", horario.isDisponible());
        return doc;
    }

    /**
     * recibe un Document de horario y lo convierte a HorarioPojo
     * @param doc
     * @return 
     */
    public static HorarioPojo toPojo(Document doc) {
        HorarioPojo horario = new HorarioPojo();
        
        horario.setNombreDia(doc.getString("nombreDia"));
        horario.setInicio(LocalTime.parse(doc.getString("inicio")));
        horario.setFin(LocalTime.parse(doc.getString("fin")));
        horario.setDisponible(doc.getBoolean("disponible"));
        return horario;
    }
}