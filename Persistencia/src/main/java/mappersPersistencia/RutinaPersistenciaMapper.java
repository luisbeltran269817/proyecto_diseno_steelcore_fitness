/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.DetalleRutinaPojo;
import dominios.RutinaPojo;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author luiscarlosbeltran
 */
public class RutinaPersistenciaMapper {
    public static Document toDocument(RutinaPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("idEntrenador", pojo.getIdEntrenador());
        doc.append("nombre", pojo.getNombre());
        //igual aca la conversion para que se inserte bien como ISODate en mongose mete como date
        doc.append("fechaCreacion", java.util.Date.from(pojo.getFechaCreacion().atZone(ZoneId.systemDefault()).toInstant()));
        List<Document> detallesDocs = new ArrayList<>();
        for (DetalleRutinaPojo detalle:pojo.getDetalles()) {
            detallesDocs.add(DetalleRutinaPersistenciaMapper.toDocument(detalle));
        }
        doc.append("detalles", detallesDocs);
        return doc;
    }
    
    public static RutinaPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        RutinaPojo pojo = new RutinaPojo();
        pojo.setIdEntrenador(doc.getString("idEntrenador"));
        pojo.setNombre(doc.getString("nombre"));
        
        //se supone que para ISODate no funciona un getString
        //entonces primero sacarlo como date y despues hacerlo localdatetime porque ese es el de mi pojo
        try {
            Date fechaDate = doc.getDate("fechaCreacion");
            if (fechaDate != null) {
                pojo.setFechaCreacion(fechaDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            }
        } catch (Exception e) {
            pojo.setFechaCreacion(null);
        }
        
        List<Document> detallesDocs = (List<Document>) doc.get("detalles");
        List<DetalleRutinaPojo> detalles = new ArrayList<>();
        for (Document detalleDoc : detallesDocs) {
            detalles.add(DetalleRutinaPersistenciaMapper.toPojo(detalleDoc));
        }
        pojo.setDetalles(detalles);
        return pojo;
    }
}
