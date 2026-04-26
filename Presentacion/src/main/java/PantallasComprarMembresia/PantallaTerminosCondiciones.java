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
import static java.awt.Component.CENTER_ALIGNMENT;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 *
 * @author julian izaguirre
 */
public class PantallaTerminosCondiciones extends PantallaBase {
 private JCheckBox chkAceptar;
    private Boton btnContinuar;
 
    public PantallaTerminosCondiciones(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Términos y Condiciones - SteelCore Fitness");
        inicializarComponentes();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel card = crearCard(600, 620);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(44, 52, 44, 52));
 
        JLabel titulo = new JLabel("Términos y Condiciones");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JTextArea txtTerminos = new JTextArea(generarTextoTerminos());
        txtTerminos.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtTerminos.setForeground(Colores.TEXTO_SECUNDARIO);
        txtTerminos.setBackground(Colores.FONDO_CAMPO);
        txtTerminos.setEditable(false);
        txtTerminos.setLineWrap(true);
        txtTerminos.setWrapStyleWord(true);
        txtTerminos.setBorder(new EmptyBorder(12, 14, 12, 14));
 
        JScrollPane scroll = new JScrollPane(txtTerminos);
        scroll.setPreferredSize(new Dimension(Integer.MAX_VALUE, 300));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Colores.FONDO_CAMPO);
        scroll.setAlignmentX(CENTER_ALIGNMENT);
 
        chkAceptar = new JCheckBox("Acepto los términos y condiciones");
        chkAceptar.setFont(Colores.FUENTE_LABEL);
        chkAceptar.setForeground(Colores.TEXTO_PRINCIPAL);
        chkAceptar.setOpaque(false);
        chkAceptar.setAlignmentX(CENTER_ALIGNMENT);
 
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnAtras = crearBoton("Atrás", Boton.Variante.SECUNDARIO);
        btnContinuar = crearBoton("Continuar", Boton.Variante.PRIMARIO);
        btnContinuar.setEnabled(false);
 
        chkAceptar.addActionListener(e ->
            btnContinuar.setEnabled(chkAceptar.isSelected()));
 
        btnAtras.addActionListener(e -> controlador.irADetallePlan());
 
        btnContinuar.addActionListener(e -> controlador.irADatosBancarios());
 
        panelBtns.add(btnAtras);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnContinuar);
 
        card.add(titulo);
        card.add(Box.createVerticalStrut(24));
        card.add(scroll);
        card.add(Box.createVerticalStrut(20));
        card.add(chkAceptar);
        card.add(Box.createVerticalStrut(28));
        card.add(panelBtns);
 
        fondo.add(card);
    }
 
    private String generarTextoTerminos() {
        PlanDTO plan = controlador.getPlanSeleccionado();
        SucursalDTO sucursal = controlador.getSucursalSeleccionada();
        List<AmenidadDTO> extras = controlador.getExtrasSeleccionados();
 
        double costoExtras = (extras != null)
            ? extras.stream().mapToDouble(AmenidadDTO::getCosto).sum()
            : 0.0;
        double precioBase = (plan != null && plan.getPrecio() != null) ? plan.getPrecio() : 0.0;
        double total = precioBase + costoExtras;
 
        String nombrePlan    = (plan != null)     ? plan.getNombre()     : "—";
        String meses         = (plan != null)     ? plan.getMesesDuracion() + " mes(es)" : "—";
        String nombreSucursal= (sucursal != null) ? sucursal.getNombre() + ", " + sucursal.getCiudad() : "—";
 
        return "CONTRATO DE MEMBRESÍA — STEELCORE FITNESS\n\n"
            + "Plan contratado: " + nombrePlan + "\n"
            + "Sucursal: " + nombreSucursal + "\n"
            + "Duración: " + meses + "\n"
            + "Monto total: $" + String.format("%.2f", total) + " MXN\n\n"
            + "TÉRMINOS Y CONDICIONES:\n\n"
            + "1. La membresía es personal e intransferible.\n"
            + "2. El pago debe realizarse en su totalidad al momento de la contratación.\n"
            + "3. La membresía tendrá vigencia durante el período indicado a partir de la fecha de activación.\n"
            + "4. Cancelaciones y reembolsos sujetos a la política vigente de SteelCore Fitness.\n"
            + "5. SteelCore Fitness se reserva el derecho de modificar horarios y servicios.\n"
            + "6. El socio recibirá un código QR único como credencial de acceso a las instalaciones.\n"
            + "7. El uso de las instalaciones implica la aceptación del reglamento interno.\n"
            + "8. En caso de conducta inapropiada, SteelCore Fitness podrá cancelar la membresía sin reembolso.\n\n"
            + "Al aceptar estos términos, el usuario confirma haber leído y comprendido todas las condiciones.";
    }
}
