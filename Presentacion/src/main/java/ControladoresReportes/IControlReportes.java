package ControladoresReportes;

import Excepciones.NegocioException;
import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.awt.Component;
import java.util.List;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;

/**
 *
 * @author Noelia E.N.
 */
public interface IControlReportes {

    void iniciarAdministrarReportes();

    void irAPantallaPrincipalReportes();

    void irAPantallaPrincipalSistema();

    void irAPantallaSeleccionReportes();

    void irAPantallaFiltros();

    void irAPantallaReporte();

    void irAPantallaOpcionesReporte();

    void irAPantallaImprimir();

    void irAPantallaExportar();

    void irAPantallaEnviarPorCorreo();

    void notificarTipoSeleccionado(TipoReporteDTO tipoReporte);

    void crearReporte(FiltrosReporteDTO filtros);

    boolean validarDatos(FiltrosReporteDTO filtros);

    void exportar();

    void imprimir();

    void enviar(String correo);

    boolean validarCorreo(String correo);

    void cerrarSesion();

    List<SucursalDTO> obtenerSucursales() throws NegocioException;

    List<PlanDTO> obtenerPlanes(String idSucursal) throws NegocioException;

    List<AmenidadDTO> obtenerAmenidades() throws NegocioException;

    List<EntrenadorDTO> obtenerEntrenadores(String idSucursal) throws NegocioException;

    TipoReporteDTO getTipoReporteSeleccionado();

    FiltrosReporteDTO getFiltrosReporte();

    ReporteDTO getReporteGenerado();

    /**
     * Abre la pantalla donde se consultan los últimos reportes generados.
     */
    void irAPantallaConsultarReportes();

    List<ReporteHistorialDTO> consultarUltimosReportes() throws NegocioException;

    ReporteHistorialDTO buscarReporteHistorialPorId(String idReporte) throws NegocioException;
    
    MetricasReporteDTO obtenerResumenMensual() throws NegocioException;
}
