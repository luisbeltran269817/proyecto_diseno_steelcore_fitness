/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.ReporteHistorialPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz DAO para el historial de reportes generados.
 *
 * Define las operaciones de persistencia necesarias para guardar y consultar
 * reportes generados previamente.
 *
 * @author Noelia E.N.
 */
public interface IReporteHistorialDAO {

    /**
     * Guarda un reporte generado en el historial.
     *
     * @param reporte reporte a guardar.
     * @throws PersistenciaException si ocurre un error al guardar.
     */
    void guardar(ReporteHistorialPojo reporte) throws PersistenciaException;

    /**
     * Obtiene los últimos reportes generados.
     *
     * @param limite cantidad máxima de reportes a recuperar.
     * @return lista de reportes recientes.
     * @throws PersistenciaException si ocurre un error al consultar.
     */
    List<ReporteHistorialPojo> obtenerUltimos(int limite) throws PersistenciaException;

    /**
     * Busca un reporte histórico por su id.
     *
     * @param idReporte id del reporte.
     * @return reporte encontrado o null si no existe.
     * @throws PersistenciaException si ocurre un error al buscar.
     */
    ReporteHistorialPojo buscarPorId(String idReporte) throws PersistenciaException;
}
