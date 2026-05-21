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
import UtileriasReportes.CampoComboReporte;
import UtileriasReportes.CampoFechaReporte;
import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.TipoReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * Pantalla de captura de filtros para la generación de reportes.
 *
 * Permite seleccionar fechas, sucursal, tipo de membresía, entrenador y
 * amenidad según el tipo de reporte solicitado. Los combos muestran nombres
 * descriptivos al usuario, pero guardan internamente los ids necesarios para
 * consultar la información real.
 *
 * @author Noelia E.N
 */
public class PantallaFiltrosReporte extends PantallaBase {

    private final IControlReportes controlReportes;
    private final TipoReporteDTO tipoReporte;

    private CampoFechaReporte campoFechaInicio;
    private CampoFechaReporte campoFechaFin;

    private CampoComboReporte comboSucursal;
    private CampoComboReporte comboTipoMembresia;
    private CampoComboReporte comboEntrenador;
    private CampoComboReporte comboAmenidad;

    /**
     * Constructor de la pantalla de filtros.
     *
     * @param controlReportes controlador específico del módulo de reportes.
     * @param controladorAplicacion controlador principal de la aplicación.
     * @param tipoReporte tipo de reporte seleccionado.
     */
    public PantallaFiltrosReporte(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion,
            TipoReporteDTO tipoReporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.tipoReporte = tipoReporte;
        setTitle("Filtros del Reporte");
        inicializarComponentes();
        cargarDatosIniciales();
        setVisible(true);
    }

    /**
     * Inicializa los componentes principales de la pantalla.
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
     * Crea el encabezado visual de la pantalla.
     *
     * @return panel de encabezado.
     */
    private JPanel crearHeader() {
        JPanel header = crearCard(0, 90);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JLabel titulo = new JLabel("REPORTE COMPLETO");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        header.add(titulo, BorderLayout.CENTER);

        return header;
    }

    /**
     * Crea la sección central con el formulario de filtros.
     *
     * @return panel central.
     */
    private JPanel crearCentro() {
        JPanel centrador = new JPanel(new java.awt.GridBagLayout());
        centrador.setOpaque(false);

        JPanel card = crearCard(760, 460);
        card.setLayout(new BorderLayout(20, 20));
        card.setBorder(new EmptyBorder(28, 34, 28, 34));

        JLabel lblTipo = new JLabel("Selecciona opciones para generar: " + obtenerNombreReporte());
        lblTipo.setFont(Colores.FUENTE_SUBTITULO);
        lblTipo.setForeground(Colores.TEXTO_PRINCIPAL);
        lblTipo.setHorizontalAlignment(JLabel.CENTER);

        JPanel formulario = new JPanel(new GridLayout(0, 2, 20, 16));
        formulario.setOpaque(false);

        campoFechaInicio = new CampoFechaReporte("Desde");
        campoFechaFin = new CampoFechaReporte("Hasta");

        comboSucursal = new CampoComboReporte("Sucursal");
        comboTipoMembresia = new CampoComboReporte("Plan / Membresía");
        comboEntrenador = new CampoComboReporte("Entrenador");
        comboAmenidad = new CampoComboReporte("Amenidad");

        comboSucursal.getCombo().addActionListener(e -> {
            if (usaTipoMembresia()) {
                cargarPlanes();
            }

            if (usaEntrenador()) {
                cargarEntrenadores();
            }
        });

        formulario.add(campoFechaInicio);
        formulario.add(campoFechaFin);

        if (usaSucursal()) {
            formulario.add(comboSucursal);
        }

        if (usaTipoMembresia()) {
            formulario.add(comboTipoMembresia);
        }

        if (usaEntrenador()) {
            formulario.add(comboEntrenador);
        }

        if (usaAmenidad()) {
            formulario.add(comboAmenidad);
        }

        card.add(lblTipo, BorderLayout.NORTH);
        card.add(formulario, BorderLayout.CENTER);

        centrador.add(card);

        return centrador;
    }

