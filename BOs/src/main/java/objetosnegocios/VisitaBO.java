/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.VisitaDAO;
import Excepciones.NegocioException;
import dominios.VisitaPojo;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
import excepciones.PersistenciaException;
import interfaces.IVisitaBO;
import interfaces.IVisitaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersBO.VisitaMapper;

/**
 *
 * @author Gael Galaviz
 */
public class VisitaBO implements IVisitaBO{
    
    private final IVisitaDAO visitaDAO;
    private final SucursalBO sucursalBO;
    private static final Logger logger = Logger.getLogger(VisitaBO.class.getName());
    
    public VisitaBO() {
        this.visitaDAO = new VisitaDAO();
        this.sucursalBO = new SucursalBO();
    }
    /**
     * Método que registra una visita
     * @param idCliente el id del Cliente
     * @param idSucursal el id de la Sucursal
     * @param visita la visita a registrar
     * @throws NegocioException ai ocurre un error
     */
    @Override
    public VisitaDTO guardar(String idCliente, String idSucursal, VisitaDTO visita) throws NegocioException {
        try {
            if (visita == null) {
                throw new NegocioException("La visita no puede ser null");
            }
            
            VisitaPojo pojo =VisitaMapper.toPojo(idCliente,idSucursal, visita);
            visitaDAO.guardar(idCliente, pojo);
            logger.log(Level.INFO, "Visita guardada correctamente");
            return visita;
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al guardar visita", e);
            throw new NegocioException("Error al guardar visita");
        }
    }
    /**
     * Método que obtiene las visitas de un cliente
     * @param idCliente el id del cliente
     * @return lista de visitias
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<VisitaDTO> obtenerPorCliente(String idCliente)throws NegocioException {
        try {
        
            List<VisitaPojo> pojos =visitaDAO.obtenerPorCliente( idCliente);

            List<VisitaDTO> visitas = new ArrayList<>();

            for (VisitaPojo pojo : pojos) {
                SucursalDTO sucursal =sucursalBO.buscarPorId(pojo.getIdSucursal());
                VisitaDTO dto =VisitaMapper.toDTO(pojo, sucursal);
                visitas.add(dto);
            }
            logger.log(Level.INFO, "Se obtubieron las visitas yupiiii");
            return visitas;
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener visitas", e);
            throw new NegocioException( "Error al obtener visitas");
        }
    }
}
