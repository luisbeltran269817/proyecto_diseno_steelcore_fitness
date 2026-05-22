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
import java.time.LocalDateTime;
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
    
    //metodos de caso rutina
    public List<RutinaPojo> obtenerRutinas(String correo) throws PersistenciaException;
    
    public void guardarRutina(String correo, RutinaPojo rutina) throws PersistenciaException;
    
    public boolean existeRutinaConNombre(String correo, String nombre) throws PersistenciaException;
    
    public void actualizarRutina(String correo, RutinaPojo rutina) throws PersistenciaException;
    
    public boolean borrarRutina(String correo, String nombre) throws PersistenciaException;
    
    public String obtenerIdSucursalMembresiaActiva(String correo) throws PersistenciaException;

    public void eliminarMembresiaActiva(String correo) throws PersistenciaException;

    /**
     * Consulta las citas de bienvenida embebidas en clientes usando filtros
     * para reportes.
     *
     * Este método se usa para generar reportes de desempeño de entrenadores,
     * tomando en cuenta las citas registradas dentro de los clientes.
     *
     * @param fechaInicio fecha inicial del periodo.
     * @param fechaFin fecha final del periodo.
     * @param idSucursal id de la sucursal, puede ser null o vacío.
     * @param idEntrenador id del entrenador, puede ser null o vacío.
     * @return lista de citas que cumplen con los filtros.
     * @throws PersistenciaException si ocurre un error al consultar la base de
     * datos.
     */
    public List<CitaPojo> consultarCitasParaReportes(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String idSucursal,
            String idEntrenador
    ) throws PersistenciaException;
}
