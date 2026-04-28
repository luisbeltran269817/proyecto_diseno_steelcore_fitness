/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AlmacenComprarMembresiaMock;
import DAOs.MembresiaDAO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import interfaces.IMembresiaBO;
import interfaces.IMembresiaDAO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaBO implements IMembresiaBO {
    private final IMembresiaDAO membresiaDAO;

    public MembresiaBO() {
        this.membresiaDAO = new MembresiaDAO();
    }
    
    @Override
    public void guardar(MembresiaDTO m) {
        membresiaDAO.guardar(m);
    }
    
    @Override
    public MembresiaDTO buscarPorId(String id) {
        return membresiaDAO.buscarPorId(id);
    }
    
    @Override
    public List<MembresiaDTO> obtenerPorCliente(String idCliente) {
        return membresiaDAO.obtenerPorCliente(idCliente);
    }
    
    @Override
    public void actualizar(MembresiaDTO membresia) {
        membresiaDAO.actualizar(membresia);
    }
}
