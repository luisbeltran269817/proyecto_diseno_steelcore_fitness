/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.VisitaDAO;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
import interfaces.IVisitaBO;
import interfaces.IVisitaDAO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Gael Galaviz
 */
public class VisitaBO implements IVisitaBO{
    
    private final IVisitaDAO visitaDAO;
    private final SucursalBO sucursalBO;
 
    public VisitaBO() {
        this.visitaDAO = new VisitaDAO();
        this.sucursalBO = new SucursalBO();
    }
 
    /**
     * Crea y persiste una nueva visita.
     * Resuelve el nombre y dirección de la sucursal para poblar el VisitaDTO
     * tal como lo espera la pantalla de historial de visitas existente.
     */
    @Override
    public VisitaDTO registrarVisita(String idCliente, String idSucursal) {
        // Resolución de datos de la sucursal para el historial visual
        SucursalDTO sucursal = sucursalBO.buscarPorId(idSucursal);
 
        VisitaDTO visita = new VisitaDTO();
        visita.setIdVisita(UUID.randomUUID().toString());
        visita.setFechaHora(LocalDateTime.now());
 
        if (sucursal != null) {
            visita.setGimnasio(sucursal.getNombre());
            visita.setCalle(sucursal.getCalle() != null ? sucursal.getCalle() : "");
            visita.setColonia(sucursal.getColonia() != null ? sucursal.getColonia() : "");
            visita.setCiudad(sucursal.getCiudad() != null ? sucursal.getCiudad() : "");
        } else {
            visita.setGimnasio("SteelCore Fitness");
            visita.setCalle("");
            visita.setColonia("");
            visita.setCiudad("");
        }
 
        visitaDAO.guardar(idCliente, visita);
        return visita;
    }
 
    @Override
    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        return visitaDAO.obtenerPorCliente(idCliente);
    }
}
