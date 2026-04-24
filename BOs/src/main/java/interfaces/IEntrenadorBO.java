/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.EntrenadorDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IEntrenadorBO {
    public List<EntrenadorDTO> obtenerTodos();
    public EntrenadorDTO buscarPorId(String id);
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal);
}
