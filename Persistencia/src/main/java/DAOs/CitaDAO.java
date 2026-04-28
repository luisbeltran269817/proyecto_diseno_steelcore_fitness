/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.CitaDTO;
import interfaces.ICitaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class CitaDAO implements ICitaDAO {
    private final AlmacenComprarMembresiaMock almacen;

    public CitaDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    @Override
    public void guardar(CitaDTO cita) {
        almacen.getCitas().put(cita.getIdCita(), cita);
    }
    @Override
    public CitaDTO buscarPorId(String id) {
        return almacen.getCitas().get(id);
    }
    @Override
    public List<CitaDTO> obtenerPorCliente(String idCliente) {
        List<CitaDTO> lista = new ArrayList<>();

        for (CitaDTO c : almacen.getCitas().values()) {
            if (c.getIdCliente().equals(idCliente)) {
                lista.add(c);
            }
        }
        return lista;
    }
}
