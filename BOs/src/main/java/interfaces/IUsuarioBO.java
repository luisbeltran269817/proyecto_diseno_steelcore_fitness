/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.UsuarioDTO;

/**
 *
 * @author Tungs
 */
public interface IUsuarioBO {
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo);
}
