/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.SucursalDAO;
import dominios.AmenidadPojo;
import dominios.SucursalPojo;
import dtos.AmenidadDTO;
import excepciones.PersistenciaException;
import interfaces.IAmenidadBO;
import java.util.List;
import interfaces.ISucursalDAO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersBO.AmenidadMapper;


/**
 *
 * @author Tungs
 */
public class AmenidadBO implements IAmenidadBO {
    private final ISucursalDAO sucursalDAO;
    private static final Logger logger = Logger.getLogger(AmenidadBO.class.getName());
 
    public AmenidadBO() {
        this.sucursalDAO = new SucursalDAO();
    }
 
    @Override
    public List<AmenidadDTO> obtenerTodas() {
        try {
            List<SucursalPojo> sucursales = sucursalDAO.obtenerSucursales();
            Map<String, AmenidadPojo> mapa = new LinkedHashMap<>();
            for (SucursalPojo sucursal : sucursales) {
                if (sucursal.getAmenidadesSucursal() != null) {
                    for (AmenidadPojo a : sucursal.getAmenidadesSucursal()) {
                        mapa.putIfAbsent(a.getIdAmenidad(), a);
                    }
                }
            }
            return AmenidadMapper.toDTOList(new ArrayList<>(mapa.values()));
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener amenidades", e);
            return new ArrayList<>();
        }
    }

}
