/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import dtos.EntrenadorDTO;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IEntrenadorDAO {
    public EntrenadorPojo buscarPorId(String idEntrenador) throws PersistenciaException;
    public List<EntrenadorPojo> obtenerPorSucursal(String idSucursal) throws PersistenciaException;
    public List<HorarioPojo>obtenerHorariosEntrenador(String idEntrenador)throws PersistenciaException;
}
