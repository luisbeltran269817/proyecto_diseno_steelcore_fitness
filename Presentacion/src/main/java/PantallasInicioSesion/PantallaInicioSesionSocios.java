/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInicioSesion;
 
import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.CampoContrasena;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
/**
 * Pantalla de inicio de sesion para el personal de recepcion.
 *
 * @author julian izaguirre
 */
public class PantallaInicioSesionSocios extends PantallaBase {
 
    private CampoTexto     txtCorreo;
    private CampoContrasena txtContrasena;
 
    public PantallaInicioSesionSocios(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Recepcion SteelCore — Iniciar Sesion");
        inicializarComponentes();
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(480, 580);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(60, 60, 60, 60));
 
        // Logo / subtitulo del modulo
        JLabel lblModulo = new JLabel("Modulo de Recepcion", (int) CENTER_ALIGNMENT);
        lblModulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblModulo.setForeground(Colores.ACENTO);
        lblModulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel titulo = new JLabel("Iniciar Sesion");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Ingresa tus credenciales de recepcionista");
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        txtCorreo    = new CampoTexto("Correo electronico", "correo@steelcore.com");
        txtContrasena = new CampoContrasena("Contrasena");
 
        txtCorreo.setAlignmentX(CENTER_ALIGNMENT);
        txtContrasena.setAlignmentX(CENTER_ALIGNMENT);
        txtCorreo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
 
        Boton btnIngresar = crearBoton("Ingresar al modulo", Boton.Variante.PRIMARIO);
        
 
        btnIngresar.setAlignmentX(CENTER_ALIGNMENT);
        ;
 
        // Al presionar Enter en el campo de contrasena tambien inicia sesion
        txtContrasena.addActionListener(e -> onIngresar());
        btnIngresar.addActionListener(e -> onIngresar());
 
 
        card.add(lblModulo);
        card.add(Box.createVerticalStrut(4));
        card.add(titulo);
        card.add(Box.createVerticalStrut(6));
        card.add(sub);
        card.add(Box.createVerticalStrut(40));
        card.add(txtCorreo);
        card.add(Box.createVerticalStrut(20));
        card.add(txtContrasena);
        card.add(Box.createVerticalStrut(40));
        card.add(btnIngresar);
        card.add(Box.createVerticalStrut(12));
 
        fondo.add(card);
    }
 
    /**
     * Valida campos, llama al controlador para autenticar y navega al escaner.
     *
     * El controlador.iniciarSesion() autentica contra Mongo (coleccion usuarios).
     * Si tiene exito, irAModuloRecepcion() abre BC_PantallaEspera (el escaner QR).
     */
    private void onIngresar() {
        String correo    = txtCorreo.getValor();
        String contrasena = txtContrasena.getValor();
 
        if (correo.isBlank() || contrasena.isBlank()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Completa todos los campos antes de continuar.",
                    "Campos requeridos",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        try {
            controlador.iniciarSesion(correo, contrasena);
 
            dispose();
            controlador.irAModuloRecepcion();
 
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage() != null
                        ? ex.getMessage()
                        : "Credenciales incorrectas. Intenta de nuevo.",
                    "Error de acceso",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}