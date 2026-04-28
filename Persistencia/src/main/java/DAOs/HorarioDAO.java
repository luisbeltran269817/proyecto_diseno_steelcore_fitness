/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.HorarioDTO;
import interfaces.IHorarioDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class HorarioDAO implements IHorarioDAO{
    private final AlmacenComprarMembresiaMock almacen;

    public HorarioDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    
    @Override
    public List<HorarioDTO> obtenerDisponiblesPorEntrenador(String idEntrenador) {
        List<HorarioDTO> lista = new ArrayList<>();

        for (HorarioDTO h : almacen.getHorarios().values()) {
            if (h.getIdEntrenador().equals(idEntrenador) && h.isDisponible()) {
                lista.add(h);
            }
        }

        return lista;
    }
    @Override
    public HorarioDTO buscarPorId(String id) {
        return almacen.getHorarios().get(id);
    }
    @Override
    public void actualizar(HorarioDTO horario) {
        almacen.getHorarios().put(horario.getIdHorario(), horario);
    }
}
