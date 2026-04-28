/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.MembresiaDTO;
import interfaces.IMembresiaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class MembresiaDAO implements IMembresiaDAO{
    private final AlmacenComprarMembresiaMock almacen;

    public MembresiaDAO() {
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
