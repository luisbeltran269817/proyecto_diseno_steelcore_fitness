/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.HorarioDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de entrenadores disponibles — versión rediseñada.
 *
 * @author julian izaguirre
 */
public class BC_PantallaEntrenadores extends PantallaBase {
    private static final Color COLOR_SELECCION   = new Color(80, 60, 160);
    private static final Color COLOR_LIBRE_BG    = new Color(100, 220, 140, 50);
    private static final Color COLOR_LIBRE_FG    = new Color(100, 220, 140);
    private static final Color COLOR_AVISO       = new Color(220, 150, 50);
    private static final Color COLOR_HORARIO_SEL = new Color(100, 220, 140, 30);

    private final ResultadoAccesoDTO resultado;
    private final boolean planIncluyeEntrenador;
    private final Icontrolacceso controlAcceso = FachadaControlAcceso.getInstancia();

    private List<EntrenadorDTO> entrenadoresLibres = new ArrayList<>();
    private EntrenadorDTO entrenadorSeleccionado = null;
    private String idHorarioSeleccionado = null;

    private JPanel[] filasPaneles;
    private JPanel   panelHorarios;   // área inferior del split
    private JPanel   contenedorHorarios;
    private JLabel   lblTituloHorarios;
    private Boton    btnSolicitar;


    public BC_PantallaEntrenadores(IControladorAplicacion controlador,
                                    ResultadoAccesoDTO resultado,
                                    boolean planIncluyeEntrenador) {
        super(controlador);
        this.resultado              = resultado;
        this.planIncluyeEntrenador  = planIncluyeEntrenador;
        setTitle("SteelCore Fitness – Entrenadores Disponibles");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {

        // ── Fondo centrador ──────────────────────────────────────────
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);

        // ── Card principal ───────────────────────────────────────────
        //  Usamos BorderLayout para que CENTER se estire y los botones
        //  siempre queden al final, sin importar cuántos elementos haya.
        JPanel card = crearCard(720, 680);
        card.setLayout(new BorderLayout(0, 0));

        card.add(construirEncabezado(), BorderLayout.NORTH);

        // Casos especiales
        if (!planIncluyeEntrenador) {
            card.add(panelAviso("Tu membresía actual no incluye apoyo de entrenador.",
                                "Pasa a recepción para mejorar tu plan."), BorderLayout.CENTER);
            card.add(construirBotonesSolo(), BorderLayout.SOUTH);
            fondo.add(card);
            setContentPane(fondo);
            return;
        }

        cargarEntrenadores();

        if (entrenadoresLibres.isEmpty()) {
            card.add(panelAviso("Lo sentimos, todos los entrenadores están ocupados ahora.",
                                "Intenta de nuevo en unos minutos."), BorderLayout.CENTER);
            card.add(construirBotonesSolo(), BorderLayout.SOUTH);
            fondo.add(card);
            setContentPane(fondo);
            return;
        }

        // Caso normal: hay entrenadores disponibles
        card.add(construirCentro(), BorderLayout.CENTER);
        card.add(construirBotonesCompletos(), BorderLayout.SOUTH);

        fondo.add(card);
        setContentPane(fondo);
    }

