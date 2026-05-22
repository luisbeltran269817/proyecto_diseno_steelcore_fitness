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

        JPanel card = crearCard(680, 640);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        // ── Encabezado ────────────────────────────────────────────────
        JLabel logo = new JLabel("SteelCore", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        logo.setForeground(Colores.ACENTO);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Clases Disponibles", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Selecciona la clase a la que deseas asistir hoy", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(Colores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

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
        card.add(Box.createVerticalStrut(4));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(18));

        // ── Caso 1: plan no incluye clases ────────────────────────────
        if (!planIncluyeClases) {
            agregarMensaje(card,
                "Tu plan no incluye inscripción a clases grupales.",
                "Mejora tu membresía desde recepción o la app.",
                new Color(220, 150, 50));
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        cargarClases();

        // ── Caso 2: sin clases ────────────────────────────────────────
        if (clases.isEmpty()) {
            agregarMensaje(card,
                "Esta sucursal no ofrece clases en este momento.",
                "Consulta en recepción para más información.",
                new Color(220, 150, 50));
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // ── Caso 3: lista de clases ───────────────────────────────────
        JPanel listaClases = new JPanel();
        listaClases.setLayout(new BoxLayout(listaClases, BoxLayout.Y_AXIS));
        listaClases.setOpaque(false);
        listaClases.setAlignmentX(LEFT_ALIGNMENT);

        filas = new JPanel[clases.size()];
        for (int i = 0; i < clases.size(); i++) {
            final int idx = i;
            ClaseDTO clase = clases.get(i);
            boolean llena = clase.estaLlena();

            // Card de clase con barra de cupo
            JPanel fila = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                    // Borde izquierdo de color según estado
                    Color accentColor = llena ? new Color(200, 80, 80) : new Color(100, 220, 140);
                    g2.setColor(accentColor);
                    g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);
                    g2.dispose();
                }
            };
            fila.setOpaque(false);
            fila.setBackground(Colores.FONDO_CAMPO);
            fila.setLayout(new BorderLayout(12, 0));
            fila.setBorder(new EmptyBorder(14, 20, 14, 16));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
            fila.setAlignmentX(LEFT_ALIGNMENT);
            fila.setCursor(Cursor.getPredefinedCursor(
                    llena ? Cursor.DEFAULT_CURSOR : Cursor.HAND_CURSOR));

            // Panel izquierdo: nombre + día + hora
            JPanel infoPanel = new JPanel();
            infoPanel.setOpaque(false);
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            JLabel lblNombre = new JLabel(clase.getNombre());
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblNombre.setForeground(llena ? new Color(160, 100, 100) : Colores.TEXTO_PRINCIPAL);

            String diaTexto = (clase.getDiaSemana() != null && !clase.getDiaSemana().isBlank())
                    ? clase.getDiaSemana() : "";
            String horaTexto = clase.getHorario() != null
                    ? clase.getHorario().format(FMT_HORA) : "";
            String detalleStr = "";
            if (!diaTexto.isEmpty() && !horaTexto.isEmpty())
                detalleStr = diaTexto + "  ·  " + horaTexto;
            else if (!horaTexto.isEmpty())
                detalleStr = horaTexto;
            else if (!diaTexto.isEmpty())
                detalleStr = diaTexto;

            JLabel lblDetalle = new JLabel(detalleStr);
            lblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            lblDetalle.setForeground(Colores.TEXTO_SECUNDARIO);

            // Barra de cupo
            int cupoMax  = clase.getCupoMaximo();
            int cupoDisp = clase.getCupoDisponible();
            JPanel barraPanel = new JPanel(new BorderLayout(6, 0));
            barraPanel.setOpaque(false);
            barraPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));

            JPanel barraFondo = new JPanel() {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    // Fondo de barra
                    g2.setColor(new Color(255, 255, 255, 30));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                    // Relleno según ocupación
                    if (cupoMax > 0) {
                        int ocupados = cupoMax - cupoDisp;
                        int fillW = (int) ((double) ocupados / cupoMax * getWidth());
                        Color fillColor = llena ? new Color(200, 80, 80, 180)
                                : cupoDisp <= cupoMax / 4 ? new Color(220, 150, 50, 200)
                                : new Color(100, 220, 140, 200);
                        g2.setColor(fillColor);
                        g2.fillRoundRect(0, 0, fillW, getHeight(), 6, 6);
                    }
                    g2.dispose();
                }
            };
            barraFondo.setOpaque(false);
            barraFondo.setPreferredSize(new Dimension(0, 6));

            JLabel lblCupo = new JLabel(cupoDisp + "/" + cupoMax + " lugares");
            lblCupo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            lblCupo.setForeground(Colores.TEXTO_SECUNDARIO);

            barraPanel.add(barraFondo, BorderLayout.CENTER);
            barraPanel.add(lblCupo, BorderLayout.EAST);

            infoPanel.add(lblNombre);
            infoPanel.add(Box.createVerticalStrut(3));
            infoPanel.add(lblDetalle);
            infoPanel.add(Box.createVerticalStrut(6));
            infoPanel.add(barraPanel);

            // Chip de estado derecha
            JLabel lblEstado = new JLabel(llena ? "LLENA" : "DISPONIBLE") {
                @Override protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg = llena ? new Color(200, 80, 80, 60) : new Color(100, 220, 140, 60);
                    g2.setColor(bg);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
            lblEstado.setForeground(llena ? new Color(220, 100, 100) : new Color(100, 220, 140));
            lblEstado.setBorder(new EmptyBorder(4, 10, 4, 10));
            lblEstado.setOpaque(false);
            lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
            lblEstado.setPreferredSize(new Dimension(95, 28));

            JPanel rightPanel = new JPanel(new GridBagLayout());
            rightPanel.setOpaque(false);
            rightPanel.add(lblEstado);

            fila.add(infoPanel, BorderLayout.CENTER);
            fila.add(rightPanel, BorderLayout.EAST);

            if (!llena) {
                fila.addMouseListener(new MouseAdapter() {
                    @Override public void mouseClicked(MouseEvent e) { seleccionarFila(idx); }
                    @Override public void mouseEntered(MouseEvent e) {
                        if (claseSeleccionadaIndex != idx) fila.setBackground(Colores.ACENTO_PRESS);
                        fila.repaint();
                    }
                    @Override public void mouseExited(MouseEvent e) {
                        if (claseSeleccionadaIndex != idx) fila.setBackground(Colores.FONDO_CAMPO);
                        fila.repaint();
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
        scroll.setPreferredSize(new Dimension(580, 340));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        // Nota de instrucción
        JLabel lblNota = new JLabel("Haz clic en una clase para seleccionarla");
        lblNota.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblNota.setForeground(Colores.TEXTO_SECUNDARIO);
        lblNota.setAlignmentX(LEFT_ALIGNMENT);

        Boton btnInscribirse = crearBoton("INSCRIBIRSE", Boton.Variante.PRIMARIO);
        btnInscribirse.setAlignmentX(CENTER_ALIGNMENT);
        btnInscribirse.addActionListener(e -> procesarInscripcion());

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        panelBotones.add(btnRegresar);
        panelBotones.add(btnInscribirse);

        card.add(scroll);
        card.add(Box.createVerticalStrut(8));
        card.add(lblNota);
        card.add(Box.createVerticalStrut(20));
        card.add(panelBotones);

        fondo.add(card);
    }

    // -----------------------------------------------------------------

    private void cargarClases() {
        try {
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
        if (claseSeleccionadaIndex >= 0 && claseSeleccionadaIndex < filas.length) {
            filas[claseSeleccionadaIndex].setBackground(Colores.FONDO_CAMPO);
            filas[claseSeleccionadaIndex].repaint();
        }
        claseSeleccionadaIndex = index;
        filas[index].setBackground(new Color(80, 60, 160));
        filas[index].repaint();
    }

    private void procesarInscripcion() {
        if (claseSeleccionadaIndex < 0) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una clase primero.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        ClaseDTO clase = clases.get(claseSeleccionadaIndex);
        if (clase.estaLlena()) {
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, el cupo para esta clase ya está lleno.\n"
                    + "Por favor selecciona otro horario.",
                    "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            controlAcceso.inscribirClase(clase.getIdClase());
            String horarioTexto = clase.getHorario() != null
                    ? " - " + clase.getHorario().format(FMT_HORA) : "";
            JOptionPane.showMessageDialog(this,
                    "¡Inscripción realizada!\n" + clase.getNombre() + horarioTexto,
                    "Inscripción exitosa", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        } catch (AccesoDenegadoException ex) {
            JOptionPane.showMessageDialog(this,
                    "Lo sentimos, el cupo para esta clase ya está lleno.\n"
                    + "Por favor selecciona otro horario.",
                    "Cupo lleno", JOptionPane.WARNING_MESSAGE);
            claseSeleccionadaIndex = -1;
            dispose();
            new BC_PantallaClases(controlador, resultado, true).setVisible(true);
        }
    }
}