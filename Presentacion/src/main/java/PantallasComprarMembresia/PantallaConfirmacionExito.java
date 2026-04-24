/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author julian izaguirre 
 */
public class PantallaConfirmacionExito /**extends PantallaBase*/ {
    /**private ResultadoDTO resultado;

    public PantallaConfirmacionExito(IControladorAplicacion controlador, ResultadoDTO resultado) {
        super(controlador);
        this.resultado = resultado;
        setTitle("¡Compra Exitosa! - SteelCore");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(400, 400);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(50, 40, 50, 40));

        JLabel titulo = new JLabel("¡Bienvenido a SteelCore!");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(new java.awt.Color(46, 204, 113)); // Verde éxito
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblMensaje = new JLabel("<html><center>" + resultado.getMensaje() + "</center></html>");
        lblMensaje.setAlignmentX(CENTER_ALIGNMENT);
        lblMensaje.setForeground(Colores.TEXTO_PRINCIPAL); // <--- COLOR AGREGADO
        
        JLabel lblFolio = new JLabel("Folio de Transacción: " + resultado.getFolio());
        lblFolio.setFont(Colores.FUENTE_SUBTITULO);
        lblFolio.setAlignmentX(CENTER_ALIGNMENT);
        lblFolio.setForeground(Colores.TEXTO_SECUNDARIO); // <--- COLOR AGREGADO

        Boton btnIrPerfil = crearBoton("Volver a mi Perfil", Boton.Variante.PRIMARIO);
        btnIrPerfil.addActionListener(e -> {
            setVisible(false);
            controlador.irAPerfilUsuario();
        });

        card.add(titulo);
        card.add(Box.createVerticalStrut(20));
        card.add(lblMensaje);
        card.add(Box.createVerticalStrut(30));
        card.add(lblFolio);
        card.add(Box.createVerticalStrut(40));
        card.add(btnIrPerfil);

        fondo.add(card);
    }
    */
}
