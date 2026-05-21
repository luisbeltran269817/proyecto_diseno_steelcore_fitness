/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IEntrenadorBO {
    public EntrenadorDTO buscarPorId(String idEntrenador)throws NegocioException;
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal) throws NegocioException;
    public List<HorarioDTO>obtenerHorariosEntrenador(String idEntrenador)throws NegocioException;
    void actualizarDisponibilidadHorario(String idEntrenador, String idHorario, boolean disponible) throws NegocioException;
}
