/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.MantenimientoPiezaPojo;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MantenimientoPiezaPersistenciaMapper {
    
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(MantenimientoPiezaPojo pojo) {

        Document doc = new Document();

        doc.append("idMantenimientoPieza", pojo.getIdMantenimientoPieza());
        doc.append("idMantenimiento", pojo.getIdMantenimiento());
        doc.append("idPieza", pojo.getIdPieza());
        doc.append("cantidad", pojo.getCantidad());

        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static MantenimientoPiezaPojo toPojo(Document doc) {

        MantenimientoPiezaPojo pojo = new MantenimientoPiezaPojo();

        pojo.setIdMantenimientoPieza(doc.getString("idMantenimientoPieza"));
        pojo.setIdMantenimiento(doc.getString("idMantenimiento"));
        pojo.setIdPieza(doc.getString("idPieza"));
        pojo.setCantidad(doc.getInteger("cantidad"));

        return pojo;
    }
}
