/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package InicioAplicacion;

import Controladores.ControladorAplicacion;
import Controladores.IControladorAplicacion;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada para la vista de recep
 *
 * @author julian izaguirre
 */
public class Control_Acceso_Socio {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IControladorAplicacion controlador = ControladorAplicacion.getInstancia();

            controlador.irAInicioSesionRecepcion();
        });
    }
}