/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasRutina;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Colores;
import dtos.EjercicioDTO;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaSeleccionEjercicios extends JDialog {

    private final String nombreDia;
    private JPanel panelEjercicios;
    private List<EjercicioDTO> ejerciciosActuales;
    private final List<EjercicioDTO> ejerciciosSeleccionados = new ArrayList<>();
    private final Consumer<String> callbackGrupo;
    private final Consumer<List<EjercicioDTO>> callbackEjercicios;
    private String grupoSeleccionado = null;

    public PantallaSeleccionEjercicios(JFrame parent, IControladorAplicacion controlador, String nombreDia, Consumer<String> callbackGrupo, Consumer<List<EjercicioDTO>> callbackEjercicios) {
        super(parent, "Seleccionar Ejercicios - " + nombreDia, true);
        this.controlador = controlador;
        this.nombreDia = nombreDia;
        this.callbackGrupo = callbackGrupo;
        this.callbackEjercicios = callbackEjercicios;
        setSize(800, 600);
        setLocationRelativeTo(parent);
        getContentPane().setBackground(Colores.FONDO_PRINCIPAL);
        setLayout(new BorderLayout());
        inicializarComponentes();
        setVisible(true);
    }

    private IControladorAplicacion controlador;

    private void inicializarComponentes() {

        // Panel izquierdo - grupos musculares
        JPanel panelGrupos = new JPanel();
        panelGrupos.setLayout(new BoxLayout(panelGrupos, BoxLayout.Y_AXIS));
        panelGrupos.setBackground(Colores.FONDO_CARD);
        panelGrupos.setBorder(BorderFactory.createEmptyBorder(24, 16, 24, 16));
        panelGrupos.setPreferredSize(new Dimension(180, 0));

        JLabel lblGrupos = new JLabel("Grupo muscular");
        lblGrupos.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblGrupos.setForeground(Colores.TEXTO_PRINCIPAL);
        lblGrupos.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelGrupos.add(lblGrupos);
        panelGrupos.add(Box.createVerticalStrut(20));
        
        //si en el futuro se quieren meter mas ejercicios nomas meter el grupo muscular aqui
        //pero como mi caso no es nada de insertar nuevos ejercicios, pues aqui esta esto
        //el grupo debe ser exacto a como esta en BD
        String[] grupos = {"Pecho", "Espalda", "Pierna", "Descanso"};
        for (String grupo : grupos) {
            JButton btnGrupo = crearBotonGrupo(grupo);
            btnGrupo.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnGrupo.addActionListener(e -> cargarEjercicios(grupo));
            panelGrupos.add(btnGrupo);
            panelGrupos.add(Box.createVerticalStrut(12));
        }

        // Panel derecho - ejercicios
        panelEjercicios = new JPanel();
        panelEjercicios.setLayout(new BoxLayout(panelEjercicios, BoxLayout.Y_AXIS));
        panelEjercicios.setBackground(Colores.FONDO_PRINCIPAL);
        panelEjercicios.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JScrollPane scrollEjercicios = new JScrollPane(panelEjercicios);
        scrollEjercicios.setOpaque(false);
        scrollEjercicios.getViewport().setBackground(Colores.FONDO_PRINCIPAL);
        scrollEjercicios.setBorder(null);
        scrollEjercicios.getVerticalScrollBar().setUnitIncrement(16);

        JLabel lblInstruccion = new JLabel("Selecciona un grupo muscular");
        lblInstruccion.setFont(Colores.FUENTE_SUBTITULO);
        lblInstruccion.setForeground(Colores.TEXTO_SECUNDARIO);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelEjercicios.add(Box.createVerticalGlue());
        panelEjercicios.add(lblInstruccion);
        panelEjercicios.add(Box.createVerticalGlue());

        // Panel inferior - botones
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(12, 24, 24, 24));

        JButton btnCancelar = crearBotonDialog("Cancelar", Colores.BTN_SECUNDARIO, Colores.BTN_SECUNDARIO_H, Colores.BTN_SECUNDARIO_P);
        JButton btnConfirmar = crearBotonDialog("Confirmar", Colores.BTN_PRIMARIO, Colores.BTN_PRIMARIO_H, Colores.BTN_PRIMARIO_P);

        btnCancelar.addActionListener(e -> dispose());
        
        btnConfirmar.addActionListener(e -> {
            if ("Descanso".equals(grupoSeleccionado)) {
                ejerciciosSeleccionados.clear();
            }
            if (grupoSeleccionado != null) {
                callbackGrupo.accept(grupoSeleccionado);
                callbackEjercicios.accept(ejerciciosSeleccionados);
            }
            dispose();
        });
        
        panelInferior.add(btnCancelar, BorderLayout.WEST);
        panelInferior.add(btnConfirmar, BorderLayout.EAST);

        add(panelGrupos, BorderLayout.WEST);
        add(scrollEjercicios, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarEjercicios(String grupoMuscular) {
        this.grupoSeleccionado = grupoMuscular;
        panelEjercicios.removeAll();
        ejerciciosActuales = new ArrayList<>();

        if ("Descanso".equals(grupoMuscular)) {
            JLabel lblDescanso = new JLabel("Día de descanso, sin ejercicios");
            lblDescanso.setFont(Colores.FUENTE_SUBTITULO);
            lblDescanso.setForeground(Colores.TEXTO_SECUNDARIO);
            lblDescanso.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelEjercicios.add(Box.createVerticalGlue());
            panelEjercicios.add(lblDescanso);
            panelEjercicios.add(Box.createVerticalGlue());
            panelEjercicios.revalidate();
            panelEjercicios.repaint();
            return;
        }

        try {
            ejerciciosActuales = controlador.recuperarEjercicios(grupoMuscular);
        } catch (NegocioException e) {
            ejerciciosActuales = new ArrayList<>();
        }

        if (ejerciciosActuales.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay ejercicios para este grupo");
            lblVacio.setFont(Colores.FUENTE_SUBTITULO);
            lblVacio.setForeground(Colores.TEXTO_SECUNDARIO);
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelEjercicios.add(Box.createVerticalGlue());
            panelEjercicios.add(lblVacio);
            panelEjercicios.add(Box.createVerticalGlue());
        } else {
            for (EjercicioDTO ejercicio : ejerciciosActuales) {
                panelEjercicios.add(crearTarjetaEjercicio(ejercicio));
                panelEjercicios.add(Box.createVerticalStrut(12));
            }
        }

        panelEjercicios.revalidate();
        panelEjercicios.repaint();
    }

    private JPanel crearTarjetaEjercicio(EjercicioDTO ejercicio) {
        JPanel tarjeta = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 14, 14);
                g2.dispose();
            }
        };
        tarjeta.setOpaque(false);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

        // Checkbox a la izquierda
        JCheckBox checkBox = new JCheckBox();
        checkBox.setOpaque(false);
        checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()) {
                ejerciciosSeleccionados.add(ejercicio);
            } else {
                ejerciciosSeleccionados.removeIf(ej -> ej.getIdEjercicio().equals(ejercicio.getIdEjercicio()));
            }
        });

        // Panel de texto: nombre arriba, descripcion abajo
        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 4));
        panelTexto.setOpaque(false);
        panelTexto.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));

        JLabel lblNombre = new JLabel(ejercicio.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel lblDescripcion = new JLabel(ejercicio.getDescripcion());
        lblDescripcion.setFont(Colores.FUENTE_LABEL);
        lblDescripcion.setForeground(Colores.TEXTO_SECUNDARIO);

        panelTexto.add(lblNombre);
        panelTexto.add(lblDescripcion);

        tarjeta.add(checkBox, BorderLayout.WEST);
        tarjeta.add(panelTexto, BorderLayout.CENTER);

        return tarjeta;
    }

    private JButton crearBotonGrupo(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(Colores.FUENTE_BOTON_SM);
        btn.setForeground(Colores.TEXTO_PRINCIPAL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(148, 40));
        return btn;
    }

    private JButton crearBotonDialog(String texto, Color base, Color hover, Color press) {
        Color[] colorActual = {base};
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(colorActual[0]);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(Colores.ACENTO.brighter());
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(Colores.FUENTE_BOTON);
        btn.setForeground(Colores.TEXTO_PRINCIPAL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 44));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { colorActual[0] = hover;  btn.repaint(); }
            @Override public void mouseExited(MouseEvent e)   { colorActual[0] = base;   btn.repaint(); }
            @Override public void mousePressed(MouseEvent e)  { colorActual[0] = press;  btn.repaint(); }
            @Override public void mouseReleased(MouseEvent e) { colorActual[0] = hover;  btn.repaint(); }
        });
        return btn;
    }

    public List<EjercicioDTO> getEjerciciosSeleccionados() {
        return ejerciciosSeleccionados;
    }
    
    public String getGrupoSeleccionado() {
        return grupoSeleccionado;
    }
}
