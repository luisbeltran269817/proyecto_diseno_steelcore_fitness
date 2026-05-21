/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominioAcceso.VisitaAccesoPojo;
import dtosControlDeAcceso.VisitaDTO;
import java.time.LocalDateTime;

/**
 * Pasa los datos de la visita de dominio a DTO y viceversa
 *
 * @author julian izaguirre
 */
public class VisitaAccesoMapper {

    /**
     * Prepara un objeto visita nuevo con la hora actual
     * 
     * @param idCliente Identificador del socio
     * @param idSucursal Sucursal donde ocurre la visita
     * @return Objeto visita listo para guardar
     */
    public static VisitaAccesoPojo toNuevoPojo(String idCliente, String idSucursal) {
        VisitaAccesoPojo pojo = new VisitaAccesoPojo();
        pojo.setIdCliente(idCliente);
        pojo.setIdSucursal(idSucursal);
        pojo.setFechaHora(LocalDateTime.now());
        pojo.setTipoServicio("AREA_GENERAL"); 
        pojo.setIdRecursoAsignado(null);
        return pojo;
    }

    /** 
     * Pasa la visita guardada a un DTO para la pantalla
     * 
     * @param pojo Visita extraida de base de datos
     * @return DTO para regresar a las vistas
     */
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