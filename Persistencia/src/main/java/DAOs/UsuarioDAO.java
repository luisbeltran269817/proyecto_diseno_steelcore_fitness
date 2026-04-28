/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.UsuarioDTO;
import interfaces.IUsuarioDAO;

/**
 *
 * @author luiscarlosbeltran
 */
public class UsuarioDAO implements IUsuarioDAO{
    private final AlmacenComprarMembresiaMock almacen;

    public UsuarioDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    
    @Override
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        return almacen.getUsuarios().get(correo);
    }
}
