/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;


import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtos.SucursalDTO;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author julian izaguirre
 */
public class PantallaSeleccionSucursal extends PantallaBase{
    private Tabla tablaSucursales;
    private CampoTexto txtBuscar;

    private List<SucursalDTO> sucursales;

    public PantallaSeleccionSucursal(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Seleccionar Sucursal - SteelCore Fitness");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

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
        contenedor.setPreferredSize(new Dimension(860, 620));
        contenedor.setBorder(new EmptyBorder(40, 0, 40, 0));

        JLabel titulo = new JLabel("Selecciona una Sucursal");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitulo = new JLabel("Elige el gimnasio más cercano a ti");
        subtitulo.setFont(Colores.FUENTE_SUBTITULO);
        subtitulo.setForeground(Colores.TEXTO_SECUNDARIO);
        subtitulo.setAlignmentX(CENTER_ALIGNMENT);

        txtBuscar = new CampoTexto("Buscar", "Ciudad, colonia o nombre...");
        txtBuscar.setAlignmentX(CENTER_ALIGNMENT);
        txtBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        tablaSucursales = new Tabla("Sucursales disponibles", new String[]{"ID", "Nombre", "Ciudad", "Colonia"}
        );

        tablaSucursales.setAlignmentX(CENTER_ALIGNMENT);
        tablaSucursales.setMaximumSize(new Dimension(Integer.MAX_VALUE, 320));

        JTable table = tablaSucursales.getTabla();
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(
                        t, value, isSelected, hasFocus, row, col);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0
                            ? Colores.FONDO_CAMPO
                            : new Color(58, 40, 130));
                }
                c.setForeground(isSelected ? Color.WHITE : Colores.TEXTO_PRINCIPAL);
                return c;
            }
        });

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

        Boton btnRegresar = new Boton("Regresar", Boton.Variante.SECUNDARIO);
        Boton btnSeleccionar = new Boton("Seleccionar", Boton.Variante.PRIMARIO);

        btnRegresar.addActionListener(e -> {
            setVisible(false);
            controlador.irAPerfilUsuario();
        });

        btnSeleccionar.addActionListener(e -> onSeleccionar());

        txtBuscar.addPropertyChangeListener("valor", e
                -> tablaSucursales.filtrar(txtBuscar.getValor())
        );

        panelBotones.add(btnRegresar);
        panelBotones.add(Box.createHorizontalStrut(16));
        panelBotones.add(btnSeleccionar);
        panelBotones.setAlignmentX(CENTER_ALIGNMENT);

        contenedor.add(titulo);
        contenedor.add(Box.createVerticalStrut(8));
        contenedor.add(subtitulo);
        contenedor.add(Box.createVerticalStrut(32));
        contenedor.add(txtBuscar);
        contenedor.add(Box.createVerticalStrut(16));
        contenedor.add(tablaSucursales);
        contenedor.add(Box.createVerticalStrut(24));
        contenedor.add(panelBotones);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 40, 0, 40);

        fondo.add(contenedor, gbc);
    }


    private void cargarSucursales() {
        sucursales = controlador.obtenerSucursales();
        tablaSucursales.limpiar();
        for (SucursalDTO s : sucursales) {
            tablaSucursales.agregarFila(new Object[]{s.getIdSucursal(),s.getNombre(),s.getCiudad(),s.getColonia()
            });
        }
    }
    private void onSeleccionar() {
        int fila = tablaSucursales.getFilaSeleccionada();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,"Por favor seleccione una sucursal.","Aviso",JOptionPane.WARNING_MESSAGE);
            return;
        }
        SucursalDTO seleccionada = sucursales.get(fila);
        controlador.seleccionarSucursal(seleccionada);
        setVisible(false);
        //Mas tarde hago este método
        //controlador.irASeleccionPlan();
    }
}
