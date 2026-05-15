/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.CitaPojo;
import dominios.CitaPojo.EstadoCitaPojo;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class CitaPersistenciaMapper {
      public static Document toDocument(CitaPojo pojo) {

        if (pojo == null) {
            return null;
        }

        Document doc = new Document();

        doc.append("idCita", pojo.getIdCita());
        doc.append("idCliente", pojo.getIdCliente());
        doc.append("idEntrenador", pojo.getIdEntrenador());
        doc.append("idSucursal", pojo.getIdSucursal());
        doc.append("idHorario", pojo.getIdHorario());

        if (pojo.getFechaHora() != null) {
            doc.append("fechaHora", pojo.getFechaHora().toString());
        }

        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }

        doc.append("notas", pojo.getNotas());

        return doc;
    }

    public static CitaPojo toPojo(Document doc) {

        if (doc == null) {
            return null;
        }

        CitaPojo pojo = new CitaPojo();

        pojo.setIdCita(doc.getString("idCita"));
        pojo.setIdCliente(doc.getString("idCliente"));
        pojo.setIdEntrenador(doc.getString("idEntrenador"));
        pojo.setIdSucursal(doc.getString("idSucursal"));
        pojo.setIdHorario(doc.getString("idHorario"));

        String fechaHora = doc.getString("fechaHora");

        if (fechaHora != null) {
            pojo.setFechaHora(LocalDateTime.parse(fechaHora));
        }

        String estado = doc.getString("estado");

        if (estado != null) {
            pojo.setEstado(EstadoCitaPojo.valueOf(estado));
        }

        pojo.setNotas(doc.getString("notas"));

        return pojo;
    }
}
