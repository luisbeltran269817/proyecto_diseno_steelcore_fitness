/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.SucursalDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface ISucursalDAO {
    public List<SucursalDTO> obtenerTodas();
    public SucursalDTO buscarPorId(String id);
}
