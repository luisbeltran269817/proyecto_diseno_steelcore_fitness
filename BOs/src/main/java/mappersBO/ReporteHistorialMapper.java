/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominios.ReporteHistorialPojo;
import dtosReportes.FiltrosReporteDTO;
import dtosReportes.MetricasReporteDTO;
import dtosReportes.ReporteDTO;
import dtosReportes.ReporteHistorialDTO;
import dtosReportes.TipoReporteDTO;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Mapper de negocio para el historial de reportes.
 *
 * Convierte reportes generados en objetos de historial para guardarlos en
 * persistencia, y convierte objetos de historial de dominio en DTOs para
 * mostrarlos en presentación.
 *
 * Este mapper no genera archivos PDF ni consulta base de datos. Solo transforma
 * datos entre capas.
 *
 * @author Noelia E.N.
 */
public class ReporteHistorialMapper {

    /**
     * Convierte un ReporteDTO generado en un ReporteHistorialPojo para guardar
     * un resumen del reporte en MongoDB.
     *
     * @param reporte reporte generado.
     * @return POJO de historial listo para persistencia.
     */
    public static ReporteHistorialPojo toPojo(ReporteDTO reporte) {
        if (reporte == null) {
            return null;
        }

        ReporteHistorialPojo pojo = new ReporteHistorialPojo();

        pojo.setIdReporte(UUID.randomUUID().toString());

        if (reporte.getTipoReporte() != null) {
            pojo.setTipoReporte(reporte.getTipoReporte().name());
        }

        pojo.setFechaGeneracion(LocalDateTime.now());

        FiltrosReporteDTO filtros = reporte.getFiltros();

        if (filtros != null) {
            pojo.setFechaInicio(filtros.getFechaInicio());
            pojo.setFechaFin(filtros.getFechaFin());
            pojo.setSucursal(filtros.getSucursal());
            pojo.setTipoMembresia(filtros.getTipoMembresia());
            pojo.setEntrenador(filtros.getEntrenador());
            pojo.setAmenidad(filtros.getAmenidad());
        }

        MetricasReporteDTO metricas = reporte.getMetricas();

        if (metricas != null) {
            pojo.setTotalIngresos(metricas.getTotalIngresos());
            pojo.setMembresiasVendidas(metricas.getMembresiasVendidas());
            pojo.setRenovaciones(metricas.getRenovaciones());
            pojo.setNuevosSocios(metricas.getNuevosSocios());

            pojo.setSucursalConMasVentas(metricas.getSucursalConMasVentas());
            pojo.setEntrenadorConMasClientes(metricas.getEntrenadorConMasClientes());
            pojo.setAmenidadMasSolicitada(metricas.getAmenidadMasSolicitada());
            pojo.setTipoMembresiaMasVendida(metricas.getTipoMembresiaMasVendida());
        }

        return pojo;
    }

    /**
     * Convierte un ReporteHistorialPojo en ReporteHistorialDTO para presentar
     * el historial en pantalla.
     *
     * @param pojo POJO de historial obtenido desde persistencia.
     * @return DTO de historial.
     */
    public static ReporteHistorialDTO toDTO(ReporteHistorialPojo pojo) {
        if (pojo == null) {
            return null;
        }

        ReporteHistorialDTO dto = new ReporteHistorialDTO();

        dto.setIdReporte(pojo.getIdReporte());

        if (pojo.getTipoReporte() != null && !pojo.getTipoReporte().isBlank()) {
            dto.setTipoReporte(TipoReporteDTO.valueOf(pojo.getTipoReporte()));
        }

        dto.setFechaGeneracion(pojo.getFechaGeneracion());
        dto.setFechaInicio(pojo.getFechaInicio());
        dto.setFechaFin(pojo.getFechaFin());

        dto.setSucursal(pojo.getSucursal());
        dto.setTipoMembresia(pojo.getTipoMembresia());
        dto.setEntrenador(pojo.getEntrenador());
        dto.setAmenidad(pojo.getAmenidad());

        dto.setTotalIngresos(pojo.getTotalIngresos());
        dto.setMembresiasVendidas(pojo.getMembresiasVendidas());
        dto.setRenovaciones(pojo.getRenovaciones());
        dto.setNuevosSocios(pojo.getNuevosSocios());

        dto.setSucursalConMasVentas(pojo.getSucursalConMasVentas());
        dto.setEntrenadorConMasClientes(pojo.getEntrenadorConMasClientes());
        dto.setAmenidadMasSolicitada(pojo.getAmenidadMasSolicitada());
        dto.setTipoMembresiaMasVendida(pojo.getTipoMembresiaMasVendida());

        return dto;
    }
}
