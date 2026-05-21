/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.HorarioTecnicoPojo;
import java.time.LocalTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class HorarioTecnicoPersistenciaMapper {
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(HorarioTecnicoPojo pojo) {

        Document doc = new Document();

        doc.append("idHorario", pojo.getIdHorario());
        doc.append("nombreDia", pojo.getNombreDia());
        doc.append("horaInicio", pojo.getHoraInicio().toString());
        doc.append("horaFin", pojo.getHoraFin().toString());
        doc.append("disponible", pojo.isDisponible());

        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static HorarioTecnicoPojo toPojo(Document doc) {

        HorarioTecnicoPojo pojo = new HorarioTecnicoPojo();

        pojo.setIdHorario(doc.getString("idHorario"));
        pojo.setNombreDia(doc.getString("nombreDia"));
        pojo.setHoraInicio(LocalTime.parse(doc.getString("horaInicio")));
        pojo.setHoraFin(LocalTime.parse(doc.getString("horaFin")));
        pojo.setDisponible(doc.getBoolean("disponible"));

        return pojo;
    }
    
}
