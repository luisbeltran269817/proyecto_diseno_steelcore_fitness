package interfacesAcceso;

import dominioAcceso.ClasePojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Contrato de acceso a datos para la colección "clases".
 *
 * @author julian izaguirre
 */
public interface IClaseDAO {

    /**
     * Devuelve todas las clases de una sucursal cuyo idPlan coincide con el
     * plan del socio, o cuyo idPlan es null (disponibles para cualquier plan).
     */
    List<ClasePojo> obtenerPorSucursalYPlan(String idSucursal, String idPlan)
            throws PersistenciaException;

    /**
     * Busca una clase por su id.
     */
    ClasePojo buscarPorId(String idClase) throws PersistenciaException;

    /**
     * Inscribe al socio en la clase de forma atómica.
     * Lanza PersistenciaException si el cupo está lleno o el socio ya está inscrito.
     */
    void inscribirSocio(String idClase, String idCliente) throws PersistenciaException;
}