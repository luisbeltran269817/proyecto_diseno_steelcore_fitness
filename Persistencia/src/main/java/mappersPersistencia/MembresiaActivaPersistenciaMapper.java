/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.MembresiaActivaPojo;
import dominios.MembresiaPojo.EstadoMembresiaPojo;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MembresiaActivaPersistenciaMapper {
     public static Document toDocument(MembresiaActivaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("idMembresia", pojo.getIdMembresia());
        doc.append("idPlan", pojo.getIdPlan());
        if (pojo.getFechaCaducidad() != null) {
            doc.append("fechaCaducidad", pojo.getFechaCaducidad().toString());
        }
        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }
        return doc;
    }

    public static MembresiaActivaPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        MembresiaActivaPojo pojo = new MembresiaActivaPojo();
        pojo.setIdMembresia(doc.getString("idMembresia"));
        pojo.setIdPlan(doc.getString("idPlan"));
        String fechaCaducidad = doc.getString("fechaCaducidad");
        if (fechaCaducidad != null) {
            pojo.setFechaCaducidad(LocalDateTime.parse(fechaCaducidad));
        }
        String estado = doc.getString("estado");
        if (estado != null) {
            pojo.setEstado(EstadoMembresiaPojo.valueOf(estado));
        }
        return pojo;
    }
    
}
