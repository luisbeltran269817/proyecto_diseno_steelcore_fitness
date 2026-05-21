/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegociosReportes;

import DAOs.ReporteHistorialDAO;
import Excepciones.NegocioException;
import dominios.ReporteHistorialPojo;
import dtos.CitaDTO;
import dtos.EntrenadorDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;
import excepciones.PersistenciaException;
import infraestructuraReportes.GeneradorPDFReporte;
import infraestructuraReportes.IGeneradorPDFReporte;
import infraestructuraReportes.IImpresorReporte;
import infraestructuraReportes.ImpresorReportePDF;
import infraestructuraReportes.IEnviadorCorreoReporte;
import infraestructuraReportes.EnviadorCorreoReporteSMTP;
import interfaces.IClienteBO;
import interfaces.IEntrenadorBO;
import interfaces.IMembresiaBO;
import interfaces.IReporteHistorialDAO;
import interfaces.ISucursalBO;
import interfacesReportesBO.IAdministrarReportesBO;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mappersBO.ReporteHistorialMapper;
import objetosnegocios.ClienteBO;
import objetosnegocios.EntrenadorBO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.SucursalBO;

/**
 * Clase de negocio encargada de administrar la generación y manejo de reportes
 * comerciales y financieros del sistema.
 *
 * Esta clase implementa la lógica principal del caso de uso de Administración
 * Comercial y Reportes Financieros. Su responsabilidad es validar los datos
 * recibidos, generar reportes, coordinar la exportación en PDF, la impresión y
 * el envío por correo electrónico.
 *
 * En esta primera versión, el reporte se genera con datos provisionales para
 * permitir la integración con la capa de presentación y el subsistema. Más
 * adelante, esta clase deberá consultar los BOs o DAOs correspondientes para
 * obtener información real de pagos, membresías, sucursales, amenidades y
 * entrenadores.
 *
 * @author Noelia E.N.
 */
public class AdministrarReportesBO implements IAdministrarReportesBO {

    private final IGeneradorPDFReporte generadorPDF;
    private final IImpresorReporte impresorReporte;
    private final IEnviadorCorreoReporte enviadorCorreo;
    private final IMembresiaBO membresiaBO;
    private final ISucursalBO sucursalBO;
    private final IClienteBO clienteBO;
    private final IEntrenadorBO entrenadorBO;
    private final IReporteHistorialDAO reporteHistorialDAO;

    /**
     * Constructor de la clase de negocio de reportes.
     *
     * Inicializa los servicios de infraestructura necesarios para generar,
     * imprimir y enviar reportes en formato PDF. También inicializa los BOs
     * necesarios para consultar información real del sistema.
     */
    public AdministrarReportesBO() {
        this.generadorPDF = new GeneradorPDFReporte();
        this.impresorReporte = new ImpresorReportePDF();
        this.enviadorCorreo = new EnviadorCorreoReporteSMTP();

        this.membresiaBO = new MembresiaBO();
        this.sucursalBO = new SucursalBO();
        this.clienteBO = new ClienteBO();
        this.entrenadorBO = new EntrenadorBO();
        this.reporteHistorialDAO = new ReporteHistorialDAO();
    }

    /**
     * Crea un reporte comercial o financiero a partir del tipo de reporte y los
     * filtros seleccionados por el administrador.
     *
     * Este método valida los datos recibidos, delega el cálculo de métricas
     * según el tipo de reporte seleccionado y guarda el reporte generado en el
     * historial si contiene datos.
     *
     * @param tipoReporte tipo de reporte que se desea generar.
     * @param filtros filtros aplicados para generar el reporte.
     * @return reporte generado con métricas principales y estado de datos.
     * @throws NegocioException si el tipo de reporte es nulo, los filtros son
     * inválidos o no se puede consultar la información.
     */
    @Override
    public ReporteDTO crearReporte(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros) throws NegocioException {
        validarTipoReporte(tipoReporte);
        validarFiltros(filtros);

        MetricasReporteDTO metricas = calcularMetricasPorTipo(tipoReporte, filtros);

        ReporteDTO reporte = new ReporteDTO();
        reporte.setTipoReporte(tipoReporte);
        reporte.setFiltros(filtros);
        reporte.setMetricas(metricas);
        reporte.setTieneDatos(tieneDatos(metricas));

        if (reporte.isTieneDatos()) {
            guardarHistorialReporte(reporte);
        }

        return reporte;
    }

