/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface ISucursalBO {
    //Agregar aquí los métodos nuevos
    public List<SucursalDTO>obtenerSucursales()throws NegocioException;
    public SucursalDTO buscarPorId(String idSucursal)throws NegocioException;
    public PlanDTO buscarPlanPorId(String idPlan)throws NegocioException;
    
}
