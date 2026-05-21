/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioMantenimiento;

import DAOsMantenimiento.TecnicoDAO;
import Excepciones.NegocioException;
import dominios_mantenimiento.TecnicoPojo;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.PersistenciaException;
import interfacesMantenimiento.ITecnicoBO;
import interfacesMantenimiento.ITecnicoDAO;
import java.time.LocalTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoBO.TecnicoDTOMapper;

/**
 *
 * @author Tungs
 */
public class TecnicoBO implements ITecnicoBO {
    private static final Logger logger = Logger.getLogger(TecnicoBO.class.getName());
    private final ITecnicoDAO tecnicoDAO;

    public TecnicoBO() {
        this.tecnicoDAO = new TecnicoDAO();
    }
    /**
     * Método que obtiene un técnico de la BD
     * @param idTecnico el id del técnico
     * @return el técnico obtenido
     * @throws NegocioException si ocurre un error
     */
    @Override
    public TecnicoDTO obtenerTecnico(String idTecnico) throws NegocioException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new NegocioException("El id del técnico es inválido");
            }
            TecnicoPojo pojo = tecnicoDAO.obtenerTecnico(idTecnico);
            return TecnicoDTOMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener técnico", ex);
            throw new NegocioException("Error al obtener técnico");
        }
    }

    /**
     * Método que ovtiene todos los técnicos
     * @return una lista con los técnicos
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<TecnicoDTO> obtenerTecnicos() throws NegocioException {
        try {
            List<TecnicoPojo> pojos = tecnicoDAO.obtenerTecnicos();
            return TecnicoDTOMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener técnicos", ex);
            throw new NegocioException("Error al obtener técnicos");
        }
    }

    /**
     * Método que valida si un técnico tiene un horario disponible
     * @param idTecnico el id del técnico
     * @param nombreDia el nombre del día del horario
     * @param horaInicio la hora de inicio
     * @param horaFin la hora final
     * @return verdadero si tiene horario disponible, falso en caso contrario
     * @throws NegocioException si ocurre un error
     */
    @Override
    public boolean tieneHorarioDisponible(String idTecnico, String nombreDia, LocalTime horaInicio, LocalTime horaFin) throws NegocioException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new NegocioException("El id del técnico es inválido");
            }
            if (nombreDia == null || nombreDia.isBlank()) {
                throw new NegocioException("El día es inválido");
            }
            if (horaInicio == null || horaFin == null) {
                throw new NegocioException("Las horas no pueden ser null");
            }
            return tecnicoDAO.tieneHorarioDisponible(idTecnico, nombreDia, horaInicio, horaFin);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al validar horario del técnico", ex);
            throw new NegocioException("Error al validar horario del técnico");
        }
    }

    /**
     * Método que actualiza el estado de un horario
     * @param idTecnico el id del técnico
     * @param idHorario el id del horario
     * @param disponible el estado del horario del técnico
     * @throws NegocioException 
     */
    @Override
    public void actualizarEstadoHorario(String idTecnico, String idHorario, boolean disponible) throws NegocioException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new NegocioException("El id del técnico es inválido");
            }
            if (idHorario == null || idHorario.isBlank()) {
                throw new NegocioException("El id del horario es inválido");
            }
            tecnicoDAO.actualizarEstadoHorario(idTecnico, idHorario, disponible);
            logger.log(Level.INFO, "Estado del horario actualizado correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar estado del horario", ex);
            throw new NegocioException("Error al actualizar estado del horario");
        }
    }

}
