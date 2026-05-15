/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.PagoPojo;
import dominios.PagoPojo.EstadoPagoPojo;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class PagoPersistenciaMapper {
    public static Document toDocument(PagoPojo pojo) {

        if (pojo == null) {
            return null;
        }
        Document doc = new Document();

        doc.append("idPago", pojo.getIdPago());
        doc.append("idCliente", pojo.getIdCliente());
        doc.append("monto", pojo.getMonto());
        doc.append("metodoPago", pojo.getMetodoPago());

        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }

        if (pojo.getFecha() != null) {
            doc.append("fecha", pojo.getFecha().toString());
        }

        return doc;
    }

    public static PagoPojo toPojo(Document doc) {

        if (doc == null) {
            return null;
        }

        PagoPojo pojo = new PagoPojo();

        pojo.setIdPago(doc.getString("idPago"));
        pojo.setIdCliente(doc.getString("idCliente"));
        pojo.setMonto(doc.getDouble("monto"));
        pojo.setMetodoPago(doc.getString("metodoPago"));

        String estado = doc.getString("estado");

        if (estado != null) {
            pojo.setEstado(EstadoPagoPojo.valueOf(estado));
        }

        String fecha = doc.getString("fecha");

        if (fecha != null) {
            pojo.setFecha(LocalDateTime.parse(fecha));
        }
        return pojo;
    }
}
