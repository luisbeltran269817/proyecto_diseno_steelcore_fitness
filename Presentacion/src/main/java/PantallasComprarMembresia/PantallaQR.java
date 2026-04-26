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
        
        JPanel qrPanel = crearQRVisual();
        qrPanel.setAlignmentX(CENTER_ALIGNMENT);
 
        MembresiaDTO m = obtenerMembresia();
        String codigoTexto = (m != null && m.getCodigoQR() != null)
            ? m.getCodigoQR()
            : "QR-STEELCORE-" + System.currentTimeMillis();
 
        JLabel lblCodigo = new JLabel(codigoTexto, SwingConstants.CENTER);
        lblCodigo.setFont(new Font("Monospaced", Font.BOLD, 12));
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
 
    private JPanel crearQRVisual() {
        String seed = (controlador.getUsuarioActual() != null)
            ? controlador.getUsuarioActual().getCorreo()
            : "default";
 
        return new JPanel() {
            private final boolean[][] matriz = generarMatrizQR(seed);
 
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
                int size   = 200;
                int modulos = 21;
                int celda  = size / modulos;
                int offX   = (getWidth()  - size) / 2;
                int offY   = (getHeight() - size) / 2;
 
                // Fondo blanco del QR
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(offX - 8, offY - 8, size + 16, size + 16, 12, 12));
 
                // Módulos del QR
                g2.setColor(new Color(20, 20, 20));
                for (int r = 0; r < modulos; r++) {
                    for (int c = 0; c < modulos; c++) {
                        if (matriz[r][c]) {
                            g2.fillRect(offX + c * celda, offY + r * celda, celda - 1, celda - 1);
                        }
                    }
                }
 
                // Tres esquinas de posición (patrones fijos del QR real)
                dibujarEsquina(g2, offX, offY, celda);
                dibujarEsquina(g2, offX + (modulos - 7) * celda, offY, celda);
                dibujarEsquina(g2, offX, offY + (modulos - 7) * celda, celda);
 
                g2.dispose();
            }
 
            @Override public Dimension getPreferredSize() { return new Dimension(220, 220); }
            @Override public Dimension getMinimumSize()   { return getPreferredSize(); }
            @Override public Dimension getMaximumSize()   { return getPreferredSize(); }
        };
    }
 
    private boolean[][] generarMatrizQR(String seed) {
        boolean[][] m = new boolean[21][21];
        Random rnd = new Random(seed.hashCode());
        for (int r = 0; r < 21; r++)
            for (int c = 0; c < 21; c++)
                m[r][c] = rnd.nextBoolean();
        return m;
    }
 
    private void dibujarEsquina(Graphics2D g2, int x, int y, int celda) {
        // Marco exterior negro
        g2.setColor(new Color(20, 20, 20));
        g2.fillRect(x, y, 7 * celda, 7 * celda);
        // Interior blanco
        g2.setColor(Color.WHITE);
        g2.fillRect(x + celda, y + celda, 5 * celda, 5 * celda);
        // Centro negro
        g2.setColor(new Color(20, 20, 20));
        g2.fillRect(x + 2 * celda, y + 2 * celda, 3 * celda, 3 * celda);
    }
 
    private JPanel crearPanelInfo(MembresiaDTO m) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
 
        if (m != null) {
            String vigencia = (m.getFechaCaducidad() != null)
                ? m.getFechaCaducidad().format(FMT)
                : "—";
 
            agregarFila(p, "Plan:",     m.getIdPlan() != null ? m.getIdPlan() : "—");
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
