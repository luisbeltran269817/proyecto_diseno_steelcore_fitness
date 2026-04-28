/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.PlanDAO;
import dtos.PlanDTO;
import interfaces.IPlanBO;
import interfaces.IPlanDAO;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PlanBO implements IPlanBO{
    private final IPlanDAO planDAO;

    public PlanBO() {
        this.planDAO = new PlanDAO();
    }
    
    @Override
    public List<PlanDTO> obtenerTodos() {
        return planDAO.obtenerTodos();
    }
    
    @Override
    public PlanDTO buscarPorId(String idPlan) {
        return planDAO.buscarPorId(idPlan);
    }
    
    @Override
    public List<PlanDTO> obtenerPorSucursal(String idSucursal) {
        return planDAO.obtenerPorSucursal(idSucursal);
    }
}
