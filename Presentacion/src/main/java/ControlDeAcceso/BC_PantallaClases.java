package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de clases disponibles para el plan del socio.
 *
 * Usa dtosControlDeAcceso.ClaseDTO (DTO real de la fachada).
 * ClaseDTO tiene: idClase, nombre, horario (LocalTime), cupoDisponible,
 *                 cupoMaximo, diaSemana. estaLlena() = cupoDisponible <= 0.
 *
 * La fachada ya guarda el contexto de la visita activa (idVisita, idCliente,
 * idPlan, idSucursal) — las pantallas NO repiten esos IDs al llamar metodos
 * de accion como inscribirClase().
 */
public class BC_PantallaClases extends PantallaBase {

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final ResultadoAccesoDTO resultado;
    private final boolean planIncluyeClases;
    private final Icontrolacceso controlAcceso = FachadaControlAcceso.getInstancia();

    private List<ClaseDTO> clases = new ArrayList<>();
    private int    claseSeleccionadaIndex = -1;
    private JPanel[] filas;

    public BC_PantallaClases(IControladorAplicacion controlador,
                              ResultadoAccesoDTO resultado,
                              boolean planIncluyeClases) {
        super(controlador);
        this.resultado         = resultado;
        this.planIncluyeClases = planIncluyeClases;
        setTitle("SteelCore Fitness - Clases Disponibles");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(560, 580);
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

        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.setAlignmentX(CENTER_ALIGNMENT);
        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);
        });

        card.add(logo);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(14));
        card.add(sep);
        card.add(Box.createVerticalStrut(18));

        // ── Caso 1: plan no incluye clases ────────────────────────────────────
        if (!planIncluyeClases) {
            agregarMensaje(card,
                "Lo sentimos, tu plan no incluye inscripcion",
                "a las clases del gimnasio.",
                new Color(220, 150, 50));
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // Cargar clases reales de MongoDB via fachada
        // La fachada ya conoce idSucursal, idPlan e idCliente internamente
        cargarClases();

        // ── Caso 2: sucursal sin clases (lista vacia) ─────────────────────────
        if (clases.isEmpty()) {
            agregarMensaje(card,
                "Esta sucursal no ofrece clases en este momento.",
                "Consulta en recepcion para mas informacion.",
                new Color(220, 150, 50));
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // ── Caso 3: hay clases — lista seleccionable ──────────────────────────
        JPanel listaClases = new JPanel();
        listaClases.setLayout(new BoxLayout(listaClases, BoxLayout.Y_AXIS));
        listaClases.setOpaque(false);
        listaClases.setAlignmentX(LEFT_ALIGNMENT);

        filas = new JPanel[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            final int idx = i;
            ClaseDTO clase = clases.get(i);
            boolean llena = clase.estaLlena();

            // Texto: "Yoga - 10:00 (5/20)"
            String horarioTexto = clase.getHorario() != null
                    ? " - " + clase.getHorario().format(FMT_HORA)
                    : (clase.getDiaSemana() != null ? " - " + clase.getDiaSemana() : "");
            String cupoTexto = clase.getCupoMaximo() > 0
                    ? " (" + clase.getCupoDisponible() + "/" + clase.getCupoMaximo() + ")"
                    : "";

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

            JLabel lblClase = new JLabel(clase.getNombre() + horarioTexto + cupoTexto);
            lblClase.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblClase.setForeground(llena ? new Color(180, 90, 90) : Colores.TEXTO_PRINCIPAL);

            JLabel lblEstado = new JLabel(llena ? "LLENA" : "DISPONIBLE");
            lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEstado.setForeground(llena ? new Color(200, 80, 80) : new Color(100, 220, 140));

            fila.add(lblClase, BorderLayout.CENTER);
            fila.add(lblEstado, BorderLayout.EAST);

            if (!llena) {
                fila.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) { seleccionarFila(idx); }
                    @Override public void mouseEntered(MouseEvent e) {
                        if (claseSeleccionadaIndex != idx) fila.setBackground(Colores.ACENTO_PRESS);
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        if (claseSeleccionadaIndex != idx) fila.setBackground(Colores.FONDO_CAMPO);
                    }
                });
            }

            filas[i] = fila;
            listaClases.add(fila);
            listaClases.add(Box.createVerticalStrut(8));
        }

        JScrollPane scroll = new JScrollPane(listaClases);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(460, 260));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        Boton btnInscribirse = crearBoton("INSCRIBIRSE", Boton.Variante.PRIMARIO);
        btnInscribirse.setAlignmentX(CENTER_ALIGNMENT);
        btnInscribirse.addActionListener(e -> procesarInscripcion());

        card.add(scroll);
        card.add(Box.createVerticalStrut(24));
        card.add(btnInscribirse);
        card.add(Box.createVerticalStrut(10));
        card.add(btnRegresar);

        fondo.add(card);
    }

    // -----------------------------------------------------------------

    private void cargarClases() {
        try {
            // La fachada usa internamente idSucursal, idPlan e idCliente
            // que guardo al procesar el QR — pasamos null para que use los suyos
            clases = controlAcceso.obtenerClasesPorPlan(resultado.getIdSucursal());
        } catch (AccesoDenegadoException ex) {
            clases = new ArrayList<>();
        }
    }

    private void agregarMensaje(JPanel card, String linea1, String linea2, Color color) {
        JLabel lbl1 = new JLabel(linea1, SwingConstants.CENTER);
        lbl1.setFont(Colores.FUENTE_LABEL);
        lbl1.setForeground(color);
        lbl1.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lbl2 = new JLabel(linea2, SwingConstants.CENTER);
        lbl2.setFont(Colores.FUENTE_LABEL);
        lbl2.setForeground(color);
        lbl2.setAlignmentX(CENTER_ALIGNMENT);

        card.add(lbl1);
        card.add(Box.createVerticalStrut(4));
        card.add(lbl2);
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

        ClaseDTO clase = clases.get(claseSeleccionadaIndex);

        if (clase.estaLlena()) {
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, el cupo para esta clase ya esta lleno.\n"
                    + "Por favor selecciona otro horario.",
                    "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // La fachada ya conoce idVisita e idCliente — solo pasamos idClase
            controlAcceso.inscribirClase(clase.getIdClase());

            String horarioTexto = clase.getHorario() != null
                    ? " - " + clase.getHorario().format(FMT_HORA) : "";
            JOptionPane.showMessageDialog(this,
                    "Inscripcion realizada correctamente:\n" + clase.getNombre() + horarioTexto,
                    "Inscripcion exitosa", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);

        } catch (AccesoDenegadoException ex) {
            // Cupo lleno detectado en servidor (race condition)
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, el cupo para esta clase ya esta lleno.\n"
                    + "Por favor selecciona otro horario.",
                    "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            claseSeleccionadaIndex = -1;
            dispose();
            new BC_PantallaClases(controlador, resultado, true).setVisible(true);
        }
    }
}