/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.UsuarioDTO;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IUsuarioDAO {
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo);
}
