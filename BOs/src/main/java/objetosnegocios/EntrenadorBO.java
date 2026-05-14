/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.EntrenadorDAO;
import dominios.EntrenadorPojo;
import dtos.EntrenadorDTO;
import interfaces.IEntrenadorBO;
import interfaces.IEntrenadorDAO;
import java.util.ArrayList;
import java.util.List;
import mappersBO.EntrenadorMapper;

/**
 *
 * @author Tungs
 */
public class EntrenadorBO implements IEntrenadorBO {
    private final IEntrenadorDAO entrenadorDAO;

    public EntrenadorBO() {
        this.entrenadorDAO = new EntrenadorDAO();
    }
    
    @Override
    public List<EntrenadorDTO> obtenerTodos() {
        return entrenadorDAO.obtenerTodos();
    }
    
    @Override
    public EntrenadorDTO buscarPorId(String id) {
        return entrenadorDAO.buscarPorId(id);
    }
    
    /**
     * METODO ADAPTADO PARA EL CASO BASE
     * recupera la lista de entrenadorPojo que le da el dao, y usa el mapper de negocio para convertirla en lista de entrenadordto
     * @param idSucursal
     * @return 
     */
    @Override
    public List<EntrenadorDTO> obtenerPorSucursal(String idSucursal) {
        List<EntrenadorPojo> pojos = entrenadorDAO.obtenerPorSucursal(idSucursal);
        List<EntrenadorDTO> dtos = new ArrayList<>();

        for (EntrenadorPojo pojo : pojos) {
            dtos.add(EntrenadorMapper.toDTO(pojo));
        }
        return dtos;
    }
}
