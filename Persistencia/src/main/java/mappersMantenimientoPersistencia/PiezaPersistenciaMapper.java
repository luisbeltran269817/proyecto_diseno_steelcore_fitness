/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.PiezaPojo;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class PiezaPersistenciaMapper {
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(PiezaPojo pojo) {
        Document doc = new Document();
        doc.append("_id", pojo.getIdPieza());
        doc.append("nombre", pojo.getNombre());
        doc.append("stock", pojo.getStock());
        doc.append("estado", pojo.getEstado().name());
        if (pojo.getBaja() != null) {
            doc.append("baja", BajaPersistenciaMapper.toDocument(pojo.getBaja()));
        }

        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static PiezaPojo toPojo(Document doc) {

        PiezaPojo pojo = new PiezaPojo();
        pojo.setIdPieza(doc.getString("_id"));
        pojo.setNombre(doc.getString("nombre"));
        pojo.setStock(doc.getInteger("stock"));
        pojo.setEstado(PiezaPojo.EstadoPieza.valueOf(doc.getString("estado")));
        Document bajaDoc = (Document) doc.get("baja");
        if (bajaDoc != null) {
            pojo.setBaja(BajaPersistenciaMapper.toPojo(bajaDoc));
        }

        return pojo;
    }
}
