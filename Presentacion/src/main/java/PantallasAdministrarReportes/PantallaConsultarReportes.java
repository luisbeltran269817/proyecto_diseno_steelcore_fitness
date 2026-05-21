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
import dtosReportes.ReporteHistorialDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla para consultar los últimos reportes generados.
 *
 * Muestra en una tabla el historial de reportes guardados en MongoDB. Esta
 * pantalla permite al administrador revisar reportes generados previamente sin
 * volver a crearlos desde cero.
 *
 * @author Noelia E.N.
 */
public class PantallaConsultarReportes extends PantallaBase {

    private final IControlReportes controlReportes;

    private JTable tablaReportes;
    private DefaultTableModel modeloTabla;

    private static final DateTimeFormatter FMT_FECHA_HORA
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private static final DateTimeFormatter FMT_FECHA
            = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Constructor de la pantalla.
     *
     * @param controlReportes controlador del módulo de reportes.
     * @param controladorAplicacion controlador principal de la aplicación.
     */
    public PantallaConsultarReportes(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        setTitle("Consultar reportes generados");
        inicializarComponentes();
        cargarReportes();
        setVisible(true);
    }

    /**
     * Inicializa los componentes principales.
     */
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

    /**
     * Crea el encabezado de la pantalla.
     *
     * @return panel de encabezado.
     */
    private JPanel crearHeader() {
        JPanel header = crearCard(0, 90);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JLabel titulo = new JLabel("ÚLTIMOS REPORTES GENERADOS");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        header.add(titulo, BorderLayout.CENTER);

        return header;
    }

    /**
     * Crea el área central con la tabla de reportes.
     *
     * @return panel central.
     */
    private JPanel crearCentro() {
        JPanel card = crearCard(1100, 520);
        card.setLayout(new BorderLayout(12, 12));
        card.setBorder(new EmptyBorder(24, 24, 24, 24));

        JLabel subtitulo = new JLabel("Historial de reportes");
        subtitulo.setFont(Colores.FUENTE_SUBTITULO);
        subtitulo.setForeground(Colores.TEXTO_PRINCIPAL);

        String[] columnas = {
            "ID",
            "Fecha generación",
            "Tipo",
            "Periodo",
            "Ingresos",
            "Membresías",
            "Sucursal destacada",
            "Entrenador destacado"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaReportes = new JTable(modeloTabla);
        tablaReportes.setRowHeight(30);
        tablaReportes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaReportes.setFont(Colores.FUENTE_CAMPO);
        tablaReportes.getTableHeader().setFont(Colores.FUENTE_BOTON);

        /*
         * Ocultamos visualmente el ID, pero lo dejamos en el modelo para poder
         * recuperar el reporte seleccionado después.
         */
        tablaReportes.getColumnModel().getColumn(0).setMinWidth(0);
        tablaReportes.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaReportes.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scroll = new JScrollPane(tablaReportes);
        scroll.setBorder(null);

        card.add(subtitulo, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);

        return card;
    }

    /**
     * Crea los botones inferiores.
     *
     * @return panel de acciones.
     */
    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnVolver = crearBoton("Volver", Boton.Variante.SECUNDARIO);
        btnVolver.addActionListener(e -> controlReportes.irAPantallaPrincipalReportes());

        Boton btnActualizar = crearBoton("Actualizar", Boton.Variante.SECUNDARIO);
        btnActualizar.addActionListener(e -> cargarReportes());

        Boton btnVerDetalle = crearBoton("Ver detalle", Boton.Variante.PRIMARIO);
        btnVerDetalle.setPreferredSize(new Dimension(220, 50));
        btnVerDetalle.addActionListener(e -> verDetalleSeleccionado());

        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.add(btnVolver);

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.add(btnActualizar);

        JPanel derecha = new JPanel();
        derecha.setOpaque(false);
        derecha.add(btnVerDetalle);

        acciones.add(izquierda, BorderLayout.WEST);
        acciones.add(centro, BorderLayout.CENTER);
        acciones.add(derecha, BorderLayout.EAST);

        return acciones;
    }

    /**
     * Carga los reportes históricos en la tabla.
     */
    private void cargarReportes() {
        try {
            modeloTabla.setRowCount(0);

            List<ReporteHistorialDTO> reportes = controlReportes.consultarUltimosReportes();

            if (reportes == null || reportes.isEmpty()) {
                Mensaje.info(this, "No hay reportes generados en el historial.");
                return;
            }

            for (ReporteHistorialDTO reporte : reportes) {
                agregarFilaReporte(reporte);
            }

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar el historial: " + ex.getMessage());
        }
    }

