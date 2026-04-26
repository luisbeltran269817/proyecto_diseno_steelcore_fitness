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
import dtos.SucursalDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaDetallePlan extends PantallaBase {
    private final List<AmenidadDTO> extrasSeleccionados = new ArrayList<>();
    private JLabel lblMontoTotal;
    private double montoBase;
 
    public PantallaDetallePlan(IControladorAplicacion controlador) {
        super(controlador);
        PlanDTO plan = controlador.getPlanSeleccionado();
        this.montoBase = (plan != null && plan.getPrecio() != null) ? plan.getPrecio() : 0.0;
        setTitle("Detalle del Plan - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.X_AXIS));
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(960, 620));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));
 
        contenedor.add(crearPanelBeneficios());
        contenedor.add(Box.createHorizontalStrut(20));
        contenedor.add(crearPanelExtras());
 
        fondo.add(contenedor);
    }
 
    private JPanel crearPanelBeneficios() {
        PlanDTO plan = controlador.getPlanSeleccionado();
 
        JPanel card = crearCard(440, 540);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 36, 32, 36));
 
        JLabel titulo = new JLabel("Detalles del Plan");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        String nombrePlan = (plan != null) ? plan.getNombre() : "—";
        JLabel lblPlan = new JLabel(nombrePlan);
        lblPlan.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPlan.setForeground(Colores.ACENTO);
        lblPlan.setAlignmentX(CENTER_ALIGNMENT);
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblPlan);
        card.add(Box.createVerticalStrut(20));
 
        JLabel lblBen = new JLabel("Beneficios incluidos:");
        lblBen.setFont(Colores.FUENTE_LABEL);
        lblBen.setForeground(Colores.TEXTO_SECUNDARIO);
        card.add(lblBen);
        card.add(Box.createVerticalStrut(8));
 
        if (plan != null && plan.getAmenidades() != null) {
            for (AmenidadDTO a : plan.getAmenidades()) {
                JLabel lbl = new JLabel("  ✓  " + a.getNombre());
                lbl.setFont(Colores.FUENTE_CAMPO);
                lbl.setForeground(new Color(100, 220, 140));
                card.add(lbl);
                card.add(Box.createVerticalStrut(4));
            }
        }
 
        card.add(Box.createVerticalGlue());
 
        JSeparator sep = new JSeparator();
        sep.setForeground(Colores.BORDE_CARD);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        card.add(sep);
        card.add(Box.createVerticalStrut(14));
 
        JLabel lblMontoLabel = new JLabel("Monto total:");
        lblMontoLabel.setFont(Colores.FUENTE_LABEL);
        lblMontoLabel.setForeground(Colores.TEXTO_SECUNDARIO);
        card.add(lblMontoLabel);
 
        lblMontoTotal = new JLabel(String.format("$%.2f", montoBase));
        lblMontoTotal.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblMontoTotal.setForeground(Colores.TEXTO_PRINCIPAL);
        card.add(lblMontoTotal);
        card.add(Box.createVerticalStrut(20));
 
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
 
        Boton btnAtras = crearBoton("Atrás", Boton.Variante.SECUNDARIO);
        Boton btnContinuar = crearBoton("Continuar", Boton.Variante.PRIMARIO);
 
        btnAtras.addActionListener(e -> controlador.irASeleccionPlan());
 
        btnContinuar.addActionListener(e -> {
            controlador.setExtrasSeleccionados(new ArrayList<>(extrasSeleccionados));
            controlador.irATerminosCondiciones();
        });
 
        panelBtns.add(btnAtras);
        panelBtns.add(Box.createHorizontalStrut(12));
        panelBtns.add(btnContinuar);
        card.add(panelBtns);
 
        return card;
    }
 
    private JPanel crearPanelExtras() {
        JPanel card = crearCard(460, 540);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(32, 36, 32, 36));
 
        JLabel titulo = new JLabel("Extras del Plan");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
 
        JLabel sub = new JLabel("Al hacer clic en una opción se actualiza el monto.");
        sub.setFont(Colores.FUENTE_LABEL);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(4));
        card.add(sub);
        card.add(Box.createVerticalStrut(20));
 
        List<AmenidadDTO> extras = controlador.obtenerAmenidadesExtra();
 
        if (extras == null || extras.isEmpty()) {
            JLabel sinExtras = new JLabel("No hay extras disponibles.");
            sinExtras.setFont(Colores.FUENTE_LABEL);
            sinExtras.setForeground(Colores.TEXTO_PLACEHOLDER);
            card.add(sinExtras);
        } else {
            for (AmenidadDTO extra : extras) {
                JCheckBox chk = new JCheckBox(
                    String.format("%s  — $%.0f", extra.getNombre(), extra.getCosto()));
                chk.setFont(Colores.FUENTE_CAMPO);
                chk.setForeground(Colores.TEXTO_PRINCIPAL);
                chk.setOpaque(false);
 
                chk.addActionListener(e -> {
                    if (chk.isSelected()) {
                        extrasSeleccionados.add(extra);
                        montoBase += extra.getCosto();
                    } else {
                        extrasSeleccionados.remove(extra);
                        montoBase -= extra.getCosto();
                    }
                    lblMontoTotal.setText(String.format("$%.2f", montoBase));
                });
 
                card.add(chk);
                card.add(Box.createVerticalStrut(10));
            }
        }
 
        return card;
    }
}
