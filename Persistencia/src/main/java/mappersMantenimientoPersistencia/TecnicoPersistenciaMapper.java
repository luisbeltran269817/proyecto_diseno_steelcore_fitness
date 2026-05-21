/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.HorarioTecnicoPojo;
import dominios_mantenimiento.TecnicoPojo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class TecnicoPersistenciaMapper {
    
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(TecnicoPojo pojo) {
        List<Document> horariosDocs = new ArrayList<>();
        if (pojo.getHorarios() != null) {
            for (HorarioTecnicoPojo horario : pojo.getHorarios()) {
                horariosDocs.add(HorarioTecnicoPersistenciaMapper.toDocument(horario));
            }
        }
        Document doc = new Document();
        doc.append("_id", pojo.getIdTecnico());
        doc.append("nombre", pojo.getNombre());
        doc.append("horarios", horariosDocs);

        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static TecnicoPojo toPojo(Document doc) {

        TecnicoPojo pojo = new TecnicoPojo();
        pojo.setIdTecnico(doc.getString("_id"));
        pojo.setNombre(doc.getString("nombre"));
        List<Document> horariosDocs = (List<Document>) doc.get("horarios");
        List<HorarioTecnicoPojo> horarios = new ArrayList<>();
        if (horariosDocs != null) {
            for (Document horarioDoc : horariosDocs) {
                horarios.add(HorarioTecnicoPersistenciaMapper.toPojo(horarioDoc));
            }
        }
        pojo.setHorarios(horarios);

        return pojo;
    }
}
