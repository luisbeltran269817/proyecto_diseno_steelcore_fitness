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
import dtos.DetalleRutinaDTO;
import dtos.EjercicioDTO;
import dtos.RutinaDTO;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author luiscarlosbeltran
 */
public class PantallaEditorRutina extends PantallaBase {

    private RutinaDTO rutinaEnEdicion;
    private int indiceDiaActual = 0;

    // Componentes que se actualizan al cambiar de día
    private JLabel lblNombreDia;
    private JLabel lblGrupoMuscular;
    private JTextArea areaEjercicios;

    public PantallaEditorRutina(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Editor de Rutina");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        RutinaDTO rutinaRecibida = controlador.getRutinaSeleccionada();
        rutinaEnEdicion = (rutinaRecibida != null) ? rutinaRecibida : inicializarRutinaVacia();

        // Panel superior - nombre de la rutina
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSuperior.setOpaque(false);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(32, 0, 16, 0));

        JTextField campNombre = new JTextField(rutinaEnEdicion.getNombre(), 30);
        campNombre.setFont(Colores.FUENTE_CAMPO);
        campNombre.setForeground(Colores.TEXTO_PRINCIPAL);
        campNombre.setBackground(Colores.FONDO_CAMPO);
        campNombre.setCaretColor(Colores.TEXTO_PRINCIPAL);
        campNombre.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        campNombre.setPreferredSize(new Dimension(400, 42));

        JLabel lblCampoNombre = new JLabel("Nombre de la rutina:  ");
        lblCampoNombre.setFont(Colores.FUENTE_LABEL);
        lblCampoNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        panelSuperior.add(lblCampoNombre);
        panelSuperior.add(campNombre);

        // Panel central - navegador de días
        JPanel panelCentro = new JPanel(new GridBagLayout());
        panelCentro.setOpaque(false);

        // Flecha izquierda
        JButton btnIzquierda = crearBotonFlecha("<");
        btnIzquierda.setPreferredSize(new Dimension(70, 52));
        btnIzquierda.addActionListener(e -> {
            if (indiceDiaActual > 0) {
                indiceDiaActual--;
                actualizarDia();
            }
        });

        // Flecha derecha
        JButton btnDerecha = crearBotonFlecha(">");
        btnDerecha.setPreferredSize(new Dimension(70, 52));
        btnDerecha.addActionListener(e -> {
            if (indiceDiaActual < rutinaEnEdicion.getDetalles().size() - 1) {
                indiceDiaActual++;
                actualizarDia();
            }
        });

