/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInventarioMantenimiento;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtosInventarioMantenimiento.MaquinaDTO;
import excepciones.InventarioMantenimientoException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaEliminarInventario extends PantallaBase {
    private MaquinaDTO maquina;
    private boolean datosValidos = true;
    private JTextArea txtMotivo;
    private JLabel lblMaquina;
 
    public PantallaEliminarInventario(IControladorAplicacion controlador) {
        super(controlador);
 
        setTitle("Eliminar Equipo");
        setSize(740, 540);
        setLocationRelativeTo(null);
        setResizable(false);
 
        inicializarComponentes();
        cargarDatos();
 
        if (datosValidos) setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Colores.FONDO_PRINCIPAL);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setOpaque(true);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));
 
        root.add(crearHeader(),    BorderLayout.NORTH);
        root.add(crearContenido(), BorderLayout.CENTER);
        root.add(crearBotones(),   BorderLayout.SOUTH);
 
        setContentPane(root);
    }
 
 
    private JPanel crearHeader() {
        JPanel header = crearCard(0, 95);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 24, 20, 24));
 
        JLabel titulo = new JLabel("DAR DE BAJA EQUIPO", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
 
        header.add(titulo, BorderLayout.CENTER);
        return header;
    }
 
 
    private JPanel crearContenido() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setOpaque(false);
 
        JPanel card = crearCard(540, 280);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 38, 30, 38));
 
        JLabel subtitulo = new JLabel("Información de baja");
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        subtitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
        lblMaquina = new JLabel("Máquina: —");
        lblMaquina.setFont(Colores.FUENTE_LABEL);
        lblMaquina.setForeground(Colores.TEXTO_SECUNDARIO);
        lblMaquina.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        txtMotivo = new JTextArea(5, 30);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        txtMotivo.setFont(Colores.FUENTE_CAMPO);
        txtMotivo.setForeground(Colores.TEXTO_PRINCIPAL);
        txtMotivo.setBackground(Colores.FONDO_CAMPO);
        txtMotivo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        txtMotivo.setBorder(new EmptyBorder(10, 12, 10, 12));
 
        JScrollPane scroll = new JScrollPane(txtMotivo);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 115));
        scroll.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));
        scroll.getViewport().setBackground(Colores.FONDO_CAMPO);
 
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(10));
        card.add(sep);
        card.add(Box.createVerticalStrut(12));
        card.add(lblMaquina);
        card.add(Box.createVerticalStrut(24));
        card.add(crearLabel("Motivo de baja"));
        card.add(Box.createVerticalStrut(8));
        card.add(scroll);
 
        contenedor.add(card);
        return contenedor;
    }
 
 
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_PRINCIPAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
 
 
    private JPanel crearBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(4, 36, 16, 36));
 
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        Boton btnEliminar = crearBoton("Eliminar", Boton.Variante.PRIMARIO);
 
        btnRegresar.addActionListener(e -> controlador.irAInventarioMaquinas());
        btnEliminar.addActionListener(e -> eliminar());
 
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);
 
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnEliminar);
 
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);
 
        return panel;
    }
 
 
    private void cargarDatos() {
        maquina = controlador.getMaquinaSeleccionada();
        if (maquina == null) {
            datosValidos = false;
            JOptionPane.showMessageDialog(this,
                    "Selecciona una máquina antes de eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            dispose();
            return;
        }
        lblMaquina.setText("Máquina: " + maquina.getModelo() + " · " + maquina.getTipo());
    }
 
    private void eliminar() {
        try {
            if (maquina == null) {
                JOptionPane.showMessageDialog(this,
                        "No hay una máquina seleccionada.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                controlador.irAInventarioMaquinas();
                return;
            }
 
            String motivo = txtMotivo.getText();
            controlador.darBajaMaquinaInventario(maquina.getIdMaquina(), motivo);
 
            JOptionPane.showMessageDialog(this,
                    "Máquina dada de baja correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
 
            controlador.irAInventarioMaquinas();
 
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "No se pudo dar de baja la máquina", JOptionPane.ERROR_MESSAGE);
        }
    }
}
