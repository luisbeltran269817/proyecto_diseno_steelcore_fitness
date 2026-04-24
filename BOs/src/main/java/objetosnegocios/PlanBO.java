/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.PlanDTO;
import dtos.SucursalDTO;
import interfaces.IPlanBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class PlanBO implements IPlanBO{
    private final AlmacenComprarMembresiaMock almacen;

    public PlanBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    @Override
    public List<PlanDTO> obtenerTodos() {
        return new ArrayList<>(almacen.getPlanes().values());
    }
    @Override
    public PlanDTO buscarPorId(String idPlan) {
        return almacen.getPlanes().get(idPlan);
    }
    @Override
    public List<PlanDTO> obtenerPorSucursal(String idSucursal) {
        SucursalDTO sucursal = almacen.getSucursales().get(idSucursal);
        if (sucursal == null || sucursal.getPlanes() == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(sucursal.getPlanes());
    }
    
}
