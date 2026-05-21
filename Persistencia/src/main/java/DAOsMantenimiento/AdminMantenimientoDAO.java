/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOsMantenimiento;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios_mantenimiento.AdminMantenimientoPojo;
import excepciones.PersistenciaException;
import interfacesMantenimiento.IAdminMantenimientoDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersMantenimientoPersistencia.AdminMantenimientoPersistenciaMapper;
import org.bson.Document;

/**
 *
 * @author Tungs
 */
public class AdminMantenimientoDAO implements IAdminMantenimientoDAO {
    
     private static final Logger logger = Logger.getLogger(AdminMantenimientoDAO.class.getName());

    private final MongoCollection<Document> coleccion;

    public AdminMantenimientoDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("adminsMantenimiento");
    }

    /**
     * Método qeu busca un administrador de mantenimiento por su correo en la BD
     * @param correo el correo del administrador de mantenimiento
     * @return el pojo del admin de mantenimiento
     * @throws PersistenciaException so ocurre un error
     */
    @Override
    public AdminMantenimientoPojo buscarPorCorreo(String correo) throws PersistenciaException {
        try {
            if (correo == null || correo.isBlank()) {
                throw new PersistenciaException("El correo es inválido");
            }
            Document doc = coleccion.find(Filters.eq("usuario.correo", correo)).first();

            if (doc == null) {
                return null;
            }
            logger.log(Level.INFO, "Admin de mantenimiento encontrado correctamente");

            return AdminMantenimientoPersistenciaMapper.toPojo(doc);

        } catch (MongoException ex) {
            logger.log(Level.SEVERE, "Error al buscar admin de mantenimiento", ex);
            throw new PersistenciaException("Error al buscar admin de mantenimiento");
        }
    }

}
