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
    /**
     * Convierte un entrenadorPoojo a Document
     * @param entrenador
     * @return 
     */
    public static Document toDocument(EntrenadorPojo entrenador) {
        List<Document> horariosDocs = new ArrayList<>();
        
        if (entrenador.getHorarios()!= null) {
            for (HorarioPojo horario : entrenador.getHorarios()) {
                horariosDocs.add( HorarioPersistenciaMapper.toDocument(horario));
            }
        }
        
        Document doc = new Document();
        
        doc.append("_id", entrenador.getIdEntrenador());
        doc.append("nombre", entrenador.getNombre());
        doc.append("horarios", horariosDocs);
        doc.append("idSucursal", entrenador.getIdSucursal());
        return doc;
    }

    /**
     * convierte un document a entrenadorPojo
     * @param doc
     * @return 
     */
    public static EntrenadorPojo toPojo(Document doc) {
        EntrenadorPojo entrenador = new EntrenadorPojo();
        
        entrenador.setIdEntrenador(doc.getString("_id"));
        entrenador.setNombre(doc.getString("nombre"));
        
        List<Document> horariosDocs = (List<Document>) doc.get("horarios");
        List<HorarioPojo> horarios = new ArrayList<>();
        if (horariosDocs != null) {
            for (Document horarioDoc : horariosDocs) {
                horarios.add(HorarioPersistenciaMapper.toPojo(horarioDoc));
            }
        }
        entrenador.setHorarios(horarios);
        entrenador.setIdSucursal(doc.getString("idSucursal"));
        return entrenador;
    }
}
