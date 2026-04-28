/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AlmacenComprarMembresiaMock;
import DAOs.ClienteDAO;
import dtos.ClienteDTO;
import java.time.LocalDate;
import java.util.UUID;
import dtos.InicioSesionDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO {
    private final IClienteDAO clienteDAO;

    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public List<ClienteDTO> obtenerClientes() {
        return clienteDAO.obtenerClientes();
    }
    
    @Override
    public ClienteDTO buscarPorCorreo(String correo) {
        return clienteDAO.buscarPorCorreo(correo);
    }
    
    @Override
    public void actualizar(ClienteDTO cliente) {
        clienteDAO.actualizar(cliente);
    }
    
    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        return clienteDAO.obtenerHistorial(idCliente);
    }
}
