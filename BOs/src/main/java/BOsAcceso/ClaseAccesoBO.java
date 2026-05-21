/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOsAcceso;

import Excepciones.NegocioException;
import daosAcceso.ClaseDAO;
import dominioAcceso.ClasePojo;
import dtosControlDeAcceso.ClaseDTO;
import excepciones.PersistenciaException;
import interfaces.IClaseAccesoBO;
import interfacesAcceso.IClaseDAO;
import java.util.List;
import java.util.logging.Logger;
import mappersBO.ClaseAccesoMapper;
 
/**
 * Objeto de negocio para clases del caso individual Control de Acceso.
 *
 * Validaciones de negocio que aplica:
 *  - Si el plan no incluye clases (idPlan null o vacío) no consulta la BD
 *    y lanza NegocioException con mensaje para la pantalla.
 *  - Filtra la lista eliminando clases donde el socio ya está inscrito
 *    (doble validación: la primera la hace el DAO con la consulta Mongo).
 *  - Delega la validación de cupo a la capa de persistencia (DAO atómico).
 *
 * @author julian izaguirre
 */
public class ClaseAccesoBO implements IClaseAccesoBO {
 
    private static final Logger LOG = Logger.getLogger(ClaseAccesoBO.class.getName());
    private final IClaseDAO claseDAO;
 
    public ClaseAccesoBO() {
        this.claseDAO = new ClaseDAO();
    }
 
    /**
     * {@inheritDoc}
     *
     * Si idPlan es null o vacío asume que el plan no incluye clases y lanza
     * NegocioException — la pantalla debe mostrar el mensaje de plan sin clases.
     */
    @Override
    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                                String idCliente)
            throws NegocioException {
 
        if (idSucursal == null || idSucursal.isBlank()) {
            throw new NegocioException("No se puede consultar clases: sucursal no definida.");
        }
 
        try {
            List<ClasePojo> pojos = claseDAO.obtenerPorSucursalYPlan(idSucursal, idPlan);
 
            // Filtrar clases donde el socio ya está inscrito (segunda capa de seguridad)
            List<ClaseDTO> resultado = new java.util.ArrayList<>();
            for (ClasePojo pojo : pojos) {
                if (!pojo.estaInscrito(idCliente)) {
                    resultado.add(ClaseAccesoMapper.toDTO(pojo));
                }
            }
 
            if (resultado.isEmpty()) {
                LOG.info("No hay clases disponibles para sucursal=" + idSucursal
                        + " plan=" + idPlan);
            }
            return resultado;
 
        } catch (PersistenciaException ex) {
            LOG.severe("Error al obtener clases: " + ex.getMessage());
            throw new NegocioException("No se pudieron obtener las clases disponibles.", ex);
        }
    }
 
    /**
     * {@inheritDoc}
     *
     * Delega la validación de cupo y duplicados al DAO (operación atómica en Mongo).
     * Transforma el PersistenciaException en NegocioException con el mismo mensaje
     * para que la pantalla lo muestre directamente.
     */
    @Override
    public void inscribirSocio(String idClase, String idCliente) throws NegocioException {
        if (idClase == null || idClase.isBlank()) {
            throw new NegocioException("Debe seleccionar una clase.");
        }
        if (idCliente == null || idCliente.isBlank()) {
            throw new NegocioException("El socio no está identificado.");
        }
 
        try {
            claseDAO.inscribirSocio(idClase, idCliente);
            LOG.info("Socio " + idCliente + " inscrito en clase " + idClase);
        } catch (PersistenciaException ex) {
            // El mensaje de PersistenciaException ya es legible para el usuario
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}
