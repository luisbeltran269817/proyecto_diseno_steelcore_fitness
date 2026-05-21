/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.TecnicoDTO;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface ITecnicoBO {
    public TecnicoDTO obtenerTecnico(String idTecnico) throws NegocioException;
     public List<TecnicoDTO> obtenerTecnicos() throws NegocioException;
     public boolean tieneHorarioDisponible(String idTecnico, String nombreDia, LocalTime horaInicio, LocalTime horaFin) throws NegocioException;
      public void actualizarEstadoHorario(String idTecnico, String idHorario, boolean disponible) throws NegocioException;
}
