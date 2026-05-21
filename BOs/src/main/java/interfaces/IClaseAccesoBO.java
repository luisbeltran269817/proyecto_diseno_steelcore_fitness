/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtosControlDeAcceso.ClaseDTO;
import java.util.List;
 
/**
 * Contrato de negocio para el manejo de clases en el módulo de Control de Acceso.
 *
 * @author julian izaguirre
 */
public interface IClaseAccesoBO {
 
    /**
     * Obtiene las clases disponibles para el plan del socio en la sucursal.
     * Excluye clases donde el socio ya está inscrito.
     *
     * @param idSucursal sucursal donde opera la recepción
     * @param idPlan     plan de la membresía del socio
     * @param idCliente  id del socio (para excluir clases ya inscritas)
     * @return lista de ClaseDTO; nunca null
     * @throws NegocioException si el plan no incluye clases o falla la consulta
     */
    List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan, String idCliente)
            throws NegocioException;
 
    /**
     * Inscribe al socio en la clase seleccionada.
     * Valida que el plan incluya clases y que haya cupo.
     *
     * @param idClase   id de la clase
     * @param idCliente id del socio
     * @throws NegocioException si cupo lleno, ya inscrito, o plan no incluye clases
     */
    void inscribirSocio(String idClase, String idCliente) throws NegocioException;
}
