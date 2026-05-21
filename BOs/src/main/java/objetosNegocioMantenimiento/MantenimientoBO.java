/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioMantenimiento;

import DAOsMantenimiento.MantenimientoDAO;
import Excepciones.NegocioException;
import dominios_mantenimiento.MantenimientoPojo;
import dtosInventarioMantenimiento.MantenimientoDTO;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IMantenimientoBO;
import interfacesMantenimiento.IMantenimientoDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoBO.MantenimientoDTOMapper;

/**
 *
 * @author Tungs
 */
public class MantenimientoBO implements IMantenimientoBO {
     private static final Logger logger = Logger.getLogger(MantenimientoBO.class.getName());
    private final IMantenimientoDAO mantenimientoDAO;

    public MantenimientoBO() {
        this.mantenimientoDAO = new MantenimientoDAO();
    }
    
    /**
     * Método que guarda una solicitud de mantenimiento en la BD
     * @param mantenimiento el mantenimiento a registrar
     * @throws NegocioException si ocurre un error
     */
    @Override
    public void registrarMantenimiento(MantenimientoDTO mantenimiento) throws NegocioException {
        try {
            if (mantenimiento == null) {
                throw new NegocioException("El mantenimiento no puede ser null");
            }
            MantenimientoPojo pojo = MantenimientoDTOMapper.toPojo(mantenimiento);
            mantenimientoDAO.registrarMantenimiento(pojo);
            logger.log(Level.INFO, "Mantenimiento registrado correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al registrar mantenimiento", ex);
            throw new NegocioException("Error al registrar mantenimiento");
        }
    }
    /**
     * Método que obtiene un mantenimiento
     * @param idMantenimiento el id del mantenimiento
     * @return el mantenimiento encontrado
     * @throws NegocioException si ocurre un error
     */
    @Override
    public MantenimientoDTO obtenerMantenimiento(String idMantenimiento) throws NegocioException {
        try {
            if (idMantenimiento == null || idMantenimiento.isBlank()) {
                throw new NegocioException("El id del mantenimiento es inválido");
            }
            MantenimientoPojo pojo = mantenimientoDAO.obtenerMantenimiento(idMantenimiento);
            return MantenimientoDTOMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener mantenimiento", ex);
            throw new NegocioException("Error al obtener mantenimiento");
        }
    }
    /**
     * Método que obtiene todos los mantenimientos
     * @return la lista de mantenimientos
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<MantenimientoDTO> obtenerMantenimientos() throws NegocioException {
        try {
            List<MantenimientoPojo> pojos = mantenimientoDAO.obtenerMantenimientos();
            return MantenimientoDTOMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener mantenimientos", ex);
            throw new NegocioException("Error al obtener mantenimientos");
        }
    }

    /**
     * Método que le pregunta a la BD si una máquina tiene un mantenimiento activo
     * @param idMaquina el id de la máquina
     * @return verdadero si tiene mantenimiento activo, falso en caso contrario
     * @throws NegocioException si ocurre un error
     */
    @Override
    public boolean tieneMantenimientoActivo(String idMaquina) throws NegocioException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new NegocioException("El id de la máquina es inválido");
            }
            return mantenimientoDAO.tieneMantenimientoActivo(idMaquina);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al validar mantenimiento activo", ex);
            throw new NegocioException("Error al validar mantenimiento activo");
        }
    }
}
