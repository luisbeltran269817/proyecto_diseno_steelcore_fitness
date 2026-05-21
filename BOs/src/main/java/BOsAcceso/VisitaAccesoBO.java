/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOsAcceso;

import Excepciones.NegocioException;
import daosAcceso.VisitaAccesoDAO;
import dominioAcceso.VisitaAccesoPojo;
import dtosControlDeAcceso.VisitaDTO;
import excepciones.PersistenciaException;
import interfaces.IVisitaAccesoBO;
import interfacesAcceso.IVisitaAccesoDAO;
import java.util.logging.Logger;
import mappersBO.VisitaAccesoMapper;
 
/**
 * Objeto de negocio para el registro de visitas del caso individual de acceso.
 *
 * Responsabilidades:
 *  - Registrar la visita inicial con tipoServicio="AREA_GENERAL" al escanear QR.
 *  - Actualizar el tipo de servicio cuando el socio elige (clase o entrenador).
 *  - Propagar errores de persistencia como NegocioException legibles para el control.
 *
 * @author julian izaguirre
 */
public class VisitaAccesoBO implements IVisitaAccesoBO {
 
    private static final Logger LOG = Logger.getLogger(VisitaAccesoBO.class.getName());
    private final IVisitaAccesoDAO visitaDAO;
 
    public VisitaAccesoBO() {
        this.visitaDAO = new VisitaAccesoDAO();
    }
 
    /** {@inheritDoc} */
    @Override
    public VisitaDTO registrarEntrada(String idCliente, String idSucursal)
            throws NegocioException {
 
        if (idCliente == null || idCliente.isBlank()) {
            throw new NegocioException("El id del socio es requerido para registrar la entrada.");
        }
        if (idSucursal == null || idSucursal.isBlank()) {
            throw new NegocioException("El id de sucursal es requerido para registrar la entrada.");
        }
 
        try {
            VisitaAccesoPojo pojo = VisitaAccesoMapper.toNuevoPojo(idCliente, idSucursal);
            VisitaAccesoPojo guardada = visitaDAO.guardar(pojo);
            LOG.info("Entrada registrada: cliente=" + idCliente
                    + " sucursal=" + idSucursal
                    + " visita=" + guardada.getIdVisita());
            return VisitaAccesoMapper.toDTO(guardada);
 
        } catch (PersistenciaException ex) {
            LOG.severe("Error al registrar entrada: " + ex.getMessage());
            throw new NegocioException("No se pudo registrar la entrada del socio.", ex);
        }
    }
 
    /** {@inheritDoc} */
    @Override
    public void actualizarServicio(String idVisita, String tipoServicio,
                                    String idRecursoAsignado)
            throws NegocioException {
 
        if (idVisita == null || idVisita.isBlank()) {
            throw new NegocioException("El id de visita es requerido para actualizar el servicio.");
        }
 
        try {
            visitaDAO.actualizarServicio(idVisita, tipoServicio, idRecursoAsignado);
            LOG.info("Servicio actualizado: visita=" + idVisita
                    + " tipo=" + tipoServicio
                    + " recurso=" + idRecursoAsignado);
        } catch (PersistenciaException ex) {
            LOG.severe("Error al actualizar servicio: " + ex.getMessage());
            throw new NegocioException("No se pudo actualizar el servicio de la visita.", ex);
        }
    }
}
