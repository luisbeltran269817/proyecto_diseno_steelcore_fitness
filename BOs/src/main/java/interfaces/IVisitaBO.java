/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IVisitaBO {
    
    /**
     * Registra una nueva visita para el socio indicado.
     * @param idCliente correo del cliente (clave en el almacén)
     * @param idSucursal id de la sucursal donde se registra la entrada
     * @return el VisitaDTO creado y guardado
     */
    VisitaDTO registrarVisita(String idCliente, String idSucursal);
 
    /**
     * Devuelve el historial de visitas de un cliente.
     * @param idCliente correo del cliente
     * @return lista (puede estar vacía, nunca null)
     */
    List<VisitaDTO> obtenerHistorial(String idCliente);
}
