/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersAcceso;

import dominioAcceso.ClasePojo;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Convierte los objetos de clases de Java a documentos de Mongo y al reves
 *
 * @author julian izaguirre
 */
public class ClasePersistenciaMapper {

    /**
     * Pasa un objeto pojo de clase a un documento de Mongo
     * 
     * @param pojo El objeto a convertir
     * @return El documento listo para guardarse
     */
    public static Document toDocument(ClasePojo pojo) {
        if (pojo == null) return null;

        Document doc = new Document();
        doc.append("_id",        pojo.getIdClase());
        doc.append("nombre",     pojo.getNombre());
        doc.append("idSucursal", pojo.getIdSucursal());
        doc.append("idPlan",     pojo.getIdPlan());
        doc.append("diaSemana",  pojo.getDiaSemana());

        if (pojo.getHoraInicio() != null) {
            doc.append("horaInicio", pojo.getHoraInicio().toString());
        }

        doc.append("cupoMaximo", pojo.getCupoMaximo());
        doc.append("cupoActual", pojo.getCupoActual());
        doc.append("inscritos",  pojo.getInscritos() != null
                                  ? pojo.getInscritos()
                                  : new ArrayList<>());
        return doc;
    }

    /**
     * Pasa un documento de Mongo a un objeto pojo de Java
     * 
     * @param doc El documento leido de la base
     * @return Objeto poblado con los datos
     */
    public static ClasePojo toPojo(Document doc) {
        if (doc == null) return null;

        ClasePojo pojo = new ClasePojo();
        pojo.setIdClase(doc.getString("_id"));
        pojo.setNombre(doc.getString("nombre"));
        pojo.setIdSucursal(doc.getString("idSucursal"));
        pojo.setIdPlan(doc.getString("idPlan"));
        pojo.setDiaSemana(doc.getString("diaSemana"));

        String horaStr = doc.getString("horaInicio");
        if (horaStr != null) {
            pojo.setHoraInicio(LocalTime.parse(horaStr));
        }

        pojo.setCupoMaximo(doc.getInteger("cupoMaximo", 20));
        pojo.setCupoActual(doc.getInteger("cupoActual", 0));

        @SuppressWarnings("unchecked")
        List<String> inscritos = (List<String>) doc.get("inscritos");
        pojo.setInscritos(inscritos != null ? inscritos : new ArrayList<>());

        return pojo;
    }
}