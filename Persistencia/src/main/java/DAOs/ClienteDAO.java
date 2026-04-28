/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.ClienteDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import interfaces.IClienteDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public class ClienteDAO implements IClienteDAO {
    private final AlmacenComprarMembresiaMock almacen;

    public ClienteDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }

    @Override
    public List<ClienteDTO> obtenerClientes() {
        List<ClienteDTO> clientes = new ArrayList<>();

        for (UsuarioDTO u : almacen.getUsuarios().values()) {
            if (u instanceof ClienteDTO) {
                clientes.add((ClienteDTO) u);
            }
        }

        return clientes;
    }
    @Override
    public ClienteDTO buscarPorCorreo(String correo) {
        UsuarioDTO u = almacen.getUsuarios().get(correo);
        if (u instanceof ClienteDTO) {
            return (ClienteDTO) u;
        }
        return null;
    }
    @Override
    public void actualizar(ClienteDTO cliente) {
        almacen.getUsuarios().put(cliente.getCorreo(), cliente);
    }
    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        List<VisitaDTO> lista = almacen.getVisitas().get(idCliente);
        if (lista == null) {
            return new ArrayList<>();
        }
        return lista;
    }
}
