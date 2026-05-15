/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.AmenidadDAO;
import dominios.AmenidadPojo;
import dtos.AmenidadDTO;
import interfaces.IAmenidadBO;
import java.util.List;
import interfaces.IAmenidadDAO;
import mappersBO.AmenidadMapper;


/**
 *
 * @author Tungs
 */
public class AmenidadBO implements IAmenidadBO {
    private final IAmenidadDAO amenidadDAO;

    public AmenidadBO() {
        this.amenidadDAO = new AmenidadDAO();
    }
    
    //ESTE MÉTODO SE MANDA A LLAMAR DESDE EL CASO BASE
    @Override
    public List<AmenidadDTO> obtenerTodas() {
        List<AmenidadPojo> pojos = amenidadDAO.ConsultarTodas();
        return AmenidadMapper.toDTOList(pojos);
    }

}