    /**
     * Exporta un reporte en formato PDF en la ruta seleccionada.
     *
     * Este método valida el reporte y el destino. Después delega la creación
     * física del archivo PDF al generador de PDF de infraestructura.
     *
     * @param reporte reporte que se desea exportar.
     * @param destino archivo destino donde se guardará el PDF.
     * @return archivo PDF generado.
     * @throws NegocioException si el reporte es inválido, el destino es nulo o
     * ocurre un error al generar el PDF.
     */
    @Override
    public File exportarPDF(ReporteDTO reporte, File destino) throws NegocioException {
        try {
            validarReporte(reporte);

            if (destino == null) {
                throw new NegocioException("Debe seleccionar una ubicación para guardar el PDF.");
            }

            return generadorPDF.generarEnRuta(reporte, destino);

        } catch (NegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new NegocioException("No fue posible exportar el reporte en PDF: " + ex.getMessage());
        }
    }

    /**
     * Imprime un reporte previamente generado.
     *
     * Este método genera primero un PDF temporal del reporte y después solicita
     * a la infraestructura de impresión que lo envíe a una impresora.
     *
     * @param reporte reporte que se desea imprimir.
     * @throws NegocioException si el reporte es inválido, si no se puede
     * generar el PDF temporal o si ocurre un error durante la impresión.
     */
    @Override
    public void imprimirPDF(ReporteDTO reporte) throws NegocioException {
        try {
            validarReporte(reporte);

            File pdfTemporal = generarPDFTemporal(reporte);
            impresorReporte.imprimir(pdfTemporal);

        } catch (NegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new NegocioException("No fue posible imprimir el reporte: " + ex.getMessage());
        }
    }

    /**
     * Envía un reporte por correo electrónico.
     *
     * Este método valida el reporte y el correo destino. Después genera un PDF
     * temporal del reporte y solicita a la infraestructura de correo que lo
     * envíe como archivo adjunto.
     *
     * @param reporte reporte que se desea enviar.
     * @param correoDestino correo electrónico del destinatario.
     * @throws NegocioException si el reporte es inválido, el correo no tiene
     * formato correcto, no se puede generar el PDF o falla el envío del correo.
     */
    @Override
    public void enviarPDF(ReporteDTO reporte, String correoDestino) throws NegocioException {
        try {
            validarReporte(reporte);
            validarCorreo(correoDestino);

            File pdfTemporal = generarPDFTemporal(reporte);
            enviadorCorreo.enviar(correoDestino, pdfTemporal);

        } catch (NegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new NegocioException("No fue posible enviar el reporte por correo: " + ex.getMessage());
        }
    }

    /**
     * Genera un archivo PDF temporal a partir de un reporte.
     *
     * Este método valida el reporte y delega la creación del PDF temporal al
     * generador de PDF de infraestructura.
     *
     * @param reporte reporte que se desea convertir en PDF temporal.
     * @return archivo temporal generado.
     * @throws NegocioException si el reporte es inválido o no se puede generar
     * el archivo temporal.
     */
    @Override
    public File generarPDFTemporal(ReporteDTO reporte) throws NegocioException {
        try {
            validarReporte(reporte);
            return generadorPDF.generarTemporal(reporte);

        } catch (NegocioException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new NegocioException("No fue posible generar el PDF temporal: " + ex.getMessage());
        }
    }

    /**
     * Valida que el tipo de reporte no sea nulo.
     *
     * @param tipoReporte tipo de reporte seleccionado.
     * @throws NegocioException si el tipo de reporte es nulo.
     */
    private void validarTipoReporte(TipoReporteDTO tipoReporte) throws NegocioException {
        if (tipoReporte == null) {
            throw new NegocioException("Debe seleccionar un tipo de reporte.");
        }
    }

    /**
     * Valida los filtros principales del reporte.
     *
     * La fecha de inicio y la fecha de fin son obligatorias para todos los
     * reportes. Además, la fecha final no puede ser menor que la fecha inicial.
     *
     * @param filtros filtros seleccionados por el administrador.
     * @throws NegocioException si los filtros son nulos o tienen fechas
     * inválidas.
     */
    private void validarFiltros(FiltrosReporteDTO filtros) throws NegocioException {
        if (filtros == null) {
            throw new NegocioException("Debe ingresar filtros para generar el reporte.");
        }

        if (filtros.getFechaInicio() == null) {
            throw new NegocioException("Debe ingresar la fecha de inicio.");
        }

        if (filtros.getFechaFin() == null) {
            throw new NegocioException("Debe ingresar la fecha de fin.");
        }

        if (filtros.getFechaFin().isBefore(filtros.getFechaInicio())) {
            throw new NegocioException("La fecha final no puede ser menor que la fecha inicial.");
        }
    }

