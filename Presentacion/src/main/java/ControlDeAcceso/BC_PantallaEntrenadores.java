/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.Icontrolacceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de entrenadores disponibles para asignacion al socio.
 * Solo presentacion: datos hardcodeados de prueba.
 *
 * Casos del storyboard:
 *   1. Entrenador LIBRE seleccionado  -> asignacion exitosa, regresa al expediente
 *   2. Entrenador OCUPADO             -> mensaje de saturacion, se queda en pantalla
 *   3. Plan no incluye entrenador     -> constructor (controlador, resultado, false)
 *
 * @author julian izaguirre
 */
public class BC_PantallaEntrenadores extends PantallaBase {

    private static final Object[][] ENTRENADORES = {
        // Nombre,      Estado,     Hora
        {"CARLOS",   "LIBRE",   "10:00 a.m."},
        {"FERNANDA", "LIBRE",   "11:00 a.m."},
        {"FUJIMA",   "OCUPADO", "12:00 a.m."},
    };

    // Resultado del acceso para poder regresar al expediente con los mismos datos
    private final ResultadoAccesoDTO resultado;
    private final boolean planIncluyeEntrenador;
    private int entrenadorSeleccionadoIndex = -1;
    private JPanel[] filas;

    public BC_PantallaEntrenadores(IControladorAplicacion controlador, ResultadoAccesoDTO resultado) {
        this(controlador, resultado, true);
    }

    public BC_PantallaEntrenadores(IControladorAplicacion controlador,
                                    ResultadoAccesoDTO resultado,
                                    boolean planIncluyeEntrenador) {
        super(controlador);
        this.resultado = resultado;
        this.planIncluyeEntrenador = planIncluyeEntrenador;
        setTitle("SteelCore Fitness - Entrenadores Disponibles");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(620, 560);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        JLabel logo = new JLabel("SteelCore", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Colores.ACENTO);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Entrenadores Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);

