/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dominios.MembresiaPojo;
import excepciones.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IMembresiaDAO {

    public void guardar(MembresiaPojo membresia) throws PersistenciaException;

    public MembresiaPojo buscarPorId(String idMembresia) throws PersistenciaException;

    public MembresiaPojo buscarPorCodigoQR(String codigoQR) throws PersistenciaException;

    public void actualizar(MembresiaPojo membresia) throws PersistenciaException;

    /**
     * Consulta membresías usando filtros básicos para reportes.
     *
     * @param fechaInicio fecha inicial del periodo.
     * @param fechaFin fecha final del periodo.
     * @param idSucursal id de la sucursal, puede ser null o vacío.
     * @param idPlan id del plan o membresía, puede ser null o vacío.
     * @param idAmenidad id de la amenidad, puede ser null o vacío.
     * @return lista de membresías que cumplen con los filtros.
     * @throws PersistenciaException si ocurre un error al consultar la base de
     * datos.
     */
    public List<MembresiaPojo> consultarPorFiltrosReporte(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String idSucursal,
            String idPlan,
            String idAmenidad
    ) throws PersistenciaException;
}