    /**
     * Agrega un reporte a la tabla.
     *
     * @param reporte reporte histórico.
     */
    private void agregarFilaReporte(ReporteHistorialDTO reporte) {
        if (reporte == null) {
            return;
        }

        String fechaGeneracion = reporte.getFechaGeneracion() != null
                ? reporte.getFechaGeneracion().format(FMT_FECHA_HORA)
                : "Sin fecha";

        String periodo = obtenerPeriodo(reporte);

        String tipo = reporte.getTipoReporte() != null
                ? obtenerNombreTipo(reporte)
                : "Sin tipo";

        modeloTabla.addRow(new Object[]{
            reporte.getIdReporte(),
            fechaGeneracion,
            tipo,
            periodo,
            "$" + reporte.getTotalIngresos(),
            reporte.getMembresiasVendidas(),
            valor(reporte.getSucursalConMasVentas()),
            valor(reporte.getEntrenadorConMasClientes())
        });
    }

    /**
     * Muestra el detalle básico del reporte seleccionado.
     */
    private void verDetalleSeleccionado() {
        int fila = tablaReportes.getSelectedRow();

        if (fila == -1) {
            Mensaje.error(this, "Debe seleccionar un reporte.");
            return;
        }

        int filaModelo = tablaReportes.convertRowIndexToModel(fila);
        String idReporte = String.valueOf(modeloTabla.getValueAt(filaModelo, 0));

        try {
            ReporteHistorialDTO reporte = controlReportes.buscarReporteHistorialPorId(idReporte);

            Mensaje.info(this, construirDetalle(reporte));

        } catch (NegocioException ex) {
            Mensaje.error(this, ex.getMessage());
        }
    }

    /**
     * Construye el texto de detalle del reporte.
     *
     * @param reporte reporte histórico.
     * @return detalle en texto.
     */
    private String construirDetalle(ReporteHistorialDTO reporte) {
        if (reporte == null) {
            return "No se encontró información del reporte.";
        }

        return "Tipo: " + obtenerNombreTipo(reporte)
                + "\nFecha generación: " + (reporte.getFechaGeneracion() != null ? reporte.getFechaGeneracion().format(FMT_FECHA_HORA) : "Sin fecha")
                + "\nPeriodo: " + obtenerPeriodo(reporte)
                + "\n\nMétricas:"
                + "\nIngresos totales: $" + reporte.getTotalIngresos()
                + "\nMembresías vendidas: " + reporte.getMembresiasVendidas()
                + "\nRenovaciones: " + reporte.getRenovaciones()
                + "\nNuevos socios: " + reporte.getNuevosSocios()
                + "\n\nIndicadores:"
                + "\nSucursal con más ventas: " + valor(reporte.getSucursalConMasVentas())
                + "\nTipo de membresía más vendida: " + valor(reporte.getTipoMembresiaMasVendida())
                + "\nAmenidad más solicitada: " + valor(reporte.getAmenidadMasSolicitada())
                + "\nEntrenador con más clientes: " + valor(reporte.getEntrenadorConMasClientes());
    }

    /**
     * Obtiene el periodo del reporte.
     *
     * @param reporte reporte histórico.
     * @return periodo formateado.
     */
    private String obtenerPeriodo(ReporteHistorialDTO reporte) {
        String inicio = reporte.getFechaInicio() != null
                ? reporte.getFechaInicio().format(FMT_FECHA)
                : "Sin inicio";

        String fin = reporte.getFechaFin() != null
                ? reporte.getFechaFin().format(FMT_FECHA)
                : "Sin fin";

        return inicio + " - " + fin;
    }

    /**
     * Obtiene el nombre visible del tipo de reporte.
     *
     * @param reporte reporte histórico.
     * @return nombre del tipo.
     */
    private String obtenerNombreTipo(ReporteHistorialDTO reporte) {
        if (reporte == null || reporte.getTipoReporte() == null) {
            return "Sin tipo";
        }

        switch (reporte.getTipoReporte()) {
            case GENERAL:
                return "General";
            case VENTAS_MEMBRESIAS:
                return "Ventas de membresías";
            case INGRESOS:
                return "Ingresos";
            case POR_SUCURSAL:
                return "Por sucursal";
            case DESEMPENO_ENTRENADORES:
                return "Desempeño de entrenadores";
            default:
                return "Sin tipo";
        }
    }

    /**
     * Regresa un valor textual seguro.
     *
     * @param texto texto original.
     * @return texto validado.
     */
    private String valor(String texto) {
        return texto == null || texto.isBlank() ? "Sin datos" : texto;
    }
}
