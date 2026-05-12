package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
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

        // ── Ícono de error ────────────────────────────────────────────────
        JLabel ico = new JLabel("✕", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.BOLD, 52));
        ico.setForeground(new Color(220, 80, 80));
        ico.setAlignmentX(CENTER_ALIGNMENT);

        // ── Título ────────────────────────────────────────────────────────
        JLabel titulo = new JLabel("Acceso Denegado", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        // ── Estado ────────────────────────────────────────────────────────
        JLabel lblEstado = new JLabel("● Estado: INACTIVO", SwingConstants.CENTER);
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
            // Redirige al flujo de compra de membresía (otro subsistema)
            controlador.iniciarCompraMembresia();
        });

        Boton btnVolver = crearBoton("Volver a Recepción", Boton.Variante.SECUNDARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            dispose();
            new BC_PantallaEspera(controlador).setVisible(true);
        });

        // ── Ensamblar ─────────────────────────────────────────────────────
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
}
