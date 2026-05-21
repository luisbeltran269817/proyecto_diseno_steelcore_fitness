/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import dominios_mantenimiento.TecnicoPojo;
import excepciones.PersistenciaException;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface ITecnicoDAO {
     TecnicoPojo obtenerTecnico(String idTecnico) throws PersistenciaException;

    List<TecnicoPojo> obtenerTecnicos() throws PersistenciaException;

    boolean tieneHorarioDisponible(String idTecnico, String nombreDia, LocalTime horaInicio, LocalTime horaFin) throws PersistenciaException;

    void actualizarEstadoHorario(String idTecnico, String idHorario, boolean disponible) throws PersistenciaException;
}