    /**
     * Valida que exista un reporte generado y que contenga datos.
     *
     * @param reporte reporte que se desea validar.
     * @throws NegocioException si el reporte es nulo o no contiene datos.
     */
    private void validarReporte(ReporteDTO reporte) throws NegocioException {
        if (reporte == null) {
            throw new NegocioException("No hay un reporte generado.");
        }

        if (!reporte.isTieneDatos()) {
            throw new NegocioException("El reporte no contiene datos.");
        }
    }

    /**
     * Valida el formato básico de un correo electrónico.
     *
     * @param correo correo electrónico a validar.
     * @throws NegocioException si el correo es nulo, vacío o no cumple con el
     * formato esperado.
     */
    private void validarCorreo(String correo) throws NegocioException {
        if (correo == null || correo.isBlank()) {
            throw new NegocioException("Debe ingresar un correo electrónico.");
        }

        if (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new NegocioException("El correo electrónico no tiene un formato válido.");
        }
    }

    /**
     * Calcula métricas reales a partir de las membresías encontradas según los
     * filtros seleccionados.
     *
     * @param filtros filtros del reporte.
     * @return métricas calculadas.
     * @throws NegocioException si ocurre un error al consultar membresías.
     */
    private MetricasReporteDTO calcularMetricasDesdeMembresias(FiltrosReporteDTO filtros) throws NegocioException {
        List<MembresiaDTO> membresias = membresiaBO.consultarParaReportes(filtros);

        MetricasReporteDTO metricas = new MetricasReporteDTO();

        if (membresias == null || membresias.isEmpty()) {
            metricas.setTotalIngresos(0);
            metricas.setMembresiasVendidas(0);
            metricas.setRenovaciones(0);
            metricas.setNuevosSocios(0);
            metricas.setSucursalConMasVentas("Sin datos");
            metricas.setEntrenadorConMasClientes("Sin datos");
            metricas.setAmenidadMasSolicitada("Sin datos");
            metricas.setTipoMembresiaMasVendida("Sin datos");
            return metricas;
        }

        double totalIngresos = 0;
        int membresiasVendidas = membresias.size();

        Map<String, Integer> ventasPorSucursal = new HashMap<>();
        Map<String, Integer> ventasPorPlan = new HashMap<>();
        Map<String, Integer> ventasPorAmenidad = new HashMap<>();

        for (MembresiaDTO membresia : membresias) {
            if (membresia.getMontoPagado() != null) {
                totalIngresos += membresia.getMontoPagado();
            }

            sumarConteo(ventasPorSucursal, membresia.getIdSucursal());
            sumarConteo(ventasPorPlan, membresia.getIdPlan());

            if (membresia.getAmenidadesExtra() != null) {
                membresia.getAmenidadesExtra().forEach(amenidad -> {
                    if (amenidad != null) {
                        sumarConteo(ventasPorAmenidad, amenidad.getNombre());
                    }
                });
            }
        }

        metricas.setTotalIngresos(totalIngresos);
        metricas.setMembresiasVendidas(membresiasVendidas);

        /*
         * Por ahora se consideran todos como nuevos socios porque todavía no estamos
         * distinguiendo renovación contra primera compra
         */
        metricas.setNuevosSocios(membresiasVendidas);
        metricas.setRenovaciones(0);

        String idSucursalMasVentas = obtenerClaveConMayorConteo(ventasPorSucursal);
        String idPlanMasVendido = obtenerClaveConMayorConteo(ventasPorPlan);
        String amenidadMasSolicitada = obtenerClaveConMayorConteo(ventasPorAmenidad);

        metricas.setSucursalConMasVentas(obtenerNombreSucursal(idSucursalMasVentas));
        metricas.setTipoMembresiaMasVendida(obtenerNombrePlan(idPlanMasVendido));
        metricas.setAmenidadMasSolicitada(amenidadMasSolicitada);
        /*
         * Esta métrica se conectará después con citas, entrenadores o servicios
         */
        metricas.setEntrenadorConMasClientes("Sin datos");

        return metricas;
    }

