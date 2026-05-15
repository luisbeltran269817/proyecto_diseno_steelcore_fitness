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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappersBO.EntrenadorMapper;
import mappersBO.HorarioMapper;

/**
 *
 * @author Tungs
 */
public class EntrenadorBO implements IEntrenadorBO {
    private final IEntrenadorDAO entrenadorDAO;
    private static final Logger logger =Logger.getLogger(EntrenadorBO.class.getName());
    
    public EntrenadorBO() {
        this.entrenadorDAO = new EntrenadorDAO();
    }
    
    @Override
    public EntrenadorDTO buscarPorId(String idEntrenador)throws NegocioException {
        try {
            EntrenadorPojo pojo =entrenadorDAO.buscarPorId(idEntrenador);
            if (pojo == null) {
                logger.warning("No existe el entrenador");
                throw new NegocioException("El entrenador no existe");
            }
            logger.info("Entrenador encontrado");
            return EntrenadorMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.severe("Error al buscar entrenador");
            throw new NegocioException("No fue posible buscar el entrenador");
        }
    }
    
    /**
     * METODO ADAPTADO PARA EL CASO BASE
     * recupera la lista de entrenadorPojo que le da el dao, y usa el mapper de negocio para convertirla en lista de entrenadordto
     * @param idSucursal
     * @return 
     */
    @Override
    public List<EntrenadorDTO>obtenerPorSucursal(String idSucursal)throws NegocioException {
        try {
            List<EntrenadorPojo> pojos =entrenadorDAO.obtenerPorSucursal(idSucursal);
            logger.info("Entrenadores obtenidos correctamente");
            return EntrenadorMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.severe("Error al obtener entrenadores");
            throw new NegocioException("No fue posible obtener los entrenadores");
        }
    }
    
    /**
     * Método que obtiene los horarios de un entrenador
     * @param idEntrenador el id del entrenador a buscar
     * @return una lista de horarioDTO
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<HorarioDTO>obtenerHorariosEntrenador(String idEntrenador)throws NegocioException {
        try {
            List<HorarioPojo> horarios =entrenadorDAO.obtenerHorariosEntrenador(idEntrenador);
            logger.info("Horarios obtenidos correctamente");
            return HorarioMapper.toDTOList(horarios);
        } catch (PersistenciaException ex) {
            logger.severe("Error al obtener horarios");
            throw new NegocioException("No fue posible obtener los horarios");
        }
    }
}
