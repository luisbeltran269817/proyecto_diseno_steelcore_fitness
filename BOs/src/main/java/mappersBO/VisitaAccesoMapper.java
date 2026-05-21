package mappersBO;

import dominioAcceso.VisitaAccesoPojo;
import dtosControlDeAcceso.VisitaDTO;
import java.time.LocalDateTime;

/**
 * Mapper entre VisitaAccesoPojo (dominio) y VisitaDTO (caso individual de acceso).
 *
 * @author julian izaguirre
 */
public class VisitaAccesoMapper {

    /**
     * Construye un VisitaAccesoPojo nuevo para insertar.
     * El id se deja vacío para que el DAO genere un ObjectId.
     */
    public static VisitaAccesoPojo toNuevoPojo(String idCliente, String idSucursal) {
        VisitaAccesoPojo pojo = new VisitaAccesoPojo();
        pojo.setIdCliente(idCliente);
        pojo.setIdSucursal(idSucursal);
        pojo.setFechaHora(LocalDateTime.now());
        pojo.setTipoServicio("AREA_GENERAL");  // default hasta que el socio elija
        pojo.setIdRecursoAsignado(null);
        return pojo;
    }

    /** Convierte pojo guardado en DTO para devolver a la capa de control. */
    public static VisitaDTO toDTO(VisitaAccesoPojo pojo) {
        if (pojo == null) return null;

        VisitaDTO dto = new VisitaDTO();
        dto.setIdVisita(pojo.getIdVisita());
        dto.setIdCliente(pojo.getIdCliente());
        dto.setIdSucursal(pojo.getIdSucursal());
        dto.setFechaHora(pojo.getFechaHora());
        dto.setTipoServicio(pojo.getTipoServicio());
        dto.setIdRecursoAsignado(pojo.getIdRecursoAsignado());
        return dto;
    }
}