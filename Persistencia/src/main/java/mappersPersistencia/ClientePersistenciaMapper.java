/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.ClientePojo;
import java.time.LocalDate;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class ClientePersistenciaMapper {
    public static Document toDocument(ClientePojo pojo) {

        if (pojo == null) {
            return null;
        }
        Document doc = new Document();
        doc.append("_id", pojo.getIdCliente());
        if (pojo.getUsuario() != null) {
            doc.append("usuario",
                    UsuarioPersistenciaMapper.toDocument(
                            pojo.getUsuario()));
        }
        doc.append("apellidoPaterno", pojo.getApellidoPaterno());
        doc.append("apellidoMaterno", pojo.getApellidoMaterno());
        if (pojo.getFechaNacimiento() != null) {
            doc.append("fechaNacimiento",
                    pojo.getFechaNacimiento().toString());
        }
        doc.append("curp", pojo.getCurp());
        if (pojo.getMembresiaActiva() != null) {
            doc.append("membresiaActiva",
                    MembresiaActivaPersistenciaMapper.toDocument(
                            pojo.getMembresiaActiva()));
        }
        if (pojo.getCitaBienvenida() != null) {
            doc.append("citaBienvenida",
                    CitaPersistenciaMapper.toDocument(
                            pojo.getCitaBienvenida()));
        }
        return doc;
    }

    public static ClientePojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }
        ClientePojo pojo = new ClientePojo();
        pojo.setIdCliente(doc.getString("_id"));
        Document usuarioDoc = (Document) doc.get("usuario");
        if (usuarioDoc != null) {
            pojo.setUsuario(
                    UsuarioPersistenciaMapper.toPojo(usuarioDoc));
        }
        pojo.setApellidoPaterno(doc.getString("apellidoPaterno"));
        pojo.setApellidoMaterno(doc.getString("apellidoMaterno"));
        String fechaNacimiento = doc.getString("fechaNacimiento");
        if (fechaNacimiento != null) {
            pojo.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        }
        pojo.setCurp(doc.getString("curp"));
        Document membresiaDoc = (Document) doc.get("membresiaActiva");
        if (membresiaDoc != null) {
            pojo.setMembresiaActiva(MembresiaActivaPersistenciaMapper.toPojo(membresiaDoc));
        }
        Document citaDoc = (Document) doc.get("citaBienvenida");

        if (citaDoc != null) {
            pojo.setCitaBienvenida(
                    CitaPersistenciaMapper.toPojo(citaDoc));
        }
        return pojo;
    }
}
