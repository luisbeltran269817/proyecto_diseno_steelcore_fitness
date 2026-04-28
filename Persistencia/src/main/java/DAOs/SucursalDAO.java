/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.SucursalDTO;
import interfaces.ISucursalDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class SucursalDAO implements ISucursalDAO{
    private final AlmacenComprarMembresiaMock almacen;

    public SucursalDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }

    public List<SucursalDTO> obtenerTodas() {
        return new ArrayList<>(almacen.getSucursales().values());
    }

    public SucursalDTO buscarPorId(String id) {
        return almacen.getSucursales().get(id);
    }
}
