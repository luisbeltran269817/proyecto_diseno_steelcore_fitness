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
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionPlan extends PantallaBase {
    private PlanDTO planSeleccionado;
    private ButtonGroup grupoBotones;
    private JPanel panelPlanes;
 
    public PantallaSeleccionPlan(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Plan - SteelCore Fitness");
        inicializarComponentes();
        cargarPlanes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        SucursalDTO sucursal = controlador.getSucursalSeleccionada();
 
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(900, 620));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));
 
        JLabel titulo = new JLabel("Planes de Membresía");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        String infoSucursal = sucursal != null
            ? "Sucursal: " + sucursal.getNombre() + " — " + sucursal.getCiudad()
            : "Sucursal no seleccionada";
        JLabel sub = new JLabel(infoSucursal);
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.ACENTO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        panelPlanes = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 10));
        panelPlanes.setOpaque(false);
        panelPlanes.setAlignmentX(CENTER_ALIGNMENT);
        grupoBotones = new ButtonGroup();
 
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnAtras = crearBoton("Atrás", Boton.Variante.SECUNDARIO);
        Boton btnContinuar = crearBoton("Continuar", Boton.Variante.PRIMARIO);
 
        btnAtras.addActionListener(e -> controlador.irASeleccionSucursal());
 
        btnContinuar.addActionListener(e -> {
            if (planSeleccionado == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un plan.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.setPlanSeleccionado(planSeleccionado);
            controlador.irADetallePlan();
        });
 
        panelBtns.add(btnAtras);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnContinuar);
 
        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(8));
        contenedor.add(sub);
        contenedor.add(Box.createVerticalStrut(28));
        contenedor.add(panelPlanes);
        contenedor.add(Box.createVerticalStrut(28));
        contenedor.add(panelBtns);
 
        fondo.add(contenedor);
    }
 
    private void cargarPlanes() {
        SucursalDTO sucursal = controlador.getSucursalSeleccionada();
        if (sucursal == null) {
            return;
        }
 
        List<PlanDTO> planes = controlador.obtenerPlanesDeSucursal(sucursal.getIdSucursal());
        panelPlanes.removeAll();
        for (PlanDTO p : planes) {
            panelPlanes.add(crearCardPlan(p));
        }
        panelPlanes.revalidate();
        panelPlanes.repaint();
    }
 
    private JPanel crearCardPlan(PlanDTO plan) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(24, 24, 24, 24));
        card.setPreferredSize(new Dimension(240, 320));
 
        JLabel lblNombre = new JLabel(plan.getNombre());
        lblNombre.setFont(Colores.FUENTE_TITULO);
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel lblPrecio = new JLabel(String.format("$%.0f", plan.getPrecio()));
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 38));
        lblPrecio.setForeground(Colores.ACENTO);
        lblPrecio.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel lblMeses = new JLabel(plan.getMesesDuracion() + " mes(es)");
        lblMeses.setFont(Colores.FUENTE_LABEL);
        lblMeses.setForeground(Colores.TEXTO_SECUNDARIO);
        lblMeses.setAlignmentX(CENTER_ALIGNMENT);
 
        card.add(lblNombre);
        card.add(Box.createVerticalStrut(6));
        card.add(lblPrecio);
        card.add(Box.createVerticalStrut(4));
        card.add(lblMeses);
        card.add(Box.createVerticalStrut(10));
 
        if (plan.getAmenidades() != null) {
            for (AmenidadDTO a : plan.getAmenidades()) {
                JLabel lblAm = new JLabel("✓ " + a.getNombre());
                lblAm.setFont(Colores.FUENTE_LABEL);
                lblAm.setForeground(new Color(100, 220, 140));
                lblAm.setAlignmentX(CENTER_ALIGNMENT);
                card.add(lblAm);
                card.add(Box.createVerticalStrut(2));
            }
        }
 
        card.add(Box.createVerticalStrut(14));
 
        JRadioButton radio = new JRadioButton("Seleccionar");
        radio.setFont(Colores.FUENTE_BOTON_SM);
        radio.setForeground(Colores.TEXTO_PRINCIPAL);
        radio.setOpaque(false);
        radio.setAlignmentX(CENTER_ALIGNMENT);
        radio.addActionListener(e -> planSeleccionado = plan);
        grupoBotones.add(radio);
        card.add(radio);
 
        return card;
    }
}
