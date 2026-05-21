/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOsMantenimiento;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominios_mantenimiento.HorarioTecnicoPojo;
import dominios_mantenimiento.TecnicoPojo;
import excepciones.PersistenciaException;
import interfacesMantenimiento.ITecnicoDAO;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoPersistencia.TecnicoPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class TecnicoDAO implements ITecnicoDAO{
    private static final Logger logger = Logger.getLogger(TecnicoDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public TecnicoDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("tecnicos");
    }
    /**
     * Método que obtiene un Técnico de la BD
     * @param idTecnico el id dedl tecnico a obtener
     * @return un técnico
     * @throws PersistenciaException  si ocurre un error
     */
    @Override
    public TecnicoPojo obtenerTecnico(String idTecnico) throws PersistenciaException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new PersistenciaException("El id del técnico es inválido");
            }
            Document doc = coleccion.find(Filters.eq("_id", idTecnico)).first();
            if (doc == null) {
                logger.log(Level.WARNING, "No se encontró el técnico con id: ", idTecnico);
                return null;
            }
            logger.log(Level.INFO, "Técnico obtenido correctamente: ", idTecnico);
            return TecnicoPersistenciaMapper.toPojo(doc);
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener técnico", ex);
            throw new PersistenciaException("Error al obtener técnico");
        }
    }

    /**
     * Método que obtiene todos los técnicos de la BD
     * @return la lista de técnicos
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public List<TecnicoPojo> obtenerTecnicos() throws PersistenciaException {
        try {
            List<TecnicoPojo> tecnicos = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find();

            for (Document doc : docs) {
                tecnicos.add(TecnicoPersistenciaMapper.toPojo(doc));
            }
            logger.log(Level.INFO, "Técnicos obtenidos correctamente. Total: ", tecnicos.size());
            return tecnicos;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al obtener técnicos", ex);
            throw new PersistenciaException("Error al obtener técnicos");
        }
    }
    
    /**
     * Método que revisa si el técnico tiene un horario disponible a cierto día y hora
     * @param idTecnico el id del técnico
     * @param nombreDia el nombre del día
     * @param horaInicio la hora de inicio
     * @param horaFin la hora del fin
     * @return verdadero si tiene, falso en caso contrario
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public boolean tieneHorarioDisponible(String idTecnico, String nombreDia, LocalTime horaInicio, LocalTime horaFin) throws PersistenciaException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new PersistenciaException("El id del técnico es inválido");
            }
            if (nombreDia == null || nombreDia.isBlank()) {
                throw new PersistenciaException("El día del horario es inválido");
            }
            if (horaInicio == null || horaFin == null) {
                throw new PersistenciaException("Las horas del horario no pueden ser null");
            }

            TecnicoPojo tecnico = obtenerTecnico(idTecnico);
            if (tecnico == null || tecnico.getHorarios() == null) {
                return false;
            }
            for (HorarioTecnicoPojo horario : tecnico.getHorarios()) {
                if (horario.isDisponible() && horario.getNombreDia().equalsIgnoreCase(nombreDia) && !horaInicio.isBefore(horario.getHoraInicio()) && !horaFin.isAfter(horario.getHoraFin())) {
                    logger.log(Level.INFO, "El técnico tiene horario disponible");
                    return true;
                }
            }
            logger.log(Level.INFO, "El técnico no tiene horario disponible");
            return false;
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al validar horario disponible", ex);
            throw new PersistenciaException("Error al validar horario disponible");
        }
    }
    
    /**
     * Método que actualiza el estado del horario de un técnico
     * @param idTecnico el id del técnico a actualizar
     * @param idHorario el id del horario a actualizar
     * @param disponible el nuevo estado
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void actualizarEstadoHorario(String idTecnico, String idHorario, boolean disponible) throws PersistenciaException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new PersistenciaException("El id del técnico es inválido");
            }
            if (idHorario == null || idHorario.isBlank()) {
                throw new PersistenciaException("El id del horario es inválido");
            }
            UpdateResult resultado = coleccion.updateOne(Filters.and(Filters.eq("_id", idTecnico), Filters.eq("horarios.idHorario", idHorario)), Updates.set("horarios.$.disponible", disponible));

            if (resultado.getMatchedCount() == 0) {
                logger.log(Level.WARNING, "No se encontró el técnico u horario para actualizar");
                throw new PersistenciaException("No se encontró el técnico u horario para actualizar");
            }
            logger.log(Level.INFO, "Estado del horario actualizado correctamente");
        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al actualizar estado del horario", ex);
            throw new PersistenciaException("Error al actualizar estado del horario");
        }
    }
}
