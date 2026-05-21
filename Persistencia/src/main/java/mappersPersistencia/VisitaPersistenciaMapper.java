/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.VisitaPojo;
import java.time.LocalDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author Tungs
 */
public class VisitaPersistenciaMapper {
    
    public static Document toDocument(VisitaPojo pojo) {

        if (pojo == null) {
            return null;
        }

        Document doc = new Document();

        // Si no viene EL id 
        // generamos uno nuevo para evitar _id null duplicado en MongoDB
        String idVisita = pojo.getIdVisita();
        if (idVisita == null || idVisita.isBlank()) {
            idVisita = new ObjectId().toHexString();
            pojo.setIdVisita(idVisita);
        }
        doc.append("_id", idVisita);
        doc.append("idCliente", pojo.getIdCliente());
        doc.append("idSucursal", pojo.getIdSucursal());

        if (pojo.getFechaHora() != null) {

            doc.append(
                    "fechaHora",
                    pojo.getFechaHora().toString());
        }

        return doc;
    }

    public static VisitaPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }

        VisitaPojo pojo = new VisitaPojo();

        pojo.setIdVisita(doc.getString("_id"));
        pojo.setIdCliente( doc.getString("idCliente"));
        pojo.setIdSucursal(doc.getString("idSucursal"));
        String fechaHora = doc.getString("fechaHora");
        if (fechaHora != null) {
            pojo.setFechaHora(LocalDateTime.parse(fechaHora));
        }
        return pojo;
    }
}
