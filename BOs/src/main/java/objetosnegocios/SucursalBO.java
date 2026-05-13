/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.SucursalDAO;
import Excepciones.NegocioException;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import excepciones.PersistenciaException;
import interfaces.ISucursalBO;
import interfaces.ISucursalDAO;
import java.util.List;
import java.util.logging.Logger;
import mappersBO.PlanMapper;
import mappersBO.SucursalMapper;

/**
 *
 * @author julian izaguirre
 */
public class SucursalBO implements ISucursalBO{
    private final ISucursalDAO sucursalDAO;
    private static final Logger logger =Logger.getLogger(SucursalBO.class.getName());
    public SucursalBO() {
        this.sucursalDAO = new SucursalDAO();
    }

    @Override
    public List<SucursalDTO>obtenerSucursales()throws NegocioException {
        try {
            List<SucursalPojo> pojos= sucursalDAO.obtenerSucursales();
            logger.info("Sucursales obtenidas");
            return SucursalMapper.toDTOList(pojos);
        } catch (PersistenciaException ex) {
            logger.severe("Error al obtener sucursales");
            throw new NegocioException("No fue posible obtener sucursales");
        }
    }
    @Override
    public SucursalDTO buscarPorId(String idSucursal)throws NegocioException {
        try {
            SucursalPojo pojo = sucursalDAO.buscarPorId(idSucursal);
            if (pojo == null) {
                throw new NegocioException("La sucursal no existe");
            }
            logger.info("Sucursal encontrada");
            return SucursalMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.severe("Error al buscar sucursal");
            throw new NegocioException("Error al buscar sucursal");
        }
    }
    @Override
    public PlanDTO buscarPlanPorId(String idPlan)throws NegocioException {
        try {
            PlanPojo pojo= sucursalDAO.buscarPlanPorId(idPlan);
            if (pojo == null) {
                throw new NegocioException("El plan no existe");
            }
            logger.info( "Plan encontrado");
            return PlanMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.severe("Error al buscar plan");
            throw new NegocioException("Error al buscar plan"        );
        }
    }
}
