/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import CasoBase.PantallaInicio;

/**
 *
 * @author luiscarlosbeltran
 */
public class ControlPantallas {
    
    private PantallaInicio pantallaInicio;

    public ControlPantallas() {
    }

    /**
     * metodo para iniciar el sistema, muestra la pantalla de inicio
     */
    public void mostrarPantallaInicio() {
        if (pantallaInicio == null) {
            pantallaInicio = new PantallaInicio(this);
        }
        pantallaInicio.setVisible(true);
    }
}