/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AlmacenComprarMembresiaMock;
import DAOs.AmenidadDAO;
import dtos.AmenidadDTO;
import interfaces.IAmenidadBO;
import interfaces.IAmenidadDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tungs
 */
public class AmenidadBO implements IAmenidadBO {
    private final IAmenidadDAO amenidadDAO;

    public AmenidadBO() {
        this.amenidadDAO = new AmenidadDAO();
    }
    
    @Override
    public List<AmenidadDTO> obtenerTodas() {
        return amenidadDAO.obtenerTodas();
    }
    @Override
    public AmenidadDTO buscarPorId(String id) {
        return amenidadDAO.buscarPorId(id);
    }
    @Override
    public List<AmenidadDTO> buscarPorIds(List<String> ids) {
        return amenidadDAO.buscarPorIds(ids);
    }
}
