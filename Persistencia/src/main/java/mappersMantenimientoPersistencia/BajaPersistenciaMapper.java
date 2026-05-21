/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.BajaPojo;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class BajaPersistenciaMapper {
    
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(BajaPojo pojo) {
        Document doc = new Document();
        doc.append("idBaja", pojo.getIdBaja());
        doc.append("motivo", pojo.getMotivo());
        doc.append("fechaBaja", pojo.getFechaBaja().toString());
        doc.append("tipo", pojo.getTipo());

        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static BajaPojo toPojo(Document doc) {
        BajaPojo pojo = new BajaPojo();
        pojo.setIdBaja(doc.getString("idBaja"));
        pojo.setMotivo(doc.getString("motivo"));
        pojo.setFechaBaja(LocalDateTime.parse(doc.getString("fechaBaja")));
        pojo.setTipo(doc.getString("tipo"));
        return pojo;
    }
}
