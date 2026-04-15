/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CasoBase;

import Controladores.ControlPantallas;
import Utilerias.BotonUtileria;
import Utilerias.LabelUtileria;
import java.awt.Color;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaInicio extends JFrame {

    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;
    private ControlPantallas controlP;

    public PantallaInicio(ControlPantallas controlP) {
        this.controlP = controlP;
        configurarVentana();
        iniciarComponentes();
    }

    private void configurarVentana() {
        setTitle("SteelCore Fitness");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(220, 220, 220));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
    }

    private void iniciarComponentes() {

        add(Box.createVerticalStrut(80)); // espacio arriba

        add(LabelUtileria.crearTitulo("STEELCORE FITNESS"));

        add(Box.createVerticalStrut(80)); // espacio entre título y botones

        btnIniciarSesion = BotonUtileria.crearBoton("Iniciar Sesion");
        add(btnIniciarSesion);

        add(Box.createVerticalStrut(30));

        btnRegistrarse = BotonUtileria.crearBoton("Registrarse");
        add(btnRegistrarse);
    }
}
