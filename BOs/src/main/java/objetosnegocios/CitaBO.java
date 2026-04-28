/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AlmacenComprarMembresiaMock;
import DAOs.CitaDAO;
import dtos.CitaDTO;
import interfaces.ICitaBO;
import interfaces.ICitaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class CitaBO implements ICitaBO {
    private final ICitaDAO citaDAO;

    public CitaBO() {
        this.citaDAO = new CitaDAO();
    }
    @Override
    public void guardar(CitaDTO cita) {
        citaDAO.guardar(cita);
    }
    @Override
    public CitaDTO buscarPorId(String id) {
        return citaDAO.buscarPorId(id);
    }
    @Override
    public List<CitaDTO> obtenerPorCliente(String idCliente) {
        return citaDAO.obtenerPorCliente(idCliente);
    }
}
