/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.EjercicioPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IEjercicioDAO {
    public List<EjercicioPojo> obtenerPorListaIds(List<String> idsEjercicios) throws PersistenciaException;
    public List<EjercicioPojo> recuperarEjercicios(String grupoMuscular) throws PersistenciaException;
}
