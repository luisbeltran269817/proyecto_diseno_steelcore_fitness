/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.EntrenadorDTO;
import dtos.SucursalDTO;
import interfaces.IEntrenadorBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class EntrenadorBO implements IEntrenadorBO {
    private final AlmacenComprarMembresiaMock almacen;

    public EntrenadorBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    @Override
    public List<EntrenadorDTO> obtenerTodos() {
        return new ArrayList<>(almacen.getEntrenadores().values());
    }
    @Override
    public EntrenadorDTO buscarPorId(String id) {
        return almacen.getEntrenadores().get(id);
    }

    @Override
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal) {
        List<EntrenadorDTO> lista = new ArrayList<>();
        for (EntrenadorDTO e : almacen.getEntrenadores().values()) {
            if (e.getSucursales() != null) {
                for (SucursalDTO s : e.getSucursales()) {
                    if (s.getIdSucursal().equals(idSucursal)) {
                        lista.add(e);
                        break;
                    }
                }
            }
        }
        return lista;
    }
    
}
