/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.SucursalDTO;
import interfaces.ISucursalBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class SucursalBO implements ISucursalBO{
    private final AlmacenComprarMembresiaMock almacen;

    public SucursalBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }

    public List<SucursalDTO> obtenerTodas() {
        return new ArrayList<>(almacen.getSucursales().values());
    }

    public SucursalDTO buscarPorId(String id) {
        return almacen.getSucursales().get(id);
    }
}
