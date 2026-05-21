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
 * Logica de negocio para las clases en el control de acceso
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
     * Busca las clases disponibles para el socio y quita en las que ya esta inscrito
     *
     * @param idSucursal La sucursal donde esta el socio
     * @param idPlan El plan de su membresia
     * @param idCliente El identificador del socio
     * @return Lista de clases disponibles
     * @throws NegocioException Si falta la sucursal o falla la consulta
     */
    @Override
    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                                String idCliente)
            throws NegocioException {

        if (idSucursal == null || idSucursal.isBlank()) {
            throw new NegocioException("No se puede consultar clases: sucursal no definida");
        }

        try {
            List<ClasePojo> pojos = claseDAO.obtenerPorSucursalYPlan(idSucursal, idPlan);

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
            throw new NegocioException("No se pudieron obtener las clases disponibles", ex);
        }
    }

    /**
     * Manda al DAO la instruccion para meter al socio a la clase
     *
     * @param idClase Clase a la que va a entrar
     * @param idCliente Socio que se inscribe
     * @throws NegocioException Si faltan datos o la clase ya esta llena
     */
    @Override
    public void inscribirSocio(String idClase, String idCliente) throws NegocioException {
        if (idClase == null || idClase.isBlank()) {
            throw new NegocioException("Debe seleccionar una clase");
        }
        if (idCliente == null || idCliente.isBlank()) {
            throw new NegocioException("El socio no esta identificado");
        }

        try {
            claseDAO.inscribirSocio(idClase, idCliente);
            LOG.info("Socio " + idCliente + " inscrito en clase " + idClase);
        } catch (PersistenciaException ex) {
            throw new NegocioException(ex.getMessage(), ex);
        }
    }
}