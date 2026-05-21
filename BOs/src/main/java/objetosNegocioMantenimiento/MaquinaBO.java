/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioMantenimiento;

import DAOsMantenimiento.MaquinaDAO;
import Excepciones.NegocioException;
import dominios_mantenimiento.BajaPojo;
import dominios_mantenimiento.MaquinaPojo;
import dtosInventarioMantenimiento.BajasInventarioDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IMaquinaBO;
import interfacesMantenimiento.IMaquinaDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoBO.BajaDTOMapper;
import mappersMantenimientoBO.MaquinaDTOMapper;

/**
 *
 * @author Tungs
 */
public class MaquinaBO implements IMaquinaBO {
    
    private static final Logger logger = Logger.getLogger(MaquinaBO.class.getName());
    private final IMaquinaDAO maquinaDAO;

    public MaquinaBO() {
        this.maquinaDAO = new MaquinaDAO();
    }
    
    /**
     * Método que registra una máquina en la BD
     * @param maquina la maquina a registrar
     * @throws NegocioException si ocurre un error
     */
    @Override
    public void registrarMaquina(MaquinaDTO maquina) throws NegocioException {
        try {
            if (maquina == null) {
                throw new NegocioException("La máquina no puede ser null");
            }
            MaquinaPojo pojo = MaquinaDTOMapper.toPojo(maquina);
            maquinaDAO.registrarMaquina(pojo);
            logger.log(Level.INFO, "Máquina registrada correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al registrar máquina", ex);
            throw new NegocioException("Error al registrar máquina");
        }
    }
    
    /**
     * Método que obtiene una máquina de la bd
     * @param idMaquina el id de la máquina a obtener
     * @return la maquina obtenida
     * @throws NegocioException si ocurre un error 
     */
    @Override
    public MaquinaDTO obtenerMaquina(String idMaquina) throws NegocioException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new NegocioException("El id de la máquina es inválido");
            }
            MaquinaPojo pojo = maquinaDAO.obtenerMaquina(idMaquina);
            return MaquinaDTOMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener máquina", ex);
            throw new NegocioException("Error al obtener máquina");
        }
    }

    /**
     * Método que obtiene todas las máquinas
     * @return una lista con todas las máquinas
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<MaquinaDTO> obtenerMaquinas() throws NegocioException {
        try {
            List<MaquinaPojo> pojos = maquinaDAO.obtenerMaquinas();

            return MaquinaDTOMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener máquinas", ex);
            throw new NegocioException("Error al obtener máquinas");
        }
    }

    /**
     * Metodo que actualiza una máquina en la BD
     * @param maquina la máquina a actualizar
     * @throws NegocioException 
     */
    @Override
    public void actualizarMaquina(MaquinaDTO maquina) throws NegocioException {
        try {
            if (maquina == null) {
                throw new NegocioException("La máquina no puede ser null");
            }

            MaquinaPojo pojo = MaquinaDTOMapper.toPojo(maquina);
            maquinaDAO.actualizarMaquina(pojo);

            logger.log(Level.INFO, "Máquina actualizada correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar máquina", ex);
            throw new NegocioException("Error al actualizar máquina");
        }
    }

    /**
     * método que cambia el estado de una máquina 
     * @param idMaquina el id de la maquina
     * @param estado el nuevo estado
     * @throws NegocioException si ocurre un error 
     */
    @Override
    public void cambiarEstado(String idMaquina, MaquinaDTO.EstadoMaquinaDTO estado) throws NegocioException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new NegocioException("El id de la máquina es inválido");
            }

            if (estado == null) {
                throw new NegocioException("El estado de la máquina no puede ser null");
            }

            MaquinaPojo.EstadoMaquina estadoPojo = MaquinaPojo.EstadoMaquina.valueOf(estado.name());
            maquinaDAO.cambiarEstado(idMaquina, estadoPojo);

            logger.log(Level.INFO, "Estado de máquina actualizado correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al cambiar estado de máquina", ex);
            throw new NegocioException("Error al cambiar estado de máquina");
        }
    }

    /**
     * Método que registra una baja de una máquina
     * @param idMaquina el id de la máquina a dar de baja
     * @param baja la baja
     * @throws NegocioException si ocurre un error
     */
    @Override
    public void registrarBaja(String idMaquina, BajasInventarioDTO baja) throws NegocioException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new NegocioException("El id de la máquina es inválido");
            }

            if (baja == null) {
                throw new NegocioException("La baja no puede ser null");
            }

            BajaPojo bajaPojo = BajaDTOMapper.toPojo(baja);
            maquinaDAO.registrarBaja(idMaquina, bajaPojo);

            logger.log(Level.INFO, "Baja de máquina registrada correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al registrar baja de máquina", ex);
            throw new NegocioException("Error al registrar baja de máquina");
        }
    }
    /**
     * Método que filtra máquinas por sucursal y por estado
     * @param idSucursal el id de la sucursal
     * @param estado el estado de la maquina
     * @return una lista con los filtros aplicados
     * @throws NegocioException si ocurre un error 
     */
    @Override
    public List<MaquinaDTO> filtrarMaquinas(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws NegocioException {
        try {
            MaquinaPojo.EstadoMaquina estadoPojo = null;
            if (estado != null) {
                estadoPojo = MaquinaPojo.EstadoMaquina.valueOf(estado.name());
            }
            List<MaquinaPojo> pojos = maquinaDAO.filtrarMaquinas(idSucursal, estadoPojo);
            return MaquinaDTOMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al filtrar máquinas", ex);
            throw new NegocioException("Error al filtrar máquinas");
        }
    }
}
