/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasRutina;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.EntrenadorDTO;
import dtos.RutinaDTO;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaBusquedaEntrenador extends PantallaBase {
    private final RutinaDTO rutina;
    private final Consumer<String> callbackIdEntrenador;

    public PantallaBusquedaEntrenador(IControladorAplicacion controlador, RutinaDTO rutina, Consumer<String> callbackIdEntrenador) {
        super(controlador);
        this.rutina = rutina;
        this.callbackIdEntrenador = callbackIdEntrenador;
        setTitle("Buscar Entrenador");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        List<EntrenadorDTO> entrenadores = new ArrayList<>();
        try {
            entrenadores = controlador.obtenerEntrenadoresDeSucursalActual();
        } catch (NegocioException e) {
            entrenadores = new ArrayList<>();
        }

        // Panel de tarjetas
        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new BoxLayout(panelTarjetas, BoxLayout.Y_AXIS));
        panelTarjetas.setBackground(Colores.ACENTO);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        if (entrenadores.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay entrenadores disponibles en tu sucursal");
            lblVacio.setFont(Colores.FUENTE_SUBTITULO);
            lblVacio.setForeground(Color.BLACK);
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTarjetas.add(Box.createVerticalGlue());
            panelTarjetas.add(lblVacio);
            panelTarjetas.add(Box.createVerticalGlue());
        } else {
            for (EntrenadorDTO entrenador : entrenadores) {
                panelTarjetas.add(crearTarjetaEntrenador(entrenador));
                panelTarjetas.add(Box.createVerticalStrut(16));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panelTarjetas);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(Colores.ACENTO);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(16, 32, 32, 32));

        Boton btnVolver = crearBoton("Volver", Boton.Variante.SECUNDARIO);
        btnVolver.addActionListener(e -> controlador.irAVistaRutina());
        panelInferior.add(btnVolver, BorderLayout.WEST);

        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearTarjetaEntrenador(EntrenadorDTO entrenador) {
        JPanel tarjeta = new JPanel(new GridBagLayout()) {
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
        tarjeta.setOpaque(false);
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        String nombreCompleto = entrenador.getNombre();
        JLabel lblNombre = new JLabel(nombreCompleto.trim());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        tarjeta.add(lblNombre, gbc);

        JButton btnSeleccionar = crearBotonTarjeta("Seleccionar");
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        tarjeta.add(btnSeleccionar, gbc);
        
        btnSeleccionar.addActionListener(e -> {
            callbackIdEntrenador.accept(entrenador.getIdEntrenador());
        });

        return tarjeta;
    }

    private JButton crearBotonTarjeta(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_PRINCIPAL);
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
        btn.setPreferredSize(new Dimension(130, 36));
        return btn;
    }
}
