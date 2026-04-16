/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaBienvenida extends PantallaBase {
    public PantallaBienvenida(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Bienvenido");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(460, 520);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 60, 60, 60));

        JLabel icono = new JLabel("◈") {
            {
                setFont(new Font("Segoe UI", Font.PLAIN, 48));
                setForeground(Colores.ACENTO);
                setAlignmentX(CENTER_ALIGNMENT);
            }
        };
        
        JLabel titulo = new JLabel("Bienvenido");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(300, 1));
        
        Boton btnInicioSesion = crearBoton("Iniciar Sesión", Boton.Variante.PRIMARIO);
        Boton btnRegistro = crearBoton("Registrarse", Boton.Variante.SECUNDARIO);

        btnInicioSesion.setAlignmentX(CENTER_ALIGNMENT);
        btnRegistro.setAlignmentX(CENTER_ALIGNMENT);
        btnInicioSesion.setMaximumSize(new Dimension(320, 52));
        btnRegistro.setMaximumSize(new Dimension(320, 52));

        btnInicioSesion.addActionListener(e -> controlador.irAInicioSesion());

        card.add(icono);
        card.add(Box.createVerticalStrut(16));
        card.add(titulo);
        card.add(Box.createVerticalStrut(8));
        card.add(Box.createVerticalStrut(40));
        card.add(sep);
        card.add(Box.createVerticalStrut(40));
        card.add(btnInicioSesion);
        card.add(Box.createVerticalStrut(16));
        card.add(btnRegistro);
        
        fondo.add(card);
    }
}
