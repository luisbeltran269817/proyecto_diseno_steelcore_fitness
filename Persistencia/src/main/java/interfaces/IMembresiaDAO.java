/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import dtos.MembresiaDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IMembresiaDAO {
    public void guardar(MembresiaDTO m);
    public MembresiaDTO buscarPorId(String id);
    public List<MembresiaDTO> obtenerPorCliente(String idCliente);
    public void actualizar(MembresiaDTO membresia);
}
