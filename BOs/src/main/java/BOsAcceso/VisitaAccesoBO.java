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
 * Maneja la logica para registrar las visitas de los socios
 *
 * @author julian izaguirre
 */
public class VisitaAccesoBO implements IVisitaAccesoBO {

    private static final Logger LOG = Logger.getLogger(VisitaAccesoBO.class.getName());
    private final IVisitaAccesoDAO visitaDAO;

    public VisitaAccesoBO() {
        this.visitaDAO = new VisitaAccesoDAO();
    }

    /**
     * Crea la visita inicial cuando el socio escanea su codigo
     *
     * @param idCliente Identificador del socio
     * @param idSucursal Sucursal donde esta entrando
     * @return Los datos de la visita recien creada
     * @throws NegocioException Si faltan datos o falla al guardar
     */
    @Override
    public VisitaDTO registrarEntrada(String idCliente, String idSucursal)
            throws NegocioException {

        if (idCliente == null || idCliente.isBlank()) {
            throw new NegocioException("El id del socio es requerido para registrar la entrada");
        }
        if (idSucursal == null || idSucursal.isBlank()) {
            throw new NegocioException("El id de sucursal es requerido para registrar la entrada");
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
            throw new NegocioException("No se pudo registrar la entrada del socio", ex);
        }
    }

    /**
     * Guarda si el socio pidio entrenador clase o area general
     *
     * @param idVisita Visita que se va a actualizar
     * @param tipoServicio Categoria del servicio
     * @param idRecursoAsignado Identificador del entrenador o clase si aplica
     * @throws NegocioException Si falta la visita o hay error en base de datos
     */
    @Override
    public void actualizarServicio(String idVisita, String tipoServicio,
                                    String idRecursoAsignado)
            throws NegocioException {

        if (idVisita == null || idVisita.isBlank()) {
            throw new NegocioException("El id de visita es requerido para actualizar el servicio");
        }

        try {
            visitaDAO.actualizarServicio(idVisita, tipoServicio, idRecursoAsignado);
            LOG.info("Servicio actualizado: visita=" + idVisita
                    + " tipo=" + tipoServicio
                    + " recurso=" + idRecursoAsignado);
        } catch (PersistenciaException ex) {
            LOG.severe("Error al actualizar servicio: " + ex.getMessage());
            throw new NegocioException("No se pudo actualizar el servicio de la visita", ex);
        }
    }
}