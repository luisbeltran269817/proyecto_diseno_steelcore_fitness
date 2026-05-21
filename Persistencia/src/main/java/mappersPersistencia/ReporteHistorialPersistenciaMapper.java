/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersPersistencia;

import dominios.ReporteHistorialPojo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.bson.Document;

/**
 * Mapper de persistencia para reportes guardados en historial.
 *
 * Convierte objetos ReporteHistorialPojo a Document de MongoDB y viceversa.
 * Esta clase permite guardar y recuperar reportes generados sin almacenar el
 * archivo PDF, únicamente la información resumida del reporte.
 *
 * @author Noelia E.N.
 */
public class ReporteHistorialPersistenciaMapper {

    /**
     * Convierte un ReporteHistorialPojo a Document.
     *
     * @param pojo reporte historial de dominio.
     * @return documento de MongoDB.
     */
    public static Document toDocument(ReporteHistorialPojo pojo) {
        if (pojo == null) {
            return null;
        }

        Document doc = new Document();

        doc.append("_id", pojo.getIdReporte());
        doc.append("tipoReporte", pojo.getTipoReporte());

        if (pojo.getFechaGeneracion() != null) {
            doc.append("fechaGeneracion", pojo.getFechaGeneracion().toString());
        }

        if (pojo.getFechaInicio() != null) {
            doc.append("fechaInicio", pojo.getFechaInicio().toString());
        }

        if (pojo.getFechaFin() != null) {
            doc.append("fechaFin", pojo.getFechaFin().toString());
        }

        doc.append("sucursal", pojo.getSucursal());
        doc.append("tipoMembresia", pojo.getTipoMembresia());
        doc.append("entrenador", pojo.getEntrenador());
        doc.append("amenidad", pojo.getAmenidad());

        doc.append("totalIngresos", pojo.getTotalIngresos());
        doc.append("membresiasVendidas", pojo.getMembresiasVendidas());
        doc.append("renovaciones", pojo.getRenovaciones());
        doc.append("nuevosSocios", pojo.getNuevosSocios());

        doc.append("sucursalConMasVentas", pojo.getSucursalConMasVentas());
        doc.append("entrenadorConMasClientes", pojo.getEntrenadorConMasClientes());
        doc.append("amenidadMasSolicitada", pojo.getAmenidadMasSolicitada());
        doc.append("tipoMembresiaMasVendida", pojo.getTipoMembresiaMasVendida());

        return doc;
    }

    /**
     * Convierte un Document de MongoDB a ReporteHistorialPojo.
     *
     * @param doc documento obtenido de MongoDB.
     * @return reporte historial de dominio.
     */
    public static ReporteHistorialPojo toPojo(Document doc) {
        if (doc == null) {
            return null;
        }

        ReporteHistorialPojo pojo = new ReporteHistorialPojo();

        pojo.setIdReporte(doc.getString("_id"));
        pojo.setTipoReporte(doc.getString("tipoReporte"));

        String fechaGeneracion = doc.getString("fechaGeneracion");
        if (fechaGeneracion != null) {
            pojo.setFechaGeneracion(LocalDateTime.parse(fechaGeneracion));
        }

        String fechaInicio = doc.getString("fechaInicio");
        if (fechaInicio != null) {
            pojo.setFechaInicio(LocalDate.parse(fechaInicio));
        }

        String fechaFin = doc.getString("fechaFin");
        if (fechaFin != null) {
            pojo.setFechaFin(LocalDate.parse(fechaFin));
        }

        pojo.setSucursal(doc.getString("sucursal"));
        pojo.setTipoMembresia(doc.getString("tipoMembresia"));
        pojo.setEntrenador(doc.getString("entrenador"));
        pojo.setAmenidad(doc.getString("amenidad"));

        Double totalIngresos = doc.getDouble("totalIngresos");
        pojo.setTotalIngresos(totalIngresos != null ? totalIngresos : 0);

        Integer membresiasVendidas = doc.getInteger("membresiasVendidas");
        pojo.setMembresiasVendidas(membresiasVendidas != null ? membresiasVendidas : 0);

        Integer renovaciones = doc.getInteger("renovaciones");
        pojo.setRenovaciones(renovaciones != null ? renovaciones : 0);

        Integer nuevosSocios = doc.getInteger("nuevosSocios");
        pojo.setNuevosSocios(nuevosSocios != null ? nuevosSocios : 0);

        pojo.setSucursalConMasVentas(doc.getString("sucursalConMasVentas"));
        pojo.setEntrenadorConMasClientes(doc.getString("entrenadorConMasClientes"));
        pojo.setAmenidadMasSolicitada(doc.getString("amenidadMasSolicitada"));
        pojo.setTipoMembresiaMasVendida(doc.getString("tipoMembresiaMasVendida"));

        return pojo;
    }
}
