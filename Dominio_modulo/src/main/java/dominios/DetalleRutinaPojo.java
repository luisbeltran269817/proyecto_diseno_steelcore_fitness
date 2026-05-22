/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class DetalleRutinaPojo {
    String nombreDia;
    String grupoMuscular;
    List<String>idsEjercicios;

    public DetalleRutinaPojo() {
    }

    public DetalleRutinaPojo(String nombreDia, String grupoMuscular, List<String> idsEjercicios) {
        this.nombreDia = nombreDia;
        this.grupoMuscular = grupoMuscular;
        this.idsEjercicios = idsEjercicios;
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

    public List<String> getIdsEjercicios() {
        return idsEjercicios;
    }

    public void setIdsEjercicios(List<String> idsEjercicios) {
        this.idsEjercicios = idsEjercicios;
    }
    
    
}
