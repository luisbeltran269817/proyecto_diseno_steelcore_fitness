/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IClienteDAO {
    List<ClientePojo> obtenerClientes() throws PersistenciaException;

    ClientePojo buscarPorCorreo(String correo) throws PersistenciaException;

    void actualizar(ClientePojo cliente) throws PersistenciaException;

    MembresiaActivaPojo obtenerMembresiaActiva(String correo) throws PersistenciaException;

    void guardarCitaBienvenida(String correo, CitaPojo cita) throws PersistenciaException;
    public void eliminarMembresiaActiva(String correo)throws PersistenciaException;
}
