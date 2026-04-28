/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.HorarioDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IHorarioDAO {
    public List<HorarioDTO> obtenerDisponiblesPorEntrenador(String idEntrenador);
    public HorarioDTO buscarPorId(String id);
    public void actualizar(HorarioDTO horario);
}
