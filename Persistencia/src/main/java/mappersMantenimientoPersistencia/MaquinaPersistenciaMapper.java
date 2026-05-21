/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.MaquinaPojo;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MaquinaPersistenciaMapper {
    
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(MaquinaPojo pojo) {
        Document doc = new Document();
        doc.append("_id", pojo.getIdMaquina());
        doc.append("idSucursal", pojo.getIdSucursal());
        doc.append("modelo", pojo.getModelo());
        doc.append("tipo", pojo.getTipo());
        if (pojo.getEstado() != null) {
            doc.append("estado", pojo.getEstado().name());
        }
        if (pojo.getUltimoMantenimiento() != null) {
            doc.append("ultimoMantenimiento", UltimoMantenimientoPersistenciaMapper.toDocument(pojo.getUltimoMantenimiento()));
        }
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
    public static MaquinaPojo toPojo(Document doc) {
        MaquinaPojo pojo = new MaquinaPojo();
        pojo.setIdMaquina(doc.getString("_id"));
        pojo.setIdSucursal(doc.getString("idSucursal"));
        pojo.setModelo(doc.getString("modelo"));
        pojo.setTipo(doc.getString("tipo"));
        String estado = doc.getString("estado");
        if (estado != null) {
            pojo.setEstado(MaquinaPojo.EstadoMaquina.valueOf(estado));
        }
        Document ultimoMantenimientoDoc = (Document) doc.get("ultimoMantenimiento");
        if (ultimoMantenimientoDoc != null) {
            pojo.setUltimoMantenimiento(UltimoMantenimientoPersistenciaMapper.toPojo(ultimoMantenimientoDoc));
        }
        Document bajaDoc = (Document) doc.get("baja");
        if (bajaDoc != null) {
            pojo.setBaja(BajaPersistenciaMapper.toPojo(bajaDoc));
        }
        return pojo;
    }
}
