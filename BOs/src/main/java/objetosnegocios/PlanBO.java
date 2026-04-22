/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.PlanDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PlanBO {
    private final AlmacenComprarMembresiaMock almacen;
 
    public PlanBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
 

    public List<PlanDTO> obtenerTodos() {
        return new ArrayList<>(almacen.getPlanes().values());
    }
 

    public PlanDTO buscarPorId(String idPlan) {
        if (idPlan == null || idPlan.isBlank()) return null;
        return almacen.getPlanes().get(idPlan);
    }
 
    public boolean esPlanValido(String idPlan) {
        return buscarPorId(idPlan) != null;
    }
 
    public boolean incluyeEntrenador(String idPlan) {
        PlanDTO plan = buscarPorId(idPlan);
        return plan != null && plan.isIncluyeEntrenador();
    }
}
