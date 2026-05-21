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
import static dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO.BUENAS_CONDICIONES;
import static dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO.INACTIVO;
import static dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO.MANTENIMIENTO_PREVENTVO;
import static dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO.MANTENIMIENTO_URGENTE;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Tungs
 */
public class PantallaInventarioMaquinas extends PantallaBase {
    private Tabla tablaMaquinas;
    private JComboBox<String> cmbEstado;
    private JComboBox<SucursalDTO> cmbSucursal;
 
    private List<SucursalDTO> sucursales;
    private List<MaquinaDTO> maquinasMostradas;
 
    public PantallaInventarioMaquinas(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Inventario de Máquinas");
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
        root.add(crearPanelBotones(), BorderLayout.SOUTH);
 
        setContentPane(root);
    }
 
 
    private JPanel crearHeader() {
        JPanel panel = crearCard(0, 100);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(22, 24, 22, 24));
 
        JLabel titulo = new JLabel("SISTEMA DE INVENTARIO", SwingConstants.CENTER);
        titulo.setFont(new Font("Consolas", Font.BOLD, 36));
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
 
        panel.add(titulo, BorderLayout.CENTER);
        return panel;
    }
 
 
    private JPanel crearTabsModulo() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 18, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
 
        Boton btnMantenimiento = crearBoton("Mantenimiento", Boton.Variante.SECUNDARIO);
        Boton btnInventario    = crearBoton("Inventario",    Boton.Variante.PRIMARIO);
 
        btnMantenimiento.addActionListener(e -> controlador.irAInventarioMantenimiento());
        btnInventario.addActionListener(e -> { /* NO hace nada de nadaaaa x2 */ });
 
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
        String[] columnas = {"Sucursal", "Modelo", "Tipo", "Estado"};
        tablaMaquinas = new Tabla("Máquinas registradas", columnas);
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
        for (MaquinaDTO.EstadoMaquinaDTO estado : MaquinaDTO.EstadoMaquinaDTO.values()) {
            cmbEstado.addItem(estado.name());
        }
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
 
 
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(4, 20, 20, 20));
 
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> controlador.cerrarSesion());
 
        Boton btnAgregar = crearBoton("Agregar Equipo", Boton.Variante.PRIMARIO);
        btnAgregar.addActionListener(e -> controlador.irAAgregarInventario());
 
        Boton btnEliminar = crearBoton("Eliminar Equipo", Boton.Variante.SECUNDARIO);
        btnEliminar.addActionListener(e -> {
            if (controlador.getMaquinaSeleccionada() == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona una máquina de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.irAEliminarInventario();
        });
 
        Boton btnActualizar = crearBoton("Actualizar Equipo", Boton.Variante.PRIMARIO);
        btnActualizar.addActionListener(e -> {
            if (controlador.getMaquinaSeleccionada() == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona una máquina de la tabla.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.irAModificarInventario();
        });
 
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnRegresar);
 
        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        centro.setOpaque(false);
        centro.add(btnAgregar);
        centro.add(btnEliminar);
        centro.add(btnActualizar);
 
        panel.add(izquierda, BorderLayout.WEST);
        panel.add(centro,    BorderLayout.CENTER);
 
        return panel;
    }
 
 
    private void cargarDatos() {
        try {
            sucursales = controlador.obtenerSucursales();
            cargarComboSucursales();
            cargarMaquinasFiltradas();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(this,
                    "No fue posible cargar los datos de inventario.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void cargarComboSucursales() {
        cmbSucursal.removeAllItems();
        cmbSucursal.addItem(null);
        if (sucursales != null) {
            for (SucursalDTO sucursal : sucursales) cmbSucursal.addItem(sucursal);
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
                    obtenerNombreSucursal(maquina.getIdSucursal()),
                    maquina.getModelo(),
                    maquina.getTipo(),
                    formatearEstado(maquina.getEstado())
                });
            }
        } catch (InventarioMantenimientoException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error al filtrar máquinas", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    private void seleccionarMaquinaDesdeTabla() {
        int fila = tablaMaquinas.getTabla().getSelectedRow();
        if (fila < 0 || maquinasMostradas == null || fila >= maquinasMostradas.size()) return;
        controlador.setMaquinaSeleccionada(maquinasMostradas.get(fila));
    }
 
    private void manejarSeleccionParaAccion(Runnable accion) {
        if (controlador.getMaquinaSeleccionada() == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona una máquina de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        accion.run();
    }
 
    private String obtenerNombreSucursal(String idSucursal) {
        if (idSucursal == null || sucursales == null) return "—";
        for (SucursalDTO sucursal : sucursales) {
            if (idSucursal.equals(sucursal.getIdSucursal())) return sucursal.getNombre();
        }
        return idSucursal;
    }
 
    private String formatearEstado(MaquinaDTO.EstadoMaquinaDTO estado) {
        if (estado == null) return "—";
        switch (estado) {
            case BUENAS_CONDICIONES:      return "Buenas condiciones";
            case MANTENIMIENTO_PREVENTVO: return "Mantenimiento preventivo";
            case MANTENIMIENTO_URGENTE:   return "Mantenimiento urgente";
            case INACTIVO:                return "Inactivo";
            default:                      return estado.name();
        }
    }
}