    /** Encabezado: logo, título, subtítulo, separador */
    private JPanel construirEncabezado() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(32, 44, 16, 44));

        JLabel logo = centrado(new JLabel("SteelCore"));
        logo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logo.setForeground(Colores.ACENTO);

        JLabel titulo = centrado(new JLabel("Entrenadores Disponibles"));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel subtitulo = centrado(new JLabel("Selecciona un entrenador y elige su horario"));
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(Colores.TEXTO_SECUNDARIO);

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(logo);
        p.add(Box.createVerticalStrut(4));
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(subtitulo);
        p.add(Box.createVerticalStrut(16));
        p.add(sep);
        return p;
    }

    /** Panel central: lista de entrenadores (arriba) + panel de horarios (abajo) */
    private JPanel construirCentro() {
        JPanel centro = new JPanel(new BorderLayout(0, 0));
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(0, 44, 0, 44));

        // ── Lista de entrenadores ──────────────────────────────────
        JPanel wrapLista = new JPanel(new BorderLayout(0, 8));
        wrapLista.setOpaque(false);

        // Cabecera de columnas
        JPanel cabecera = new JPanel(new GridLayout(1, 3));
        cabecera.setOpaque(false);
        cabecera.setBorder(new EmptyBorder(0, 14, 4, 14));
        for (String col : new String[]{"ENTRENADOR", "ESPECIALIDAD", "ESTADO"}) {
            JLabel lbl = new JLabel(col);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
            lbl.setForeground(Colores.TEXTO_SECUNDARIO);
            cabecera.add(lbl);
        }
        wrapLista.add(cabecera, BorderLayout.NORTH);

        // Filas de entrenadores
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
        listaPanel.setOpaque(false);

        filasPaneles = new JPanel[entrenadoresLibres.size()];
        for (int i = 0; i < entrenadoresLibres.size(); i++) {
            JPanel fila = construirFilaEntrenador(i, entrenadoresLibres.get(i));
            filasPaneles[i] = fila;
            listaPanel.add(fila);
            listaPanel.add(Box.createVerticalStrut(5));
        }

        // Label contador
        JLabel lblTotal = new JLabel("  " + entrenadoresLibres.size() + " entrenador(es) disponible(s)");
        lblTotal.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblTotal.setForeground(Colores.TEXTO_SECUNDARIO);

        JScrollPane scrollLista = scroll(listaPanel, 600, calcularAlturaLista());
        wrapLista.add(scrollLista, BorderLayout.CENTER);
        wrapLista.add(lblTotal, BorderLayout.SOUTH);

        // ── Panel de horarios (oculto hasta que se seleccione un entrenador) ──
        panelHorarios = new JPanel(new BorderLayout(0, 8));
        panelHorarios.setOpaque(false);
        panelHorarios.setVisible(false);

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(Colores.BORDE_CARD);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        lblTituloHorarios = new JLabel("Horarios disponibles");
        lblTituloHorarios.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTituloHorarios.setForeground(Colores.TEXTO_PRINCIPAL);

        contenedorHorarios = new JPanel();
        contenedorHorarios.setLayout(new BoxLayout(contenedorHorarios, BoxLayout.Y_AXIS));
        contenedorHorarios.setOpaque(false);

        JScrollPane scrollHorarios = scroll(contenedorHorarios, 600, 160);

        JPanel northHorarios = new JPanel(new BorderLayout());
        northHorarios.setOpaque(false);
        northHorarios.setBorder(new EmptyBorder(12, 0, 8, 0));
        northHorarios.add(sep2, BorderLayout.NORTH);
        northHorarios.add(lblTituloHorarios, BorderLayout.SOUTH);

        panelHorarios.add(northHorarios,   BorderLayout.NORTH);
        panelHorarios.add(scrollHorarios,  BorderLayout.CENTER);

        // ── Ensamblar centro ───────────────────────────────────────
        centro.add(wrapLista,    BorderLayout.NORTH);
        centro.add(panelHorarios, BorderLayout.CENTER);
        return centro;
    }

    /** Construye una fila clickeable para un entrenador */
    private JPanel construirFilaEntrenador(int idx, EntrenadorDTO ent) {
        // Avatar circular con inicial
        String inicial = String.valueOf(ent.getNombre().charAt(0)).toUpperCase();
        JLabel avatar = new JLabel(inicial, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_LIBRE_BG);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(COLOR_LIBRE_FG);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawOval(1, 1, getWidth()-2, getHeight()-2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        avatar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        avatar.setForeground(COLOR_LIBRE_FG);
        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(36, 36));
        avatar.setMinimumSize(new Dimension(36, 36));
        avatar.setMaximumSize(new Dimension(36, 36));

        JLabel lblNombre = new JLabel(ent.getNombre().toUpperCase());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        JPanel col1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        col1.setOpaque(false);
        col1.add(avatar);
        col1.add(lblNombre);

        JLabel lblEsp = new JLabel("Entrenador personal");
        lblEsp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblEsp.setForeground(Colores.TEXTO_SECUNDARIO);
        JPanel col2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col2.setOpaque(false);
        col2.add(lblEsp);

        JLabel lblEstado = new JLabel("● LIBRE") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COLOR_LIBRE_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblEstado.setForeground(COLOR_LIBRE_FG);
        lblEstado.setBorder(new EmptyBorder(3, 10, 3, 10));
        lblEstado.setOpaque(false);
        JPanel col3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        col3.setOpaque(false);
        col3.add(lblEstado);

        JPanel fila = new JPanel(new GridLayout(1, 3, 0, 0));
        fila.setOpaque(true);
        fila.setBackground(Colores.FONDO_CAMPO);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                new EmptyBorder(10, 14, 10, 14)));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 58));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        fila.add(col1);
        fila.add(col2);
        fila.add(col3);

        fila.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e)  { seleccionarEntrenador(idx, ent, fila); }
            @Override public void mouseEntered(MouseEvent e)  {
                if (entrenadorSeleccionado != ent) fila.setBackground(Colores.ACENTO_PRESS);
            }
            @Override public void mouseExited(MouseEvent e)   {
                if (entrenadorSeleccionado != ent) fila.setBackground(Colores.FONDO_CAMPO);
            }
        });

        return fila;
    }

    /** Botones solo con "Regresar" (casos de error) */
    private JPanel construirBotonesSolo() {
        JPanel p = crearBarraBotones();
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> navegarRegresar());
        p.add(btnRegresar);
        return p;
    }

    /** Botones "Regresar" + "Solicitar" */
    private JPanel construirBotonesCompletos() {
        JPanel p = crearBarraBotones();

        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> navegarRegresar());

        btnSolicitar = crearBoton("SOLICITAR", Boton.Variante.PRIMARIO);
        btnSolicitar.addActionListener(e -> procesarSolicitud());

        p.add(btnRegresar);
        p.add(btnSolicitar);
        return p;
    }

    /** Crea la barra de botones con padding */
    private JPanel crearBarraBotones() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(16, 0, 28, 0));
        return p;
    }

    /** Panel de aviso (plan sin entrenador / todos ocupados) */
    private JPanel panelAviso(String linea1, String linea2) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(30, 44, 10, 44));

        JLabel l1 = centrado(new JLabel(linea1));
        l1.setFont(Colores.FUENTE_LABEL);
        l1.setForeground(COLOR_AVISO);

        JLabel l2 = centrado(new JLabel(linea2));
        l2.setFont(Colores.FUENTE_LABEL);
        l2.setForeground(COLOR_AVISO);

        p.add(l1);
        p.add(Box.createVerticalStrut(6));
        p.add(l2);
        return p;
    }

    // ════════════════════════════════════════════════════════════════════
    //  LÓGICA
    // ════════════════════════════════════════════════════════════════════

    private void cargarEntrenadores() {
        try {
            List<EntrenadorDTO> todos = controlAcceso.obtenerEntrenadoresDisponibles(resultado.getIdSucursal());
            if (todos != null) {
                for (EntrenadorDTO e : todos) {
                    if (e.estaDisponible()) entrenadoresLibres.add(e);
                }
            }
        } catch (AccesoDenegadoException ex) {
            entrenadoresLibres = new ArrayList<>();
        }
    }

    private void seleccionarEntrenador(int idx, EntrenadorDTO ent, JPanel filaClickeada) {
        // Restaurar color de todas las filas
        for (JPanel f : filasPaneles) {
            if (f != null) f.setBackground(Colores.FONDO_CAMPO);
        }
        filaClickeada.setBackground(COLOR_SELECCION);
        entrenadorSeleccionado = ent;
        idHorarioSeleccionado  = null;

        // Reconstruir lista de horarios
        contenedorHorarios.removeAll();

        List<HorarioDTO> horariosDisp = obtenerHorariosDisponibles(ent.getIdEntrenador());

        if (horariosDisp.isEmpty()) {
            JLabel sinH = new JLabel("No hay horarios disponibles para este entrenador.");
            sinH.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            sinH.setForeground(COLOR_AVISO);
            sinH.setBorder(new EmptyBorder(6, 4, 6, 0));
            contenedorHorarios.add(sinH);
            lblTituloHorarios.setText("Horarios de " + ent.getNombre());
        } else {
            lblTituloHorarios.setText("Horarios de " + ent.getNombre() + "  ·  Elige uno:");
            ButtonGroup bg = new ButtonGroup();
            for (HorarioDTO h : horariosDisp) {
                contenedorHorarios.add(construirFilaHorario(h, bg));
                contenedorHorarios.add(Box.createVerticalStrut(4));
            }
        }

        panelHorarios.setVisible(true);
        contenedorHorarios.revalidate();
        contenedorHorarios.repaint();
        panelHorarios.revalidate();
        panelHorarios.repaint();
    }

    /** Construye una fila de horario seleccionable */
    private JPanel construirFilaHorario(HorarioDTO h, ButtonGroup bg) {
        String dia    = h.getDiaSemana()  != null ? h.getDiaSemana()           : "—";
        String inicio = h.getHoraInicio() != null ? h.getHoraInicio().toString() : "—";
        String fin    = h.getHoraFin()    != null ? h.getHoraFin().toString()    : "—";

        JRadioButton rb = new JRadioButton(dia + "    " + inicio + " – " + fin);
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rb.setForeground(Colores.TEXTO_PRINCIPAL);
        rb.setOpaque(false);
        rb.addActionListener(ev -> idHorarioSeleccionado = h.getIdHorario());
        bg.add(rb);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
        fila.setOpaque(true);
        fila.setBackground(Colores.FONDO_CAMPO);
        fila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                new EmptyBorder(2, 8, 2, 8)));
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fila.add(rb);

        // Click en toda la fila también selecciona el radio
        fila.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                rb.setSelected(true);
                idHorarioSeleccionado = h.getIdHorario();
                // Destacar fila seleccionada
                for (Component c : fila.getParent().getComponents()) {
                    if (c instanceof JPanel) ((JPanel) c).setBackground(Colores.FONDO_CAMPO);
                }
                fila.setBackground(COLOR_HORARIO_SEL);
            }
            @Override public void mouseEntered(MouseEvent e) {
                if (!rb.isSelected()) fila.setBackground(Colores.ACENTO_PRESS);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!rb.isSelected()) fila.setBackground(Colores.FONDO_CAMPO);
            }
        });

        return fila;
    }

    private List<HorarioDTO> obtenerHorariosDisponibles(String idEntrenador) {
        try {
            return controlAcceso.obtenerHorariosEntrenador(idEntrenador);
        } catch (AccesoDenegadoException ex) {
            return new ArrayList<>();
        }
    }

    private void procesarSolicitud() {
        if (entrenadorSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un entrenador.",
                    "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (idHorarioSeleccionado == null || idHorarioSeleccionado.isBlank()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un horario para el entrenador.",
                    "Sin horario", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            controlAcceso.asignarEntrenador(
                    entrenadorSeleccionado.getIdEntrenador(),
                    idHorarioSeleccionado);

            JOptionPane.showMessageDialog(this,
                    "¡Entrenador asignado con éxito!\n" + entrenadorSeleccionado.getNombre().toUpperCase(),
                    "Asignación exitosa", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);

        } catch (AccesoDenegadoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMotivo(), "No disponible", JOptionPane.WARNING_MESSAGE);
            entrenadorSeleccionado = null;
            idHorarioSeleccionado  = null;
            dispose();
            new BC_PantallaEntrenadores(controlador, resultado, true).setVisible(true);
        }
    }

    private void navegarRegresar() {
        dispose();
        new BC_PantallaExpediente(controlador, resultado).setVisible(true);
    }

    // ════════════════════════════════════════════════════════════════════
    //  UTILIDADES
    // ════════════════════════════════════════════════════════════════════

    /** Establece CENTER_ALIGNMENT y devuelve el mismo label */
    private static <T extends JLabel> T centrado(T lbl) {
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }

    /** Crea un JScrollPane estilizado */
    private JScrollPane scroll(JComponent contenido, int w, int h) {
        JScrollPane sp = new JScrollPane(contenido);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        sp.setPreferredSize(new Dimension(w, h));
        sp.setMaximumSize(new Dimension(Integer.MAX_VALUE, h));
        sp.setAlignmentX(Component.LEFT_ALIGNMENT);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        return sp;
    }

    /** Calcula altura óptima para la lista según número de entrenadores (máx 220px) */
    private int calcularAlturaLista() {
        int n = entrenadoresLibres.size();
        return Math.min(n * 63 + 10, 220);
    }
}