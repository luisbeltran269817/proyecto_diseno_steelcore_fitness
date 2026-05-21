/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInventarioMantenimiento;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import excepciones.InventarioMantenimientoException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaProgramarMantenimiento extends PantallaBase {

    private JTextArea txtDescripcion;
    private JSpinner spnFechaHora;
    private JComboBox<TecnicoDTO> cmbTecnicos;
    private JLabel lblMaquina;
    private JLabel lblResumenHorario;
    
    public PantallaProgramarMantenimiento(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Programación de Mantenimiento");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        cargarDatosIniciales();
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

        root.add(crearHeader(),    BorderLayout.NORTH);
        root.add(crearContenido(), BorderLayout.CENTER);
        root.add(crearBotones(),   BorderLayout.SOUTH);

        setContentPane(root);
    }


    private JPanel crearHeader() {
        JPanel header = crearCard(0, 105);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(22, 24, 22, 24));

        JLabel titulo = new JLabel("PROGRAMACIÓN DE MANTENIMIENTO", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 36));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

        header.add(titulo, BorderLayout.CENTER);
        return header;
    }


    private JPanel crearContenido() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setOpaque(false);

        JPanel card = crearCard(620, 470);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(34, 42, 34, 42));

        JLabel paso = new JLabel("Paso 1 de 3");
        paso.setFont(new Font("Segoe UI", Font.BOLD, 13));
        paso.setForeground(Colores.ACENTO);
        paso.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitulo = new JLabel("Datos generales de la solicitud");
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        subtitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblMaquina = new JLabel("Máquina seleccionada: —");
        lblMaquina.setFont(Colores.FUENTE_LABEL);
        lblMaquina.setForeground(Colores.TEXTO_SECUNDARIO);
        lblMaquina.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(paso);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(8));
        card.add(lblMaquina);
        card.add(Box.createVerticalStrut(26));

        txtDescripcion = new JTextArea(4, 30);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(Colores.FUENTE_CAMPO);
        txtDescripcion.setForeground(Colores.TEXTO_PRINCIPAL);
        txtDescripcion.setBackground(Colores.FONDO_CAMPO);
        txtDescripcion.setCaretColor(Colores.TEXTO_PRINCIPAL);
        txtDescripcion.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollDescripcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        scrollDescripcion.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));
        scrollDescripcion.getViewport().setBackground(Colores.FONDO_CAMPO);

        card.add(crearLabel("Descripción del problema"));
        card.add(Box.createVerticalStrut(8));
        card.add(scrollDescripcion);
        card.add(Box.createVerticalStrut(20));

        SpinnerDateModel modeloFecha = new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE);
        spnFechaHora = new JSpinner(modeloFecha);
        spnFechaHora.setFont(Colores.FUENTE_CAMPO);
        spnFechaHora.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        spnFechaHora.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSpinner.DateEditor editor = new JSpinner.DateEditor(spnFechaHora, "dd/MM/yyyy   HH:mm");
        spnFechaHora.setEditor(editor);
        estilizarSpinner(spnFechaHora, editor);

        card.add(crearLabel("Fecha y hora del mantenimiento"));
        card.add(Box.createVerticalStrut(8));
        card.add(spnFechaHora);
        card.add(Box.createVerticalStrut(20));

        cmbTecnicos = new JComboBox<>();
        cmbTecnicos.setFont(Colores.FUENTE_CAMPO);
        cmbTecnicos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        cmbTecnicos.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCombo(cmbTecnicos);

        card.add(crearLabel("Técnico encargado"));
        card.add(Box.createVerticalStrut(8));
        card.add(cmbTecnicos);
        card.add(Box.createVerticalStrut(18));

        lblResumenHorario = new JLabel("Se validará la disponibilidad del técnico antes de continuar.");
        lblResumenHorario.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblResumenHorario.setForeground(Colores.TEXTO_SECUNDARIO);
        lblResumenHorario.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(lblResumenHorario);

        contenedor.add(card);
        return contenedor;
    }


    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(Colores.FUENTE_LABEL);
        label.setForeground(Colores.TEXTO_PRINCIPAL);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void estilizarSpinner(JSpinner spinner, JSpinner.DateEditor editor) {
        spinner.setBackground(Colores.FONDO_CAMPO);
        spinner.setForeground(Colores.TEXTO_PRINCIPAL);
        spinner.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));


        JFormattedTextField tf = editor.getTextField();
        tf.setBackground(Colores.FONDO_CAMPO);
        tf.setForeground(Colores.TEXTO_PRINCIPAL);
        tf.setCaretColor(Colores.TEXTO_PRINCIPAL);
        tf.setFont(Colores.FUENTE_CAMPO);
        tf.setBorder(new EmptyBorder(8, 12, 8, 12));
        tf.setHorizontalAlignment(JTextField.CENTER);

        tf.setSelectionColor(Colores.ACENTO);
        tf.setSelectedTextColor(Colores.TEXTO_PRINCIPAL);
        tf.setEditable(false);
        tf.setBackground(Colores.FONDO_CAMPO);
        tf.setForeground(Colores.TEXTO_PRINCIPAL);

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

    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBackground(Colores.FONDO_CAMPO);
        combo.setForeground(Colores.TEXTO_PRINCIPAL);
        combo.setFont(Colores.FUENTE_CAMPO);
        combo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? Colores.ACENTO_PRESS : Colores.FONDO_CAMPO);
                setForeground(Colores.TEXTO_PRINCIPAL);
                setBorder(new EmptyBorder(4, 8, 4, 8));
                if (value instanceof TecnicoDTO) {
                    setText(((TecnicoDTO) value).getNombre());
                } else {
                    setText("Seleccione un técnico");
                }
                return this;
            }
        });
    }


    private JPanel crearBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(4, 36, 24, 36));

        Boton btnRegresar  = crearBoton("Regresar",   Boton.Variante.SECUNDARIO);
        Boton btnSiguiente = crearBoton("Siguiente →", Boton.Variante.PRIMARIO);

        btnRegresar.addActionListener(e -> controlador.irAInventarioMantenimiento());
        btnSiguiente.addActionListener(e -> avanzarSiguiente());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnSiguiente);

        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);

        return panel;
    }


    private void cargarDatosIniciales() {
        cargarMaquinaSeleccionada();
        cargarTecnicos();
    }

    private void cargarMaquinaSeleccionada() {
        MaquinaDTO maquina = controlador.getMaquinaSeleccionada();
        if (maquina == null) {
            lblMaquina.setText("Máquina seleccionada: —");
            return;
        }
        lblMaquina.setText("Máquina seleccionada: " + maquina.getModelo() + " · " + maquina.getTipo());
    }

    private void cargarTecnicos() {
        try {
            List<TecnicoDTO> tecnicos = controlador.obtenerTecnicosMantenimiento();
            cmbTecnicos.removeAllItems();
            cmbTecnicos.addItem(null);
            if (tecnicos != null) {
                for (TecnicoDTO tecnico : tecnicos) cmbTecnicos.addItem(tecnico);
            }
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los técnicos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void avanzarSiguiente() {
        try {
            MaquinaDTO maquina = controlador.getMaquinaSeleccionada();
            if (maquina == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona una máquina antes de programar mantenimiento.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                controlador.irAInventarioMantenimiento();
                return;
            }

            String descripcion = txtDescripcion.getText();
            if (descripcion == null || descripcion.isBlank()) {
                JOptionPane.showMessageDialog(this,
                        "Ingresa una descripción del problema.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            TecnicoDTO tecnico = (TecnicoDTO) cmbTecnicos.getSelectedItem();
            if (tecnico == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un técnico encargado.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Date fechaSeleccionada = (Date) spnFechaHora.getValue();
            LocalDateTime fechaProgramada = fechaSeleccionada.toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();

            boolean disponible = controlador.tecnicoTieneHorarioDisponible(
                    tecnico.getIdTecnico(), fechaProgramada);

            if (!disponible) {
                JOptionPane.showMessageDialog(this,
                        "El técnico no tiene horario disponible para la fecha y hora seleccionadas.",
                        "Horario no disponible", JOptionPane.WARNING_MESSAGE);
                return;
            }

            controlador.guardarDatosProgramacionMantenimiento(descripcion, fechaProgramada, tecnico);
            controlador.irASeleccionPiezasMantenimiento();

        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudo continuar", JOptionPane.ERROR_MESSAGE);
        }
    }
}
