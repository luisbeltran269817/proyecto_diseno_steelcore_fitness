/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.EntrenadorDAO;
import Excepciones.NegocioException;
import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import excepciones.PersistenciaException;
import interfaces.IEntrenadorBO;
import interfaces.IEntrenadorDAO;
import java.util.List;
import java.util.logging.Logger;
import mappersBO.EntrenadorMapper;
import mappersBO.HorarioMapper;

/**
 * Objeto de negocio para Entrenadores.
 *
 * @author Tungs y julian izaguirre
 */
public class EntrenadorBO implements IEntrenadorBO {

    private final IEntrenadorDAO entrenadorDAO;
    private static final Logger logger = Logger.getLogger(EntrenadorBO.class.getName());

    public EntrenadorBO() {
        this.entrenadorDAO = new EntrenadorDAO();
    }

    @Override
    public EntrenadorDTO buscarPorId(String idEntrenador) throws NegocioException {
        try {
            EntrenadorPojo pojo = entrenadorDAO.buscarPorId(idEntrenador);
            if (pojo == null) {
                logger.warning("No existe el entrenador: " + idEntrenador);
                throw new NegocioException("El entrenador no existe");
            }
            logger.info("Entrenador encontrado: " + idEntrenador);
            return EntrenadorMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.severe("Error al buscar entrenador: " + ex.getMessage());
            throw new NegocioException("No fue posible buscar el entrenador", ex);
        }
    }

    @Override
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal) throws NegocioException {
        try {
            List<EntrenadorPojo> pojos = entrenadorDAO.obtenerPorSucursal(idSucursal);
            logger.info("Entrenadores obtenidos para sucursal: " + idSucursal);
            return EntrenadorMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.severe("Error al obtener entrenadores: " + ex.getMessage());
            throw new NegocioException("No fue posible obtener los entrenadores", ex);
        }
    }

    @Override
    public List<HorarioDTO> obtenerHorariosEntrenador(String idEntrenador) throws NegocioException {
        try {
            List<HorarioPojo> horarios = entrenadorDAO.obtenerHorariosEntrenador(idEntrenador);
            logger.info("Horarios obtenidos para entrenador: " + idEntrenador);
            return HorarioMapper.toDTOList(horarios);
        } catch (PersistenciaException ex) {
            logger.severe("Error al obtener horarios: " + ex.getMessage());
            throw new NegocioException("No fue posible obtener los horarios", ex);
        }
    }

    /**
     * Marca un horario específico del entrenador como disponible o no disponible.
     *
     * Se llama desde ControlAcceso cuando un socio solicita un entrenador:
     * el horario elegido se marca disponible=false para evitar doble asignación.
     *
     * Validaciones de negocio:
     *  - El entrenador debe existir.
     *  - Si se intenta marcar como ocupado (false) un horario ya ocupado,
     *    lanza NegocioException — otro socio se adelantó.
     *
     * @param idEntrenador id del entrenador
     * @param idHorario    id del sub-documento horario a modificar
     * @param disponible   nuevo estado del horario
     * @throws NegocioException si el entrenador no existe, el horario no existe,
     *                          o ya está ocupado
     */
    @Override
    public void actualizarDisponibilidadHorario(String idEntrenador,
                                                 String idHorario,
                                                 boolean disponible)
            throws NegocioException {

        if (idEntrenador == null || idEntrenador.isBlank()) {
            throw new NegocioException("El id del entrenador es requerido.");
        }
        if (idHorario == null || idHorario.isBlank()) {
            throw new NegocioException("El id del horario es requerido.");
        }

        try {
            // Validar que el entrenador existe y el horario tiene el estado correcto
            EntrenadorPojo entrenador = entrenadorDAO.buscarPorId(idEntrenador);
            if (entrenador == null) {
                throw new NegocioException("El entrenador seleccionado no existe en el sistema.");
            }

            if (!disponible) {
                // Se quiere marcar como ocupado: verificar que siga disponible
                boolean encontrado = false;
                if (entrenador.getHorarios() != null) {
                    for (HorarioPojo h : entrenador.getHorarios()) {
                        if (idHorario.equals(h.getIdHorario())) {
                            encontrado = true;
                            if (!h.isDisponible()) {
                                throw new NegocioException(
                                        "Lo sentimos, el entrenador ya fue asignado "
                                        + "a otro socio en este horario.");
                            }
                            break;
                        }
                    }
                }
                if (!encontrado) {
                    throw new NegocioException(
                            "El horario seleccionado no existe para este entrenador.");
                }
            }

            entrenadorDAO.actualizarDisponibilidadHorario(idEntrenador, idHorario, disponible);
            logger.info("Disponibilidad actualizada: entrenador=" + idEntrenador
                    + " horario=" + idHorario + " disponible=" + disponible);

        } catch (PersistenciaException ex) {
            logger.severe("Error al actualizar disponibilidad: " + ex.getMessage());
            throw new NegocioException("Error al actualizar la disponibilidad del entrenador.", ex);
        }
    }
}