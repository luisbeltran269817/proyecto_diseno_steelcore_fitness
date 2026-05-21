/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import Controlador.ControlPlanearRutina;
import Excepciones.NegocioException;
import dtos.EjercicioDTO;
import dtos.EntrenadorDTO;
import dtos.RutinaDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class FachadaPlanearRutina implements IPlanearRutina {
    private final ControlPlanearRutina control;

    public FachadaPlanearRutina() {
        this.control = new ControlPlanearRutina();
    }

    @Override
    public List<RutinaDTO> obtenerRutinas(String correo) throws NegocioException {
        return control.obtenerRutinas(correo);
    }
    
    @Override
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular) throws NegocioException{
        return control.recuperarEjercicios(grupoMuscular);
    }
    
    @Override
    public RutinaDTO guardarRutina(String correo, RutinaDTO rutina) throws NegocioException{
        return control.guardarRutina(correo, rutina);
    }

    @Override
    public boolean borrarRutina(String correo, String nombre) throws NegocioException {
        return control.borrarRutina(correo, nombre);
    }
    
    @Override
    public String obtenerIdSucursalMembresiaActiva(String correo) throws NegocioException{
        return control.obtenerIdSucursalMembresiaActiva(correo);
    }
    
    @Override
    public EntrenadorDTO obtenerEntrenadorPorId(String id) throws NegocioException{
        return control.obtenerEntrenadorPorId(id);
    }
    
    @Override
    public RutinaDTO obtenerPlantilla(String nombre) throws NegocioException{
        return control.obtenerPlantilla(nombre);
    }
}
