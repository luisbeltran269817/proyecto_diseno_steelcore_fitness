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
import Utilerias.Tabla;
import dtos.SucursalDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import excepciones.InventarioMantenimientoException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Tungs
 */
public class PantallaInventarioMantenimiento extends PantallaBase {
    private Tabla tablaMaquinas;
    private JComboBox<String> cmbEstado;
    private JComboBox<SucursalDTO> cmbSucursal;
 
    private List<MaquinaDTO> maquinas;
    private List<SucursalDTO> sucursales;
 
    private Boton btnProgramar;
    private Boton btnRegresar;
 
    private List<MaquinaDTO> maquinasMostradas;
    
    public PantallaInventarioMantenimiento(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Sistema de Mantenimiento");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        inicializarComponentes();
        cargarDatos();
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
 
        JPanel superior = new JPanel();
        superior.setOpaque(false);
        superior.setLayout(new BoxLayout(superior, BoxLayout.Y_AXIS));
        superior.add(crearHeader());
        superior.add(Box.createVerticalStrut(18));
        superior.add(crearTabsModulo());
 
        root.add(superior, BorderLayout.NORTH);
        root.add(crearCentro(), BorderLayout.CENTER);
        root.add(crearPanelAcciones(), BorderLayout.SOUTH);
 
        setContentPane(root);
    }
 
 
    private JPanel crearHeader() {
        JPanel panel = crearCard(0, 110);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(24, 24, 24, 24));
 
