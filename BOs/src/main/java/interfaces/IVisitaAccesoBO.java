/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtosControlDeAcceso.VisitaDTO;
 
/**
 * Contrato de negocio para el registro de visitas del caso individual de acceso.
 *
 * @author julian izaguirre
 */
public interface IVisitaAccesoBO {
 
    /**
     * Registra la visita inicial cuando el socio escanea su QR.
     * El tipo de servicio queda como "AREA_GENERAL" por defecto
     * hasta que el socio elige un servicio adicional.
     *
     * @param idCliente  id del socio
     * @param idSucursal id de la sucursal
     * @return VisitaDTO con el id generado y la hora de registro
     * @throws NegocioException si falla la inserción
     */
    VisitaDTO registrarEntrada(String idCliente, String idSucursal) throws NegocioException;
 
    /**
     * Actualiza el tipo de servicio elegido por el socio en esta visita.
     *
     * @param idVisita          id de la visita a actualizar
     * @param tipoServicio      "AREA_GENERAL", "CLASE" o "ENTRENADOR"
     * @param idRecursoAsignado id de la clase o entrenador; null para AREA_GENERAL
     * @throws NegocioException si la visita no existe o falla la actualización
     */
    void actualizarServicio(String idVisita, String tipoServicio,
                             String idRecursoAsignado) throws NegocioException;
}
