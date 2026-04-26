/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;


import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.SucursalDTO;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionSucursal extends PantallaBase{
    private ButtonGroup grupoBotones;
    private SucursalDTO sucursalSeleccionada;
    private JPanel panelSucursales;
 
    public PantallaSeleccionSucursal(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Sucursal - SteelCore Fitness");
        inicializarComponentes();
        cargarSucursales();
        setVisible(true);
    }
 
    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);
 
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);
        contenedor.setPreferredSize(new Dimension(780, 600));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));
 
        JLabel titulo = new JLabel("Selecciona tu Sucursal");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);
 
        JLabel sub = new JLabel("Elige el gimnasio más cercano a ti");
        sub.setFont(Colores.FUENTE_SUBTITULO);
        sub.setForeground(Colores.TEXTO_SECUNDARIO);
        sub.setAlignmentX(CENTER_ALIGNMENT);
 
        panelSucursales = new JPanel();
        panelSucursales.setLayout(new BoxLayout(panelSucursales, BoxLayout.Y_AXIS));
        panelSucursales.setOpaque(false);
        panelSucursales.setAlignmentX(CENTER_ALIGNMENT);
        grupoBotones = new ButtonGroup();
 
        JScrollPane scroll = new JScrollPane(panelSucursales);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(740, 360));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));
        scroll.setAlignmentX(CENTER_ALIGNMENT);
 
        JPanel panelBtns = new JPanel();
        panelBtns.setOpaque(false);
        panelBtns.setLayout(new BoxLayout(panelBtns, BoxLayout.X_AXIS));
        panelBtns.setAlignmentX(CENTER_ALIGNMENT);
 
        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        Boton btnContinuar = crearBoton("Continuar", Boton.Variante.PRIMARIO);
 
        btnRegresar.addActionListener(e -> controlador.irAPerfilUsuario());
 
        btnContinuar.addActionListener(e -> {
            if (sucursalSeleccionada == null) {
                JOptionPane.showMessageDialog(this,
                    "Por favor selecciona una sucursal.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            controlador.setSucursalSeleccionada(sucursalSeleccionada);
            controlador.irASeleccionPlan();
        });
 
        panelBtns.add(btnRegresar);
        panelBtns.add(Box.createHorizontalStrut(16));
        panelBtns.add(btnContinuar);
 
        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(8));
        contenedor.add(sub);
        contenedor.add(Box.createVerticalStrut(28));
        contenedor.add(scroll);
        contenedor.add(Box.createVerticalStrut(24));
        contenedor.add(panelBtns);
 
        fondo.add(contenedor);
    }
 
    private void cargarSucursales() {
        List<SucursalDTO> sucursales = controlador.obtenerSucursales();
        panelSucursales.removeAll();
        for (SucursalDTO s : sucursales) {
            panelSucursales.add(crearFilaSucursal(s));
            panelSucursales.add(Box.createVerticalStrut(10));
        }
        panelSucursales.revalidate();
        panelSucursales.repaint();
    }
 
    private JPanel crearFilaSucursal(SucursalDTO s) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.setColor(Colores.BORDE_CARD);
                g2.setStroke(new BasicStroke(1f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(14, 0));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
 
        JLabel nombre = new JLabel(s.getNombre());
        nombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nombre.setForeground(Colores.TEXTO_PRINCIPAL);
 
        JLabel direccion = new JLabel(s.getColonia() + ", " + s.getCiudad());
        direccion.setFont(Colores.FUENTE_LABEL);
        direccion.setForeground(Colores.TEXTO_SECUNDARIO);
 
        JPanel info = new JPanel();
        info.setOpaque(false);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(nombre);
        info.add(Box.createVerticalStrut(4));
        info.add(direccion);
 
        JRadioButton radio = new JRadioButton("Seleccionar");
        radio.setFont(Colores.FUENTE_BOTON_SM);
        radio.setForeground(Colores.TEXTO_PRINCIPAL);
        radio.setOpaque(false);
        radio.addActionListener(e -> sucursalSeleccionada = s);
        grupoBotones.add(radio);
 
        card.add(info, BorderLayout.CENTER);
        card.add(radio, BorderLayout.EAST);
        return card;
    }
}