    /**
     * Suma una ocurrencia a un mapa de conteo.
     *
     * @param mapa mapa donde se acumula el conteo.
     * @param clave clave que se desea contar.
     */
    private void sumarConteo(Map<String, Integer> mapa, String clave) {
        if (clave == null || clave.isBlank()) {
            return;
        }

        mapa.put(clave, mapa.getOrDefault(clave, 0) + 1);
    }

    /**
     * Obtiene la clave con mayor conteo dentro de un mapa.
     *
     * @param mapa mapa de conteos.
     * @return clave con mayor conteo o "Sin datos" si el mapa está vacío.
     */
    private String obtenerClaveConMayorConteo(Map<String, Integer> mapa) {
        if (mapa == null || mapa.isEmpty()) {
            return "Sin datos";
        }

        String mayorClave = null;
        int mayorConteo = -1;

        for (Map.Entry<String, Integer> entry : mapa.entrySet()) {
            if (entry.getValue() > mayorConteo) {
                mayorClave = entry.getKey();
                mayorConteo = entry.getValue();
            }
        }

        return mayorClave != null ? mayorClave : "Sin datos";
    }

    /**
     * Obtiene el nombre de una sucursal a partir de su id.
     *
     * Si no se encuentra la sucursal o ocurre un error al consultarla, se
     * regresa el mismo id para no detener la generación del reporte.
     *
     * @param idSucursal id de la sucursal.
     * @return nombre de la sucursal o el id recibido si no se encuentra.
     */
    private String obtenerNombreSucursal(String idSucursal) {
        if (idSucursal == null || idSucursal.isBlank() || idSucursal.equals("Sin datos")) {
            return "Sin datos";
        }

        try {
            SucursalDTO sucursal = sucursalBO.buscarPorId(idSucursal);

            if (sucursal != null && sucursal.getNombre() != null && !sucursal.getNombre().isBlank()) {
                return sucursal.getNombre();
            }

            return idSucursal;

        } catch (NegocioException ex) {
            return idSucursal;
        }
    }

    /**
     * Obtiene el nombre de un plan a partir de su id.
     *
     * Si no se encuentra el plan o ocurre un error al consultarlo, se regresa
     * el mismo id para no detener la generación del reporte.
     *
     * @param idPlan id del plan o membresía.
     * @return nombre del plan o el id recibido si no se encuentra.
     */
    private String obtenerNombrePlan(String idPlan) {
        if (idPlan == null || idPlan.isBlank() || idPlan.equals("Sin datos")) {
            return "Sin datos";
        }

        try {
            PlanDTO plan = sucursalBO.buscarPlanPorId(idPlan);

            if (plan != null && plan.getNombre() != null && !plan.getNombre().isBlank()) {
                return plan.getNombre();
            }

            return idPlan;

        } catch (NegocioException ex) {
            return idPlan;
        }
    }

    /**
     * Determina si las métricas contienen información suficiente para mostrar
     * un reporte.
     *
     * @param metricas métricas calculadas.
     * @return true si el reporte contiene datos; false en caso contrario.
     */
    private boolean tieneDatos(MetricasReporteDTO metricas) {
        if (metricas == null) {
            return false;
        }

        if (metricas.getMembresiasVendidas() > 0) {
            return true;
        }

        return metricas.getEntrenadorConMasClientes() != null
                && !metricas.getEntrenadorConMasClientes().isBlank()
                && !metricas.getEntrenadorConMasClientes().equalsIgnoreCase("Sin datos")
                && !metricas.getEntrenadorConMasClientes().equalsIgnoreCase("Seleccione una sucursal");
    }

    /**
     * Calcula las métricas correspondientes según el tipo de reporte
     * solicitado.
     *
     * @param tipoReporte tipo de reporte seleccionado.
     * @param filtros filtros aplicados al reporte.
     * @return métricas calculadas.
     * @throws NegocioException si el tipo de reporte no es válido o si ocurre
     * un error al consultar información.
     */
    private MetricasReporteDTO calcularMetricasPorTipo(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros)
            throws NegocioException {

        switch (tipoReporte) {
            case GENERAL:
                return calcularReporteGeneral(filtros);

            case VENTAS_MEMBRESIAS:
                return calcularReporteVentasMembresias(filtros);

            case INGRESOS:
                return calcularReporteIngresos(filtros);

            case POR_SUCURSAL:
                return calcularReportePorSucursal(filtros);

            case DESEMPENO_ENTRENADORES:
                return calcularReporteDesempenoEntrenadores(filtros);

            default:
                throw new NegocioException("Tipo de reporte no soportado.");
        }
    }

