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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla que muestra el código QR de la membresía activa del socio.
 * El socio la abre y pone la pantalla frente al scanner de recepción.
 *
 * @author julian izaguirre
 */
public class PantallaQR extends PantallaBase {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        JPanel card = crearCard(520, 660);
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

        String urlTexto = (m != null && m.getCodigoQR() != null) ? acortarUrl(m.getCodigoQR()) : "—";
        JLabel lblCodigo = new JLabel(urlTexto, SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Monospaced", Font.PLAIN, 10));
        lblCodigo.setForeground(Colores.TEXTO_SECUNDARIO);
        lblCodigo.setAlignmentX(CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel infoPanel = crearPanelInfo(m);
        infoPanel.setAlignmentX(CENTER_ALIGNMENT);

        Boton btnVolver = crearBoton("Volver al inicio", Boton.Variante.PRIMARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            controlador.detenerServidorQR();
            controlador.irAPerfilUsuario();
        });

        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(qrPanel);
        card.add(Box.createVerticalStrut(8));
        card.add(lblCodigo);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
        card.add(infoPanel);
        card.add(Box.createVerticalStrut(24));
        card.add(btnVolver);

        fondo.add(card);
    }

    private JPanel crearQRPanel(MembresiaDTO m) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        int tam = 280;
        panel.setPreferredSize(new Dimension(tam, tam));
        panel.setMinimumSize(new Dimension(tam, tam));
        panel.setMaximumSize(new Dimension(tam, tam));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        if (m == null || m.getIdMembresia() == null) {
            panel.add(etiquetaError("Membresía no disponible"), BorderLayout.CENTER);
            return panel;
        }

        byte[] qrBytes;
        try {
            qrBytes =controlador.generarQRMembresia(m.getIdMembresia());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible generar el código QR.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);

            panel.add(
                    etiquetaError(
                            "Error al generar el QR"),
                    BorderLayout.CENTER);
            return panel;
        }

        if (qrBytes == null) {

            panel.add(
                    etiquetaError(
                            "Error al generar el QR"),
                    BorderLayout.CENTER);

            return panel;
        }

        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(qrBytes));
            if (img != null) {
                int interior = tam - 16;
                Image scaled = img.getScaledInstance(interior, interior, Image.SCALE_SMOOTH);
                panel.add(new JLabel(new ImageIcon(scaled)), BorderLayout.CENTER);
            } else {
                panel.add(etiquetaError("No se pudo leer el QR"), BorderLayout.CENTER);
            }
        } catch (Exception ex) {
            panel.add(etiquetaError("Error al mostrar el QR"), BorderLayout.CENTER);
        }

        controlador.iniciarServidorQR(qrBytes);

        return panel;
    }

    private JLabel etiquetaError(String msg) {
        JLabel lbl = new JLabel(msg, SwingConstants.CENTER);
        lbl.setFont(Colores.FUENTE_LABEL);
        lbl.setForeground(Colores.TEXTO_SECUNDARIO);
        return lbl;
    }

    private String acortarUrl(String url) {
        try {
            int idx = url.indexOf("?id=");
            if (idx < 0) return url;
            return "steelcorefitness.com/acceso?id=" + url.substring(idx + 4, Math.min(idx + 12, url.length())) + "...";
        } catch (Exception e) {
            return url;
        }
    }

    private JPanel crearPanelInfo(MembresiaDTO m) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        if (m != null) {
            String vigencia = m.getFechaCaducidad() != null ? m.getFechaCaducidad().format(FMT) : "—";
            agregarFila(p, "Plan:", m.getIdPlan() != null ? m.getIdPlan() : "—");
            agregarFila(p, "Sucursal:", m.getIdSucursal() != null ? m.getIdSucursal() : "—");
            agregarFila(p, "Vigencia:", vigencia);
        } else {
            JLabel lbl = new JLabel("Tu membresía ha sido activada correctamente");
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
            MembresiaDTO reciente = controlador.getMembresiaRecienCreada();
            if (reciente != null) return reciente;
            return controlador.obtenerMembresiaActiva();
        } catch (Exception e) {
            return null;
        }
    }
}