/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Fachada;

import Excepciones.NegocioException;
import dtos.EjercicioDTO;
import dtos.EntrenadorDTO;
import dtos.RutinaDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IPlanearRutina {
    /**
     * metodo que consigue las rutinas de un cliente por su correo
     * si no tiene regresa null
     * si tiene, regresa una lista de RutinaDTO
     * cada RutinaDTO adentro tiene una lista de detalleRutinaDTO
     * y cada detalle adentro tiene una lista de EjercicioDTO o referencias a ejercicios
     * NOTA: cambiar tipo de dato a List<RutinaDTO>
     * @param correo 
     * @return  
     */
    public List<RutinaDTO> obtenerRutinas(String correo) throws NegocioException;
    
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular) throws NegocioException;
    
    public RutinaDTO guardarRutina(String correo, RutinaDTO rutina) throws NegocioException;

    public boolean borrarRutina(String correo, String nombre) throws NegocioException;
    
    public String obtenerIdSucursalMembresiaActiva(String correo) throws NegocioException;
    
    public EntrenadorDTO obtenerEntrenadorPorId(String id) throws NegocioException;
    
    public RutinaDTO obtenerPlantilla(String nombre) throws NegocioException;
}
