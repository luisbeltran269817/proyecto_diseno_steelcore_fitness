/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.AmenidadDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IAmenidadDAO {
    public List<AmenidadDTO> obtenerTodas();
    public AmenidadDTO buscarPorId(String id);
    public List<AmenidadDTO> buscarPorIds(List<String> ids);
}
