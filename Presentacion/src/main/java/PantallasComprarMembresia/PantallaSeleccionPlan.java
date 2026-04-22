/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionPlan extends PantallaBase {
    private Tabla tablaPlanes;
    private List<PlanDTO> planes;
    private IComprarMembresia subsistema;
    
    // Guardamos la sucursal que viene de la pantalla anterior
    private SucursalDTO sucursalPrevia;

    public PantallaSeleccionPlan(IControladorAplicacion controlador, SucursalDTO sucursalSeleccionada) {
        super(controlador);
        this.sucursalPrevia = sucursalSeleccionada;
        this.subsistema = new FachadaComprarMembresia();
        
        setTitle("Seleccionar Plan - SteelCore Fitness");
        inicializarComponentes();
        cargarPlanes();
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
        contenedor.setPreferredSize(new Dimension(860, 500));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));

        JLabel titulo = new JLabel("Elige tu Plan");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Gimnasio seleccionado: " + sucursalPrevia.getNombre());
        subtitulo.setFont(Colores.FUENTE_SUBTITULO);
        subtitulo.setForeground(Colores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        tablaPlanes = new Tabla("Catálogo de Planes", new String[]{"ID", "Plan", "Precio Base", "Descripción"});
        tablaPlanes.setAlignmentX(CENTER_ALIGNMENT);
        tablaPlanes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

        Boton btnRegresar = new Boton("Atrás", Boton.Variante.SECUNDARIO);
        Boton btnSeleccionar = new Boton("Ir a Pagar", Boton.Variante.PRIMARIO);

        btnRegresar.addActionListener(e -> {
            setVisible(false);
            controlador.SeleccionSucursal();
        });

        btnSeleccionar.addActionListener(e -> onSeleccionar());

        panelBotones.add(btnRegresar);
        panelBotones.add(Box.createHorizontalStrut(16));
        panelBotones.add(btnSeleccionar);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);

        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(8));
        contenedor.add(subtitulo);
        contenedor.add(Box.createVerticalStrut(32));
        contenedor.add(tablaPlanes);
        contenedor.add(Box.createVerticalStrut(24));
        contenedor.add(panelBotones);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 40, 0, 40);
        fondo.add(contenedor, gbc);
    }

    private void cargarPlanes() {
        planes = subsistema.obtenerPlanes();
        tablaPlanes.limpiar();
        for (PlanDTO p : planes) {
            tablaPlanes.agregarFila(new Object[]{ p.getIdPlan(), p.getNombre(), "$" + p.getPrecio(), p.getDescripcion() });
        }
    }

    private void onSeleccionar() {
        int fila = tablaPlanes.getFilaSeleccionada();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor selecciona un plan.", "Aviso", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        PlanDTO planSeleccionado = planes.get(fila);
        setVisible(false);
        
        controlador.ResumenCompra(sucursalPrevia, planSeleccionado);
    }
    
}