        // Frame del día
        JPanel panelDia = crearPanelDia();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 16, 0, 16);
        gbc.gridy = 0;

        gbc.gridx = 0; panelCentro.add(btnIzquierda, gbc);
        gbc.gridx = 1; panelCentro.add(panelDia, gbc);
        gbc.gridx = 2; panelCentro.add(btnDerecha, gbc);

        // Panel inferior - botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 24, 0));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(16, 32, 32, 32));

        Boton btnVolver = crearBoton("Volver", Boton.Variante.SECUNDARIO);
        Boton btnEditarDia = crearBoton("Editar Dia", Boton.Variante.SECUNDARIO);
        Boton btnConfirmar = crearBoton("Confirmar Rutina", Boton.Variante.PRIMARIO);
        
        btnVolver.addActionListener(e -> {
            controlador.irAVistaRutina();
        });
        
        //para abrir la pantallita de ejercicios
        btnEditarDia.addActionListener(e -> {
            DetalleRutinaDTO detalleActual = rutinaEnEdicion.getDetalles().get(indiceDiaActual);
            controlador.abrirSeleccionEjercicios(detalleActual.getNombreDia(), grupo -> detalleActual.setGrupoMuscular(grupo),
            ejercicios -> {
                detalleActual.setEjercicios(ejercicios);
                actualizarDia();
            }
            );
        });
        
        btnConfirmar.addActionListener(e -> {
            String nombre = campNombre.getText().trim();
            if (nombre.isBlank()) {
                mostrarError("El nombre de la rutina no puede estar vacío.");
                return;
            }
            rutinaEnEdicion.setNombre(nombre);
            try {
                controlador.guardarRutina(rutinaEnEdicion);
                JOptionPane.showMessageDialog(this, "Rutina guardada correctamente",  "Yo quiero taco bell", JOptionPane.INFORMATION_MESSAGE);
                controlador.irAVistaRutina();
            } catch (NegocioException ex) {
                mostrarError(ex.getMessage());
            }
        });

        panelInferior.add(btnVolver);
        panelInferior.add(btnEditarDia);
        panelInferior.add(btnConfirmar);

        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentro, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelDia() {
        JPanel panel = new JPanel(new BorderLayout()) {
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
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(380, 280));

        //nombre del dia y grupo
        JPanel panelBanner = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panelBanner.setBackground(Colores.ACENTO);
        panelBanner.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        lblNombreDia = new JLabel(rutinaEnEdicion.getDetalles().get(0).getNombreDia());
        lblNombreDia.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNombreDia.setForeground(Color.BLACK);
        
        String grupoInicial = rutinaEnEdicion.getDetalles().get(0).getGrupoMuscular();
        String textoGrupo = (grupoInicial != null && !grupoInicial.isBlank()) ? grupoInicial : "Sin elección";
        lblGrupoMuscular = new JLabel("— " + textoGrupo);
        lblGrupoMuscular.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblGrupoMuscular.setForeground(Color.BLACK);
        
        panelBanner.add(lblNombreDia);
        panelBanner.add(lblGrupoMuscular);
        
        // Área de ejercicios
        areaEjercicios = new JTextArea();
        areaEjercicios.setFont(Colores.FUENTE_LABEL);
        areaEjercicios.setForeground(Color.BLACK);
        areaEjercicios.setBackground(Color.decode("#CCCCCC"));
        areaEjercicios.setEditable(false);
        areaEjercicios.setLineWrap(true);
        areaEjercicios.setWrapStyleWord(true);
        areaEjercicios.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        areaEjercicios.setText(obtenerTextoEjercicios(0));

        JScrollPane scrollEjercicios = new JScrollPane(areaEjercicios);
        scrollEjercicios.setBorder(null);
        scrollEjercicios.setOpaque(false);

        panel.add(panelBanner, BorderLayout.NORTH);
        panel.add(scrollEjercicios, BorderLayout.CENTER);

        return panel;
    }

    private JButton crearBotonFlecha(String simbolo) {
        JButton btn = new JButton(simbolo) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setForeground(Colores.TEXTO_PRINCIPAL);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(52, 52));
        return btn;
    }

    private void actualizarDia() {
        DetalleRutinaDTO detalle = rutinaEnEdicion.getDetalles().get(indiceDiaActual);
        lblNombreDia.setText(detalle.getNombreDia());
        String grupo = detalle.getGrupoMuscular();
        lblGrupoMuscular.setText("— " + ((grupo != null && !grupo.isBlank()) ? grupo : "Sin elección"));
        areaEjercicios.setText(obtenerTextoEjercicios(indiceDiaActual));
    }

    private String obtenerTextoEjercicios(int indice) {
        DetalleRutinaDTO detalle = rutinaEnEdicion.getDetalles().get(indice);
        if (detalle.getEjercicios() == null || detalle.getEjercicios().isEmpty()) {
            return "No hay ejercicios";
        }
        StringBuilder sb = new StringBuilder();
        for (EjercicioDTO ejercicio : detalle.getEjercicios()) {
            //con una viñeta se ven bien pros B) 
            sb.append("• ").append(ejercicio.getNombre()).append("\n");
            if (ejercicio.getDescripcion() != null && !ejercicio.getDescripcion().isBlank()) {
                sb.append("   ").append(ejercicio.getDescripcion()).append("\n");
            }
            sb.append("\n");
        }
        return sb.toString().trim();
    }

    private RutinaDTO inicializarRutinaVacia() {
        RutinaDTO rutina = new RutinaDTO();
        rutina.setNombre("");
        rutina.setFechaCreacion(LocalDateTime.now());
        List<DetalleRutinaDTO> detalles = new ArrayList<>();
        for (String dia : new String[]{"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"}) {
            DetalleRutinaDTO detalle = new DetalleRutinaDTO();
            detalle.setNombreDia(dia);
            detalle.setGrupoMuscular("Descanso");
            detalle.setEjercicios(new ArrayList<>());
            detalles.add(detalle);
        }
        rutina.setDetalles(detalles);
        return rutina;
    }
    
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}