        // Regresar al expediente del socio
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);
        });

        // Caso: plan no incluye servicio de entrenador
        if (!planIncluyeEntrenador) {
            JLabel lblLinea1 = new JLabel("Tu membresia actual no incluye apoyo de entrenador.", SwingConstants.CENTER);
            lblLinea1.setFont(Colores.FUENTE_LABEL);
            lblLinea1.setForeground(new Color(220, 150, 50));
            lblLinea1.setAlignmentX(CENTER_ALIGNMENT);

            JLabel lblLinea2 = new JLabel("Pasa a recepcion o usa tu app para mejorar tu plan.", SwingConstants.CENTER);
            lblLinea2.setFont(Colores.FUENTE_LABEL);
            lblLinea2.setForeground(new Color(220, 150, 50));
            lblLinea2.setAlignmentX(CENTER_ALIGNMENT);

            card.add(logo);
            card.add(Box.createVerticalStrut(6));
            card.add(titulo);
            card.add(Box.createVerticalStrut(14));
            card.add(sep);
            card.add(Box.createVerticalStrut(30));
            card.add(lblLinea1);
            card.add(Box.createVerticalStrut(4));
            card.add(lblLinea2);
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // Filtros visuales (sin funcionalidad real en esta version de presentacion)
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFiltros.setOpaque(false);
        panelFiltros.setAlignmentX(LEFT_ALIGNMENT);
        panelFiltros.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        JLabel lblFiltrarPor = new JLabel("Filtrar por:");
        lblFiltrarPor.setFont(Colores.FUENTE_LABEL);
        lblFiltrarPor.setForeground(Colores.TEXTO_SECUNDARIO);

        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"ESTADO", "LIBRE", "OCUPADO"});
        cmbEstado.setFont(Colores.FUENTE_LABEL);
        cmbEstado.setBackground(Colores.FONDO_CAMPO);
        cmbEstado.setForeground(Colores.TEXTO_PRINCIPAL);
        cmbEstado.setPreferredSize(new Dimension(110, 30));

        JComboBox<String> cmbHorario = new JComboBox<>(
                new String[]{"HORARIO", "10:00 a.m.", "11:00 a.m.", "12:00 a.m."});
        cmbHorario.setFont(Colores.FUENTE_LABEL);
        cmbHorario.setBackground(Colores.FONDO_CAMPO);
        cmbHorario.setForeground(Colores.TEXTO_PRINCIPAL);
        cmbHorario.setPreferredSize(new Dimension(120, 30));

        panelFiltros.add(lblFiltrarPor);
        panelFiltros.add(cmbEstado);
        panelFiltros.add(cmbHorario);

        // Encabezado de la tabla
        JPanel encabezado = new JPanel(new GridLayout(1, 3, 0, 0));
        encabezado.setOpaque(false);
        encabezado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        encabezado.setAlignmentX(LEFT_ALIGNMENT);
        for (String col : new String[]{"NAME", "ESTADO", "Hora"}) {
            JLabel lbl = new JLabel(col, SwingConstants.LEFT);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(Colores.TEXTO_SECUNDARIO);
            encabezado.add(lbl);
        }

        // Filas de entrenadores
        JPanel listaEntrenadores = new JPanel();
        listaEntrenadores.setLayout(new BoxLayout(listaEntrenadores, BoxLayout.Y_AXIS));
        listaEntrenadores.setOpaque(false);
        listaEntrenadores.setAlignmentX(LEFT_ALIGNMENT);

        filas = new JPanel[ENTRENADORES.length];
        for (int i = 0; i < ENTRENADORES.length; i++) {
            final int idx = i;
            Object[] ent = ENTRENADORES[i];
            boolean ocupado = "OCUPADO".equals(ent[1]);

            JPanel fila = new JPanel(new GridLayout(1, 3, 0, 0));
            fila.setOpaque(true);
            fila.setBackground(Colores.FONDO_CAMPO);
            fila.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                    new EmptyBorder(10, 14, 10, 14)));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            fila.setAlignmentX(LEFT_ALIGNMENT);
            fila.setCursor(Cursor.getPredefinedCursor(
                    ocupado ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR));

            JLabel lblNombre = new JLabel((String) ent[0]);
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

            JLabel lblEstado = new JLabel((String) ent[1]);
            lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEstado.setForeground(ocupado ? new Color(200, 100, 80) : new Color(100, 220, 140));

            JLabel lblHora = new JLabel((String) ent[2]);
            lblHora.setFont(Colores.FUENTE_LABEL);
            lblHora.setForeground(Colores.TEXTO_SECUNDARIO);

            fila.add(lblNombre);
            fila.add(lblEstado);
            fila.add(lblHora);

            if (!ocupado) {
                fila.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e)  { seleccionarFila(idx); }
                    @Override public void mouseEntered(MouseEvent e)  {
                        if (entrenadorSeleccionadoIndex != idx)
                            fila.setBackground(Colores.ACENTO_PRESS);
                    }
                    @Override public void mouseExited(MouseEvent e)   {
                        if (entrenadorSeleccionadoIndex != idx)
                            fila.setBackground(Colores.FONDO_CAMPO);
                    }
                });
            }

            filas[i] = fila;
            listaEntrenadores.add(fila);
            listaEntrenadores.add(Box.createVerticalStrut(6));
        }

        JLabel lblTotal = new JLabel("Total de Entrenadores: 20");
        lblTotal.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblTotal.setForeground(Colores.TEXTO_SECUNDARIO);
        lblTotal.setAlignmentX(LEFT_ALIGNMENT);

        // Panel de botones en la parte inferior
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        Boton btnSolicitar = crearBoton("SOLICITAR", Boton.Variante.PRIMARIO);
        btnSolicitar.addActionListener(e -> procesarSolicitud());

        panelBotones.add(btnRegresar);
        panelBotones.add(btnSolicitar);

        card.add(logo);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(14));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
        card.add(panelFiltros);
        card.add(Box.createVerticalStrut(12));
        card.add(encabezado);
        card.add(Box.createVerticalStrut(8));
        card.add(listaEntrenadores);
        card.add(Box.createVerticalStrut(8));
        card.add(lblTotal);
        card.add(Box.createVerticalStrut(20));
        card.add(panelBotones);

        fondo.add(card);
    }

    private void seleccionarFila(int index) {
        if (entrenadorSeleccionadoIndex >= 0 && entrenadorSeleccionadoIndex < filas.length)
            filas[entrenadorSeleccionadoIndex].setBackground(Colores.FONDO_CAMPO);
        entrenadorSeleccionadoIndex = index;
        filas[index].setBackground(new Color(80, 60, 160));
    }

    private void procesarSolicitud() {
        if (entrenadorSeleccionadoIndex < 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un entrenador.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object[] ent = ENTRENADORES[entrenadorSeleccionadoIndex];

        // Caso: entrenador saturado, se queda en pantalla para elegir otro
        if ("OCUPADO".equals(ent[1])) {
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, la base de datos del Entrenador devuelve un estado de saturado.",
                    "Entrenador no disponible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Caso: asignacion exitosa, regresa al expediente del socio
        JOptionPane.showMessageDialog(this,
                "Entrenador asignado con exito: " + ent[0] + " - " + ent[2],
                "Entrenador asignado", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new BC_PantallaExpediente(controlador, resultado).setVisible(true);
    }
}