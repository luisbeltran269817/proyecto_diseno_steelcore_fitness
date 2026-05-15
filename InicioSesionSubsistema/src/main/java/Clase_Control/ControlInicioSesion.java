/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import dtos.ClienteDTO;
import dtos.UsuarioDTO;
import objetosnegocios.ClienteBO;

/**
 *
 * @author julian izaguirre
 */
public class ControlInicioSesion {
    private final ClienteBO clienteBO;
    public ControlInicioSesion() {
        this.clienteBO = new ClienteBO();
    }
    
    public UsuarioDTO iniciarSesion(String correo, String contraseña) throws Exception {
        ClienteDTO cliente = clienteBO.buscarPorCorreo(correo);
        if (cliente == null) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
        if (!cliente.getContraseña().equals(contraseña)) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
        return cliente;
    }
}
