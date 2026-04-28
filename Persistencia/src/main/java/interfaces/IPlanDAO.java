/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.PlanDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IPlanDAO {
    public List<PlanDTO> obtenerTodos();
    public PlanDTO buscarPorId(String idPlan);
    public List<PlanDTO> obtenerPorSucursal(String idSucursal);
}
