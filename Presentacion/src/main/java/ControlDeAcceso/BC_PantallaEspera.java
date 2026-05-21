/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtosControlDeAcceso.ResultadoAccesoDTO;

import PantallasInicioSesion.PantallaInicioSesionSocios;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Modulo de Recepcion — pantalla principal del subsistema Control de Acceso.
 *
 * CORRECCIONES aplicadas respecto a la versión anterior:
 *   1. Campo de código más grande: altura 52px, fuente Consolas 16pt.
 *   2. Botón COPIAR funcional: copia al portapapeles del sistema y muestra
 *      "COPIADO" durante 1.5 segundos como feedback visual.
 *   3. Main separado: este módulo ya no abre login. MainAcceso.java llama
 *      directamente a irAModuloRecepcion().
 *
 * Lo que NO cambia (la lógica ya funcionaba):
 *   - Cámara QR con Webcam/Robot fallback.
 *   - procesarCodigo() → BC_PantallaExpediente o BC_PantallaAccesoDenegado.
 *   - Botón "Ver QR del Socio".
 *   - Botón "Cerrar sesión".
 *
 * @author julian izaguirre
 */
public class BC_PantallaEspera extends PantallaBase {

    private static final Map<DecodeHintType, Object> QR_HINTS;
    static {
        QR_HINTS = new EnumMap<>(DecodeHintType.class);
        QR_HINTS.put(DecodeHintType.POSSIBLE_FORMATS, List.of(BarcodeFormat.QR_CODE));
        QR_HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
    }

    private final Icontrolacceso controlAcceso;

    private QRCamaraCapturador camaraCapturador;
    private JLabel  lblVisor;
    private JLabel  lblEstadoCamara;
    private JButton btnCamara;
    private boolean camaraActiva = false;

    // CORRECCIÓN: campo con tamaño mayor para que sea más cómodo de leer/pegar
    private JTextField txtCodigo;

    private volatile boolean procesando = false;

