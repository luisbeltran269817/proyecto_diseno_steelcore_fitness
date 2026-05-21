/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersMantenimientoPersistencia;

import dominios_mantenimiento.AdminMantenimientoPojo;
import mappersPersistencia.UsuarioPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoPersistenciaMapper {
    
    public static Document toDocument(AdminMantenimientoPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();

        doc.append("_id", pojo.getIdAdminMantenimiento());

        if (pojo.getUsuario() != null) {
            doc.append("usuario", UsuarioPersistenciaMapper.toDocument(pojo.getUsuario()));
        }
        return doc;
    }

    public static AdminMantenimientoPojo toPojo(Document doc) {

        if (doc == null) {
            return null;
        }
        AdminMantenimientoPojo pojo = new AdminMantenimientoPojo();
        pojo.setIdAdminMantenimiento(doc.getString("_id"));
        Document usuarioDoc = (Document) doc.get("usuario");
        if (usuarioDoc != null) {
            pojo.setUsuario(UsuarioPersistenciaMapper.toPojo(usuarioDoc));
        }
        return pojo;
    }
}
