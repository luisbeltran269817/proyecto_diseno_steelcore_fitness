/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.MembresiaDAO;
import Excepciones.NegocioException;
import dominios.MembresiaPojo;
import dtos.MembresiaDTO;
import dtosReportes.FiltrosReporteDTO;
import excepciones.PersistenciaException;
import interfaces.IMembresiaBO;
import interfaces.IMembresiaDAO;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersBO.MembresiaMapper;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaBO implements IMembresiaBO {

    private final IMembresiaDAO membresiaDAO;
    private static final Logger logger = Logger.getLogger(MembresiaBO.class.getName());

    public MembresiaBO() {
        this.membresiaDAO = new MembresiaDAO();
    }

    @Override
    public void guardar(MembresiaDTO membresia) throws NegocioException {
        try {
            if (membresia == null) {
                throw new NegocioException("La membresía no puede ser null");
            }
            MembresiaPojo pojo = MembresiaMapper.toPojo(membresia);
            membresiaDAO.guardar(pojo);
            logger.log(Level.INFO, "Membresía guardada correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al guardar membresía", e);
            throw new NegocioException("Error al guardar la membresía", e);
        }
    }

    @Override
    public MembresiaDTO buscarPorId(String idMembresia) throws NegocioException {
        try {
            if (idMembresia == null || idMembresia.isBlank()) {
                throw new NegocioException("El id es inválido");
            }
            MembresiaPojo pojo = membresiaDAO.buscarPorId(idMembresia);
            return MembresiaMapper.toDTO(pojo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al buscar membresía", e);
            throw new NegocioException("Error al buscar la membresía", e);
        }
    }

    @Override
    public MembresiaDTO buscarPorCodigoQR(String codigoQR) throws NegocioException {
        try {
            if (codigoQR == null || codigoQR.isBlank()) {
                return null;
            }
            MembresiaPojo pojo = membresiaDAO.buscarPorCodigoQR(codigoQR);
            return MembresiaMapper.toDTO(pojo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al buscar membresia por QR", e);
            return null;
        }
    }

    @Override
    public void actualizar(MembresiaDTO membresia) throws NegocioException {
        try {
            if (membresia == null) {
                throw new NegocioException("La membresía no puede ser null");
            }
            MembresiaPojo pojo = MembresiaMapper.toPojo(membresia);
            membresiaDAO.actualizar(pojo);
            logger.log(Level.INFO, "Membresía actualizada correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al actualizar membresía", e);
            throw new NegocioException("Error al actualizar la membresía", e);
        }
    }

    /**
     * Consulta membresías aplicando los filtros seleccionados para reportes.
     *
     * Convierte las fechas del DTO de filtros a LocalDateTime para consultar
     * desde el inicio del día hasta el final del día. Después convierte los
     * POJOs obtenidos desde persistencia a DTOs para que puedan ser usados por
     * la capa de negocio de reportes.
     *
     * @param filtros filtros seleccionados por el administrador.
     * @return lista de membresías que cumplen con los filtros.
     * @throws NegocioException si los filtros son inválidos o falla la
     * consulta.
     */
    @Override
    public List<MembresiaDTO> consultarParaReportes(FiltrosReporteDTO filtros) throws NegocioException {
        try {
            if (filtros == null) {
                throw new NegocioException("Debe ingresar filtros para consultar membresías.");
            }

            if (filtros.getFechaInicio() == null || filtros.getFechaFin() == null) {
                throw new NegocioException("Debe ingresar fecha de inicio y fecha de fin.");
            }

            if (filtros.getFechaFin().isBefore(filtros.getFechaInicio())) {
                throw new NegocioException("La fecha final no puede ser menor que la fecha inicial.");
            }

            List<MembresiaPojo> pojos = membresiaDAO.consultarPorFiltrosReporte(
                    filtros.getFechaInicio().atStartOfDay(),
                    filtros.getFechaFin().atTime(LocalTime.MAX),
                    filtros.getSucursal(),
                    filtros.getTipoMembresia(),
                    filtros.getAmenidad()
            );

            List<MembresiaDTO> dtos = new ArrayList<>();

            for (MembresiaPojo pojo : pojos) {
                dtos.add(MembresiaMapper.toDTO(pojo));
            }

            return dtos;

        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al consultar membresías para reportes", e);
            throw new NegocioException("Error al consultar membresías para reportes", e);
        }
    }
}
