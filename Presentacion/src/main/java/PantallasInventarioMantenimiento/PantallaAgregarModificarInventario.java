/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasInventarioMantenimiento;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.SucursalDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import excepciones.InventarioMantenimientoException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaAgregarModificarInventario extends PantallaBase {
    private MaquinaDTO maquinaExistente;
    private final boolean modoModificar;
    private JComboBox<SucursalDTO> cmbSucursal;
    private JTextField txtModelo;
    private JTextField txtTipo;
    private JComboBox<MaquinaDTO.EstadoMaquinaDTO> cmbEstado;
    private boolean datosValidos = true;
    private List<SucursalDTO> sucursales;
 
    public PantallaAgregarModificarInventario(IControladorAplicacion controlador, boolean modoModificar) {
        super(controlador);
        this.modoModificar = modoModificar;
 
        setTitle(modoModificar ? "Actualizar Equipo" : "Agregar Equipo");
        setSize(760, 680);
        setLocationRelativeTo(null);
        setResizable(false);
 
        inicializarComponentes();
        cargarDatos();
        if (datosValidos) setVisible(true);
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
        JPanel header = crearCard(0, 95);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 24, 20, 24));
 
        String texto = maquinaExistente == null ? "AGREGAR EQUIPO" : "ACTUALIZAR EQUIPO";
        JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 32));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
 
        header.add(titulo, BorderLayout.CENTER);
        return header;
    }
  
    private JPanel crearContenido() {
        JPanel contenedor = new JPanel(new GridBagLayout());
        contenedor.setOpaque(false);
 
        JPanel card = crearCard(560, 430);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 38, 30, 38));
 
        JLabel subtitulo = new JLabel("Datos de la máquina");
        subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        subtitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        subtitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
        cmbSucursal = new JComboBox<>();
        cmbSucursal.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        cmbSucursal.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCombo(cmbSucursal);
 
        txtModelo = crearCampoTexto();

        txtTipo = crearCampoTexto();
 
        cmbEstado = new JComboBox<>(MaquinaDTO.EstadoMaquinaDTO.values());
        cmbEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        cmbEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCombo(cmbEstado);
 
        card.add(subtitulo);
        card.add(Box.createVerticalStrut(12));
        card.add(sep);
        card.add(Box.createVerticalStrut(22));
 
        card.add(crearLabel("Sucursal"));
        card.add(Box.createVerticalStrut(6));
        card.add(cmbSucursal);
        card.add(Box.createVerticalStrut(16));
 
        card.add(crearLabel("Modelo"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtModelo);
        card.add(Box.createVerticalStrut(16));
 
        card.add(crearLabel("Tipo"));
        card.add(Box.createVerticalStrut(6));
        card.add(txtTipo);
        card.add(Box.createVerticalStrut(16));
 
        card.add(crearLabel("Estado"));
        card.add(Box.createVerticalStrut(6));
        card.add(cmbEstado);
 
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
 
    private JTextField crearCampoTexto() {
        JTextField tf = new JTextField();
        tf.setFont(Colores.FUENTE_CAMPO);
        tf.setForeground(Colores.TEXTO_PRINCIPAL);
        tf.setBackground(Colores.FONDO_CAMPO);
        tf.setCaretColor(Colores.TEXTO_PRINCIPAL);
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        tf.setSelectionColor(Colores.ACENTO);
        tf.setSelectedTextColor(Colores.TEXTO_PRINCIPAL);
        return tf;
    }
 
    private void estilizarCombo(JComboBox<?> combo) {
        combo.setBackground(Colores.FONDO_CAMPO);
        combo.setForeground(Colores.TEXTO_PRINCIPAL);
        combo.setFont(Colores.FUENTE_CAMPO);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        combo.setBorder(BorderFactory.createLineBorder(Colores.BORDE_CAMPO, 1, true));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(isSelected ? Colores.ACENTO_PRESS : Colores.FONDO_CAMPO);
                setForeground(isSelected ? Colores.TEXTO_PRINCIPAL : Colores.TEXTO_PLACEHOLDER);
                setBorder(new EmptyBorder(6, 10, 6, 10));
                if (value instanceof SucursalDTO) {
                    setForeground(Colores.TEXTO_PRINCIPAL);
                    setText(((SucursalDTO) value).getNombre());
                } else if (value instanceof MaquinaDTO.EstadoMaquinaDTO) {
                    setForeground(Colores.TEXTO_PRINCIPAL);
                    setText(value.toString().replace("_", " "));
                } else if (value == null) {
                    setText("Seleccione una sucursal");
                } else {
                    setForeground(Colores.TEXTO_PRINCIPAL);
                    setText(value.toString());
                }
                return this;
            }
        });
    }
  
    private JPanel crearBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(4, 36, 16, 36));
 
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        Boton btnAceptar  = crearBoton("Aceptar",  Boton.Variante.PRIMARIO);
 
        btnRegresar.addActionListener(e -> controlador.irAInventarioMaquinas());
        btnAceptar.addActionListener(e -> guardar());
 
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);
 
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnAceptar);
 
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);
 
        return panel;
    }
  
    private void cargarDatos() {
        try {
            sucursales = controlador.obtenerSucursales();
            cmbSucursal.removeAllItems();
            cmbSucursal.addItem(null);
            if (sucursales != null) {
                for (SucursalDTO sucursal : sucursales) cmbSucursal.addItem(sucursal);
            }
            if (modoModificar) {
                maquinaExistente = controlador.getMaquinaSeleccionada();
                if (maquinaExistente == null) {
                    datosValidos = false;
                    JOptionPane.showMessageDialog(this,
                            "Selecciona una máquina antes de actualizar.",
                            "Aviso", JOptionPane.WARNING_MESSAGE);
                    dispose();
                    return;
                }
                precargarMaquina();
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar las sucursales.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void precargarMaquina() {
        txtModelo.setText(maquinaExistente.getModelo());
        txtTipo.setText(maquinaExistente.getTipo());
        if (maquinaExistente.getEstado() != null) {
            cmbEstado.setSelectedItem(maquinaExistente.getEstado());
        }
        if (sucursales != null) {
            for (SucursalDTO sucursal : sucursales) {
                if (sucursal.getIdSucursal().equals(maquinaExistente.getIdSucursal())) {
                    cmbSucursal.setSelectedItem(sucursal);
                    break;
                }
            }
        }
    }
 
    private void guardar() {
        try {
            SucursalDTO sucursal = (SucursalDTO) cmbSucursal.getSelectedItem();
            String idSucursal    = sucursal != null ? sucursal.getIdSucursal() : null;
            String modelo        = txtModelo.getText();
            String tipo          = txtTipo.getText();
            MaquinaDTO.EstadoMaquinaDTO estado = (MaquinaDTO.EstadoMaquinaDTO) cmbEstado.getSelectedItem();
 
            if (!modoModificar) {
                controlador.registrarMaquinaInventario(idSucursal, modelo, tipo, estado);
                JOptionPane.showMessageDialog(this,
                        "Máquina registrada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                controlador.actualizarMaquinaInventario(
                        maquinaExistente.getIdMaquina(), idSucursal, modelo, tipo, estado);
                JOptionPane.showMessageDialog(this,
                        "Máquina actualizada correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
 
            controlador.irAInventarioMaquinas();
 
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "No se pudo guardar la máquina", JOptionPane.ERROR_MESSAGE);
        }
    }
}
