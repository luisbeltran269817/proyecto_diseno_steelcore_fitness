/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dominios.EntrenadorPojo;
import dtos.EntrenadorDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IEntrenadorDAO {
    public List<EntrenadorDTO> obtenerTodos();
    public EntrenadorDTO buscarPorId(String id);
    public List<EntrenadorPojo> obtenerPorSucursal(String idSucursal);
}
