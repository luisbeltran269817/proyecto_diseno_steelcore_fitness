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
import dtos.AmenidadDTO;
import dtos.PlanDTO;
import java.awt.*;
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
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

         // Calculamos el total de forma segura; si falla mostramos un aviso pero
         // dejamos la pantalla funcional para que el usuario pueda regresar
         String textoMonto;
         try {
             textoMonto = "Total a pagar: " + calcularTotal();
         } catch (NegocioException ex) {
             textoMonto = "Total a pagar: (no disponible)";
             JOptionPane.showMessageDialog(this,
                 "No se pudo calcular el total. Verifique su seleccion de plan.",
                 "Advertencia", JOptionPane.WARNING_MESSAGE);
         }
         JLabel lblMonto = new JLabel(textoMonto);
         lblMonto.setFont(new Font("Segoe UI", Font.BOLD, 18));
         lblMonto.setForeground(Colores.ACENTO);
         lblMonto.setAlignmentX(CENTER_ALIGNMENT);
         
         txtTitular = crearCampo("Nombre del titular");
         txtNumero = crearCampo("Número de tarjeta (16 dígitos)");
         txtVencimiento = crearCampo("Fecha de vencimiento (MM/AA)");
         txtCVV = crearCampo("CVV");
         
         aplicarFiltroNumerico(txtNumero, 16);
         aplicarFiltroNumerico(txtCVV, 4);
         
         JPanel panelBtns = new JPanel();
         panelBtns.setOpaque(false);
         panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
         panelBtns.setAlignmentX(CENTER_ALIGNMENT);
         
         Boton btnAtras    = crearBoton("Atrás", Boton.Variante.SECUNDARIO);
         Boton btnProceder = crearBoton("Proceder al pago", Boton.Variante.PRIMARIO);
         
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
            public void insertString(DocumentFilter.FilterBypass fb, int off, String str, AttributeSet a)
                    throws BadLocationException {
                if (str != null && str.matches("\\d+")
                        && fb.getDocument().getLength() + str.length() <= maxLen)
                    super.insertString(fb, off, str, a);
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int off, int len, String str, AttributeSet a)
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
        String numero = txtNumero.getText().trim();
        String token;

        if (numero.startsWith("4")) {
            token = "tok_visa"; 
        } else {
            token = "tok_chargeDeclined";
        }

        controlador.setTokenTarjeta(token);

        controlador.confirmarCompra();
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Datos incompletos", JOptionPane.WARNING_MESSAGE);
    }

    private String calcularTotal() throws NegocioException {
        double total = controlador.calcularTotal();
        return String.format("$%.2f MXN", total);
    }
}
