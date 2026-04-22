/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInicioSesion;

import Controladores.IControladorAplicacion;
import Fachada.FachadaInicioSesion;
import Fachada.IInicioSesion;
import Utilerias.Boton;
import Utilerias.CampoContrasena;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.InicioSesionDTO;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaInicioSesion extends PantallaBase {

    private CampoTexto      txtCorreo;
    private CampoContrasena txtContrasena;
 
    private IInicioSesion  subsistema;
    private InicioSesionDTO sesionActual;
 
    public PantallaInicioSesion(IControladorAplicacion controlador) {
        super(controlador);
        this.subsistema = new FachadaInicioSesion();
        setTitle("Inicio de Sesión - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(480, 560);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 60, 60, 60));
 
        JLabel titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        txtCorreo     = new CampoTexto("Correo electrónico", "correo@ejemplo.com");
        txtContrasena = new CampoContrasena("Contraseña");
 
        txtCorreo.setAlignmentX(CENTER_ALIGNMENT);
        txtContrasena.setAlignmentX(CENTER_ALIGNMENT);
        txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
 
        Boton btnIngresar = crearBoton("Ingresar",  Boton.Variante.PRIMARIO);
        Boton btnRegresar = crearBoton("Regresar",  Boton.Variante.SECUNDARIO);
 
        btnIngresar.setAlignmentX(CENTER_ALIGNMENT);
        btnRegresar.setAlignmentX(CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        btnRegresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
 
        btnIngresar.addActionListener(e -> onIngresar());
        btnRegresar.addActionListener(e -> {
            setVisible(false);
            controlador.irABienvenida();
        });
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(44));
        card.add(txtCorreo);
        card.add(Box.createVerticalStrut(20));
        card.add(txtContrasena);
        card.add(Box.createVerticalStrut(40));
        card.add(btnIngresar);
        card.add(Box.createVerticalStrut(12));
        card.add(btnRegresar);
 
        fondo.add(card);
    }
 
    private void onIngresar() {
        String correo    = txtCorreo.getValor();
        String contrasena = txtContrasena.getValor();
 
        if (correo.isBlank() || contrasena.isBlank()) {
            mostrarError("Por favor completa todos los campos.");
            return;
        }
 
        // La pantalla llama a la interfaz — no sabe nada del BO ni de la clase control
        InicioSesionDTO sesion = subsistema.iniciarSesion(correo, contrasena);
 
        if (sesion != null && sesion.isActivo()) {
            this.sesionActual = sesion;
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido, " + sesion.getNombre() + "!",
                "Acceso Exitoso", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
            controlador.iniciarSesion(sesion);
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }
 
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje,
            "Error de Validación", JOptionPane.ERROR_MESSAGE);
    }
}
