/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.VisitaPojo;
import dtos.VisitaDTO;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public interface IVisitaDAO {
    
   public List<VisitaPojo> obtenerPorCliente(String idCliente)throws PersistenciaException;
   public VisitaPojo guardar(String idCliente,VisitaPojo visita) throws PersistenciaException;
}
