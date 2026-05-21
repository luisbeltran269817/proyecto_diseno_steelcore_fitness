/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioMantenimiento;

import DAOsMantenimiento.AdminMantenimientoDAO;
import Excepciones.NegocioException;
import dominios_mantenimiento.AdminMantenimientoPojo;
import dtosInventarioMantenimiento.AdminMantenimientoDTO;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IAdminMantenimientoBO;
import interfacesMantenimiento.IAdminMantenimientoDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoBO.AdminMantenimientoDTOMapper;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoBO implements IAdminMantenimientoBO {
     private static final Logger logger = Logger.getLogger(AdminMantenimientoBO.class.getName());

    private final IAdminMantenimientoDAO adminDAO;

    public AdminMantenimientoBO() {
        this.adminDAO = new AdminMantenimientoDAO();
    }

    /**
     * Método que busca a un administrador por su correo en al base de datos
     * @param correo
     * @return
     * @throws NegocioException 
     */
    @Override
    public AdminMantenimientoDTO buscarPorCorreo(String correo) throws NegocioException {
        try {
            if (correo == null || correo.isBlank()) {
                throw new NegocioException("El correo es inválido");
            }
            AdminMantenimientoPojo pojo = adminDAO.buscarPorCorreo(correo);
            return AdminMantenimientoDTOMapper.toDTO(pojo);
        } catch (PersistenciaException ex) {
            logger.log(Level.SEVERE, "Error al buscar admin de mantenimiento", ex);
            throw new NegocioException("Error al buscar admin de mantenimiento");
        }
    }

}
