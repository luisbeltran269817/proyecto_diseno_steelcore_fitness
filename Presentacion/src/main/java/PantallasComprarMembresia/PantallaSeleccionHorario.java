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
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionHorario extends PantallaBase{
    private HorarioDTO horarioSeleccionado;
    private ButtonGroup grupoBotones;
    private JPanel panelHorarios;
 
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm");
 
    public PantallaSeleccionHorario(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Horario - SteelCore Fitness");
        inicializarComponentes();
        cargarHorarios();
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
 
        EntrenadorDTO entrenador = controlador.getEntrenadorSeleccionado();
        String nombreEntrenador = (entrenador != null) ? entrenador.getNombre() : "Instructor";
 
        JLabel titulo = new JLabel("Elige tu Horario");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Instructor: " + nombreEntrenador);
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.ACENTO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        panelHorarios = new JPanel();
        panelHorarios.setLayout(new BoxLayout(panelHorarios, BoxLayout.Y_AXIS));
        panelHorarios.setOpaque(false);
        panelHorarios.setAlignmentX(CENTER_ALIGNMENT);
        grupoBotones = new ButtonGroup();
 
        JScrollPane scroll = new JScrollPane(panelHorarios);
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
 
        Boton btnAtras     = crearBoton("Atrás",      Boton.Variante.SECUNDARIO);
        Boton btnContinuar = crearBoton("Confirmar",  Boton.Variante.PRIMARIO);
 
        btnAtras.addActionListener(e -> controlador.irASeleccionInstructor());
 
        btnContinuar.addActionListener(e -> {
            if (horarioSeleccionado == null) {
                JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un horario.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.setHorarioSeleccionado(horarioSeleccionado);
            try {
                controlador.confirmarCitaBienvenida();
            } catch (RuntimeException ex) {
                JOptionPane.showMessageDialog(this,
                    ex.getMessage(), "Error al agendar",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            controlador.irAQR();
        });
 
        panelBtns.add(btnAtras);
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
 
    private void cargarHorarios() {
        EntrenadorDTO entrenador = controlador.getEntrenadorSeleccionado();
        panelHorarios.removeAll();
 
        if (entrenador == null) {
            panelHorarios.add(mensajeSinHorarios());
            panelHorarios.revalidate();
            return;
        }
 
        List<HorarioDTO> horarios =
            controlador.obtenerHorariosDeEntrenador(entrenador.getIdEntrenador());
 
        if (horarios == null || horarios.isEmpty()) {
            panelHorarios.add(mensajeSinHorarios());
        } else {
            for (HorarioDTO h : horarios) {
                panelHorarios.add(crearFilaHorario(h));
                panelHorarios.add(Box.createVerticalStrut(10));
            }
        }
 
        panelHorarios.revalidate();
        panelHorarios.repaint();
    }
 
    private JPanel mensajeSinHorarios() {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
 
        JLabel ico = new JLabel("⚠", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        ico.setForeground(new Color(230, 180, 60));
        ico.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel msg = new JLabel("Lo sentimos, no hay horarios disponibles.");
        msg.setFont(Colores.FUENTE_LABEL);
        msg.setForeground(Colores.TEXTO_SECUNDARIO);
        msg.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Puedes agendar tu cita más tarde desde tu perfil.");
        sub.setFont(Colores.FUENTE_LABEL);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnIrQR = crearBoton("Continuar de todas formas", Boton.Variante.PRIMARIO);
        btnIrQR.setAlignmentX(CENTER_ALIGNMENT);
        btnIrQR.addActionListener(e -> controlador.irAQR());
 
        p.add(Box.createVerticalStrut(30));
        p.add(ico);
        p.add(Box.createVerticalStrut(14));
        p.add(msg);
        p.add(Box.createVerticalStrut(6));
        p.add(sub);
        p.add(Box.createVerticalStrut(24));
        p.add(btnIrQR);
        return p;
    }
 
    private JPanel crearFilaHorario(HorarioDTO h) {
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
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
 
        String textoInicio = (h.getInicio() != null) ? h.getInicio().format(FMT) : "—";
        String textoFin    = (h.getFin()    != null) ? h.getFin().format(FMT)    : "—";
 
        JLabel lblInicio = new JLabel("Inicio: " + textoInicio);
        lblInicio.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInicio.setForeground(Colores.TEXTO_PRINCIPAL);
 
        JLabel lblFin = new JLabel("Fin:     " + textoFin);
        lblFin.setFont(Colores.FUENTE_LABEL);
        lblFin.setForeground(Colores.TEXTO_SECUNDARIO);
 
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(lblInicio);
        info.add(Box.createVerticalStrut(4));
        info.add(lblFin);
 
        JRadioButton radio = new JRadioButton("Seleccionar");
        radio.setFont(Colores.FUENTE_BOTON_SM);
        radio.setForeground(Colores.TEXTO_PRINCIPAL);
        radio.setOpaque(false);
        radio.addActionListener(e -> horarioSeleccionado = h);
        grupoBotones.add(radio);
 
        card.add(info,  BorderLayout.CENTER);
        card.add(radio, BorderLayout.EAST);
        return card;
    }
}