    public BC_PantallaEspera(IControladorAplicacion controlador) {
        super(controlador);
        this.controlAcceso = FachadaControlAcceso.getInstancia();
        setTitle("SteelCore Fitness — Modulo de Recepcion");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        // Tarjeta un poco más alta para acomodar el campo grande
        JPanel card = crearCard(500, 700);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 44, 32, 44));

        JLabel logo = new JLabel("Modulo de Recepcion", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        logo.setForeground(Colores.ACENTO);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Esperando QR del socio", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Escanea con la camara o ingresa el codigo manualmente",
                SwingConstants.CENTER);
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        // ── Visor de cámara ─────────────────────────────────────────────────
        lblVisor = new JLabel("", SwingConstants.CENTER);
        lblVisor.setPreferredSize(new Dimension(380, 220));
        lblVisor.setMaximumSize(new Dimension(380, 220));
        lblVisor.setMinimumSize(new Dimension(380, 220));
        lblVisor.setBackground(Color.BLACK);
        lblVisor.setOpaque(true);
        lblVisor.setAlignmentX(CENTER_ALIGNMENT);

        lblEstadoCamara = new JLabel("Camara apagada", SwingConstants.CENTER);
        lblEstadoCamara.setFont(Colores.FUENTE_LABEL);
        lblEstadoCamara.setForeground(new Color(180, 180, 180));
        lblEstadoCamara.setAlignmentX(CENTER_ALIGNMENT);

        JPanel visorWrapper = new JPanel();
        visorWrapper.setLayout(new OverlayLayout(visorWrapper));
        visorWrapper.setOpaque(false);
        visorWrapper.setMaximumSize(new Dimension(380, 240));
        visorWrapper.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelVisor = new JPanel(new BorderLayout());
        panelVisor.setBackground(Color.BLACK);
        panelVisor.setMaximumSize(new Dimension(380, 240));
        panelVisor.add(lblVisor, BorderLayout.CENTER);
        panelVisor.add(lblEstadoCamara, BorderLayout.SOUTH);
        visorWrapper.add(panelVisor);

        btnCamara = new JButton("Iniciar camara");
        btnCamara.setFont(Colores.FUENTE_LABEL);
        btnCamara.setForeground(Colores.TEXTO_PRINCIPAL);
        btnCamara.setBackground(Colores.ACENTO);
        btnCamara.setOpaque(true);
        btnCamara.setBorderPainted(false);
        btnCamara.setFocusPainted(false);
        btnCamara.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCamara.setAlignmentX(CENTER_ALIGNMENT);
        btnCamara.addActionListener(e -> toggleCamara());

        JLabel lblOr = new JLabel("o ingresa el codigo manualmente", SwingConstants.CENTER);
        lblOr.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblOr.setForeground(Colores.TEXTO_SECUNDARIO);
        lblOr.setAlignmentX(CENTER_ALIGNMENT);

        // ── CORRECCIÓN 1: Campo de código MÁS GRANDE ────────────────────────
        // Antes: altura por defecto (~32px), fuente de campo normal.
        // Ahora: altura fija 52px, fuente monoespaciada 16pt para que los
        //        códigos UUID/QR largos sean fáciles de leer y pegar.
        txtCodigo = new JTextField();
        txtCodigo.setFont(new Font("Consolas", Font.PLAIN, 16));
        txtCodigo.setForeground(Colores.TEXTO_PRINCIPAL);
        txtCodigo.setBackground(Colores.FONDO_CAMPO);
        txtCodigo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        txtCodigo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                new EmptyBorder(12, 14, 12, 14)));   // padding vertical mayor
        txtCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));  // altura 52px
        txtCodigo.setPreferredSize(new Dimension(300, 52));
        txtCodigo.setAlignmentX(LEFT_ALIGNMENT);
        txtCodigo.addActionListener(e -> procesarCodigoManual());        // Enter = procesar

        // ── CORRECCIÓN 2: Botón COPIAR funcional ────────────────────────────
        // Antes: el botón no tenía addActionListener, no hacía nada.
        // Ahora: copia el texto del campo al portapapeles del sistema (Windows/macOS/Linux).
        //        Muestra "COPIADO ✓" por 1.5 segundos como feedback visual.
        JButton btnCopiar = new JButton("COPIAR");
        btnCopiar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnCopiar.setToolTipText("Copiar codigo al portapapeles");
        btnCopiar.setFocusPainted(false);
        btnCopiar.setBorderPainted(false);
        btnCopiar.setBackground(Colores.FONDO_CAMPO);
        btnCopiar.setForeground(Colores.TEXTO_PRINCIPAL);
        btnCopiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCopiar.setPreferredSize(new Dimension(110, 52));  // mismo alto que el campo
        btnCopiar.addActionListener(e -> {
            String texto = txtCodigo.getText().trim();
            if (!texto.isEmpty()) {
                // Copia real al portapapeles del sistema operativo
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(texto), null);
                // Feedback visual temporal
                btnCopiar.setText("COPIADO ✓");
                btnCopiar.setForeground(new Color(100, 220, 140));
                Timer t = new Timer(1500, ev -> {
                    btnCopiar.setText("COPIAR");
                    btnCopiar.setForeground(Colores.TEXTO_PRINCIPAL);
                });
                t.setRepeats(false);
                t.start();
            } else {
                // Si el campo está vacío, dar feedback de error
                btnCopiar.setText("¡Vacío!");
                Timer t = new Timer(1000, ev -> btnCopiar.setText("COPIAR"));
                t.setRepeats(false);
                t.start();
            }
        });

        // Panel fila: campo grande + botón copiar alineados en altura
        JPanel panelCodigo = new JPanel(new BorderLayout(8, 0));
        panelCodigo.setOpaque(false);
        panelCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
        panelCodigo.setAlignmentX(LEFT_ALIGNMENT);
        panelCodigo.add(txtCodigo, BorderLayout.CENTER);
        panelCodigo.add(btnCopiar, BorderLayout.EAST);

        Boton btnProcesar = crearBoton("Procesar codigo", Boton.Variante.PRIMARIO);
        btnProcesar.setAlignmentX(CENTER_ALIGNMENT);
        btnProcesar.addActionListener(e -> procesarCodigoManual());

        Boton btnVerQR = crearBoton("Ver mi QR", Boton.Variante.SECUNDARIO);
        btnVerQR.setAlignmentX(CENTER_ALIGNMENT);
        btnVerQR.addActionListener(e -> {
            detenerCamara();
            dispose();
            controlador.irAQRDesdeRecepcion();
        });

        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(LEFT_ALIGNMENT);

        Boton btnCerrarSesion = crearBoton("Cerrar sesion", Boton.Variante.SECUNDARIO);
        btnCerrarSesion.setAlignmentX(CENTER_ALIGNMENT);
        btnCerrarSesion.addActionListener(e -> {
            detenerCamara();
            controlador.cerrarSesion();
            dispose();
            new PantallaInicioSesionSocios(controlador).setVisible(true);
        });

        // Ensamblar tarjeta
        card.add(logo);
        card.add(Box.createVerticalStrut(6));
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(16));
        card.add(visorWrapper);
        card.add(Box.createVerticalStrut(10));
        card.add(btnCamara);
        card.add(Box.createVerticalStrut(18));
        card.add(lblOr);
        card.add(Box.createVerticalStrut(10));
        card.add(panelCodigo);    // campo grande + botón copiar
        card.add(Box.createVerticalStrut(12));
        card.add(btnProcesar);
        card.add(Box.createVerticalStrut(10));
        card.add(btnVerQR);
        card.add(Box.createVerticalStrut(16));
        card.add(sep);
        card.add(Box.createVerticalStrut(10));
        card.add(btnCerrarSesion);

        fondo.add(card);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                detenerCamara();
            }
        });

        SwingUtilities.invokeLater(() -> txtCodigo.requestFocusInWindow());
    }

    // -----------------------------------------------------------------
    //  Control de la cámara — sin cambios, ya funcionaba
    // -----------------------------------------------------------------

    private void toggleCamara() {
        if (camaraActiva) {
            detenerCamara();
        } else {
            iniciarCamara();
        }
    }

    private void iniciarCamara() {
        camaraCapturador = new QRCamaraCapturador(
            frame -> SwingUtilities.invokeLater(() -> {
                Image img = frame.getScaledInstance(380, 220, Image.SCALE_FAST);
                lblVisor.setIcon(new ImageIcon(img));
                lblEstadoCamara.setText("");
            }),
            codigo -> {
                if (!procesando) {
                    procesando = true;
                    SwingUtilities.invokeLater(() -> procesarCodigo(codigo));
                }
            }
        );

        boolean abierta = camaraCapturador.iniciar();
        if (abierta) {
            camaraActiva = true;
            btnCamara.setText("Detener camara");
            lblEstadoCamara.setText("Esperando codigo QR...");
        } else {
            camaraCapturador = null;
            JOptionPane.showMessageDialog(this,
                "No se pudo acceder a la camara.\nUsa el campo manual.",
                "Camara no disponible",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void detenerCamara() {
        if (camaraCapturador != null) {
            camaraCapturador.detener();
            camaraCapturador = null;
        }
        camaraActiva = false;
        btnCamara.setText("Iniciar camara");
        lblVisor.setIcon(null);
        lblEstadoCamara.setText("Camara apagada");
    }

    // -----------------------------------------------------------------
    //  Procesamiento del código QR — sin cambios, ya funcionaba
    // -----------------------------------------------------------------

    private void procesarCodigoManual() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa un codigo QR.",
                    "Campo vacio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        procesarCodigo(codigo);
    }

    private void procesarCodigo(String codigo) {
        detenerCamara();

        try {
            ResultadoAccesoDTO resultado = controlAcceso.procesarQR(codigo);

            txtCodigo.setText("");
            procesando = false;
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);

        } catch (AccesoDenegadoException ex) {
            txtCodigo.setText("");
            procesando = false;
            dispose();
            new BC_PantallaAccesoDenegado(controlador, ex.getMotivo()).setVisible(true);

        } catch (Exception ex) {
            txtCodigo.setText("");
            procesando = false;
            JOptionPane.showMessageDialog(this,
                    "Ocurrio un error al procesar el codigo.\nIntenta de nuevo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // -----------------------------------------------------------------
    //  Clase interna: captura de cámara / pantalla para decodificar QR
    //  Sin cambios respecto a la versión original — ya funcionaba.
    // -----------------------------------------------------------------

    private static class QRCamaraCapturador {

        @FunctionalInterface interface OnFrame { void onFrame(BufferedImage frame); }
        @FunctionalInterface interface OnQR    { void onQR(String codigo); }

        private final OnFrame onFrame;
        private final OnQR    onQR;
        private Thread  hilo;
        private volatile boolean corriendo = false;
        private Object  webcam;

        QRCamaraCapturador(OnFrame onFrame, OnQR onQR) {
            this.onFrame = onFrame;
            this.onQR    = onQR;
        }

        boolean iniciar() {
            try {
                Class<?> webcamClass = Class.forName("com.github.sarxos.webcam.Webcam");
                java.lang.reflect.Method getDefault = webcamClass.getMethod("getDefault");
                webcam = getDefault.invoke(null);
                if (webcam == null) return false;

                java.lang.reflect.Method open = webcamClass.getMethod("open");
                open.invoke(webcam);

                corriendo = true;
                hilo = new Thread(this::bucleCaptura, "QR-Camara");
                hilo.setDaemon(true);
                hilo.start();
                return true;

            } catch (ClassNotFoundException e) {
                return iniciarConRobot();
            } catch (Exception e) {
                return false;
            }
        }

        private boolean iniciarConRobot() {
            try {
                Robot robot = new Robot();
                corriendo = true;
                hilo = new Thread(() -> {
                    MultiFormatReader reader = new MultiFormatReader();
                    reader.setHints(QR_HINTS);

                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    int capturaW = pantalla.width  / 2;
                    int capturaH = pantalla.height / 2;
                    int capturaX = pantalla.width  / 4;
                    int capturaY = pantalla.height / 4;
                    Rectangle areaCaptura = new Rectangle(capturaX, capturaY, capturaW, capturaH);

                    while (corriendo) {
                        try {
                            BufferedImage frame = robot.createScreenCapture(areaCaptura);
                            onFrame.onFrame(frame);
                            BufferedImage reducida = escalarImagen(frame, 640);
                            detectarQR(reducida, reader);
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                            break;
                        } catch (Exception ignored) {}
                    }
                }, "QR-Robot");
                hilo.setDaemon(true);
                hilo.start();
                return true;
            } catch (AWTException e) {
                return false;
            }
        }

        private void bucleCaptura() {
            MultiFormatReader reader = new MultiFormatReader();
            reader.setHints(QR_HINTS);

            try {
                Class<?> webcamClass = Class.forName("com.github.sarxos.webcam.Webcam");
                try {
                    java.lang.reflect.Method setViewSize =
                        webcamClass.getMethod("setViewSize", Dimension.class);
                    setViewSize.invoke(webcam, new Dimension(640, 480));
                } catch (Exception ignored) {}

                java.lang.reflect.Method getImage = webcamClass.getMethod("getImage");

                while (corriendo) {
                    BufferedImage frame = (BufferedImage) getImage.invoke(webcam);
                    if (frame != null) {
                        onFrame.onFrame(frame);
                        BufferedImage reducida = escalarImagen(frame, 640);
                        detectarQR(reducida, reader);
                    }
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // cámara desconectada durante la captura
            }
        }

        private BufferedImage escalarImagen(BufferedImage original, int maxAncho) {
            if (original.getWidth() <= maxAncho) return original;
            double ratio = (double) maxAncho / original.getWidth();
            int nuevoAlto = (int) (original.getHeight() * ratio);
            BufferedImage resultado = new BufferedImage(maxAncho, nuevoAlto,
                    BufferedImage.TYPE_INT_RGB);
            resultado.createGraphics().drawImage(
                original.getScaledInstance(maxAncho, nuevoAlto, Image.SCALE_FAST),
                0, 0, null);
            return resultado;
        }

        private void detectarQR(BufferedImage imagen, MultiFormatReader reader) {
            try {
                BinaryBitmap bitmap = new BinaryBitmap(
                    new HybridBinarizer(
                        new BufferedImageLuminanceSource(imagen)));
                Result resultado = reader.decode(bitmap, QR_HINTS);
                if (resultado != null) {
                    onQR.onQR(resultado.getText());
                }
            } catch (NotFoundException e) {
                // Frame sin QR, normal
            } catch (Exception e) {
                // Error puntual, ignorar
            }
        }

        void detener() {
            corriendo = false;
            if (hilo != null) {
                hilo.interrupt();
            }
            if (webcam != null) {
                try {
                    Class<?> webcamClass = Class.forName("com.github.sarxos.webcam.Webcam");
                    java.lang.reflect.Method close = webcamClass.getMethod("close");
                    close.invoke(webcam);
                } catch (Exception ignored) {}
                webcam = null;
            }
        }
    }
}