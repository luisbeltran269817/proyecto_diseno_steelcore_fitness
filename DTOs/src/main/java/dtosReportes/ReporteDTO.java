/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosReportes;

/**
 * Clase DTO para un reporte
 * @author Noelia E.N
 */
public class ReporteDTO {

    private TipoReporteDTO tipoReporte;
    private FiltrosReporteDTO filtros;
    private MetricasReporteDTO metricas;
    private boolean tieneDatos;

    public ReporteDTO() {
    }

    public ReporteDTO(TipoReporteDTO tipoReporte, FiltrosReporteDTO filtros,
                      MetricasReporteDTO metricas, boolean tieneDatos) {
        this.tipoReporte = tipoReporte;
        this.filtros = filtros;
        this.metricas = metricas;
        this.tieneDatos = tieneDatos;
    }

    public TipoReporteDTO getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporteDTO tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public FiltrosReporteDTO getFiltros() {
        return filtros;
    }

    public void setFiltros(FiltrosReporteDTO filtros) {
        this.filtros = filtros;
    }

    public MetricasReporteDTO getMetricas() {
        return metricas;
    }

    public void setMetricas(MetricasReporteDTO metricas) {
        this.metricas = metricas;
    }

    public boolean isTieneDatos() {
        return tieneDatos;
    }

    public void setTieneDatos(boolean tieneDatos) {
        this.tieneDatos = tieneDatos;
    }
}