    /**
     * Calcula el reporte general.
     *
     * @param filtros filtros aplicados.
     * @return métricas del reporte general.
     * @throws NegocioException si ocurre un error al consultar información.
     */
    private MetricasReporteDTO calcularReporteGeneral(FiltrosReporteDTO filtros) throws NegocioException {
        return calcularMetricasDesdeMembresias(filtros);
    }

    /**
     * Calcula el reporte de ventas de membresías.
     *
     * @param filtros filtros aplicados.
     * @return métricas del reporte de ventas.
     * @throws NegocioException si ocurre un error al consultar información.
     */
    private MetricasReporteDTO calcularReporteVentasMembresias(FiltrosReporteDTO filtros) throws NegocioException {
        return calcularMetricasDesdeMembresias(filtros);
    }

    /**
     * Calcula el reporte de ingresos.
     *
     * @param filtros filtros aplicados.
     * @return métricas del reporte de ingresos.
     * @throws NegocioException si ocurre un error al consultar información.
     */
    private MetricasReporteDTO calcularReporteIngresos(FiltrosReporteDTO filtros) throws NegocioException {
        return calcularMetricasDesdeMembresias(filtros);
    }

    /**
     * Calcula el reporte por sucursal.
     *
     * @param filtros filtros aplicados.
     * @return métricas del reporte por sucursal.
     * @throws NegocioException si ocurre un error al consultar información.
     */
    private MetricasReporteDTO calcularReportePorSucursal(FiltrosReporteDTO filtros) throws NegocioException {
        return calcularMetricasDesdeMembresias(filtros);
    }

    /**
     * Calcula el reporte de desempeño de entrenadores usando citas de
     * bienvenida.
     *
     * Las citas están embebidas dentro de los clientes, por lo que se consultan
     * a través de ClienteBO. Se calcula el entrenador con más clientes
     * atendidos con base en las citas confirmadas o completadas dentro del
     * rango de fechas.
     *
     * @param filtros filtros aplicados al reporte.
     * @return métricas del reporte de entrenadores.
     * @throws NegocioException si ocurre un error al consultar citas o
     * entrenadores.
     */
    private MetricasReporteDTO calcularReporteDesempenoEntrenadores(FiltrosReporteDTO filtros) throws NegocioException {
        MetricasReporteDTO metricas = calcularMetricasDesdeMembresias(filtros);

        List<CitaDTO> citas = clienteBO.consultarCitasParaReportes(filtros);

        if (citas == null || citas.isEmpty()) {
            metricas.setEntrenadorConMasClientes("Sin datos");
            return metricas;
        }

        Map<String, Set<String>> clientesPorEntrenador = new HashMap<>();
        Map<String, Integer> citasPorEntrenador = new HashMap<>();

        for (CitaDTO cita : citas) {
            if (cita == null || cita.getIdEntrenador() == null || cita.getIdEntrenador().isBlank()) {
                continue;
            }

            String idEntrenador = cita.getIdEntrenador();

            sumarConteo(citasPorEntrenador, idEntrenador);

            clientesPorEntrenador.putIfAbsent(idEntrenador, new HashSet<>());

            if (cita.getIdCliente() != null && !cita.getIdCliente().isBlank()) {
                clientesPorEntrenador.get(idEntrenador).add(cita.getIdCliente());
            }
        }

        String idEntrenadorMasClientes = obtenerEntrenadorConMasClientes(clientesPorEntrenador);

        if (idEntrenadorMasClientes.equals("Sin datos")) {
            metricas.setEntrenadorConMasClientes("Sin datos");
            return metricas;
        }

        String nombreEntrenador = obtenerNombreEntrenador(idEntrenadorMasClientes);
        int clientesAtendidos = clientesPorEntrenador.get(idEntrenadorMasClientes).size();
        int citasImpartidas = citasPorEntrenador.getOrDefault(idEntrenadorMasClientes, 0);

        metricas.setEntrenadorConMasClientes(
                nombreEntrenador + " (" + clientesAtendidos + " clientes, " + citasImpartidas + " citas)"
        );

        return metricas;
    }

