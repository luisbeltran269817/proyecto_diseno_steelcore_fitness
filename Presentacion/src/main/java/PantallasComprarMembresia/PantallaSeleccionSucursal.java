/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.SucursalDTO;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla para que el usuario seleccione una sucursal del gimnasio.
 * Se comunica únicamente con IControladorAplicacion.
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionSucursal extends PantallaBase {

    private ButtonGroup grupoBotones;
    private JPanel panelSucursales;
    private SucursalDTO sucursalSeleccionada = null;

    private JWindow popupWindow;
    private JLabel  popupNombre, popupDireccion;
    private JButton popupBtn;

    // Panel central que contiene el mapa (o el placeholder si falla)
    private JPanel panelCentro;

    public PantallaSeleccionSucursal(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Sucursal - SteelCore Fitness");
        inicializarComponentes();
        cargarSucursales();
        registrarListenerMarcadores();
        controlador.ubicarUsuarioAutomaticamente();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        fondo.add(crearPanelIzquierdo(), BorderLayout.WEST);

        // Panel central con placeholder por si el mapa aun no esta listo
        panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(Colores.FONDO_PRINCIPAL);
        fondo.add(panelCentro, BorderLayout.CENTER);

        // Intentar agregar el mapa — si es null se muestra placeholder
        agregarComponenteMapa();

        crearPopup();
    }

    /**
     * Intenta obtener el widget del mapa y agregarlo al panel central.
     * Si el mapa aun no esta registrado muestra un placeholder y reintenta
     * en un segundo plano para no bloquear la UI.
     */
    private void agregarComponenteMapa() {
        JComponent mapa = null;
        try {
            mapa = controlador.getComponenteMapa();
        } catch (Exception ex) {
            System.getLogger(getClass().getName()).log(
                System.Logger.Level.WARNING, "Mapa no disponible aun: {0}", ex.getMessage());
        }

        if (mapa != null) {
            panelCentro.add(mapa, BorderLayout.CENTER);
        } else {
            // Placeholder visual mientras el mapa carga
            JLabel cargando = new JLabel("Cargando mapa...", SwingConstants.CENTER);
            cargando.setForeground(Colores.TEXTO_SECUNDARIO);
            cargando.setFont(Colores.FUENTE_SUBTITULO);
            panelCentro.add(cargando, BorderLayout.CENTER);

            // Reintentar en 500ms sin bloquear el hilo de Swing
            Timer timer = new Timer(500, null);
            timer.addActionListener(e -> {
                JComponent mapaReintentar = null;
                try {
                    mapaReintentar = controlador.getComponenteMapa();
                } catch (Exception ex) { /* sigue esperando */ }

                if (mapaReintentar != null) {
                    timer.stop();
                    panelCentro.removeAll();
                    panelCentro.add(mapaReintentar, BorderLayout.CENTER);
                    panelCentro.revalidate();
                    panelCentro.repaint();
                }
            });
            timer.start();
        }
    }

    private void registrarListenerMarcadores() {
        controlador.setOnMarcadorClickListener(idSucursal -> {
            SucursalDTO s = controlador.onMarcadorClickeado(idSucursal);
            if (s != null) SwingUtilities.invokeLater(() -> {
                sucursalSeleccionada = s;
                mostrarPopup(s);
                sincronizarRadio(s);
            });
        });
    }

    private void cargarSucursales() {
        try {
            List<SucursalDTO> lista = controlador.iniciarMapa();
            panelSucursales.removeAll();
            grupoBotones = new ButtonGroup();
            for (SucursalDTO s : lista) {
                panelSucursales.add(crearCard(s));
                panelSucursales.add(Box.createVerticalStrut(10));
            }
            panelSucursales.revalidate();
            panelSucursales.repaint();
        } catch (NegocioException ex) {
            System.getLogger(PantallaSeleccionSucursal.class.getName())
                .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(36, 32, 36, 20));
        panel.setPreferredSize(new Dimension(300, 0));

        JLabel titulo = new JLabel("<html>Selecciona<br>tu Sucursal</html>");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel sub = new JLabel("Elige el gimnasio más cercano");
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(LEFT_ALIGNMENT);

        panelSucursales = new JPanel();
        panelSucursales.setLayout(new BoxLayout(panelSucursales, BoxLayout.Y_AXIS));
        panelSucursales.setOpaque(false);
        panelSucursales.setAlignmentX(LEFT_ALIGNMENT);

        JScrollPane scroll = new JScrollPane(panelSucursales);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setAlignmentX(LEFT_ALIGNMENT);

        Boton btnContinuar = crearBoton("Continuar", Boton.Variante.PRIMARIO);
        Boton btnRegresar  = crearBoton("Regresar",  Boton.Variante.SECUNDARIO);
        btnContinuar.setAlignmentX(LEFT_ALIGNMENT);
        btnRegresar.setAlignmentX(LEFT_ALIGNMENT);
        btnContinuar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnRegresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));

        btnRegresar.addActionListener(e -> controlador.irAPerfilUsuario());
        btnContinuar.addActionListener(e -> {
            if (sucursalSeleccionada == null) {
                JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una sucursal.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.setSucursalSeleccionada(sucursalSeleccionada);
            controlador.irASeleccionPlan();
        });

        panel.add(titulo);       panel.add(Box.createVerticalStrut(6));
        panel.add(sub);          panel.add(Box.createVerticalStrut(24));
        panel.add(scroll);       panel.add(Box.createVerticalStrut(24));
        panel.add(btnContinuar); panel.add(Box.createVerticalStrut(10));
        panel.add(btnRegresar);
        return panel;
    }

    private JPanel crearCard(SucursalDTO s) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(10, 0));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));
        card.setAlignmentX(LEFT_ALIGNMENT);

        JLabel nombre = new JLabel(s.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nombre.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel dir = new JLabel(s.getColonia() + ", " + s.getCiudad());
        dir.setFont(Colores.FUENTE_LABEL);
        dir.setForeground(Colores.TEXTO_SECUNDARIO);

        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(nombre);
        info.add(Box.createVerticalStrut(3));
        info.add(dir);

        JRadioButton radio = new JRadioButton("Elegir");
        radio.setFont(Colores.FUENTE_BOTON_SM);
        radio.setForeground(Colores.TEXTO_PRINCIPAL);
        radio.setOpaque(false);
        radio.addActionListener(e -> {
            sucursalSeleccionada = s;
            controlador.onMarcadorClickeado(s.getIdSucursal());
            controlador.centrarMapaEn(s.getLatitud(), s.getLongitud());
        });

        grupoBotones.add(radio);
        card.add(info,  BorderLayout.CENTER);
        card.add(radio, BorderLayout.EAST);
        return card;
    }

    private void crearPopup() {
        popupWindow = new JWindow(this);
        popupWindow.setAlwaysOnTop(true);

        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(22, 33, 62));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(new Color(106, 13, 173));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(12, 14, 12, 14));

        popupNombre = new JLabel();
        popupNombre.setFont(new Font("Segoe UI", Font.BOLD, 13));
        popupNombre.setForeground(new Color(255, 215, 0));
        popupNombre.setAlignmentX(LEFT_ALIGNMENT);

        popupDireccion = new JLabel();
        popupDireccion.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        popupDireccion.setForeground(new Color(170, 170, 204));
        popupDireccion.setAlignmentX(LEFT_ALIGNMENT);

        popupBtn = new JButton("✓ Seleccionar");
        popupBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        popupBtn.setForeground(Color.WHITE);
        popupBtn.setBackground(new Color(106, 13, 173));
        popupBtn.setBorder(new EmptyBorder(6, 10, 6, 10));
        popupBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        popupBtn.setOpaque(true);
        popupBtn.setFocusPainted(false);
        popupBtn.setAlignmentX(LEFT_ALIGNMENT);
        popupBtn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { popupBtn.setBackground(new Color(139, 47, 201)); }
            @Override public void mouseExited (MouseEvent e) { popupBtn.setBackground(new Color(106, 13, 173)); }
        });

        card.add(popupNombre);
        card.add(Box.createVerticalStrut(3));
        card.add(popupDireccion);
        card.add(Box.createVerticalStrut(8));
        card.add(popupBtn);
        popupWindow.setContentPane(card);

        // Cerrar popup al hacer clic en cualquier parte del mapa
        // Solo si el componente ya existe en ese momento
        JComponent compMapa = null;
        try { compMapa = controlador.getComponenteMapa(); } catch (Exception ignored) {}
        if (compMapa != null) {
            final JComponent mapaFinal = compMapa;
            mapaFinal.addMouseListener(new MouseAdapter() {
                @Override public void mousePressed(MouseEvent e) { popupWindow.setVisible(false); }
            });
        }
    }

    private void mostrarPopup(SucursalDTO s) {
        popupNombre.setText(s.getNombre());

        StringBuilder dir = new StringBuilder("<html>");
        if (s.getCalle()   != null && !s.getCalle().isBlank())   dir.append(s.getCalle()).append(", ");
        if (s.getColonia() != null && !s.getColonia().isBlank()) dir.append(s.getColonia());
        dir.append("<br>");
        if (s.getCiudad()  != null && !s.getCiudad().isBlank())  dir.append(s.getCiudad());
        dir.append("</html>");
        popupDireccion.setText(dir.toString());

        for (ActionListener al : popupBtn.getActionListeners())
            popupBtn.removeActionListener(al);

        popupBtn.addActionListener(e -> {
            sucursalSeleccionada = s;
            controlador.onMarcadorClickeado(s.getIdSucursal());
            controlador.centrarMapaEn(s.getLatitud(), s.getLongitud());
            popupWindow.setVisible(false);
            sincronizarRadio(s);
        });

        JComponent mv = null;
        try { mv = controlador.getComponenteMapa(); } catch (Exception ignored) {}
        if (mv == null) return; // si el mapa no está listo no mostrar popup

        Point p = mv.getLocationOnScreen();
        popupWindow.setLocation(
            p.x + mv.getWidth()  / 2 - 105,
            p.y + mv.getHeight() / 2 - 60
        );
        popupWindow.pack();
        popupWindow.setVisible(true);
    }

    private void sincronizarRadio(SucursalDTO s) {
        for (Component comp : panelSucursales.getComponents()) {
            if (!(comp instanceof JPanel card)) continue;
            BorderLayout bl = (BorderLayout) card.getLayout();
            Component east   = bl.getLayoutComponent(BorderLayout.EAST);
            Component center = bl.getLayoutComponent(BorderLayout.CENTER);
            if (!(east   instanceof JRadioButton radio)) continue;
            if (!(center instanceof JPanel info))        continue;
            for (Component c : info.getComponents())
                if (c instanceof JLabel lbl && lbl.getText().equals(s.getNombre()))
                    radio.setSelected(true);
        }
    }
}