package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.Icontrolacceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.VisitaDTO;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Expediente del Socio — Acceso Permitido.
 *
 * Se muestra después de que el subsistema ControlAcceso validó el QR
 * y ya registró la visita. Esta pantalla es solo presentación:
 * no realiza lógica de negocio, solo muestra el resultado y permite
 * al socio seleccionar el servicio que usará.
 *
 * @author julian izaguirre
 */
public class BC_PantallaExpediente extends PantallaBase {

    private final ResultadoAccesoDTO resultado;

    private static final DateTimeFormatter FMT_HORA =
            DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter FMT_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        JPanel card = crearCard(620, 560);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        ClienteDTO   cliente   = resultado.getCliente();
        MembresiaDTO membresia = resultado.getMembresia();
        VisitaDTO    visita    = resultado.getVisitaRegistrada();

        // ── Cabecera: badge + nombre + estado ────────────────────────────
        JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        cabecera.setOpaque(false);
        cabecera.setAlignmentX(LEFT_ALIGNMENT);
        cabecera.setMaximumSize(new Dimension(Integer.MAX_VALUE, 64));

        JLabel badge = new JLabel(iniciales(cliente.getNombre()), SwingConstants.CENTER);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 20));
        badge.setForeground(Colores.TEXTO_PRINCIPAL);
        badge.setBackground(Colores.ACENTO);
        badge.setOpaque(true);
        badge.setPreferredSize(new Dimension(54, 54));

        JPanel infoHeader = new JPanel();
        infoHeader.setOpaque(false);
        infoHeader.setLayout(new BoxLayout(infoHeader, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel(cliente.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel lblEstado = new JLabel("● Membresía ACTIVA");
        lblEstado.setFont(Colores.FUENTE_LABEL);
        lblEstado.setForeground(new Color(100, 220, 140));

        infoHeader.add(lblNombre);
        infoHeader.add(Box.createVerticalStrut(4));
        infoHeader.add(lblEstado);

        cabecera.add(badge);
        cabecera.add(infoHeader);

        // ── Separador ─────────────────────────────────────────────────────
        JSeparator sep1 = new JSeparator();
        sep1.setForeground(Colores.BORDE_CARD);
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep1.setAlignmentX(LEFT_ALIGNMENT);

        // ── Info de la visita registrada ──────────────────────────────────
        String horaAcceso = (visita != null && visita.getFechaHora() != null)
                ? visita.getFechaHora().format(FMT_HORA)
                : "—";
        String sucursalNombre = (visita != null && visita.getGimnasio() != null)
                ? visita.getGimnasio()
                : "—";

        JLabel lblAcceso = new JLabel("✅  Acceso registrado en " + sucursalNombre
                + " a las " + horaAcceso);
        lblAcceso.setFont(Colores.FUENTE_LABEL);
        lblAcceso.setForeground(new Color(100, 220, 140));
        lblAcceso.setAlignmentX(LEFT_ALIGNMENT);

        // ── Info de membresía ─────────────────────────────────────────────
        String planId   = membresia.getIdPlan()         != null ? membresia.getIdPlan()  : "—";
        String sucIdStr = membresia.getIdSucursal()     != null ? membresia.getIdSucursal() : "—";
        String vigencia = membresia.getFechaCaducidad() != null
                ? membresia.getFechaCaducidad().toLocalDate().format(FMT_FECHA)
                : "—";

        JPanel panelMembresia = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        panelMembresia.setOpaque(false);
        panelMembresia.setAlignmentX(LEFT_ALIGNMENT);
        panelMembresia.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

        panelMembresia.add(etiqueta("Plan:", planId));
        panelMembresia.add(etiqueta("Sucursal:", sucIdStr));
        panelMembresia.add(etiqueta("Vigencia:", vigencia));

        // ── Separador 2 ───────────────────────────────────────────────────
        JSeparator sep2 = new JSeparator();
        sep2.setForeground(Colores.BORDE_CARD);
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep2.setAlignmentX(LEFT_ALIGNMENT);

        // ── Selector de servicio ──────────────────────────────────────────
        JLabel lblServicios = new JLabel("¿Qué servicio usarás hoy?");
        lblServicios.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblServicios.setForeground(Colores.TEXTO_PRINCIPAL);
        lblServicios.setAlignmentX(LEFT_ALIGNMENT);

        Boton btnAreaGeneral = crearBoton("Área General",         Boton.Variante.PRIMARIO);
        Boton btnClases      = crearBoton("Consultar Clases",      Boton.Variante.SECUNDARIO);
        Boton btnEntrenador  = crearBoton("Solicitar Entrenador",  Boton.Variante.SECUNDARIO);
        Boton btnRegresar    = crearBoton("Regresar a Recepción",  Boton.Variante.SECUNDARIO);

        btnAreaGeneral.setAlignmentX(CENTER_ALIGNMENT);
        btnClases.setAlignmentX(CENTER_ALIGNMENT);
        btnEntrenador.setAlignmentX(CENTER_ALIGNMENT);
        btnRegresar.setAlignmentX(CENTER_ALIGNMENT);

        // El registro de visita YA se hizo en el subsistema al procesar el QR.
        // Aquí solo mostramos confirmación visual.
        btnAreaGeneral.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Acceso a Área General confirmado.\n¡Échale ganas, "
                    + cliente.getNombre() + "! 💪",
                "Área General",
                JOptionPane.INFORMATION_MESSAGE)
        );

        btnClases.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Módulo de clases próximamente disponible.",
                "Consultar Clases",
                JOptionPane.INFORMATION_MESSAGE)
        );

        btnEntrenador.addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Módulo de entrenadores próximamente disponible.",
                "Solicitar Entrenador",
                JOptionPane.INFORMATION_MESSAGE)
        );

        btnRegresar.addActionListener(e -> {
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        });

        // ── Ensamblar ─────────────────────────────────────────────────────
        card.add(cabecera);
        card.add(Box.createVerticalStrut(16));
        card.add(sep1);
        card.add(Box.createVerticalStrut(10));
        card.add(lblAcceso);
        card.add(Box.createVerticalStrut(6));
        card.add(panelMembresia);
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

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Construye un mini-panel "Etiqueta: Valor" */
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
        for (int i = 0; i < Math.min(2, partes.length); i++) {
            sb.append(partes[i].charAt(0));
        }
        return sb.toString().toUpperCase();
    }
}