        JLabel titulo = new JLabel("SISTEMA DE MANTENIMIENTO", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 38));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
 
        panel.add(titulo, BorderLayout.CENTER);
        return panel;
    }
 
 
    private JPanel crearTabsModulo() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 18, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
 
        Boton btnMantenimiento = crearBoton("Mantenimiento", Boton.Variante.PRIMARIO);
        Boton btnInventario    = crearBoton("Inventario",    Boton.Variante.SECUNDARIO);
 
        btnMantenimiento.addActionListener(e -> { /* pantalla actual */ });
        btnInventario.addActionListener(e -> controlador.irAInventarioMaquinas());
 
        panel.add(btnMantenimiento);
        panel.add(btnInventario);
        return panel;
    }
 
 
    private JPanel crearCentro() {
        JPanel panel = new JPanel(new BorderLayout(24, 0));
        panel.setOpaque(false);
 
        panel.add(crearPanelTabla(),   BorderLayout.CENTER);
        panel.add(crearPanelFiltros(), BorderLayout.EAST);
 
        return panel;
    }
 
    private JPanel crearPanelTabla() {
        String[] columnas = {"Modelo", "Tipo", "Estado", "Último mantenimiento", "Sucursal"};
        tablaMaquinas = new Tabla("Máquinas", columnas);
        JTable table = tablaMaquinas.getTabla();
 
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
 
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) seleccionarMaquinaDesdeTabla();
        });
 
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(tablaMaquinas, BorderLayout.CENTER);
        return panel;
    }
 
 
    private JPanel crearPanelFiltros() {
        JPanel card = crearCard(280, 0);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(28, 20, 28, 20));
 
        JLabel lblTitulo = new JLabel("Filtrar por:");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Colores.TEXTO_PRINCIPAL);
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
 
        cmbEstado = new JComboBox<>();
        cmbEstado.addItem("Todos");
        cmbEstado.addItem("BUENAS_CONDICIONES");
        cmbEstado.addItem("MANTENIMIENTO_PREVENTIVO");
        cmbEstado.addItem("MANTENIMIENTO_URGENTE");
        cmbEstado.addItem("INACTIVO");
        estilizarCombo(cmbEstado);
        cmbEstado.addActionListener(e -> cargarMaquinasFiltradas());
 
        cmbSucursal = new JComboBox<>();
        estilizarCombo(cmbSucursal);
        cmbSucursal.addActionListener(e -> cargarMaquinasFiltradas());
 
        card.add(lblTitulo);
        card.add(Box.createVerticalStrut(8));
        card.add(sep);
        card.add(Box.createVerticalStrut(20));
        card.add(crearGrupoFiltro("Estado",   cmbEstado));
        card.add(Box.createVerticalStrut(16));
        card.add(crearGrupoFiltro("Sucursal", cmbSucursal));
        card.add(Box.createVerticalGlue());
 
        return card;
    }
 
    private JPanel crearGrupoFiltro(String etiqueta, JComponent componente) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
 
        JLabel lbl = new JLabel(etiqueta.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(Colores.TEXTO_PLACEHOLDER);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
 
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        componente.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
 
        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(componente);
        return p;
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
                if (value == null) {
                    setText("Todas");
                } else if (value instanceof SucursalDTO) {
                    setText(((SucursalDTO) value).getNombre());
                } else {
                    setText(value.toString().replace("_", " "));
                }
                return this;
            }
        });
    }
 
 
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
 
        btnRegresar  = crearBoton("Regresar",                Boton.Variante.SECUNDARIO);
        btnProgramar = crearBoton("Programar mantenimiento", Boton.Variante.PRIMARIO);
 
        btnRegresar.addActionListener(e -> {
            dispose();
            controlador.cerrarSesion();
        });
 
        btnProgramar.addActionListener(e -> {
            try {
                controlador.prepararProgramacionMantenimiento();
            } catch (InventarioMantenimientoException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "No se puede programar mantenimiento",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
 
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);
 
        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnProgramar);
 
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(derecha,   BorderLayout.EAST);
 
        return panel;
    }
 
 
    private void cargarDatos() {
        try {
            sucursales = controlador.obtenerSucursales();
            cargarComboSucursales();
            cargarMaquinasFiltradas();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar las sucursales.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void cargarComboSucursales() {
        cmbSucursal.removeAllItems();
        cmbSucursal.addItem(null);
        if (sucursales != null) {
            for (SucursalDTO s : sucursales) cmbSucursal.addItem(s);
        }
    }
 
    private void cargarMaquinasFiltradas() {
        try {
            tablaMaquinas.limpiar();
            controlador.setMaquinaSeleccionada(null);
 
            String estadoSeleccionado        = (String) cmbEstado.getSelectedItem();
            SucursalDTO sucursalSeleccionada = (SucursalDTO) cmbSucursal.getSelectedItem();
 
            String idSucursal = sucursalSeleccionada != null ? sucursalSeleccionada.getIdSucursal() : null;
            MaquinaDTO.EstadoMaquinaDTO estado = null;
 
            if (estadoSeleccionado != null && !estadoSeleccionado.equals("Todos")) {
                estado = MaquinaDTO.EstadoMaquinaDTO.valueOf(estadoSeleccionado);
            }
 
            maquinasMostradas = controlador.filtrarMaquinasMantenimiento(idSucursal, estado);
            if (maquinasMostradas == null) maquinasMostradas = new ArrayList<>();
 
            for (MaquinaDTO maquina : maquinasMostradas) {
                tablaMaquinas.agregarFila(new Object[]{
                    maquina.getModelo(),
                    maquina.getTipo(),
                    formatearEstado(maquina.getEstado()),
                    obtenerTextoUltimoMantenimiento(maquina),
                    obtenerNombreSucursal(maquina.getIdSucursal())
                });
            }
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error al filtrar máquinas", JOptionPane.ERROR_MESSAGE);
        }
    }
 

    private String obtenerTextoUltimoMantenimiento(MaquinaDTO maquina) {
        if (maquina.getUltimoMantenimiento() == null) return "—";
        if (maquina.getUltimoMantenimiento().getFecha() == null)
            return maquina.getUltimoMantenimiento().getIdMantenimiento();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return maquina.getUltimoMantenimiento().getFecha().format(fmt);
    }
 
    private String obtenerNombreSucursal(String idSucursal) {
        if (idSucursal == null || sucursales == null) return "—";
        for (SucursalDTO s : sucursales) {
            if (idSucursal.equals(s.getIdSucursal())) return s.getNombre();
        }
        return idSucursal;
    }
 
    private String formatearEstado(MaquinaDTO.EstadoMaquinaDTO estado) {
        if (estado == null) return "—";
        return estado.name().replace("_", " ");
    }
 
    private void seleccionarMaquinaDesdeTabla() {
        int fila = tablaMaquinas.getTabla().getSelectedRow();
        if (fila < 0 || maquinasMostradas == null || fila >= maquinasMostradas.size()) return;
        controlador.setMaquinaSeleccionada(maquinasMostradas.get(fila));
    }
}
 
