/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.ClienteDAO;
import DAOs.MembresiaDAO;
import Excepciones.NegocioException;
import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import dominios.MembresiaPojo;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import interfaces.IMembresiaDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersBO.CitaMapper;
import mappersBO.ClienteMapper;
import mappersBO.MembresiaMapper;

/**
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO {
    private final IClienteDAO clienteDAO;
    private final IMembresiaDAO membresiaDAO;
    private static final Logger logger = Logger.getLogger(ClienteBO.class.getName());
    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();
        this.membresiaDAO= new MembresiaDAO();
    }
    
    @Override
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        try {
            List<ClientePojo> pojos= clienteDAO.obtenerClientes();
            List<ClienteDTO> clientes = new ArrayList<>();
            for (ClientePojo pojo : pojos) {
                ClienteDTO dto= ClienteMapper.toDTO(pojo);
                clientes.add(dto);
            }
            logger.log(Level.INFO, "Los clientes se obtuvieron correctamente");
            return clientes;
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener clientes", e);
            throw new NegocioException( "Error al obtener clientes");
        }
    }

    @Override
    public ClienteDTO buscarPorCorreo(String correo) throws NegocioException {
        try {
            ClientePojo pojo= clienteDAO.buscarPorCorreo(correo);
            logger.log(Level.INFO, "Cliente encontrado correctamente");
            return ClienteMapper.toDTO(pojo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al buscar cliente", e);
            throw new NegocioException( "Error al buscar cliente");
        }
    }

    @Override
    public void actualizar(ClienteDTO cliente) throws NegocioException {
        try {
            if (cliente == null) {
                throw new NegocioException( "El cliente no puede ser null");
            }
            ClientePojo pojo= ClienteMapper.toPojo(cliente);
            clienteDAO.actualizar(pojo);
            logger.log(Level.INFO, "Cliente actualizado correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al actualizar cliente", e);
            throw new NegocioException( "Error al actualizar cliente");
        }
    }

    @Override
    public MembresiaDTO obtenerMembresiaActiva(String correo) throws NegocioException {
        try {

            MembresiaActivaPojo snapshot = clienteDAO.obtenerMembresiaActiva(correo);
            if (snapshot == null) {
                return null;
            }
            MembresiaPojo pojo =membresiaDAO.buscarPorId(snapshot.getIdMembresia());
            if (pojo == null) {
                return null;
            }
            logger.log(Level.INFO,"Membresía activa encontrada");
            return MembresiaMapper.toDTO(pojo);

        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE,"Error al obtener membresía activa", e);
            throw new NegocioException(
                    "Error al obtener membresía activa");
        }
    }

    @Override
    public void guardarCitaBienvenida(String correo, CitaDTO cita) throws NegocioException {
        try {
            if (correo == null || correo.isBlank()) {
                throw new NegocioException( "El correo no puede estar vacío");
            }
            if (cita == null) {throw new NegocioException( "La cita no puede ser null");
            }
            CitaPojo pojo= CitaMapper.toPojo(cita);
            clienteDAO.guardarCitaBienvenida(correo, pojo);
            logger.log(Level.INFO, "Cita guardada correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al guardar cita", e);
            throw new NegocioException( "Error al guardar cita");
        }
    }
    
    @Override
    public void eliminarMembresiaActiva(String correo) throws NegocioException {
        try {
            clienteDAO.eliminarMembresiaActiva(correo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al eliminar la meembresía activa",e);
            throw new NegocioException("Error al eliminar membresía activa");
        }
    }
    
    
}
