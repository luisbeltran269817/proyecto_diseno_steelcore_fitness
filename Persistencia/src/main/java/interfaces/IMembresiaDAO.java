/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dominios.MembresiaPojo;
import excepciones.PersistenciaException;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IMembresiaDAO {
    public void guardar(MembresiaPojo membresia) throws PersistenciaException;
    public MembresiaPojo buscarPorId(String idMembresia) throws PersistenciaException;
    public MembresiaPojo buscarPorCodigoQR(String codigoQR) throws PersistenciaException;
    public void actualizar(MembresiaPojo membresia) throws PersistenciaException;
}
