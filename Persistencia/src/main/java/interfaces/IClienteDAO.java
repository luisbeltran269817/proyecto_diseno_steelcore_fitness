/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ClienteDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IClienteDAO {
    public List<ClienteDTO> obtenerClientes();
    public ClienteDTO buscarPorCorreo(String correo);
    public void actualizar(ClienteDTO cliente);
    public List<VisitaDTO> obtenerHistorial(String idCliente);
}
