/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.AmenidadPojo;
import dominios.PlanPojo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class PlanPersistenciaMapper {
    
    public static Document toDocument(PlanPojo plan) {
        List<Document> amenidadesDocs= new ArrayList<>();
        if (plan.getAmenidades() != null) {
            for (AmenidadPojo amenidad: plan.getAmenidades()) {
                amenidadesDocs.add(AmenidadPersistenciaMapper.toDocument(amenidad));
            }
        }
        Document doc = new Document();
        doc.append("idPlan", plan.getIdPlan());
        doc.append("nombre", plan.getNombre());
        doc.append("precio", plan.getPrecio());
        doc.append("descripcion", plan.getDescripcion());
        doc.append("mesesDuracion", plan.getMesesDuracion());
        doc.append("amenidades", amenidadesDocs);

        return doc;
    }
    
    public static PlanPojo toPojo(Document doc) {

        PlanPojo plan = new PlanPojo();

        plan.setIdPlan(doc.getString("idPlan"));

        plan.setNombre(doc.getString("nombre"));
        plan.setPrecio(doc.getDouble("precio"));

        plan.setDescripcion(doc.getString("descripcion"));

        plan.setMesesDuracion(doc.getInteger("mesesDuracion"));

        List<Document> amenidadesDocs= (List<Document>) doc.get("amenidades");

        List<AmenidadPojo> amenidades= new ArrayList<>();

        if (amenidadesDocs != null) {
            for (Document amenidadDoc: amenidadesDocs) {
                amenidades.add(AmenidadPersistenciaMapper.toPojo(amenidadDoc));
            }
        }
        plan.setAmenidades(amenidades);
        return plan;
    }
}
