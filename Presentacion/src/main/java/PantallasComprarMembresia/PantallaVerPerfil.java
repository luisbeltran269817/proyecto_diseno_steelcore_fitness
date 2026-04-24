/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.CampoContrasena;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.UsuarioDTO;
import java.awt.BorderLayout;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaVerPerfil /**extends PantallaBase*/ {
    /**
    private UsuarioDTO usuario;

    public PantallaVerPerfil(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Perfil");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {

        usuario = controlador.obtenerPerfil();

        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(480, 560);
        card.setLayout(new BorderLayout(15, 15));
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel titulo = new JLabel("Perfil", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

        card.add(titulo, BorderLayout.NORTH);

        JPanel panelCampos = new JPanel();
        panelCampos.setOpaque(false);
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

        CampoTexto txtCorreo = new CampoTexto("Correo electrónico", usuario.getCorreo());
        CampoTexto txtContrasena = new CampoTexto("Contraseña", "falta atributo contraseña");
        CampoTexto txtNombre = new CampoTexto("Nombre", usuario.getNombre());
        CampoTexto txtAP = new CampoTexto("Apellido Paterno", "falta atributo apellido paterno");
        CampoTexto txtAM = new CampoTexto("Apellido Materno", "falta atributo apellido materno");
        CampoTexto txtFechaNacimiento = new CampoTexto("Fecha de Nacimiento", "falta atributo fechaNacimiento");

        CampoTexto[] campos = {
            txtCorreo, txtContrasena, txtNombre,
            txtAP, txtAM, txtFechaNacimiento
        };

        for (CampoTexto campo : campos) {
            campo.setAlignmentX(CENTER_ALIGNMENT);
            campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

            panelCampos.add(campo);
            panelCampos.add(Box.createVerticalStrut(15));
        }

        JScrollPane scroll = new JScrollPane(panelCampos);
        scroll.setBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(14);

        card.add(scroll, BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        Boton btnVolver = crearBoton("Volver", Boton.Variante.PRIMARIO);
        Boton btnMod = crearBoton("Modificar datos", Boton.Variante.SECUNDARIO);
        Boton btnCerrarSesion = crearBoton("Cerrar sesión", Boton.Variante.SECUNDARIO);

        Boton[] botones = {btnVolver, btnMod, btnCerrarSesion};

        for (Boton btn : botones) {
            btn.setAlignmentX(CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));

            panelBotones.add(btn);
            panelBotones.add(Box.createVerticalStrut(10));
        }

        btnVolver.addActionListener(e -> controlador.irAPerfilUsuario());
        
        btnCerrarSesion.addActionListener(e -> controlador.irABienvenida());

        card.add(panelBotones, BorderLayout.SOUTH);

        fondo.add(card);
    }
    */
}
