package ControlDeAcceso;

import Controladores.IControladorAplicacion;
import Fachada.FachadaControlAcceso;
import Fachada.Icontrolacceso;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import Fachada.Icontrolacceso.ResultadoAccesoDTO;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Módulo de Recepción — pantalla principal del subsistema Control de Acceso.
 *
 * @author julian izaguirre
 */
public class BC_PantallaEspera extends PantallaBase {

    // Hints para ZXing: solo decodificar QR_CODE → mucho más rápido
    private static final Map<DecodeHintType, Object> QR_HINTS;
    static {
        QR_HINTS = new EnumMap<>(DecodeHintType.class);
        QR_HINTS.put(DecodeHintType.POSSIBLE_FORMATS, List.of(BarcodeFormat.QR_CODE));
        QR_HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
    }

    private final Icontrolacceso controlAcceso;

    private QRCamaraCapturador camaraCapturador;
    private JLabel lblVisor;
    private JLabel lblEstadoCamara;
    private JButton btnCamara;
    private boolean camaraActiva = false;

    private JTextField txtCodigo;

    private volatile boolean procesando = false;

    public BC_PantallaEspera(IControladorAplicacion controlador) {
        super(controlador);
        this.controlAcceso = FachadaControlAcceso.getInstancia();
        setTitle("SteelCore Fitness — Módulo de Recepción");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(500, 650);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 44, 32, 44));

        JLabel logo = new JLabel("⬡", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI", Font.PLAIN, 38));
        logo.setForeground(Colores.ACENTO);
        logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Módulo de Recepción", SwingConstants.CENTER);
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = new JLabel("Escanea el QR con la cámara o ingrésalo manualmente",
                SwingConstants.CENTER);
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        lblVisor = new JLabel("", SwingConstants.CENTER);
        lblVisor.setPreferredSize(new Dimension(380, 220));
        lblVisor.setMaximumSize(new Dimension(380, 220));
        lblVisor.setMinimumSize(new Dimension(380, 220));
        lblVisor.setBackground(Color.BLACK);
        lblVisor.setOpaque(true);
        lblVisor.setAlignmentX(CENTER_ALIGNMENT);

        lblEstadoCamara = new JLabel("Cámara apagada", SwingConstants.CENTER);
        lblEstadoCamara.setFont(Colores.FUENTE_LABEL);
        lblEstadoCamara.setForeground(new Color(180, 180, 180));
        lblEstadoCamara.setAlignmentX(CENTER_ALIGNMENT);

        JPanel visorWrapper = new JPanel();
        visorWrapper.setLayout(new OverlayLayout(visorWrapper));
        visorWrapper.setOpaque(false);
        visorWrapper.setMaximumSize(new Dimension(380, 220));
        visorWrapper.setAlignmentX(CENTER_ALIGNMENT);
        JPanel panelVisor = new JPanel(new BorderLayout());
        panelVisor.setBackground(Color.BLACK);
        panelVisor.setMaximumSize(new Dimension(380, 220));
        panelVisor.add(lblVisor, BorderLayout.CENTER);
        panelVisor.add(lblEstadoCamara, BorderLayout.SOUTH);
        visorWrapper.add(panelVisor);

        btnCamara = new JButton("📷  Iniciar cámara");
        btnCamara.setFont(Colores.FUENTE_LABEL);
        btnCamara.setForeground(Colores.TEXTO_PRINCIPAL);
        btnCamara.setBackground(Colores.ACENTO);
        btnCamara.setOpaque(true);
        btnCamara.setBorderPainted(false);
        btnCamara.setFocusPainted(false);
        btnCamara.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCamara.setAlignmentX(CENTER_ALIGNMENT);
        btnCamara.addActionListener(e -> toggleCamara());

        JLabel lblOr = new JLabel("— o ingresar código manualmente —", SwingConstants.CENTER);
        lblOr.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblOr.setForeground(Colores.TEXTO_SECUNDARIO);
        lblOr.setAlignmentX(CENTER_ALIGNMENT);

        // Campo de texto seleccionable (permite copiar/pegar)
        txtCodigo = new JTextField();
        txtCodigo.setFont(Colores.FUENTE_CAMPO);
        txtCodigo.setForeground(Colores.TEXTO_PRINCIPAL);
        txtCodigo.setBackground(Colores.FONDO_CAMPO);
        txtCodigo.setCaretColor(Colores.TEXTO_PRINCIPAL);
        txtCodigo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                new EmptyBorder(10, 14, 10, 14)));
        txtCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        txtCodigo.setAlignmentX(LEFT_ALIGNMENT);
        txtCodigo.addActionListener(e -> procesarCodigoManual());

        // Panel con campo + botón copiar en la misma fila
        JPanel panelCodigo = new JPanel(new BorderLayout(8, 0));
        panelCodigo.setOpaque(false);
        panelCodigo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        panelCodigo.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnCopiar = new JButton("📋");
        btnCopiar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnCopiar.setToolTipText("Copiar código al portapapeles");
        btnCopiar.setFocusPainted(false);
        btnCopiar.setBorderPainted(false);
        btnCopiar.setBackground(Colores.FONDO_CAMPO);
        btnCopiar.setForeground(Colores.TEXTO_PRINCIPAL);
        btnCopiar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCopiar.addActionListener(e -> {
            String texto = txtCodigo.getText().trim();
            if (!texto.isEmpty()) {
                Toolkit.getDefaultToolkit()
                        .getSystemClipboard()
                        .setContents(new StringSelection(texto), null);
                btnCopiar.setText("✅");
                Timer t = new Timer(1500, ev -> btnCopiar.setText("📋"));
                t.setRepeats(false);
                t.start();
            }
        });

        panelCodigo.add(txtCodigo, BorderLayout.CENTER);
        panelCodigo.add(btnCopiar, BorderLayout.EAST);

        Boton btnProcesar = crearBoton("Procesar código", Boton.Variante.PRIMARIO);
        btnProcesar.setAlignmentX(CENTER_ALIGNMENT);
        btnProcesar.addActionListener(e -> procesarCodigoManual());

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
        card.add(panelCodigo);
        card.add(Box.createVerticalStrut(12));
        card.add(btnProcesar);
        card.add(Box.createVerticalStrut(18));

        Boton btnVolver = crearBoton("← Volver al perfil", Boton.Variante.SECUNDARIO);
        btnVolver.setAlignmentX(CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> {
            detenerCamara();
            dispose();
            controlador.irAPerfilUsuario();
        });
        card.add(btnVolver);

        fondo.add(card);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                detenerCamara();
            }
        });

        SwingUtilities.invokeLater(() -> txtCodigo.requestFocusInWindow());
    }

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
            btnCamara.setText("Detener cámara");
            lblEstadoCamara.setText("Esperando código QR…");
        } else {
            camaraCapturador = null;
            JOptionPane.showMessageDialog(this,
                "No se pudo acceder a la cámara.\nUsa el campo manual.",
                "Cámara no disponible",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void detenerCamara() {
        if (camaraCapturador != null) {
            camaraCapturador.detener();
            camaraCapturador = null;
        }
        camaraActiva = false;
        btnCamara.setText("Iniciar cámara");
        lblVisor.setIcon(null);
        lblEstadoCamara.setText("Cámara apagada");
    }

    private void procesarCodigoManual() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor ingresa un código QR.",
                    "Campo vacío",
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
            dispose();
            new BC_PantallaExpediente(controlador, resultado).setVisible(true);

        } catch (AccesoDenegadoException ex) {
            txtCodigo.setText("");
            procesando = false;
            dispose();
            new BC_PantallaAccesoDenegado(controlador, ex.getMotivo()).setVisible(true);
        }
    }

    // ────────────────────────────────────────────────────────────────────────
    // Capturador interno
    // ────────────────────────────────────────────────────────────────────────
    private static class QRCamaraCapturador {

        @FunctionalInterface
        interface OnFrame { void onFrame(BufferedImage frame); }

        @FunctionalInterface
        interface OnQR { void onQR(String codigo); }

        private final OnFrame  onFrame;
        private final OnQR     onQR;
        private Thread         hilo;
        private volatile boolean corriendo = false;
        private Object webcam;

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

        /**
         * Fallback: captura pantalla completa y decodifica QR.
         * CORRECCIÓN: eliminado el llamado a obtenerQRDelServidor() en el loop.
         * Ese HTTP con timeout de 150ms se acumulaba en cada frame y causaba
         * que el primer QR detectado tardara varios minutos.
         */
        private boolean iniciarConRobot() {
            try {
                Robot robot = new Robot();
                corriendo = true;
                hilo = new Thread(() -> {
                    // Reutilizar reader con hints → solo QR_CODE, mucho más rápido
                    MultiFormatReader reader = new MultiFormatReader();
                    reader.setHints(QR_HINTS);

                    Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
                    Rectangle areaCaptura = new Rectangle(0, 0, pantalla.width, pantalla.height);

                    while (corriendo) {
                        try {
                            // Captura directa de pantalla, sin llamadas HTTP
                            BufferedImage frame = robot.createScreenCapture(areaCaptura);
                            onFrame.onFrame(frame);
                            detectarQR(frame, reader);
                            Thread.sleep(150); // más rápido que antes (300ms)
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

        /**
         * Loop de la webcam real.
         * CORRECCIÓN: reader con hints de formato para decodificación rápida.
         */
        private void bucleCaptura() {
            MultiFormatReader reader = new MultiFormatReader();
            reader.setHints(QR_HINTS);

            try {
                Class<?> webcamClass = Class.forName("com.github.sarxos.webcam.Webcam");
                java.lang.reflect.Method getImage = webcamClass.getMethod("getImage");

                while (corriendo) {
                    BufferedImage frame = (BufferedImage) getImage.invoke(webcam);
                    if (frame != null) {
                        onFrame.onFrame(frame);
                        detectarQR(frame, reader);
                    }
                    Thread.sleep(80); // ~12 fps es suficiente para QR
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                // cámara desconectada
            }
        }

        /**
         * Decodifica QR del frame usando los hints preconfigurados.
         * Al limitar a QR_CODE el reader no itera los 150+ formatos → instantáneo.
         */
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
                // Sin QR en el frame — normal
            } catch (Exception e) {
                // Otro error de decodificación — ignorar
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