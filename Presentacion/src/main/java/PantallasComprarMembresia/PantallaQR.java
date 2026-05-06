/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.MembresiaDTO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaQR extends PantallaBase {
    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");
 
    public PantallaQR(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Tu Membresía - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(520, 640);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));
 
        JLabel titulo = new JLabel("¡Escanéame!", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Usa este código para acceder al gimnasio", SwingConstants.CENTER);
        sub.setFont(Colores.FUENTE_LABEL);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        MembresiaDTO m = obtenerMembresia();
 
        JPanel qrPanel = crearQRPanel(m);
        qrPanel.setAlignmentX(CENTER_ALIGNMENT);
 
        String codigoTexto = (m != null && m.getCodigoQR() != null)
            ? m.getCodigoQR()
            : "—";
        JLabel lblCodigo = new JLabel(codigoTexto, SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Monospaced", Font.BOLD, 11));
        lblCodigo.setForeground(Colores.ACENTO);
        lblCodigo.setAlignmentX(CENTER_ALIGNMENT);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
        JPanel infoPanel = crearPanelInfo(m);
        infoPanel.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnVolver = crearBoton("Volver al inicio", Boton.Variante.PRIMARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> controlador.irABienvenida());
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(qrPanel);
        card.add(Box.createVerticalStrut(10));
        card.add(lblCodigo);
        card.add(Box.createVerticalStrut(20));
        card.add(sep);
        card.add(Box.createVerticalStrut(16));
        card.add(infoPanel);
        card.add(Box.createVerticalStrut(24));
        card.add(btnVolver);
 
        fondo.add(card);
    }
    
    private JPanel crearQRPanel(MembresiaDTO m) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(220, 220));
        panel.setMinimumSize  (new Dimension(220, 220));
        panel.setMaximumSize  (new Dimension(220, 220));
 
        if (m != null && m.getIdMembresia() != null) {
            // Delegamos la generación al coordinador → negocio
            byte[] qrBytes = controlador.generarQRMembresia(m.getIdMembresia());
 
            if (qrBytes != null) {
                ImageIcon icono = new ImageIcon(qrBytes);
                panel.add(new JLabel(icono), BorderLayout.CENTER);
            } else {
                panel.add(etiquetaError("Error al generar el QR"), BorderLayout.CENTER);
            }
        } else {
            panel.add(etiquetaError("Membresía no disponible"), BorderLayout.CENTER);
        }
 
        return panel;
    }
 
    private JLabel etiquetaError(String msg) {
        JLabel lbl = new JLabel(msg, SwingConstants.CENTER);
        lbl.setFont(Colores.FUENTE_LABEL);
        lbl.setForeground(Colores.TEXTO_SECUNDARIO);
        return lbl;
    }
 
    // ── Información de la membresía ───────────────────────────────────────────
 
    private JPanel crearPanelInfo(MembresiaDTO m) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
 
        if (m != null) {
            String vigencia = (m.getFechaCaducidad() != null)
                ? m.getFechaCaducidad().format(FMT)
                : "—";
            agregarFila(p, "Plan:",     m.getIdPlan()     != null ? m.getIdPlan()     : "—");
            agregarFila(p, "Sucursal:", m.getIdSucursal() != null ? m.getIdSucursal() : "—");
            agregarFila(p, "Vigencia:", vigencia);
        } else {
            JLabel lbl = new JLabel("Tu membresía ha sido activada correctamente.");
            lbl.setFont(Colores.FUENTE_LABEL);
            lbl.setForeground(Colores.TEXTO_SECUNDARIO);
            lbl.setAlignmentX(CENTER_ALIGNMENT);
            p.add(lbl);
        }
 
        return p;
    }
 
    private void agregarFila(JPanel panel, String etiqueta, String valor) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 2));
        fila.setOpaque(false);
 
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Colores.FUENTE_LABEL);
        lbl.setForeground(Colores.TEXTO_SECUNDARIO);
 
        JLabel val = new JLabel(valor);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(Colores.TEXTO_PRINCIPAL);
 
        fila.add(lbl);
        fila.add(val);
        panel.add(fila);
    }
 
    private MembresiaDTO obtenerMembresia() {
        try {
            return controlador.obtenerMembresiaActiva();
        } catch (Exception e) {
            return null;
        }
    }
}
