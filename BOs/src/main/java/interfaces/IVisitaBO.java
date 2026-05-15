/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IVisitaBO {
    
    VisitaDTO guardar(String idCliente, String idSucursal, VisitaDTO visita) throws NegocioException;
    List<VisitaDTO> obtenerPorCliente(String idCliente) throws NegocioException;
}
