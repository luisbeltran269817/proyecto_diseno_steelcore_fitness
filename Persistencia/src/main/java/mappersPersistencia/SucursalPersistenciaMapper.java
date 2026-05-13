/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.AmenidadPojo;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class SucursalPersistenciaMapper {
    
    public static Document toDocument(SucursalPojo sucursal) {

        List<Document> planesDocs = new ArrayList<>();

        if (sucursal.getPlanes() != null) {
            for (PlanPojo plan: sucursal.getPlanes()) {
                planesDocs.add(PlanPersistenciaMapper.toDocument(plan));
            }
        }
        List<Document> amenidadesDocs= new ArrayList<>();
        if (sucursal.getAmenidadesSucursal()!= null) {
            for (AmenidadPojo amenidad: sucursal.getAmenidadesSucursal()) {
                amenidadesDocs.add(AmenidadPersistenciaMapper.toDocument(amenidad));
            }
        }
        Document doc = new Document();
        doc.append("_id",sucursal.getIdSucursal());
        doc.append("nombre", sucursal.getNombre());
        doc.append("calle", sucursal.getCalle());
        doc.append("colonia",sucursal.getColonia());
        doc.append("ciudad",sucursal.getCiudad());
        doc.append("codigoPostal",sucursal.getCodigoPostal());
        doc.append("latitud", sucursal.getLatitud());
        doc.append("longitud", sucursal.getLongitud());
        doc.append("planes", planesDocs);
        doc.append("amenidadesSucursal", amenidadesDocs);

        return doc;
    }
    
    public static SucursalPojo toPojo(Document doc) {
        
        SucursalPojo sucursal =new SucursalPojo();
        sucursal.setIdSucursal(doc.getString("_id"));
        sucursal.setNombre(doc.getString("nombre"));
        sucursal.setCalle(doc.getString("calle"));
        sucursal.setColonia(doc.getString("colonia"));
        sucursal.setCiudad(doc.getString("ciudad"));
        sucursal.setCodigoPostal(doc.getString("codigoPostal"));
        sucursal.setLatitud(doc.getDouble("latitud"));
        sucursal.setLongitud(doc.getDouble("longitud"));

        List<Document> planesDocs =(List<Document>) doc.get("planes");

        List<PlanPojo> planes =new ArrayList<>();
        if (planesDocs != null) {
            for (Document planDoc: planesDocs) {
                planes.add(PlanPersistenciaMapper.toPojo(planDoc)
                );
            }
        }
        sucursal.setPlanes(planes);
        List<Document> amenidadesDocs =(List<Document>)doc.get("amenidadesSucursal");
        List<AmenidadPojo> amenidades = new ArrayList<>();
        if (amenidadesDocs != null) {
            for (Document amenidadDoc: amenidadesDocs) {
                amenidades.add(AmenidadPersistenciaMapper.toPojo(amenidadDoc));
            }
        }

        sucursal.setAmenidadesSucursal(
                amenidades
        );

        return sucursal;
    }
}
