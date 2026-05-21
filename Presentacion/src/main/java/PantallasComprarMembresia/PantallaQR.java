/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import ControlDeAcceso.BC_PantallaEspera;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.MembresiaDTO;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla que muestra el codigo QR de la membresia activa del socio.
 * 
 * @author julian izaguirre
 */
public class PantallaQR extends PantallaBase {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final boolean desdeRecepcion;


    public PantallaQR(IControladorAplicacion controlador) {
        this(controlador, false);
    }

    public PantallaQR(IControladorAplicacion controlador, boolean desdeRecepcion) {
        super(controlador);
        this.desdeRecepcion = desdeRecepcion;
        setTitle("Tu Membresia - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(520, 700);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(36, 48, 36, 48));

        JLabel titulo = new JLabel("Escanéame!", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Usa este codigo para acceder al gimnasio", SwingConstants.CENTER);
        sub.setFont(Colores.FUENTE_LABEL);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        MembresiaDTO m = obtenerMembresia();

        JPanel qrPanel = crearQRPanel(m);
        qrPanel.setAlignmentX(CENTER_ALIGNMENT);

        // Campo copiable con el codigo QR
        String codigoQR = (m != null && m.getCodigoQR() != null) ? m.getCodigoQR() : "";

        JTextField txtCodigo = new JTextField(codigoQR);
        txtCodigo.setFont(new Font("Monospaced", Font.PLAIN, 10));
        txtCodigo.setForeground(Colores.TEXTO_SECUNDARIO);
        txtCodigo.setBackground(Colores.FONDO_CAMPO);
        txtCodigo.setCaretColor(Colores.TEXTO_SECUNDARIO);
        txtCodigo.setEditable(false);
        txtCodigo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        txtCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtCodigo.setAlignmentX(CENTER_ALIGNMENT);
        txtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                txtCodigo.selectAll();
            }
        });

        JButton btnCopiar = new JButton("Copiar codigo");
        btnCopiar.setFont(Colores.FUENTE_LABEL);
        btnCopiar.setForeground(Colores.TEXTO_PRINCIPAL);
        btnCopiar.setBackground(new Color(60, 60, 100));
        btnCopiar.setOpaque(true);
        btnCopiar.setBorderPainted(false);
        btnCopiar.setFocusPainted(false);
        btnCopiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCopiar.setAlignmentX(CENTER_ALIGNMENT);
        btnCopiar.setEnabled(!codigoQR.isEmpty());
        btnCopiar.addActionListener(e -> {
            if (!codigoQR.isEmpty()) {
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(codigoQR), null);
                btnCopiar.setText("Copiado");
                Timer t = new Timer(2000, ev -> btnCopiar.setText("Copiar codigo"));
                t.setRepeats(false);
                t.start();
            }
        });

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel infoPanel = crearPanelInfo(m);
        infoPanel.setAlignmentX(CENTER_ALIGNMENT);

        String textoVolver = desdeRecepcion ? "Volver a Recepcion" : "Volver al inicio";
        Boton btnVolver = crearBoton(textoVolver, Boton.Variante.PRIMARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            controlador.detenerServidorQR();
            dispose();
            if (desdeRecepcion) {
                // Regresa al Modulo de Recepcion para seguir atendiendo socios
                new BC_PantallaEspera(controlador).setVisible(true);
            } else {
                // Regresa al perfil del socio (flujo normal de compra/app)
                controlador.irAPerfilUsuario();
            }
        });

        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
        card.add(qrPanel);
        card.add(Box.createVerticalStrut(8));
        card.add(txtCodigo);
        card.add(Box.createVerticalStrut(6));
        card.add(btnCopiar);
        card.add(Box.createVerticalStrut(14));
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
            panel.add(etiquetaError("Membresia no disponible"), BorderLayout.CENTER);
            return panel;
        }

        byte[] qrBytes;
        try {
            qrBytes = controlador.generarQRMembresia(m.getIdMembresia());
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible generar el codigo QR.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            panel.add(etiquetaError("Error al generar el QR"), BorderLayout.CENTER);
            return panel;
        }

        if (qrBytes == null) {
            panel.add(etiquetaError("Error al generar el QR"), BorderLayout.CENTER);
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
        } catch (IOException ex) {
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

    private JPanel crearPanelInfo(MembresiaDTO m) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        if (m != null) {
            String vigencia = m.getFechaCaducidad() != null
                    ? m.getFechaCaducidad().format(FMT) : "—";
            agregarFila(p, "Plan:",      m.getIdPlan()      != null ? m.getIdPlan()      : "—");
            agregarFila(p, "Sucursal:",  m.getIdSucursal()  != null ? m.getIdSucursal()  : "—");
            agregarFila(p, "Vigencia:",  vigencia);
        } else {
            JLabel lbl = new JLabel("Tu membresia ha sido activada correctamente");
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
        } catch (NegocioException e) {
            return null;
        }
    }
}