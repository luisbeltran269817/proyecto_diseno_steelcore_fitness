/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInventarioMantenimiento;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.InventarioMantenimientoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Tungs
 */
public class PantallaSeleccionPiezasMantenimiento extends PantallaBase {
    private Tabla tablaPiezas;
    private JSpinner spnCantidad;
    private JTextArea txtResumen;
 
    private List<PiezaDTO> piezas;
 
    private static final DateTimeFormatter FMT_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
 
    public PantallaSeleccionPiezasMantenimiento(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Selección de Piezas");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        cargarPiezas();
        actualizarResumen();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Colores.FONDO_PRINCIPAL);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        root.setOpaque(true);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));
 
        root.add(crearHeader(),        BorderLayout.NORTH);
        root.add(crearCentro(),        BorderLayout.CENTER);
        root.add(crearPanelBotones(),  BorderLayout.SOUTH);
 
        setContentPane(root);
    }
 
 
    private JPanel crearHeader() {
        JPanel panel = crearCard(0, 100);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(22, 24, 22, 24));
 
        JLabel titulo = new JLabel("SELECCIÓN DE PIEZAS", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 36));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
 
        panel.add(titulo, BorderLayout.CENTER);
        return panel;
    }
 
    private JPanel crearCentro() {
        JPanel panel = new JPanel(new BorderLayout(24, 0));
        panel.setOpaque(false);
 
        panel.add(crearPanelTabla(),   BorderLayout.CENTER);
        panel.add(crearPanelDerecho(), BorderLayout.EAST);
 
        return panel;
    }
 
    private JPanel crearPanelTabla() {
        String[] columnas = {"Nombre", "Estado", "Stock"};
        tablaPiezas = new Tabla("Piezas disponibles", columnas);
        JTable table = tablaPiezas.getTabla();
 
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Colores.FONDO_CAMPO : Colores.FONDO_CARD);
                }
                c.setForeground(isSelected ? Color.WHITE : Colores.TEXTO_PRINCIPAL);
                return c;
            }
        });
 
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(tablaPiezas, BorderLayout.CENTER);
        return panel;
    }
 
 
    private JPanel crearPanelDerecho() {
        JPanel panel = crearCard(320, 0);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(28, 24, 28, 24));
 
        JLabel lblPaso = new JLabel("Paso 2 de 3");
        lblPaso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblPaso.setForeground(Colores.ACENTO);
        lblPaso.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JLabel lblTitulo = new JLabel("Agregar piezas");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
        JLabel lblCantidad = crearLabel("Cantidad");
 
        spnCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        spnCantidad.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        spnCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarSpinnerNumero(spnCantidad);
 
        Boton btnAgregar = crearBoton("Agregar a la orden", Boton.Variante.PRIMARIO);
        btnAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnAgregar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnAgregar.addActionListener(e -> agregarPieza());
 
        JLabel lblResumen = crearLabel("Piezas seleccionadas");
 
        txtResumen = new JTextArea(10, 20);
        txtResumen.setEditable(false);
        txtResumen.setLineWrap(true);
        txtResumen.setWrapStyleWord(true);
        txtResumen.setFont(Colores.FUENTE_LABEL);
        txtResumen.setForeground(Colores.TEXTO_PRINCIPAL);
        txtResumen.setBackground(Colores.FONDO_CAMPO);
        txtResumen.setCaretColor(Colores.TEXTO_PRINCIPAL);
        txtResumen.setBorder(new EmptyBorder(10, 10, 10, 10));
 
        JScrollPane scrollResumen = new JScrollPane(txtResumen);
        scrollResumen.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollResumen.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));
        scrollResumen.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));
        scrollResumen.getViewport().setBackground(Colores.FONDO_CAMPO);
 
        panel.add(lblPaso);
        panel.add(Box.createVerticalStrut(6));
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(14));
        panel.add(sep);
        panel.add(Box.createVerticalStrut(20));
        panel.add(lblCantidad);
        panel.add(Box.createVerticalStrut(8));
        panel.add(spnCantidad);
        panel.add(Box.createVerticalStrut(18));
        panel.add(btnAgregar);
        panel.add(Box.createVerticalStrut(26));
        panel.add(lblResumen);
        panel.add(Box.createVerticalStrut(8));
        panel.add(scrollResumen);
        panel.add(Box.createVerticalGlue());
 
        return panel;
    }
 
 
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(4, 36, 24, 36));
 
        Boton btnRegresar = crearBoton("Regresar",   Boton.Variante.SECUNDARIO);
        Boton btnProceder = crearBoton("Proceder →", Boton.Variante.PRIMARIO);
 
        btnRegresar.addActionListener(e -> controlador.irAProgramarMantenimiento());
        btnProceder.addActionListener(e -> mostrarConfirmacion());
 
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);
 
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnProceder);
 
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);
 
        return panel;
    }
 
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_PRINCIPAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
 
    private void estilizarSpinnerNumero(JSpinner spinner) {
        spinner.setBackground(Colores.FONDO_CAMPO);
        spinner.setForeground(Colores.TEXTO_PRINCIPAL);
        spinner.setFont(Colores.FUENTE_CAMPO);
        spinner.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));
 
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
            tf.setBackground(Colores.FONDO_CAMPO);
            tf.setForeground(Colores.TEXTO_PRINCIPAL);
            tf.setCaretColor(Colores.TEXTO_PRINCIPAL);
            tf.setFont(Colores.FUENTE_CAMPO);
            tf.setBorder(new EmptyBorder(8, 12, 8, 12));
            tf.setHorizontalAlignment(JTextField.CENTER);
            tf.setSelectionColor(Colores.ACENTO);
            tf.setSelectedTextColor(Colores.TEXTO_PRINCIPAL);
        }
 
        for (Component c : spinner.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                btn.setBackground(Colores.FONDO_CAMPO);
                btn.setForeground(Colores.ACENTO);
                btn.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Colores.BORDE_CAMPO));
                btn.setFocusPainted(false);
                btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }
    }
 
 
    private void cargarPiezas() {
        try {
            piezas = controlador.obtenerPiezasMantenimiento();
            tablaPiezas.limpiar();
            if (piezas == null) return;
            for (PiezaDTO pieza : piezas) {
                tablaPiezas.agregarFila(new Object[]{
                    pieza.getNombre(),
                    obtenerTextoEstado(pieza),
                    pieza.getStock()
                });
            }
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar las piezas.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void agregarPieza() {
        try {
            int fila = tablaPiezas.getTabla().getSelectedRow();
            if (fila < 0) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona una pieza de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (piezas == null || fila >= piezas.size()) {
                JOptionPane.showMessageDialog(this,
                        "No fue posible obtener la pieza seleccionada.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            PiezaDTO pieza = piezas.get(fila);
            int cantidad = (Integer) spnCantidad.getValue();
            controlador.agregarPiezaSeleccionadaMantenimiento(pieza, cantidad);
            actualizarResumen();
            JOptionPane.showMessageDialog(this,
                    "Pieza agregada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "No se pudo agregar la pieza", JOptionPane.WARNING_MESSAGE);
        }
    }
 
    private void mostrarConfirmacion() {
        MaquinaDTO maquina           = controlador.getMaquinaSeleccionada();
        TecnicoDTO tecnico           = controlador.getTecnicoSeleccionadoMantenimiento();
        String descripcion           = controlador.getDescripcionMantenimiento();
        LocalDateTime fecha          = controlador.getFechaProgramadaMantenimiento();
        List<MantenimientoPiezaDTO> piezasSeleccionadas = controlador.obtenerPiezasSeleccionadasMantenimiento();
 
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("Confirma la solicitud de mantenimiento:\n\n");
        mensaje.append("Máquina: ").append(maquina != null ? maquina.getModelo() : "—").append("\n");
        mensaje.append("Descripción: ").append(descripcion != null ? descripcion : "—").append("\n");
        mensaje.append("Fecha y hora: ").append(fecha != null ? fecha.format(FMT_FECHA) : "—").append("\n");
        mensaje.append("Técnico: ").append(tecnico != null ? tecnico.getNombre() : "—").append("\n\n");
        mensaje.append("Piezas seleccionadas:\n");
 
        if (piezasSeleccionadas == null || piezasSeleccionadas.isEmpty()) {
            mensaje.append("Sin piezas seleccionadas.\n");
        } else {
            for (MantenimientoPiezaDTO pieza : piezasSeleccionadas) {
                mensaje.append("- ")
                       .append(obtenerNombrePieza(pieza.getIdPieza()))
                       .append(" | Cantidad: ")
                       .append(pieza.getCantidad())
                       .append("\n");
            }
        }
 
        int opcion = JOptionPane.showConfirmDialog(this,
                mensaje.toString(),
                "Confirmar mantenimiento",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
 
        if (opcion == JOptionPane.OK_OPTION) confirmarSolicitud();
    }
 
    private void confirmarSolicitud() {
        try {
            controlador.confirmarSolicitudMantenimiento();
            JOptionPane.showMessageDialog(this,
                    "Solicitud de mantenimiento creada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            controlador.irAInventarioMantenimiento();
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "No se pudo crear la solicitud", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void actualizarResumen() {
        List<MantenimientoPiezaDTO> seleccionadas = controlador.obtenerPiezasSeleccionadasMantenimiento();
        if (seleccionadas == null || seleccionadas.isEmpty()) {
            txtResumen.setText("Aún no has agregado piezas.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        int contador = 1;
        for (MantenimientoPiezaDTO mp : seleccionadas) {
            sb.append(contador)
              .append(". ")
              .append(obtenerNombrePieza(mp.getIdPieza()))
              .append("  |  Cantidad: ")
              .append(mp.getCantidad())
              .append("\n");
            contador++;
        }
        txtResumen.setText(sb.toString());
    }
 
    private String obtenerNombrePieza(String idPieza) {
        if (piezas == null || idPieza == null) return idPieza;
        for (PiezaDTO pieza : piezas) {
            if (idPieza.equals(pieza.getIdPieza())) return pieza.getNombre();
        }
        return idPieza;
    }
 
    private String obtenerTextoEstado(PiezaDTO pieza) {
        if (pieza == null || pieza.getEstado() == null) return "—";
        return pieza.getEstado() == PiezaDTO.EstadoPiezaDTO.INACTIVO ? "Inactiva" : "Activa";
    }
}
