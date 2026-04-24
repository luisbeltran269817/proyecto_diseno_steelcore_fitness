/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.UsuarioDTO;
import interfaces.IUsuarioBO;

/**
 *
 * @author Tungs
 */
public class UsuarioBO implements IUsuarioBO {
    private final AlmacenComprarMembresiaMock almacen;

    public UsuarioBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
    @Override
    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        return almacen.getUsuarios().get(correo);
    }
}
