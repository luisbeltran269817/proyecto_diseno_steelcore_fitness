/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.UsuarioDAO;
import dtos.UsuarioDTO;
import interfaces.IUsuarioBO;
import interfaces.IUsuarioDAO;

/**
 *
 * @author Tungs
 */
public class UsuarioBO implements IUsuarioBO {
    private final IUsuarioDAO usuarioDAO;

    public UsuarioBO() {
        this.usuarioDAO = new UsuarioDAO();
    }
    @Override
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        return usuarioDAO.obtenerUsuarioPorCorreo(correo);
    }
}
