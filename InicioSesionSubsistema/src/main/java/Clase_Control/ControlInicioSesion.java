/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
    
    /**
     * Valida credenciales usando BCrypt.
     *
     * @param correo correo del usuario
     * @param contraseña contraseña en texto plano ingresada por el usuario
     * @return UsuarioDTO si las credenciales son correctas
     * @throws Exception si el correo no existe o la contraseña es incorrecta
     */
    public UsuarioDTO iniciarSesion(String correo, String contraseña) throws Exception {
        ClienteDTO cliente = clienteBO.buscarPorCorreo(correo);
        if (cliente == null) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
 
        BCrypt.Result resultado = BCrypt.verifyer()
                .verify(contraseña.toCharArray(), cliente.getContraseña());
 
        if (!resultado.verified) {
            throw new Exception("Correo o contraseña incorrectos.");
        }
 
        return cliente;
    }
}
