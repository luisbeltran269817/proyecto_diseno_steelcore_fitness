package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtosControlDeAcceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Expediente del Socio — Acceso Permitido.
 *
 * Usa dtosControlDeAcceso.ResultadoAccesoDTO (el DTO real de la fachada).
 * Campos disponibles: nombreSocio, fechaRegistroEntrada, mensaje,
 *                     idPlan, planIncluyeClases, planIncluyeEntrenador.
 */
public class BC_PantallaExpediente extends PantallaBase {

    private final ResultadoAccesoDTO resultado;
    private final Icontrolacceso controlAcceso = FachadaControlAcceso.getInstancia();

    private static final DateTimeFormatter FMT_HORA  = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BC_PantallaExpediente(IControladorAplicacion controlador,
                                  ResultadoAccesoDTO resultado) {
        super(controlador);
        this.resultado = resultado;
        setTitle("SteelCore Fitness — Expediente Socio");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(640, 580);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        String nombre = resultado.getNombreSocio() != null ? resultado.getNombreSocio() : "Socio";

        // ── Cabecera: badge iniciales + nombre + ACTIVA ──────────────────────
        JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        cabecera.setOpaque(false);
        cabecera.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));

        JLabel badge = new JLabel(iniciales(nombre), SwingConstants.CENTER);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 20));
        badge.setForeground(Colores.TEXTO_PRINCIPAL);
        badge.setBackground(Colores.ACENTO);
        badge.setOpaque(true);
        badge.setPreferredSize(new Dimension(54, 54));

        JPanel infoHeader = new JPanel();
        infoHeader.setOpaque(false);
        infoHeader.setLayout(new BoxLayout(infoHeader, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(nombre);
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel lblEstado = new JLabel("Membresia ACTIVA");
        lblEstado.setFont(Colores.FUENTE_LABEL);
        lblEstado.setForeground(new Color(100, 220, 140));

        infoHeader.add(lblNombre);
        infoHeader.add(Box.createVerticalStrut(4));
        infoHeader.add(lblEstado);

        cabecera.add(badge);
        cabecera.add(infoHeader);

        JSeparator sep1 = new JSeparator();
        sep1.setForeground(Colores.BORDE_CARD);
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setAlignmentX(LEFT_ALIGNMENT);

        // ── Info acceso registrado ────────────────────────────────────────────
        String horaAcceso = (resultado.getFechaRegistroEntrada() != null)
                ? resultado.getFechaRegistroEntrada().format(FMT_HORA)
                : "Ahora";

        JLabel lblAcceso = new JLabel("Acceso registrado a las " + horaAcceso);
        lblAcceso.setFont(Colores.FUENTE_LABEL);
        lblAcceso.setForeground(new Color(100, 220, 140));
        lblAcceso.setAlignmentX(LEFT_ALIGNMENT);

        // ── Datos del plan ────────────────────────────────────────────────────
        String planStr    = resultado.getIdPlan() != null ? resultado.getIdPlan() : "—";
        String vigenciaStr = "—";  // ResultadoAccesoDTO no expone fecha de caducidad directamente
        if (resultado.getMensaje() != null && resultado.getMensaje().contains("las ")) {
            vigenciaStr = resultado.getMensaje();
        }

        JPanel panelPlan = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        panelPlan.setOpaque(false);
        panelPlan.setAlignmentX(LEFT_ALIGNMENT);
        panelPlan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        panelPlan.add(etiqueta("Plan:", planStr));
        panelPlan.add(etiqueta("Clases:", resultado.isPlanIncluyeClases()    ? "Incluidas" : "No incluidas"));
        panelPlan.add(etiqueta("Entrenador:", resultado.isPlanIncluyeEntrenador() ? "Incluido"  : "No incluido"));

        JSeparator sep2 = new JSeparator();
        sep2.setForeground(Colores.BORDE_CARD);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setAlignmentX(LEFT_ALIGNMENT);

        // ── Selector de servicio ──────────────────────────────────────────────
        JLabel lblServicios = new JLabel("Que servicio usaras hoy?");
        lblServicios.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblServicios.setForeground(Colores.TEXTO_PRINCIPAL);
        lblServicios.setAlignmentX(LEFT_ALIGNMENT);

        Boton btnAreaGeneral = crearBoton("Area General",        Boton.Variante.PRIMARIO);
        Boton btnClases      = crearBoton("Consultar Clases",    Boton.Variante.SECUNDARIO);
        Boton btnEntrenador  = crearBoton("Solicitar Entrenador",Boton.Variante.SECUNDARIO);
        Boton btnRegresar    = crearBoton("Regresar a Recepcion",Boton.Variante.SECUNDARIO);

        btnAreaGeneral.setAlignmentX(CENTER_ALIGNMENT);
        btnClases.setAlignmentX(CENTER_ALIGNMENT);
        btnEntrenador.setAlignmentX(CENTER_ALIGNMENT);
        btnRegresar.setAlignmentX(CENTER_ALIGNMENT);

        // Area General: registra en fachada, muestra confirmacion y vuelve al scanner
        btnAreaGeneral.addActionListener(e -> {
            try {
                controlAcceso.registrarAreaGeneral();
            } catch (AccesoDenegadoException ex) {
                System.err.println("[Expediente] registrarAreaGeneral: " + ex.getMotivo());
            }
            JOptionPane.showMessageDialog(this,
                "Acceso a Area General confirmado.\nEchale ganas " + nombre + "!",
                "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        });

        // Clases: pasa el flag del plan para que la pantalla maneje el caso sin clases
        btnClases.addActionListener(e -> {
            dispose();
            new BC_PantallaClases(controlador, resultado, resultado.isPlanIncluyeClases())
                    .setVisible(true);
        });

        // Entrenador: pasa el flag del plan
        btnEntrenador.addActionListener(e -> {
            dispose();
            new BC_PantallaEntrenadores(controlador, resultado, resultado.isPlanIncluyeEntrenador())
                    .setVisible(true);
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        });

        // ── Ensamblar ─────────────────────────────────────────────────────────
        card.add(cabecera);
        card.add(Box.createVerticalStrut(16));
        card.add(sep1);
        card.add(Box.createVerticalStrut(10));
        card.add(lblAcceso);
        card.add(Box.createVerticalStrut(6));
        card.add(panelPlan);
        card.add(Box.createVerticalStrut(16));
        card.add(sep2);
        card.add(Box.createVerticalStrut(18));
        card.add(lblServicios);
        card.add(Box.createVerticalStrut(14));
        card.add(btnAreaGeneral);
        card.add(Box.createVerticalStrut(10));
        card.add(btnClases);
        card.add(Box.createVerticalStrut(10));
        card.add(btnEntrenador);
        card.add(Box.createVerticalStrut(22));
        card.add(btnRegresar);

        fondo.add(card);
    }

    private JPanel etiqueta(String label, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(Colores.FUENTE_LABEL);
        lbl.setForeground(Colores.TEXTO_SECUNDARIO);
        JLabel val = new JLabel(valor);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(Colores.TEXTO_PRINCIPAL);
        p.add(lbl);
        p.add(val);
        return p;
    }

    private String iniciales(String nombre) {
        if (nombre == null || nombre.isBlank()) return "?";
        String[] partes = nombre.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(2, partes.length); i++) sb.append(partes[i].charAt(0));
        return sb.toString().toUpperCase();
    }
}