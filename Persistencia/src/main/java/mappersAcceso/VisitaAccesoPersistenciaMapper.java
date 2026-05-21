/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersAcceso;

import dominioAcceso.VisitaAccesoPojo;
import java.time.LocalDateTime;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Encargado de transformar visitas entre codigo Java y la base de datos Mongo
 *
 * @author julian izaguirre
 */
public class VisitaAccesoPersistenciaMapper {

    /**
     * Transforma una visita a formato de documento de MongoDB
     * 
     * @param pojo El objeto de visita
     * @return Documento generado con sus llaves
     */
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

    /**
     * Convierte los datos que vienen de la base a un objeto de visita
     * 
     * @param doc Datos en crudo desde Mongo
     * @return La visita estructurada para Java
     */
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