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
import dtos.RutinaDTO;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaVistaRutina extends PantallaBase {

    public PantallaVistaRutina(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Vista Rutina");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        List<RutinaDTO> rutinas = new ArrayList<>();
        try {
            rutinas = controlador.obtenerRutinas();
        } catch (NegocioException e) {
            rutinas = new ArrayList<>();
        }

        //panel del centro
        JPanel panelCentro;
        if (rutinas == null || rutinas.isEmpty()) {
            panelCentro = crearPanelSinRutinas();
        } else {
            panelCentro = crearPanelConRutinas(rutinas);
        }

        //panel de abajo donde van los botones de volver y crear rutina
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(16, 32, 32, 32));

        Boton btnVolver = crearBoton("Volver", Boton.Variante.SECUNDARIO);
        Boton btnCrearRutina = crearBoton("Crear Rutina", Boton.Variante.PRIMARIO);

        panelInferior.add(btnVolver, BorderLayout.WEST);
        panelInferior.add(btnCrearRutina, BorderLayout.EAST);

        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        
        btnVolver.addActionListener(e -> {
            controlador.irAPerfilUsuario();
        });
        
        btnCrearRutina.addActionListener(e -> {
        try {
            List<RutinaDTO> rutinascuenta = controlador.obtenerRutinas();
            if (rutinascuenta != null && rutinascuenta.size() >= 5) {
                JOptionPane.showMessageDialog(this,"No puede tener mas de 5 rutinas a la vez","Maximo de rutinas",
                JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.irAMensaje();
        } catch (NegocioException ex) {
            controlador.irAMensaje();
        }
        });
        
    }

    private JPanel crearPanelSinRutinas() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Colores.ACENTO);

        JLabel label = new JLabel("Usted aun no tiene rutinas");
        label.setFont(Colores.FUENTE_SUBTITULO);
        label.setForeground(Color.BLACK);
        panel.add(label);

        return panel;
    }

    private JPanel crearPanelConRutinas(List<RutinaDTO> rutinas) {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        panelContenedor.setBackground(Colores.ACENTO);

        JPanel panelTarjetas = new JPanel();
        panelTarjetas.setLayout(new BoxLayout(panelTarjetas, BoxLayout.Y_AXIS));
        panelTarjetas.setBackground(Colores.ACENTO);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        for (RutinaDTO rutina : rutinas) {
            panelTarjetas.add(crearTarjetaRutina(rutina));
            panelTarjetas.add(Box.createVerticalStrut(16));
        }

        JScrollPane scrollPane = new JScrollPane(panelTarjetas);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setBackground(Colores.ACENTO);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelContenedor.add(scrollPane, BorderLayout.CENTER);
        return panelContenedor;
    }

    private JPanel crearTarjetaRutina(RutinaDTO rutina) {
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
        tarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        tarjeta.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);

        //arriba izquierda tarjeta
        JLabel lblNombre = new JLabel(rutina.getNombre() != null ? rutina.getNombre() : "Sin nombre");
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        tarjeta.add(lblNombre, gbc);

        //arriba derecha tarjeta
        JButton btnDetalles = crearBotonTarjeta("Ver detalles");
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        
        btnDetalles.addActionListener(e -> {
            controlador.irAEditorRutinaExistente(rutina);
        });
        
        tarjeta.add(btnDetalles, gbc);
        
        //abajo izquierda tarjeta
        String fechaTexto = rutina.getFechaCreacion() != null
                ? rutina.getFechaCreacion().toLocalDate().toString()
                : "Sin fecha";
        JLabel lblFecha = new JLabel(fechaTexto);
        lblFecha.setFont(Colores.FUENTE_LABEL);
        lblFecha.setForeground(Colores.TEXTO_SECUNDARIO);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        tarjeta.add(lblFecha, gbc);

        //abajo derecha tarjeta
        JButton btnBorrar = crearBotonTarjeta("Borrar");
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        
        btnBorrar.addActionListener(e -> {
            try {
                boolean borrado = controlador.borrarRutina(rutina.getNombre());
                if (borrado) {
                    JOptionPane.showMessageDialog(this, "La rutina \"" + rutina.getNombre() + "\" ha sido borrada.", "Rutina borrada", JOptionPane.INFORMATION_MESSAGE);
                    controlador.irAVistaRutina();
                } else {
                    JOptionPane.showMessageDialog(this,"No se pudo borrar la rutina.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NegocioException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo borrar la rutina.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        tarjeta.add(btnBorrar, gbc);
        String nombre = "No asignado";
        try {
            nombre = controlador.obtenerEntrenadorPorId(rutina.getIdEntrenador()).getNombre();
        } catch (NegocioException ex) {
            Logger.getLogger(PantallaVistaRutina.class.getName()).log(Level.SEVERE, null, ex);
        }
        JLabel lblEntrenador = new JLabel("Entrenador: " + nombre);
        lblEntrenador.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblEntrenador.setForeground(Colores.TEXTO_PRINCIPAL);
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        tarjeta.add(lblEntrenador, gbc);
        
        JButton btnEntrenador = crearBotonTarjeta("Asignar entrenador");
        gbc.gridx = 1; gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0;
        tarjeta.add(btnEntrenador, gbc);
        
        btnEntrenador.addActionListener(e -> {
            controlador.irABusquedaEntrenador(rutina, idEntrenador -> {
                try {
                    rutina.setIdEntrenador(idEntrenador);
                    //en realidad no guarda una nueva, se actualiza porque esta mandando la msima pero con entrenador
                    //ver mi control de subssitema planear rutina para contexto
                    controlador.guardarRutina(rutina);
                    JOptionPane.showMessageDialog(this, "Entrenador asignado correctamente", "taco bell plis", JOptionPane.INFORMATION_MESSAGE);
                    controlador.irAVistaRutina();
                } catch (NegocioException ex) {
                    JOptionPane.showMessageDialog(this, "No se pudo asignar el entrenador", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
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
        btn.setPreferredSize(new Dimension(120, 36));
        return btn;
    }
}
