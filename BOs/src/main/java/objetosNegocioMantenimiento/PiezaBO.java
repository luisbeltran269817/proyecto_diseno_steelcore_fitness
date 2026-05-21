/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioMantenimiento;

import DAOsMantenimiento.PiezaDAO;
import Excepciones.NegocioException;
import dominios_mantenimiento.PiezaPojo;
import dtosInventarioMantenimiento.PiezaDTO;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IPiezaBO;
import interfacesMantenimiento.IPiezaDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoBO.PiezaDTOMapper;

/**
 *
 * @author Tungs
 */
public class PiezaBO implements IPiezaBO {
    private static final Logger logger = Logger.getLogger(PiezaBO.class.getName());
    private final IPiezaDAO piezaDAO;

    public PiezaBO() {
        this.piezaDAO = new PiezaDAO();
    }
    /**
     * Método que obtiene una pieza de la BD
     * @param idPieza el id de la pieza a obtener
     * @return la pieza obtener
     * @throws NegocioException si ocurre un error
     */
    @Override
    public PiezaDTO obtenerPieza(String idPieza) throws NegocioException {
        try {
            if (idPieza == null || idPieza.isBlank()) {
                throw new NegocioException("El id de la pieza es inválido");
            }
            PiezaPojo pojo = piezaDAO.obtenerPieza(idPieza);
            return PiezaDTOMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al obtener pieza", ex);
            throw new NegocioException("Error al obtener pieza");
        }
    }

    /**
     * Método que actualiza el stock de una pieza
     * @param idPieza el id de la pieza
     * @param stock el stock actualizado
     * @throws NegocioException si ocurre un error
     */
    @Override
    public void actualizarStock(String idPieza, int stock) throws NegocioException {
        try {
            if (idPieza == null || idPieza.isBlank()) {
                throw new NegocioException("El id de la pieza es inválido");
            }
            piezaDAO.actualizarStock(idPieza, stock);
            logger.log(Level.INFO, "Stock actualizado correctamente");
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al actualizar stock", ex);
            throw new NegocioException("Error al actualizar stock");
        }
    }

    /**
     * Método que obtiene todas las piezas
     * @return una lista con las piezas
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<PiezaDTO> mostrarPiezas() throws NegocioException {
        try {
            List<PiezaPojo> pojos = piezaDAO.mostrarPiezas();
            return PiezaDTOMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al mostrar piezas", ex);
            throw new NegocioException("Error al mostrar piezas");
        }
    }
    /**
     * Método que verifica si hay stockSuficiente de una pieza
     * @param idPieza el id de la pieza
     * @param cantidad la cantidad a validar
     * @return verdadero si hay stock suficiente, falso en caso contrario
     * @throws NegocioException si ocurre un error 
     */
    @Override
    public boolean hayStockSuficiente(String idPieza, int cantidad) throws NegocioException {
        try {
            if (idPieza == null || idPieza.isBlank()) {
                throw new NegocioException("El id de la pieza es inválido");
            }
            return piezaDAO.hayStockSuficiente(idPieza, cantidad);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al validar stock", ex);
            throw new NegocioException("Error al validar stock");
        }
    }
}
