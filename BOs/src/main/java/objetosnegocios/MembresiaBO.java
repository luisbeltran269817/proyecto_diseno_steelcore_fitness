/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import interfaces.IMembresiaBO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaBO implements IMembresiaBO {
    private final AlmacenComprarMembresiaMock almacen;

    public MembresiaBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    @Override
    public void guardar(MembresiaDTO m) {
        almacen.getMembresias().put(m.getIdMembresia(), m);
    }
    @Override
    public MembresiaDTO buscarPorId(String id) {
        return almacen.getMembresias().get(id);
    }
    @Override
    public List<MembresiaDTO> obtenerPorCliente(String idCliente) {
        List<MembresiaDTO> lista = new ArrayList<>();

        for (MembresiaDTO m : almacen.getMembresias().values()) {
            if (m.getIdCliente().equals(idCliente)) {
                lista.add(m);
            }
        }
        return lista;
    }
    @Override
    public void actualizar(MembresiaDTO membresia) {
        if (membresia == null || membresia.getIdMembresia() == null) {
            return;
        }
        almacen.getMembresias().put(membresia.getIdMembresia(),membresia
        );
    }
    
    
}
