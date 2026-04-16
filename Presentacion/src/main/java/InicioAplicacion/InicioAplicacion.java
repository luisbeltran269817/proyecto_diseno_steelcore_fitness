/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package InicioAplicacion;

import Controladores.ControladorAplicacion;
import javax.swing.SwingUtilities;

/**
 *
 * @author luiscarlosbeltran
 */
public class InicioAplicacion {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorAplicacion.getInstancia().iniciar();
        });
    }
}