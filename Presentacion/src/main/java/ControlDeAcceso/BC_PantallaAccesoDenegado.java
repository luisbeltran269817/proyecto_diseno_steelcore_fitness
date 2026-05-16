package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de acceso denegado.
 *
 * @author julian izaguirre
 */
public class BC_PantallaAccesoDenegado extends PantallaBase {

    private final String motivo;

    public BC_PantallaAccesoDenegado(IControladorAplicacion controlador, String motivo) {
        super(controlador);
        this.motivo = motivo;
        setTitle("SteelCore Fitness — Acceso Denegado");
        inicializarComponentes();
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(500, 380);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(44, 52, 44, 52));

        // ── Ícono de error (solo texto, sin imagen externa) ───────────────
        JLabel ico = new JLabel("✕", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.BOLD, 52));
        ico.setForeground(new Color(220, 80, 80));
        ico.setAlignmentX(CENTER_ALIGNMENT);

        // ── Título ────────────────────────────────────────────────────────
        JLabel titulo = new JLabel("Acceso Denegado", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        // ── Estado dinámico según motivo ──────────────────────────────────
        // CORRECCIÓN: ya no muestra siempre "INACTIVO"; usa el texto del motivo
        String estadoTexto = resolverEstado(motivo);
        JLabel lblEstado = new JLabel("● " + estadoTexto, SwingConstants.CENTER);
        lblEstado.setFont(Colores.FUENTE_LABEL);
        lblEstado.setForeground(new Color(220, 80, 80));
        lblEstado.setAlignmentX(CENTER_ALIGNMENT);

        // ── Motivo (del subsistema) ───────────────────────────────────────
        JTextArea txtMotivo = new JTextArea(motivo != null ? motivo : "Acceso bloqueado.");
        txtMotivo.setFont(Colores.FUENTE_LABEL);
        txtMotivo.setForeground(Colores.TEXTO_SECUNDARIO);
        txtMotivo.setBackground(Colores.FONDO_CAMPO);
        txtMotivo.setEditable(false);
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        txtMotivo.setAlignmentX(CENTER_ALIGNMENT);
        txtMotivo.setBorder(new EmptyBorder(10, 14, 10, 14));
        txtMotivo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // ── Instrucción ───────────────────────────────────────────────────
        JLabel lblInstruccion = new JLabel(
                "<html><center>Para renovar tu membresía, pasa a caja<br>"
                + "o usa la aplicación.</center></html>",
                SwingConstants.CENTER);
        lblInstruccion.setFont(Colores.FUENTE_LABEL);
        lblInstruccion.setForeground(Colores.TEXTO_SECUNDARIO);
        lblInstruccion.setAlignmentX(CENTER_ALIGNMENT);

        // ── Botones ───────────────────────────────────────────────────────
        Boton btnRenovar = crearBoton("Renovar / Comprar Membresía", Boton.Variante.PRIMARIO);
        btnRenovar.setAlignmentX(CENTER_ALIGNMENT);
        btnRenovar.addActionListener(e -> {
            dispose();
            controlador.iniciarCompraMembresia();
        });

        Boton btnVolver = crearBoton("Volver a Recepción", Boton.Variante.SECUNDARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        });

        // ── Ensamblar (sin imagen externa) ────────────────────────────────
        card.add(ico);
        card.add(Box.createVerticalStrut(10));
        card.add(titulo);
        card.add(Box.createVerticalStrut(8));
        card.add(lblEstado);
        card.add(Box.createVerticalStrut(12));
        card.add(txtMotivo);
        card.add(Box.createVerticalStrut(12));
        card.add(lblInstruccion);
        card.add(Box.createVerticalStrut(20));
        card.add(btnRenovar);
        card.add(Box.createVerticalStrut(10));
        card.add(btnVolver);

        fondo.add(card);
    }

    /**
     * Determina el texto de estado según el motivo recibido.
     * Evita mostrar siempre "INACTIVO" cuando el motivo puede ser otro.
     */
    private String resolverEstado(String motivo) {
        if (motivo == null) return "Estado: INACTIVO";
        String m = motivo.toLowerCase();
        if (m.contains("vencida") || m.contains("vencido")) return "Estado: VENCIDA";
        if (m.contains("cancelada") || m.contains("cancelado")) return "Estado: CANCELADA";
        if (m.contains("sucursal") || m.contains("ubicación")) return "Estado: SUCURSAL INCORRECTA";
        if (m.contains("no encontrado") || m.contains("no corresponde")) return "Estado: NO REGISTRADO";
        return "Estado: INACTIVO";
    }
}