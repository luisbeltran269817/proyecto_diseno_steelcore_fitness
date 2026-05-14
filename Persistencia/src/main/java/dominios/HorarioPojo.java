/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;


import java.time.LocalTime;

/**
 *
 * @author luiscarlosbeltran
 */
public class HorarioPojo {
    private String nombreDia;
    private LocalTime inicio;
    private LocalTime fin;
    private boolean disponible;

    public HorarioPojo() {
    }

    public HorarioPojo(String nombreDia, LocalTime inicio, LocalTime fin, boolean disponible) {
        this.nombreDia = nombreDia;
        this.inicio = inicio;
        this.fin = fin;
        this.disponible = disponible;
    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    
    
}
