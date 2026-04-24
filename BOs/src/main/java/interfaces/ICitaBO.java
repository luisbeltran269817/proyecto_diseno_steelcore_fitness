/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.CitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface ICitaBO {
    public void guardar(CitaDTO cita);
    public CitaDTO buscarPorId(String id);
    public List<CitaDTO> obtenerPorCliente(String idCliente);
}
