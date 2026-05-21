/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import Controladores.IControladorAplicacion;
import ControladoresReportes.IControlReportes;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.Mensaje;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtosReportes.MetricasReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Noelia E.N
 */
public class PantallaPrincipalReportes extends PantallaBase {

    private final IControlReportes controlReportes;
    private Tabla tablaResumen;

    public PantallaPrincipalReportes(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        setTitle("Administración Comercial y Reportes");
        inicializarComponentes();
        cargarResumenMensual();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearCentro(), BorderLayout.CENTER);
        root.add(crearAcciones(), BorderLayout.SOUTH);

        add(root);
    }

    private JPanel crearHeader() {
        JPanel header = crearCard(0, 90);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JLabel titulo = new JLabel("ADMINISTRACIÓN COMERCIAL Y REPORTES");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);

        JLabel subtitulo = new JLabel("Resumen general del mes");
        subtitulo.setFont(Colores.FUENTE_SUBTITULO);
        subtitulo.setForeground(Colores.TEXTO_SECUNDARIO);

        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        textos.add(titulo);
        textos.add(Box.createVerticalStrut(5));
        textos.add(subtitulo);

        header.add(textos, BorderLayout.CENTER);

        return header;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);

        String[] columnas = {"Concepto", "Resultado"};
        tablaResumen = new Tabla("Resumen general del mes", columnas);
        centro.add(tablaResumen, BorderLayout.CENTER);

        return centro;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnCerrarSesion = crearBoton("Cerrar sesión", Boton.Variante.SECUNDARIO);
        btnCerrarSesion.addActionListener(e -> controlReportes.cerrarSesion());

        Boton btnGenerar = crearBoton("Generar reporte", Boton.Variante.PRIMARIO);
        btnGenerar.setPreferredSize(new Dimension(260, 50));
        btnGenerar.addActionListener(e -> controlReportes.irAPantallaSeleccionReportes());

        Boton btnConsultarReportes = crearBoton("Consultar reportes", Boton.Variante.SECUNDARIO);
        btnConsultarReportes.addActionListener(e -> controlReportes.irAPantallaConsultarReportes());

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        izquierda.setOpaque(false);
        izquierda.add(btnCerrarSesion);

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        derecha.setOpaque(false);
        derecha.add(btnGenerar);
        derecha.add(btnConsultarReportes);

        acciones.add(izquierda, BorderLayout.WEST);
        acciones.add(derecha, BorderLayout.EAST);

        return acciones;
    }

    /**
     * Carga el resumen mensual real en la tabla principal de reportes.
     */
    private void cargarResumenMensual() {
        tablaResumen.limpiar();

        try {
            MetricasReporteDTO metricas = controlReportes.obtenerResumenMensual();

            if (metricas == null || metricas.getMembresiasVendidas() == 0) {
                tablaResumen.agregarFila(new Object[]{"Ingresos generados", "$0.00"});
                tablaResumen.agregarFila(new Object[]{"Membresías vendidas", "0"});
                tablaResumen.agregarFila(new Object[]{"Nuevos socios registrados", "0"});
                tablaResumen.agregarFila(new Object[]{"Renovaciones de membresías", "0"});
                tablaResumen.agregarFila(new Object[]{"Sucursal con más ventas", "Sin datos"});
                tablaResumen.agregarFila(new Object[]{"Entrenador con más clientes", "Sin datos"});
                tablaResumen.agregarFila(new Object[]{"Plan más vendido", "Sin datos"});
                return;
            }

            tablaResumen.agregarFila(new Object[]{"Ingresos generados", "$" + metricas.getTotalIngresos()});
            tablaResumen.agregarFila(new Object[]{"Membresías vendidas", String.valueOf(metricas.getMembresiasVendidas())});
            tablaResumen.agregarFila(new Object[]{"Nuevos socios registrados", String.valueOf(metricas.getNuevosSocios())});
            tablaResumen.agregarFila(new Object[]{"Renovaciones de membresías", String.valueOf(metricas.getRenovaciones())});
            tablaResumen.agregarFila(new Object[]{"Sucursal con más ventas", valor(metricas.getSucursalConMasVentas())});
            tablaResumen.agregarFila(new Object[]{"Entrenador con más clientes", valor(metricas.getEntrenadorConMasClientes())});
            tablaResumen.agregarFila(new Object[]{"Plan más vendido", valor(metricas.getTipoMembresiaMasVendida())});

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar el resumen mensual: " + ex.getMessage());

            tablaResumen.agregarFila(new Object[]{"Ingresos generados", "$0.00"});
            tablaResumen.agregarFila(new Object[]{"Membresías vendidas", "0"});
            tablaResumen.agregarFila(new Object[]{"Nuevos socios registrados", "0"});
            tablaResumen.agregarFila(new Object[]{"Renovaciones de membresías", "0"});
            tablaResumen.agregarFila(new Object[]{"Sucursal con más ventas", "Sin datos"});
            tablaResumen.agregarFila(new Object[]{"Entrenador con más clientes", "Sin datos"});
            tablaResumen.agregarFila(new Object[]{"Plan más vendido", "Sin datos"});
        }
    }

    /**
     * Regresa un texto seguro para mostrar en la tabla.
     *
     * @param texto texto original.
     * @return texto validado.
     */
    private String valor(String texto) {
        return texto == null || texto.isBlank() ? "Sin datos" : texto;
    }
}
