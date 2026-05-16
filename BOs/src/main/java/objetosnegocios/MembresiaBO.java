/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.MembresiaDAO;
import Excepciones.NegocioException;
import dominios.MembresiaPojo;
import dtos.MembresiaDTO;
import excepciones.PersistenciaException;
import interfaces.IMembresiaBO;
import interfaces.IMembresiaDAO;
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
}
