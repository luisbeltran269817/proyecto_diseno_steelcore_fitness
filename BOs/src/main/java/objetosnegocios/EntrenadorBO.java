/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.EntrenadorDAO;
import dtos.EntrenadorDTO;
import interfaces.IEntrenadorBO;
import interfaces.IEntrenadorDAO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class EntrenadorBO implements IEntrenadorBO {
    private final IEntrenadorDAO entrenadorDAO;

    public EntrenadorBO() {
        this.entrenadorDAO = new EntrenadorDAO();
    }
    
    @Override
    public List<EntrenadorDTO> obtenerTodos() {
        return entrenadorDAO.obtenerTodos();
    }
    
    @Override
    public EntrenadorDTO buscarPorId(String id) {
        return entrenadorDAO.buscarPorId(id);
    }

    @Override
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal) {
        return entrenadorDAO.obtenerPorSucursal(idSucursal);
    }
    
}
