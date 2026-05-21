/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosAcceso;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import conexion.MongoConexion;
import dominioAcceso.VisitaAccesoPojo;
import excepciones.PersistenciaException;
import interfacesAcceso.IVisitaAccesoDAO;
import java.util.logging.Logger;
import mappersAcceso.VisitaAccesoPersistenciaMapper;
import org.bson.Document;

/**
 * DAO encargado de gestionar las visitas en el control de acceso
 *
 * @author julian izaguirre
 */
public class VisitaAccesoDAO implements IVisitaAccesoDAO {

    private static final Logger LOG = Logger.getLogger(VisitaAccesoDAO.class.getName());
    private final MongoCollection<Document> coleccion;

    public VisitaAccesoDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("visitas");
    }

    /** 
     * Registra una nueva visita en la base de datos
     * 
     * @param visita Objeto con los datos que se van a guardar
     * @return La misma visita despues de insertarla
     * @throws PersistenciaException Si mandan null o falla la conexion
     */
    @Override
    public VisitaAccesoPojo guardar(VisitaAccesoPojo visita) throws PersistenciaException {
        try {
            if (visita == null) {
                throw new PersistenciaException("La visita no puede ser null");
            }
            Document doc = VisitaAccesoPersistenciaMapper.toDocument(visita);
            coleccion.insertOne(doc);
            LOG.info("VisitaAcceso guardada: id=" + visita.getIdVisita());
            return visita;
        } catch (MongoException ex) {
            LOG.severe("Error al guardar visita de acceso: " + ex.getMessage());
            throw new PersistenciaException("Error al registrar la visita de acceso");
        }
    }

    /** 
     * Actualiza el tipo de servicio que pidio el socio al entrar
     * 
     * @param idVisita Identificador de la visita a modificar
     * @param tipoServicio El nombre del servicio nuevo
     * @param idRecursoAsignado Identificador del recurso asignado
     * @throws PersistenciaException Si no se puede actualizar en Mongo
     */
    @Override
    public void actualizarServicio(String idVisita, String tipoServicio,
                                    String idRecursoAsignado)
            throws PersistenciaException {
        try {
            coleccion.updateOne(
                    Filters.eq("_id", idVisita),
                    Updates.combine(
                            Updates.set("tipoServicio",      tipoServicio),
                            Updates.set("idRecursoAsignado", idRecursoAsignado)
                    )
            );
            LOG.info("Servicio actualizado en visita " + idVisita
                    + " tipo=" + tipoServicio
                    + " recurso=" + idRecursoAsignado);
        } catch (MongoException ex) {
            LOG.severe("Error al actualizar servicio en visita: " + ex.getMessage());
            throw new PersistenciaException("Error al actualizar el servicio de la visita");
        }
    }
}