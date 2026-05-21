package mappersAcceso;

import dominioAcceso.VisitaAccesoPojo;
import java.time.LocalDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Convierte entre Document (Mongo, colección "visitas") y VisitaAccesoPojo.
 * Compatible con la colección "visitas" existente; solo añade campos extra
 * (tipoServicio, idRecursoAsignado) al documento.
 *
 * @author julian izaguirre
 */
public class VisitaAccesoPersistenciaMapper {

    public static Document toDocument(VisitaAccesoPojo pojo) {
        if (pojo == null) return null;

        String idVisita = pojo.getIdVisita();
        if (idVisita == null || idVisita.isBlank()) {
            idVisita = new ObjectId().toHexString();
            pojo.setIdVisita(idVisita);
        }

        Document doc = new Document();
        doc.append("_id",               idVisita);
        doc.append("idCliente",         pojo.getIdCliente());
        doc.append("idSucursal",        pojo.getIdSucursal());
        doc.append("tipoServicio",      pojo.getTipoServicio());
        doc.append("idRecursoAsignado", pojo.getIdRecursoAsignado());

        if (pojo.getFechaHora() != null) {
            doc.append("fechaHora", pojo.getFechaHora().toString());
        }

        return doc;
    }

    public static VisitaAccesoPojo toPojo(Document doc) {
        if (doc == null) return null;

        VisitaAccesoPojo pojo = new VisitaAccesoPojo();
        pojo.setIdVisita(doc.getString("_id"));
        pojo.setIdCliente(doc.getString("idCliente"));
        pojo.setIdSucursal(doc.getString("idSucursal"));
        pojo.setTipoServicio(doc.getString("tipoServicio"));
        pojo.setIdRecursoAsignado(doc.getString("idRecursoAsignado"));

        String fechaStr = doc.getString("fechaHora");
        if (fechaStr != null) {
            pojo.setFechaHora(LocalDateTime.parse(fechaStr));
        }

        return pojo;
    }
}