/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesAcceso;

import dominioAcceso.VisitaAccesoPojo;
import excepciones.PersistenciaException;

/**
 * Interfaz para las operaciones de base de datos del control de visitas
 *
 * @author julian izaguirre
 */
public interface IVisitaAccesoDAO {

    /**
     * Guarda el registro de una visita nueva
     * 
     * @param visita Objeto visita a guardar
     * @return La visita recien creada
     * @throws PersistenciaException Si falla la operacion
     */
    VisitaAccesoPojo guardar(VisitaAccesoPojo visita) throws PersistenciaException;

    /**
     * Actualiza el servicio que solicito la visita
     *
     * @param idVisita ID de la visita a modificar
     * @param tipoServicio Categoria del servicio nuevo
     * @param idRecursoAsignado Recurso especifico asignado
     * @throws PersistenciaException Si falla la base de datos
     */
    void actualizarServicio(String idVisita, String tipoServicio, String idRecursoAsignado) throws PersistenciaException;
}