    /**
     * Crea los botones inferiores de la pantalla.
     *
     * @return panel de acciones.
     */
    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaSeleccionReportes());

        Boton btnLimpiar = crearBoton("Limpiar filtros", Boton.Variante.SECUNDARIO);
        btnLimpiar.addActionListener(e -> limpiarFiltros());

        Boton btnConfirmar = crearBoton("Confirmar reporte", Boton.Variante.PRIMARIO);
        btnConfirmar.setPreferredSize(new Dimension(240, 50));
        btnConfirmar.addActionListener(e -> generarReporte());

        JPanel izquierda = new JPanel();
        izquierda.setOpaque(false);
        izquierda.add(btnCancelar);

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.add(btnLimpiar);

        JPanel derecha = new JPanel();
        derecha.setOpaque(false);
        derecha.add(btnConfirmar);

        acciones.add(izquierda, BorderLayout.WEST);
        acciones.add(centro, BorderLayout.CENTER);
        acciones.add(derecha, BorderLayout.EAST);

        return acciones;
    }

    /**
     * Carga los datos iniciales de los combos visibles.
     */
    private void cargarDatosIniciales() {
        if (usaSucursal()) {
            cargarSucursales();
        }

        if (usaTipoMembresia()) {
            cargarPlanes();
        }

        if (usaEntrenador()) {
            cargarEntrenadores();
        }

        if (usaAmenidad()) {
            cargarAmenidades();
        }
    }

    /**
     * Carga las sucursales disponibles.
     */
    private void cargarSucursales() {
        try {
            comboSucursal.limpiarOpciones();
            comboSucursal.agregarOpcionTodos("Todas");

            List<SucursalDTO> sucursales = controlReportes.obtenerSucursales();

            for (SucursalDTO sucursal : sucursales) {
                if (sucursal != null) {
                    comboSucursal.agregarOpcion(
                            sucursal.getIdSucursal(),
                            sucursal.getNombre()
                    );
                }
            }

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar sucursales: " + ex.getMessage());
        }
    }

    /**
     * Carga los planes disponibles de acuerdo con la sucursal seleccionada.
     */
    private void cargarPlanes() {
        try {
            comboTipoMembresia.limpiarOpciones();
            comboTipoMembresia.agregarOpcionTodos("Todas");

            String idSucursal = comboSucursal != null ? comboSucursal.getValor() : "";

            if (idSucursal == null || idSucursal.isBlank()) {
                return;
            }

            List<PlanDTO> planes = controlReportes.obtenerPlanes(idSucursal);

            for (PlanDTO plan : planes) {
                if (plan != null) {
                    comboTipoMembresia.agregarOpcion(
                            plan.getIdPlan(),
                            plan.getNombre()
                    );
                }
            }

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar planes: " + ex.getMessage());
        }
    }

    /**
     * Carga los entrenadores disponibles de acuerdo con la sucursal
     * seleccionada.
     */
    private void cargarEntrenadores() {
        try {
            comboEntrenador.limpiarOpciones();
            comboEntrenador.agregarOpcionTodos("Todos");

            String idSucursal = comboSucursal != null ? comboSucursal.getValor() : "";

            if (idSucursal == null || idSucursal.isBlank()) {
                return;
            }

            List<EntrenadorDTO> entrenadores = controlReportes.obtenerEntrenadores(idSucursal);

            for (EntrenadorDTO entrenador : entrenadores) {
                if (entrenador != null) {
                    comboEntrenador.agregarOpcion(
                            entrenador.getIdEntrenador(),
                            entrenador.getNombre()
                    );
                }
            }

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar entrenadores: " + ex.getMessage());
        }
    }

    /**
     * Carga las amenidades disponibles.
     */
    private void cargarAmenidades() {
        try {
            comboAmenidad.limpiarOpciones();
            comboAmenidad.agregarOpcionTodos("Todas");

            List<AmenidadDTO> amenidades = controlReportes.obtenerAmenidades();

            for (AmenidadDTO amenidad : amenidades) {
                if (amenidad != null) {
                    comboAmenidad.agregarOpcion(
                            amenidad.getIdAmenidad(),
                            amenidad.getNombre()
                    );
                }
            }

        } catch (NegocioException ex) {
            Mensaje.error(this, "No fue posible cargar amenidades: " + ex.getMessage());
        }
    }

    /**
     * Genera el reporte usando los filtros seleccionados.
     */
    private void generarReporte() {
        try {
            FiltrosReporteDTO filtros = new FiltrosReporteDTO();

            filtros.setFechaInicio(parsearFecha(campoFechaInicio.getValor()));
            filtros.setFechaFin(parsearFecha(campoFechaFin.getValor()));

            if (usaSucursal()) {
                filtros.setSucursal(comboSucursal.getValor());
            }

            if (usaTipoMembresia()) {
                filtros.setTipoMembresia(comboTipoMembresia.getValor());
            }

            if (usaEntrenador()) {
                filtros.setEntrenador(comboEntrenador.getValor());
            }

            if (usaAmenidad()) {
                filtros.setAmenidad(comboAmenidad.getValor());
            }

            controlReportes.crearReporte(filtros);

        } catch (DateTimeParseException ex) {
            Mensaje.error(this, "Las fechas deben tener el formato yyyy-MM-dd.");
        }
    }

    /**
     * Limpia los filtros capturados.
     */
    private void limpiarFiltros() {
        campoFechaInicio.limpiar();
        campoFechaFin.limpiar();

        cargarDatosIniciales();
    }

    /**
     * Convierte una cadena de texto a LocalDate.
     *
     * @param texto fecha en formato yyyy-MM-dd.
     * @return fecha convertida o null si el texto está vacío.
     */
    private LocalDate parsearFecha(String texto) {
        if (texto == null || texto.isBlank()) {
            return null;
        }

        return LocalDate.parse(texto);
    }

    /**
     * Indica si el reporte usa filtro de sucursal.
     *
     * @return true si usa sucursal.
     */
    private boolean usaSucursal() {
        return tipoReporte == TipoReporteDTO.GENERAL
                || tipoReporte == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipoReporte == TipoReporteDTO.INGRESOS
                || tipoReporte == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    /**
     * Indica si el reporte usa filtro de tipo de membresía.
     *
     * @return true si usa tipo de membresía.
     */
    private boolean usaTipoMembresia() {
        return tipoReporte == TipoReporteDTO.GENERAL
                || tipoReporte == TipoReporteDTO.VENTAS_MEMBRESIAS
                || tipoReporte == TipoReporteDTO.INGRESOS
                || tipoReporte == TipoReporteDTO.POR_SUCURSAL;
    }

    /**
     * Indica si el reporte usa filtro de entrenador.
     *
     * @return true si usa entrenador.
     */
    private boolean usaEntrenador() {
        return tipoReporte == TipoReporteDTO.GENERAL
                || tipoReporte == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    /**
     * Indica si el reporte usa filtro de amenidad.
     *
     * @return true si usa amenidad.
     */
    private boolean usaAmenidad() {
        return tipoReporte == TipoReporteDTO.GENERAL
                || tipoReporte == TipoReporteDTO.INGRESOS
                || tipoReporte == TipoReporteDTO.POR_SUCURSAL
                || tipoReporte == TipoReporteDTO.DESEMPENO_ENTRENADORES;
    }

    /**
     * Obtiene el nombre visible del tipo de reporte seleccionado.
     *
     * @return nombre del reporte.
     */
    private String obtenerNombreReporte() {
        if (tipoReporte == null) {
            return "Reporte";
        }

        switch (tipoReporte) {
            case GENERAL:
                return "Reporte general";
            case VENTAS_MEMBRESIAS:
                return "Reporte de ventas de membresías";
            case INGRESOS:
                return "Reporte de ingresos";
            case POR_SUCURSAL:
                return "Reporte por sucursal";
            case DESEMPENO_ENTRENADORES:
                return "Reporte de desempeño de entrenadores";
            default:
                return "Reporte";
        }
    }
}
