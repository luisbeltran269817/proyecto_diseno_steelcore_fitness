/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AlmacenComprarMembresiaMock;
import DAOs.HorarioDAO;
import dtos.HorarioDTO;
import interfaces.IHorarioBO;
import interfaces.IHorarioDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class HorarioBO implements IHorarioBO {
    private final IHorarioDAO horarioDAO;

    public HorarioBO() {
        this.horarioDAO = new HorarioDAO();
    }
    
    @Override
    public List<HorarioDTO> obtenerDisponiblesPorEntrenador(String idEntrenador) {
        return horarioDAO.obtenerDisponiblesPorEntrenador(idEntrenador);
    }
    
    @Override
    public HorarioDTO buscarPorId(String id) {
        return horarioDAO.buscarPorId(id);
    }
    
    @Override
    public void actualizar(HorarioDTO horario) {
        horarioDAO.actualizar(horario);
    }
}
