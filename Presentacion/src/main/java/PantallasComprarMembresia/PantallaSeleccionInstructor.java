/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.SucursalDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionInstructor extends PantallaBase {
    private EntrenadorDTO entrenadorSeleccionado;
    private ButtonGroup grupoBotones;
    private JPanel panelEntrenadores;
 
    public PantallaSeleccionInstructor(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Instructor - SteelCore Fitness");
        inicializarComponentes();
        cargarEntrenadores();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(780, 580));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));
 
        JLabel titulo = new JLabel("Selecciona tu Instructor");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Tu cita de bienvenida es completamente gratuita");
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.ACENTO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        panelEntrenadores = new JPanel();
        panelEntrenadores.setLayout(new BoxLayout(panelEntrenadores, BoxLayout.Y_AXIS));
        panelEntrenadores.setOpaque(false);
        panelEntrenadores.setAlignmentX(CENTER_ALIGNMENT);
        grupoBotones = new ButtonGroup();
 
        JScrollPane scroll = new JScrollPane(panelEntrenadores);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(740, 340));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));
        scroll.setAlignmentX(CENTER_ALIGNMENT);
 
        // Botones
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnOmitir    = crearBoton("En otro momento", Boton.Variante.SECUNDARIO);
        Boton btnContinuar = crearBoton("Continuar",       Boton.Variante.PRIMARIO);
 
        btnOmitir.addActionListener(e -> controlador.irAQR());
 
        btnContinuar.addActionListener(e -> {
            if (entrenadorSeleccionado == null) {
                JOptionPane.showMessageDialog(this,
                    "Selecciona un instructor.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Verificar si el entrenador tiene horarios disponibles
            List<HorarioDTO> horarios = controlador.obtenerHorariosDeEntrenador(
                entrenadorSeleccionado.getIdEntrenador());
 
            if (horarios == null || horarios.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Lo sentimos, este instructor no cuenta con horarios disponibles.\n"
                    + "Por favor elige otro o intenta más tarde.",
                    "Sin horarios", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            controlador.setEntrenadorSeleccionado(entrenadorSeleccionado);
            controlador.irASeleccionHorario();
        });
 
        panelBtns.add(btnOmitir);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnContinuar);
 
        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(8));
        contenedor.add(sub);
        contenedor.add(Box.createVerticalStrut(28));
        contenedor.add(scroll);
        contenedor.add(Box.createVerticalStrut(24));
        contenedor.add(panelBtns);
 
        fondo.add(contenedor);
    }
 
    private void cargarEntrenadores() {
        SucursalDTO sucursal = controlador.getSucursalSeleccionada();
        if (sucursal == null) return;
 
        List<EntrenadorDTO> entrenadores =
            controlador.obtenerEntrenadoresDeSucursal(sucursal.getIdSucursal());
 
        panelEntrenadores.removeAll();
 
        if (entrenadores == null || entrenadores.isEmpty()) {
            JLabel sinEntrenadores = new JLabel("No hay instructores disponibles en esta sucursal.");
            sinEntrenadores.setFont(Colores.FUENTE_LABEL);
            sinEntrenadores.setForeground(Colores.TEXTO_SECUNDARIO);
            sinEntrenadores.setAlignmentX(CENTER_ALIGNMENT);
            panelEntrenadores.add(sinEntrenadores);
        } else {
            for (EntrenadorDTO e : entrenadores) {
                panelEntrenadores.add(crearFilaEntrenador(e));
                panelEntrenadores.add(Box.createVerticalStrut(10));
            }
        }
 
        panelEntrenadores.revalidate();
        panelEntrenadores.repaint();
    }
 
    private JPanel crearFilaEntrenador(EntrenadorDTO e) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(14, 0));
        card.setBorder(new EmptyBorder(18, 20, 18, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
 
        // Avatar con inicial
        JLabel avatar = new JLabel(String.valueOf(e.getNombre().charAt(0)).toUpperCase(),
                                   SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 20));
        avatar.setForeground(Colores.TEXTO_PRINCIPAL);
        avatar.setOpaque(true);
        avatar.setBackground(Colores.ACENTO);
        avatar.setPreferredSize(new Dimension(46, 46));
        avatar.setBorder(BorderFactory.createEmptyBorder());
 
        JLabel nombre = new JLabel(e.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nombre.setForeground(Colores.TEXTO_PRINCIPAL);
 
        JLabel idLabel = new JLabel("ID: " + e.getIdEntrenador());
        idLabel.setFont(Colores.FUENTE_LABEL);
        idLabel.setForeground(Colores.TEXTO_SECUNDARIO);
 
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(nombre);
        info.add(Box.createVerticalStrut(3));
        info.add(idLabel);
 
        JRadioButton radio = new JRadioButton("Seleccionar");
        radio.setFont(Colores.FUENTE_BOTON_SM);
        radio.setForeground(Colores.TEXTO_PRINCIPAL);
        radio.setOpaque(false);
        radio.addActionListener(ev -> entrenadorSeleccionado = e);
        grupoBotones.add(radio);
 
        card.add(avatar, BorderLayout.WEST);
        card.add(info,   BorderLayout.CENTER);
        card.add(radio,  BorderLayout.EAST);
        return card;
    }
}
