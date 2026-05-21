/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class DetalleRutinaDTO {
    private String nombreDia;
    private String grupoMuscular;
    private List<EjercicioDTO> ejercicios;

    public DetalleRutinaDTO() {
    }

    public DetalleRutinaDTO(String nombreDia, String grupoMuscular, List<EjercicioDTO> ejercicios) {
        this.nombreDia = nombreDia;
        this.grupoMuscular = grupoMuscular;
        this.ejercicios = ejercicios;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public List<EjercicioDTO> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<EjercicioDTO> ejercicios) {
        this.ejercicios = ejercicios;
    }

    
    
    
}
