/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public interface IVisitaDAO {
    
    public void guardar(String idCliente, VisitaDTO visita);
 
    List<VisitaDTO> obtenerPorCliente(String idCliente);
}
