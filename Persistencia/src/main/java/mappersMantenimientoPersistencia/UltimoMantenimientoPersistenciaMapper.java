/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.UltimoMantenimientoPojo;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class UltimoMantenimientoPersistenciaMapper {
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(UltimoMantenimientoPojo pojo) {

        if (pojo == null) {
            return null;
        }

        Document doc = new Document();
        doc.append("idMantenimiento", pojo.getIdMantenimiento());
        if (pojo.getFecha() != null) {
            doc.append("fecha", pojo.getFecha().toString());
        }
        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }
        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static UltimoMantenimientoPojo toPojo(Document doc) {

        if (doc == null) {
            return null;
        }
        UltimoMantenimientoPojo pojo = new UltimoMantenimientoPojo();
        pojo.setIdMantenimiento(doc.getString("idMantenimiento"));
        String fecha = doc.getString("fecha");
        if (fecha != null) {
            pojo.setFecha(LocalDateTime.parse(fecha));
        }
        String estado = doc.getString("estado");
        if (estado != null) {
            pojo.setEstado(UltimoMantenimientoPojo.EstadoMantenimientoSnapshot.valueOf(estado));
        }
        return pojo;
    }
}
