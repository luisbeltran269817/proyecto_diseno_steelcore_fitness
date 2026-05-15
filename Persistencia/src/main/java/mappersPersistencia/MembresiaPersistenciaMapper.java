/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.AmenidadPojo;
import dominios.MembresiaPojo;
import dominios.MembresiaPojo.EstadoMembresiaPojo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MembresiaPersistenciaMapper {
    
    public static Document toDocument(MembresiaPojo pojo) {

        if (pojo == null) {
            return null;
        }

        Document doc = new Document();

        doc.append("_id", pojo.getIdMembresia());
        doc.append("idCliente", pojo.getIdCliente());
        doc.append("idPlan", pojo.getIdPlan());
        doc.append("idSucursal", pojo.getIdSucursal());

        List<Document> amenidadesDocs = new ArrayList<>();

        if (pojo.getAmenidadesExtra() != null) {

            for (AmenidadPojo amenidad : pojo.getAmenidadesExtra()) {

                amenidadesDocs.add(
                        AmenidadPersistenciaMapper.toDocument(amenidad)
                );
            }
        }

        doc.append("amenidadesExtra", amenidadesDocs);

        doc.append("metodoPago", pojo.getMetodoPago());
        doc.append("montoPagado", pojo.getMontoPagado());

        doc.append("codigoQR", pojo.getCodigoQR());

        if (pojo.getFechaTramite() != null) {
            doc.append("fechaTramite", pojo.getFechaTramite().toString());
        }

        if (pojo.getFechaCaducidad() != null) {
            doc.append("fechaCaducidad", pojo.getFechaCaducidad().toString());
        }

        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }

        if (pojo.getPago() != null) {
            doc.append("pago", PagoPersistenciaMapper.toDocument(pojo.getPago()));
        }

        return doc;
    }

    public static MembresiaPojo toPojo(Document doc) {

        if (doc == null) {
            return null;
        }

        MembresiaPojo pojo = new MembresiaPojo();

        pojo.setIdMembresia(doc.getString("_id"));
        pojo.setIdCliente(doc.getString("idCliente"));
        pojo.setIdPlan(doc.getString("idPlan"));
        pojo.setIdSucursal(doc.getString("idSucursal"));

        List<Document> amenidadesDocs = (List<Document>) doc.get("amenidadesExtra");

        List<AmenidadPojo> amenidades = new ArrayList<>();

        if (amenidadesDocs != null) {

            for (Document amenidadDoc : amenidadesDocs) {

                amenidades.add(
                        AmenidadPersistenciaMapper.toPojo(amenidadDoc)
                );
            }
        }

        pojo.setAmenidadesExtra(amenidades);

        pojo.setMetodoPago(doc.getString("metodoPago"));
        pojo.setMontoPagado(doc.getDouble("montoPagado"));

        pojo.setCodigoQR(doc.getString("codigoQR"));

        String fechaTramite = doc.getString("fechaTramite");

        if (fechaTramite != null) {
            pojo.setFechaTramite(LocalDateTime.parse(fechaTramite));
        }

        String fechaCaducidad = doc.getString("fechaCaducidad");

        if (fechaCaducidad != null) {
            pojo.setFechaCaducidad(LocalDateTime.parse(fechaCaducidad));
        }

        String estado = doc.getString("estado");

        if (estado != null) {
            pojo.setEstado(EstadoMembresiaPojo.valueOf(estado));
        }

        Document pagoDoc = (Document) doc.get("pago");

        if (pagoDoc != null) {
            pojo.setPago(PagoPersistenciaMapper.toPojo(pagoDoc));
        }

        return pojo;
    }
}
