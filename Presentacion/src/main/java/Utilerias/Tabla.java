/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Tungs
 */
public class Tabla extends JPanel {
    
    private JTable tabla;
    private DefaultTableModel modelo;
    private TableRowSorter<DefaultTableModel> sorter;
    private JLabel titulo;

    public Tabla(String tituloTexto, String[] columnas) {
        setLayout(new BorderLayout());
        setOpaque(false);

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);

        aplicarEstiloTabla();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Colores.FONDO_CAMPO);
        scroll.setOpaque(false);

        JPanel card = new JPanel(new BorderLayout(10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);

                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);

                g2.dispose();
            }
        };

        card.setOpaque(false);
        card.setBorder(new EmptyBorder(15, 15, 15, 15));

        titulo = new JLabel(tituloTexto);
        titulo.setFont(Colores.FUENTE_SUBTITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        card.add(titulo, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        add(card, BorderLayout.CENTER);
    }

    private void aplicarEstiloTabla() {

        tabla.setBackground(Colores.FONDO_CAMPO);
        tabla.setForeground(Colores.TEXTO_PRINCIPAL);
        tabla.setGridColor(Colores.BORDE_CAMPO);
        tabla.setRowHeight(32);
        tabla.setFont(Colores.FUENTE_CAMPO);

        tabla.setSelectionBackground(Colores.ACENTO);
        tabla.setSelectionForeground(Color.WHITE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = tabla.getTableHeader();
        header.setBackground(Colores.FONDO_CARD);
        header.setForeground(Colores.TEXTO_PRINCIPAL);
        header.setFont(Colores.FUENTE_BOTON);

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }
    }


    public void agregarFila(Object[] datos) {
        modelo.addRow(datos);
    }

    public void limpiar() {
        modelo.setRowCount(0);
    }

    public void setDatos(List<Object[]> datos) {
        limpiar();
        for (Object[] fila : datos) {
            agregarFila(fila);
        }
    }
    
    public void filtrar(String texto) {
        if (texto == null || texto.isBlank()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
        }
    }
    
    public int getFilaSeleccionada() {
        int filaVista = tabla.getSelectedRow();
        if (filaVista == -1) return -1;
        return tabla.convertRowIndexToModel(filaVista);
    }
    
    public JTable getTabla() {
        return tabla;
    }
}
