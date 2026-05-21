package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.EstadoEntrenador;
import dtosControlDeAcceso.HorarioDTO;
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
 * Pantalla de entrenadores disponibles para asignacion al socio.
 *
 * Usa dtosControlDeAcceso.EntrenadorDTO (DTO real de la fachada).
 * EntrenadorDTO tiene: idEntrenador, nombre, estado (EstadoEntrenador), idSucursal.
 * estaDisponible() = estado == LIBRE.
 *
 * NOTA: EntrenadorDTO no tiene lista de horarios; la asignacion se hace
 *       llamando a asignarEntrenador(idVisita, idCliente, idEntrenador, null)
 *       ya que el horario lo gestiona el backend automaticamente.
 */
public class BC_PantallaEntrenadores extends PantallaBase {

    private static final DateTimeFormatter FMT_HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final ResultadoAccesoDTO resultado;
    private final boolean planIncluyeEntrenador;
    private final Icontrolacceso controlAcceso = FachadaControlAcceso.getInstancia();

    private List<EntrenadorDTO> entrenadores = new ArrayList<>();
    private EntrenadorDTO entrenadorSeleccionado = null;
    private JPanel[] filas;

    public BC_PantallaEntrenadores(IControladorAplicacion controlador,
                                    ResultadoAccesoDTO resultado,
                                    boolean planIncluyeEntrenador) {
        super(controlador);
        this.resultado             = resultado;
        this.planIncluyeEntrenador = planIncluyeEntrenador;
        setTitle("SteelCore Fitness - Entrenadores Disponibles");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(640, 600);
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

        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);
        });

        // ── Caso 1: plan no incluye entrenador ────────────────────────────────
        if (!planIncluyeEntrenador) {
            card.add(logo);
            card.add(Box.createVerticalStrut(6));
            card.add(titulo);
            card.add(Box.createVerticalStrut(14));
            card.add(sep);
            card.add(Box.createVerticalStrut(30));

            JLabel l1 = new JLabel("Tu membresia actual no incluye apoyo de entrenador.", SwingConstants.CENTER);
            l1.setFont(Colores.FUENTE_LABEL);
            l1.setForeground(new Color(220, 150, 50));
            l1.setAlignmentX(CENTER_ALIGNMENT);

            JLabel l2 = new JLabel("Pasa a recepcion o usa tu app para mejorar tu plan.", SwingConstants.CENTER);
            l2.setFont(Colores.FUENTE_LABEL);
            l2.setForeground(new Color(220, 150, 50));
            l2.setAlignmentX(CENTER_ALIGNMENT);

            card.add(l1);
            card.add(Box.createVerticalStrut(4));
            card.add(l2);
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // Cargar entrenadores de la fachada
        cargarEntrenadores();

        // Filtrar solo los LIBRES para mostrar en la lista
        List<EntrenadorDTO> libres = new ArrayList<>();
        for (EntrenadorDTO e : entrenadores) {
            if (e.estaDisponible()) libres.add(e);
        }

        // ── Caso 2: todos ocupados ────────────────────────────────────────────
        if (libres.isEmpty()) {
            card.add(logo);
            card.add(Box.createVerticalStrut(6));
            card.add(titulo);
            card.add(Box.createVerticalStrut(14));
            card.add(sep);
            card.add(Box.createVerticalStrut(30));

            JLabel lblSaturado = new JLabel(
                    "Lo sentimos, todos los entrenadores estan ocupados en este momento.",
                    SwingConstants.CENTER);
            lblSaturado.setFont(Colores.FUENTE_LABEL);
            lblSaturado.setForeground(new Color(220, 150, 50));
            lblSaturado.setAlignmentX(CENTER_ALIGNMENT);

            card.add(lblSaturado);
            card.add(Box.createVerticalStrut(30));
            card.add(btnRegresar);
            fondo.add(card);
            return;
        }

        // ── Caso 3: hay entrenadores libres ───────────────────────────────────
        JPanel encabezado = new JPanel(new GridLayout(1, 2, 0, 0));
        encabezado.setOpaque(false);
        encabezado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        encabezado.setAlignmentX(LEFT_ALIGNMENT);
        for (String col : new String[]{"ENTRENADOR", "ESTADO"}) {
            JLabel lbl = new JLabel(col, SwingConstants.LEFT);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lbl.setForeground(Colores.TEXTO_SECUNDARIO);
            encabezado.add(lbl);
        }

        JPanel listaEntrenadores = new JPanel();
        listaEntrenadores.setLayout(new BoxLayout(listaEntrenadores, BoxLayout.Y_AXIS));
        listaEntrenadores.setOpaque(false);

        filas = new JPanel[libres.size()];
        for (int i = 0; i < libres.size(); i++) {
            final int idx     = i;
            EntrenadorDTO ent = libres.get(i);

            JPanel fila = new JPanel(new GridLayout(1, 2, 0, 0));
            fila.setOpaque(true);
            fila.setBackground(Colores.FONDO_CAMPO);
            fila.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                    new EmptyBorder(10, 14, 10, 14)));
            fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            fila.setAlignmentX(LEFT_ALIGNMENT);
            fila.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel lblNombre = new JLabel(ent.getNombre().toUpperCase());
            lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
            lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

            JLabel lblEstado = new JLabel("LIBRE");
            lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
            lblEstado.setForeground(new Color(100, 220, 140));

            fila.add(lblNombre);
            fila.add(lblEstado);

            fila.addMouseListener(new MouseAdapter() {
                @Override public void mouseClicked(MouseEvent e)  { seleccionarFila(idx, ent); }
                @Override public void mouseEntered(MouseEvent e)  {
                    if (entrenadorSeleccionado != ent) fila.setBackground(Colores.ACENTO_PRESS);
                }
                @Override public void mouseExited(MouseEvent e)   {
                    if (entrenadorSeleccionado != ent) fila.setBackground(Colores.FONDO_CAMPO);
                }
            });

            filas[i] = fila;
            listaEntrenadores.add(fila);
            listaEntrenadores.add(Box.createVerticalStrut(6));
        }

        JScrollPane scrollLista = new JScrollPane(listaEntrenadores);
        scrollLista.setOpaque(false);
        scrollLista.getViewport().setOpaque(false);
        scrollLista.setBorder(null);
        scrollLista.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLista.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollLista.setPreferredSize(new Dimension(544, 280));
        scrollLista.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        scrollLista.setAlignmentX(LEFT_ALIGNMENT);
        scrollLista.getVerticalScrollBar().setUnitIncrement(16);

        JLabel lblTotal = new JLabel("Total de entrenadores disponibles: " + libres.size());
        lblTotal.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblTotal.setForeground(Colores.TEXTO_SECUNDARIO);
        lblTotal.setAlignmentX(LEFT_ALIGNMENT);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        panelBotones.setOpaque(false);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        Boton btnSolicitar = crearBoton("SOLICITAR", Boton.Variante.PRIMARIO);
        btnSolicitar.addActionListener(e -> procesarSolicitud(libres));

        panelBotones.add(btnRegresar);
        panelBotones.add(btnSolicitar);

        card.add(logo);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(14));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
        card.add(encabezado);
        card.add(Box.createVerticalStrut(8));
        card.add(scrollLista);
        card.add(Box.createVerticalStrut(8));
        card.add(lblTotal);
        card.add(Box.createVerticalStrut(20));
        card.add(panelBotones);

        fondo.add(card);
    }

    // -----------------------------------------------------------------

    private void cargarEntrenadores() {
        try {
            // Usa la sucursal que viene en el resultado del acceso
            entrenadores = controlAcceso.obtenerEntrenadoresDisponibles(resultado.getIdSucursal());
        } catch (AccesoDenegadoException ex) {
            entrenadores = new ArrayList<>();
        }
    }

    private void seleccionarFila(int index, EntrenadorDTO ent) {
        for (JPanel fila : filas) {
            if (fila != null) fila.setBackground(Colores.FONDO_CAMPO);
        }
        entrenadorSeleccionado = ent;
        filas[index].setBackground(new Color(80, 60, 160));
    }

    private void procesarSolicitud(List<EntrenadorDTO> libres) {
        if (entrenadorSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un entrenador.",
                    "Sin seleccion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // asignarEntrenador(idVisita, idCliente, idEntrenador, idHorario)
            // idHorario = null; el backend asigna el horario disponible automaticamente
            controlAcceso.asignarEntrenador(
                    resultado.getIdVisita(),
                    resultado.getIdCliente(),
                    entrenadorSeleccionado.getIdEntrenador(),
                    null);

            JOptionPane.showMessageDialog(this,
                    "Entrenador asignado con exito:\n"
                    + entrenadorSeleccionado.getNombre().toUpperCase(),
                    "Asignacion exitosa", JOptionPane.INFORMATION_MESSAGE);

            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);

        } catch (AccesoDenegadoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMotivo(),
                    "No disponible", JOptionPane.WARNING_MESSAGE);
            entrenadorSeleccionado = null;
            dispose();
            new BC_PantallaEntrenadores(controlador, resultado, true).setVisible(true);
        }
    }
}