/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesAcceso;

import dominioAcceso.ClasePojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Contrato para definir las reglas de persistencia de las clases
 *
 * @author julian izaguirre
 */
public interface IClaseDAO {

    /**
     * Trae las clases disponibles segun sucursal y plan
     * 
     * @param idSucursal Sucursal a consultar
     * @param idPlan Plan del socio
     * @return Lista de clases encontradas
     * @throws PersistenciaException Si hay problemas con la conexion
     */
    List<ClasePojo> obtenerPorSucursalYPlan(String idSucursal, String idPlan)
            throws PersistenciaException;

    /**
     * Busca los datos de una clase especifica
     * 
     * @param idClase ID de la clase
     * @return Objeto de la clase o null
     * @throws PersistenciaException Si ocurre un error al buscar
     */
    ClasePojo buscarPorId(String idClase) throws PersistenciaException;

    /**
     * Mete a un socio en una clase si cumple los requisitos
     * 
     * @param idClase ID de la clase
     * @param idCliente ID del socio
     * @throws PersistenciaException Si ya no hay cupo o ya estaba registrado
     */
    void inscribirSocio(String idClase, String idCliente) throws PersistenciaException;
}