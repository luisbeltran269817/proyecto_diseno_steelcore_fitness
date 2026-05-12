/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.VisitaDTO;
import interfaces.IVisitaDAO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian izaguirre
 */
public class VisitaDAO implements IVisitaDAO {
 
    private final AlmacenComprarMembresiaMock almacen;
 
    public VisitaDAO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
 
    @Override
    public void guardar(String idCliente, VisitaDTO visita) {
        almacen.getVisitas()
               .computeIfAbsent(idCliente, k -> new ArrayList<>())
               .add(visita);
    }
 
    @Override
    public List<VisitaDTO> obtenerPorCliente(String idCliente) {
        List<VisitaDTO> lista = almacen.getVisitas().get(idCliente);
        return lista != null ? lista : new ArrayList<>();
    }
}
