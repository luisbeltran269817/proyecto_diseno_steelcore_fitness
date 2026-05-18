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
 * Pantalla de clases disponibles para el plan del socio.
 * Solo presentacion: datos hardcodeados de prueba.
 *
 * Casos del storyboard:
 *   1. Inscripcion exitosa      -> regresa al expediente del socio
 *   2. Cupo lleno               -> mensaje, se queda en pantalla para elegir otra
 *   3. Plan no incluye clases   -> constructor (controlador, resultado, false)
 *
 * @author julian izaguirre
 */
public class BC_PantallaClases extends PantallaBase {

    private static final String[][] CLASES = {
        {"Yoga",     "10:00 AM", "DISPONIBLE"},
        {"Spinning", "11:00 AM", "DISPONIBLE"},
        {"Pilates",  "5:00 PM",  "LLENA"},
    };

    // Resultado del acceso para poder regresar al expediente con los mismos datos
    private final ResultadoAccesoDTO resultado;
    private final boolean planIncluyeClases;
    private int claseSeleccionadaIndex = -1;
    private JPanel[] filas;

    public BC_PantallaClases(IControladorAplicacion controlador, ResultadoAccesoDTO resultado) {
        this(controlador, resultado, true);
    }

    public BC_PantallaClases(IControladorAplicacion controlador,
                              ResultadoAccesoDTO resultado,
                              boolean planIncluyeClases) {
        super(controlador);
        this.resultado = resultado;
        this.planIncluyeClases = planIncluyeClases;
        setTitle("SteelCore Fitness - Clases Disponibles");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(560, 560);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        JLabel logo = new JLabel("SteelCore", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Colores.ACENTO);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Clases Disponibles para tu Plan", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);

        // Regresar al expediente del socio
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.setAlignmentX(CENTER_ALIGNMENT);
        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);
        });

        // Caso: plan no incluye clases
        if (!planIncluyeClases) {
            JLabel lblLinea1 = new JLabel("Lo sentimos, tu plan no incluye inscripcion", SwingConstants.CENTER);
            lblLinea1.setFont(Colores.FUENTE_LABEL);
            lblLinea1.setForeground(new Color(220, 150, 50));
            lblLinea1.setAlignmentX(CENTER_ALIGNMENT);

            JLabel lblLinea2 = new JLabel("a las clases del gimnasio.", SwingConstants.CENTER);
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

        // Lista de clases disponibles
        JPanel listaClases = new JPanel();
        listaClases.setLayout(new BoxLayout(listaClases, BoxLayout.Y_AXIS));
        listaClases.setOpaque(false);
        listaClases.setAlignmentX(LEFT_ALIGNMENT);

        filas = new JPanel[CLASES.length];
        for (int i = 0; i < CLASES.length; i++) {
            final int idx = i;
            String[] clase = CLASES[i];
            boolean llena = "LLENA".equals(clase[2]);

            JPanel fila = new JPanel(new BorderLayout(12, 0));
            fila.setOpaque(true);
            fila.setBackground(Colores.FONDO_CAMPO);
            fila.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                    new EmptyBorder(12, 16, 12, 16)));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));
            fila.setAlignmentX(LEFT_ALIGNMENT);
            fila.setCursor(Cursor.getPredefinedCursor(
                    llena ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR));

            JLabel lblClase = new JLabel(clase[0] + " - " + clase[1]);
            lblClase.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblClase.setForeground(llena ? new Color(180, 90, 90) : Colores.TEXTO_PRINCIPAL);

            // Estado visible al lado derecho de la fila
            JLabel lblEstado = new JLabel(llena ? "LLENA" : "DISPONIBLE");
            lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEstado.setForeground(llena ? new Color(200, 80, 80) : new Color(100, 220, 140));

            fila.add(lblClase, BorderLayout.CENTER);
            fila.add(lblEstado, BorderLayout.EAST);

            if (!llena) {
                fila.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e)  { seleccionarFila(idx); }
                    @Override public void mouseEntered(MouseEvent e)  {
                        if (claseSeleccionadaIndex != idx)
                            fila.setBackground(Colores.ACENTO_PRESS);
                    }
                    @Override public void mouseExited(MouseEvent e)   {
                        if (claseSeleccionadaIndex != idx)
                            fila.setBackground(Colores.FONDO_CAMPO);
                    }
                });
            }

            filas[i] = fila;
            listaClases.add(fila);
            listaClases.add(Box.createVerticalStrut(8));
        }

        Boton btnInscribirse = crearBoton("INSCRIBIRSE", Boton.Variante.PRIMARIO);
        btnInscribirse.setAlignmentX(CENTER_ALIGNMENT);
        btnInscribirse.addActionListener(e -> procesarInscripcion());

        card.add(logo);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(14));
        card.add(sep);
        card.add(Box.createVerticalStrut(18));
        card.add(listaClases);
        card.add(Box.createVerticalStrut(24));
        card.add(btnInscribirse);
        card.add(Box.createVerticalStrut(10));
        card.add(btnRegresar);

        fondo.add(card);
    }

    private void seleccionarFila(int index) {
        if (claseSeleccionadaIndex >= 0 && claseSeleccionadaIndex < filas.length)
            filas[claseSeleccionadaIndex].setBackground(Colores.FONDO_CAMPO);
        claseSeleccionadaIndex = index;
        filas[index].setBackground(new Color(80, 60, 160));
    }

    private void procesarInscripcion() {
        if (claseSeleccionadaIndex < 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una clase primero.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] clase = CLASES[claseSeleccionadaIndex];

        // Caso: cupo lleno, se queda en pantalla para que el socio elija otra
        if ("LLENA".equals(clase[2])) {
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, el cupo para esta clase ya esta lleno.\n"
                    + "Por favor selecciona otro horario.",
                    "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Caso: inscripcion exitosa, regresa al expediente
        JOptionPane.showMessageDialog(this,
                "Inscripcion realizada correctamente: " + clase[0] + " - " + clase[1],
                "Inscripcion exitosa", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        new BC_PantallaExpediente(controlador, resultado).setVisible(true);
    }
}