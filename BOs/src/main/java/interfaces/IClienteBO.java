/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.RutinaDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IClienteBO {
    public List<ClienteDTO> obtenerClientes() throws NegocioException;
    public ClienteDTO buscarPorCorreo(String correo) throws NegocioException;
    public void actualizar(ClienteDTO cliente) throws NegocioException;
    public MembresiaDTO obtenerMembresiaActiva(String correo) throws NegocioException;
    public void guardarCitaBienvenida(String correo, CitaDTO cita)throws NegocioException;
    public void eliminarMembresiaActiva(String correo) throws NegocioException;
    
    //metodos rutinass
    public List<RutinaDTO> obtenerRutinas(String correo) throws NegocioException;
    public RutinaDTO guardarRutina(String correo, RutinaDTO rutinaDTO) throws NegocioException;
    public boolean existeRutinaConNombre(String correo, String nombre) throws NegocioException;
    public RutinaDTO actualizarRutina(String correo, RutinaDTO rutinaDTO) throws NegocioException;
    public boolean borrarRutina(String correo, String nombre) throws NegocioException;
    public String obtenerIdSucursalMembresiaActiva(String correo) throws NegocioException;
    public RutinaDTO obtenerPlantilla(String nombre) throws NegocioException;

}
