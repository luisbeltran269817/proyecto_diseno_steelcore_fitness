/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtosControlDeAcceso.ClaseDTO;
import java.util.List;

/**
 * Interfaz para las reglas de negocio de las clases
 *
 * @author julian izaguirre
 */
public interface IClaseAccesoBO {

    /**
     * Trae las clases que el socio puede tomar con su membresia
     *
     * @param idSucursal Sucursal a consultar
     * @param idPlan Plan del socio
     * @param idCliente Identificador del socio para filtrar
     * @return Lista de clases permitidas
     * @throws NegocioException Si algo sale mal en la consulta
     */
    List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan, String idCliente)
            throws NegocioException;

    /**
     * Valida y mete al socio a una clase
     *
     * @param idClase Identificador de la clase
     * @param idCliente Identificador del socio
     * @throws NegocioException Si la clase se lleno o ya estaba apuntado
     */
    void inscribirSocio(String idClase, String idCliente) throws NegocioException;
}