    /**
     * Obtiene el id del entrenador con mayor cantidad de clientes únicos.
     *
     * @param clientesPorEntrenador mapa con clientes únicos por entrenador.
     * @return id del entrenador con más clientes o "Sin datos".
     */
    private String obtenerEntrenadorConMasClientes(Map<String, Set<String>> clientesPorEntrenador) {
        if (clientesPorEntrenador == null || clientesPorEntrenador.isEmpty()) {
            return "Sin datos";
        }

        String idMayor = null;
        int mayorCantidad = -1;

        for (Map.Entry<String, Set<String>> entry : clientesPorEntrenador.entrySet()) {
            int cantidad = entry.getValue() != null ? entry.getValue().size() : 0;

            if (cantidad > mayorCantidad) {
                mayorCantidad = cantidad;
                idMayor = entry.getKey();
            }
        }

        return idMayor != null ? idMayor : "Sin datos";
    }

    /**
     * Obtiene el nombre de un entrenador a partir de su id.
     *
     * Si no se encuentra el entrenador o ocurre un error al consultarlo,
     * regresa el mismo id para no detener la generación del reporte.
     *
     * @param idEntrenador id del entrenador.
     * @return nombre del entrenador o el id recibido.
     */
    private String obtenerNombreEntrenador(String idEntrenador) {
        if (idEntrenador == null || idEntrenador.isBlank() || idEntrenador.equals("Sin datos")) {
            return "Sin datos";
        }

        try {
            EntrenadorDTO entrenador = entrenadorBO.buscarPorId(idEntrenador);

            if (entrenador != null && entrenador.getNombre() != null && !entrenador.getNombre().isBlank()) {
                return entrenador.getNombre();
            }

            return idEntrenador;

        } catch (NegocioException ex) {
            return idEntrenador;
        }
    }

    /**
     * Guarda un resumen del reporte generado en el historial.
     *
     * No guarda el archivo PDF, únicamente datos principales del reporte como
     * filtros, métricas, indicadores y fecha de generación.
     *
     * @param reporte reporte generado.
     * @throws NegocioException si ocurre un error al guardar el historial.
     */
    private void guardarHistorialReporte(ReporteDTO reporte) throws NegocioException {
        try {
            ReporteHistorialPojo historial = ReporteHistorialMapper.toPojo(reporte);

            if (historial == null) {
                throw new NegocioException("No fue posible preparar el historial del reporte.");
            }

            reporteHistorialDAO.guardar(historial);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible guardar el reporte en el historial.", ex);
        }
    }

    /**
     * Consulta los últimos reportes generados guardados en historial.
     *
     * @return lista de reportes históricos recientes.
     * @throws NegocioException si ocurre un error al consultar el historial.
     */
    @Override
    public List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException {
        try {
            List<ReporteHistorialPojo> pojos = reporteHistorialDAO.obtenerUltimos(10);
            List<ReporteHistorialDTO> dtos = new ArrayList<>();

            for (ReporteHistorialPojo pojo : pojos) {
                ReporteHistorialDTO dto = ReporteHistorialMapper.toDTO(pojo);

                if (dto != null) {
                    dtos.add(dto);
                }
            }

            return dtos;

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible consultar el historial de reportes.", ex);
        }
    }

    /**
     * Busca un reporte guardado en historial por su id.
     *
     * @param idReporte identificador del reporte histórico.
     * @return reporte histórico encontrado.
     * @throws NegocioException si el id es inválido, no se encuentra el reporte
     * o falla la consulta.
     */
    @Override
    public ReporteHistorialDTO buscarReporteHistorialPorId(String idReporte) throws NegocioException {
        try {
            if (idReporte == null || idReporte.isBlank()) {
                throw new NegocioException("Debe seleccionar un reporte válido.");
            }

            ReporteHistorialPojo pojo = reporteHistorialDAO.buscarPorId(idReporte);

            if (pojo == null) {
                throw new NegocioException("No se encontró el reporte solicitado.");
            }

            return ReporteHistorialMapper.toDTO(pojo);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No fue posible buscar el reporte en historial.", ex);
        }
    }

    /**
     * Obtiene un resumen mensual real usando el mes actual como periodo.
     *
     * Este método no guarda historial, porque solo se usa para llenar el
     * resumen rápido de la pantalla principal de reportes.
     *
     * @return métricas del mes actual.
     * @throws NegocioException si ocurre un error al calcular el resumen.
     */
    @Override
    public MetricasReporteDTO obtenerResumenMensual() throws NegocioException {
        java.time.LocalDate hoy = java.time.LocalDate.now();

        FiltrosReporteDTO filtros = new FiltrosReporteDTO();
        filtros.setFechaInicio(hoy.withDayOfMonth(1));
        filtros.setFechaFin(hoy.withDayOfMonth(hoy.lengthOfMonth()));

        return calcularReporteGeneral(filtros);
    }

}
