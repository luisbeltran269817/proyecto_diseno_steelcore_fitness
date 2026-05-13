/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.PlanPojo;
import dominios.SucursalPojo;
import dtos.SucursalDTO;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface ISucursalDAO {
    public List<SucursalPojo>obtenerSucursales() throws PersistenciaException;
    public SucursalPojo buscarPorId( String idSucursal) throws PersistenciaException;
    public List<PlanPojo>obtenerPlanesSucursal(String idSucursal) throws PersistenciaException;
    public PlanPojo buscarPlanPorId(String idPlan)throws PersistenciaException;
}
