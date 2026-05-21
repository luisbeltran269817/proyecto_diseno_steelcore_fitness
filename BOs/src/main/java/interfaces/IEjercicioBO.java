/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.EjercicioDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IEjercicioBO {
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular) throws NegocioException;
}
