/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.AmenidadDTO;
import interfaces.IAmenidadBO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class AmenidadBO implements IAmenidadBO {
    private final AlmacenComprarMembresiaMock almacen;

    public AmenidadBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    
    @Override
    public List<AmenidadDTO> obtenerTodas() {
        return new ArrayList<>(almacen.getAmenidades().values());
    }
    @Override
    public AmenidadDTO buscarPorId(String id) {
        return almacen.getAmenidades().get(id);
    }
    @Override
    public List<AmenidadDTO> buscarPorIds(List<String> ids) {
        List<AmenidadDTO> lista = new ArrayList<>();
        for (String id : ids) {
            AmenidadDTO a = almacen.getAmenidades().get(id);
            if (a != null) {
                lista.add(a);
            }
        }
        return lista;
    }
}
