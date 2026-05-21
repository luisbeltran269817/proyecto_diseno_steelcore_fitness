/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtosControlDeAcceso.VisitaDTO;

/**
 * Interfaz para las reglas de negocio de las visitas
 *
 * @author julian izaguirre
 */
public interface IVisitaAccesoBO {

    /**
     * Registra la entrada inicial por defecto a area general
     *
     * @param idCliente Identificador del socio
     * @param idSucursal Sucursal donde entro
     * @return DTO con la informacion de la visita generada
     * @throws NegocioException Si hay algun problema al registrar
     */
    VisitaDTO registrarEntrada(String idCliente, String idSucursal) throws NegocioException;

    /**
     * Cambia el servicio que el socio va a tomar en su visita
     *
     * @param idVisita Identificador de la visita
     * @param tipoServicio Tipo de servicio elegido
     * @param idRecursoAsignado Recurso especifico asignado
     * @throws NegocioException Si no se puede actualizar
     */
    void actualizarServicio(String idVisita, String tipoServicio,String idRecursoAsignado) throws NegocioException;
}