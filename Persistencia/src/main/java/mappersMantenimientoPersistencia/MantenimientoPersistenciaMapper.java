/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.MantenimientoPiezaPojo;
import dominios_mantenimiento.MantenimientoPojo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class MantenimientoPersistenciaMapper {
    /**
     * Método que mappea un pojo a un documento
     * @param pojo el pojo recibido
     * @return el documento transformado
     */
    public static Document toDocument(MantenimientoPojo pojo) {

        List<Document> piezasDocs = new ArrayList<>();
        if (pojo.getPiezas() != null) {
            for (MantenimientoPiezaPojo pieza : pojo.getPiezas()) {
                piezasDocs.add(MantenimientoPiezaPersistenciaMapper.toDocument(pieza));
            }
        }
        Document doc = new Document();

        doc.append("_id", pojo.getIdMantenimiento());
        doc.append("idTecnico", pojo.getIdTecnico());
        doc.append("idMaquina", pojo.getIdMaquina());
        doc.append("descripcion", pojo.getDescripcion());
        if (pojo.getFechaProgramada() != null) {
            doc.append("fechaProgramada", pojo.getFechaProgramada().toString());
        }
        if (pojo.getFechaInicio() != null) {
            doc.append("fechaInicio", pojo.getFechaInicio().toString());
        }
        if (pojo.getFechaFin() != null) {
            doc.append("fechaFin", pojo.getFechaFin().toString());
        }
        doc.append("estado", pojo.getEstado().name());
        doc.append("piezas", piezasDocs);
        return doc;
    }
    
    /**
     * Método que mappea de un documento a un pojo
     * @param doc el documento recibido
     * @return el pojo transformado
     */
    public static MantenimientoPojo toPojo(Document doc) {
        MantenimientoPojo pojo = new MantenimientoPojo();
        pojo.setIdMantenimiento(doc.getString("_id"));
        pojo.setIdTecnico(doc.getString("idTecnico"));
        pojo.setIdMaquina(doc.getString("idMaquina"));
        pojo.setDescripcion(doc.getString("descripcion"));
        if (doc.getString("fechaProgramada") != null) {
            pojo.setFechaProgramada(LocalDateTime.parse(doc.getString("fechaProgramada")));
        }
        if (doc.getString("fechaInicio") != null) {
            pojo.setFechaInicio(LocalDateTime.parse(doc.getString("fechaInicio")));
        }
        if (doc.getString("fechaFin") != null) {
            pojo.setFechaFin(LocalDateTime.parse(doc.getString("fechaFin")));
        }
        pojo.setEstado(MantenimientoPojo.EstadoMantenimiento.valueOf(doc.getString("estado")));
        List<Document> piezasDocs = (List<Document>) doc.get("piezas");
        List<MantenimientoPiezaPojo> piezas = new ArrayList<>();
        if (piezasDocs != null) {
            for (Document piezaDoc : piezasDocs) {
                piezas.add(MantenimientoPiezaPersistenciaMapper.toPojo(piezaDoc));
            }
        }
        pojo.setPiezas(piezas);
        return pojo;
    }
}
