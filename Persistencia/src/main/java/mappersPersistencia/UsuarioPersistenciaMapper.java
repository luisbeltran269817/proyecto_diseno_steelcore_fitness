/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.UsuarioPojo;
import dominios.UsuarioPojo.RolUsuarioPojo;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class UsuarioPersistenciaMapper {
    
    public static Document toDocument(UsuarioPojo pojo) {
        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("correo", pojo.getCorreo());
        doc.append("nombre", pojo.getNombre());
        doc.append("contraseña", pojo.getContraseña());
        if (pojo.getRol() != null) {
            doc.append("rol", pojo.getRol().name());
        }
        return doc;
    }

    public static UsuarioPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        UsuarioPojo pojo = new UsuarioPojo();

        pojo.setCorreo(doc.getString("correo"));
        pojo.setNombre(doc.getString("nombre"));
        pojo.setContraseña(doc.getString("contraseña"));
        String rol = doc.getString("rol");
        if (rol != null) {
            pojo.setRol(RolUsuarioPojo.valueOf(rol));
        }
        return pojo;
    }
}
