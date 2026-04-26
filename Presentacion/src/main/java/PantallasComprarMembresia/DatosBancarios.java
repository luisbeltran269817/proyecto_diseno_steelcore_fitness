/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.AmenidadDTO;
import dtos.PlanDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author julian izaguirre
 */
public class DatosBancarios extends PantallaBase {
    private JTextField txtTitular;
    private JTextField txtNumero;
    private JTextField txtVencimiento;
    private JTextField txtCVV;
 
    public DatosBancarios(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Datos Bancarios - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(520, 600);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(44, 52, 44, 52));

        JLabel titulo = new JLabel("Datos Bancarios");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblMonto = new JLabel("Total a pagar: " + calcularTotal());
        lblMonto.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblMonto.setForeground(Colores.ACENTO);
        lblMonto.setAlignmentX(CENTER_ALIGNMENT);

        txtTitular    = crearCampo("Nombre del titular");
        txtNumero     = crearCampo("Número de tarjeta (16 dígitos)");
        txtVencimiento= crearCampo("Fecha de vencimiento (MM/AA)");
        txtCVV        = crearCampo("CVV");
 
        aplicarFiltroNumerico(txtNumero, 16);
        aplicarFiltroNumerico(txtCVV, 4);

        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnAtras   = crearBoton("Atrás",           Boton.Variante.SECUNDARIO);
        Boton btnProceder= crearBoton("Proceder al pago", Boton.Variante.PRIMARIO);
 
        btnAtras.addActionListener(e -> controlador.irATerminosCondiciones());
 
        btnProceder.addActionListener(e -> procesarPago());
 
        panelBtns.add(btnAtras);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnProceder);
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblMonto);
        card.add(Box.createVerticalStrut(28));
        card.add(crearEtiqueta("Nombre del titular"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtTitular);
        card.add(Box.createVerticalStrut(14));
        card.add(crearEtiqueta("Número de tarjeta"));
        card.add(Box.createVerticalStrut(4));
        card.add(txtNumero);
        card.add(Box.createVerticalStrut(14));
 
        JPanel rowFecha = new JPanel();
        rowFecha.setOpaque(false);
        rowFecha.setLayout(new BoxLayout(rowFecha, BoxLayout.X_AXIS));
        rowFecha.setAlignmentX(LEFT_ALIGNMENT);
        rowFecha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
 
        JPanel colFecha = crearColumna("Fecha de vencimiento", txtVencimiento);
        JPanel colCVV   = crearColumna("CVV", txtCVV);
        rowFecha.add(colFecha);
        rowFecha.add(Box.createHorizontalStrut(16));
        rowFecha.add(colCVV);
 
        card.add(rowFecha);
        card.add(Box.createVerticalStrut(28));
        card.add(panelBtns);
 
        fondo.add(card);
    }

    private JTextField crearCampo(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(Colores.FUENTE_CAMPO);
        tf.setForeground(Colores.TEXTO_PRINCIPAL);
        tf.setBackground(Colores.FONDO_CAMPO);
        tf.setCaretColor(Colores.TEXTO_PRINCIPAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colores.BORDE_CARD, 1, true),
            new EmptyBorder(10, 14, 10, 14)
        ));
        tf.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        tf.setAlignmentX(LEFT_ALIGNMENT);
        return tf;
    }
 
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Colores.FUENTE_LABEL);
        lbl.setForeground(Colores.TEXTO_SECUNDARIO);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        return lbl;
    }
 
    private JPanel crearColumna(String etiqueta, JTextField campo) {
        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.add(crearEtiqueta(etiqueta));
        col.add(Box.createVerticalStrut(4));
        col.add(campo);
        return col;
    }
 
    private void aplicarFiltroNumerico(JTextField campo, int maxLen) {
        ((AbstractDocument) campo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int off, String str, AttributeSet a)
                throws BadLocationException {
                if (str != null && str.matches("\\d+")
                    && fb.getDocument().getLength() + str.length() <= maxLen)
                    super.insertString(fb, off, str, a);
            }
            @Override
            public void replace(FilterBypass fb, int off, int len, String str, AttributeSet a)
                throws BadLocationException {
                if (str != null && str.matches("\\d*")
                    && fb.getDocument().getLength() - len + str.length() <= maxLen)
                    super.replace(fb, off, len, str, a);
            }
        });
    }
 
    private void procesarPago() {
        if (txtTitular.getText().trim().isEmpty()) {
            mostrarError("Ingresa el nombre del titular");
            return;
        }
        if (txtNumero.getText().trim().length() < 16) {
            mostrarError("El número de tarjeta debe tener 16 dígitos");
            return;
        }
        if (txtVencimiento.getText().trim().isEmpty()) {
            mostrarError("Ingresa la fecha de vencimiento");
            return;
        }
        if (txtCVV.getText().trim().length() < 3) {
            mostrarError("El CVV debe tener al menos 3 dígitos");
            return;
        }
 
        try {
            controlador.confirmarCompra();
            mostrarPantallaExito();
        } catch (Exception ex) {
            controlador.irATransaccionFallida("Error interno: " + ex.getMessage());
        }
    }
 
    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Datos incompletos", JOptionPane.WARNING_MESSAGE);
    }
 
    private void mostrarPantallaExito() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
 
        JLabel ico = new JLabel("✔", SwingConstants.CENTER);
        ico.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        ico.setForeground(new Color(100, 220, 140));
        ico.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel msg = new JLabel("¡PAGO EXITOSO!", SwingConstants.CENTER);
        msg.setFont(new Font("Segoe UI", Font.BOLD, 20));
        msg.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Tu membresía ha sido activada.", SwingConstants.CENTER);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        panel.add(ico);
        panel.add(Box.createVerticalStrut(8));
        panel.add(msg);
        panel.add(Box.createVerticalStrut(4));
        panel.add(sub);
 
        int resp = JOptionPane.showOptionDialog(
            this, panel,
            "Compra Completada",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            new String[]{"Sí, agendar cita", "En otro momento"},
            "Sí, agendar cita"
        );
 
        if (resp == JOptionPane.YES_OPTION) {
            controlador.irASeleccionInstructor();
        } else {
            controlador.irAQR();
        }
    }

    private String calcularTotal() {
        PlanDTO plan = controlador.getPlanSeleccionado();
        List<AmenidadDTO> extras = controlador.getExtrasSeleccionados();
 
        double base  = (plan != null && plan.getPrecio() != null) ? plan.getPrecio() : 0.0;
        double extra = (extras != null)
            ? extras.stream().mapToDouble(AmenidadDTO::getCosto).sum()
            : 0.0;
 
        return String.format("$%.2f MXN", base + extra);
    }
}
