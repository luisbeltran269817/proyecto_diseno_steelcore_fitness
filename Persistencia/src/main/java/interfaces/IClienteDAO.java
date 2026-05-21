/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import dominios.RutinaPojo;
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
    
    //metodos de caso rutina
    public List<RutinaPojo> obtenerRutinas(String correo) throws PersistenciaException;
    
    public void guardarRutina(String correo, RutinaPojo rutina) throws PersistenciaException;
    
    public boolean existeRutinaConNombre(String correo, String nombre) throws PersistenciaException;
    
    public void actualizarRutina(String correo, RutinaPojo rutina) throws PersistenciaException;
    
    public boolean borrarRutina(String correo, String nombre) throws PersistenciaException;
    
    public String obtenerIdSucursalMembresiaActiva(String correo) throws PersistenciaException;
}
