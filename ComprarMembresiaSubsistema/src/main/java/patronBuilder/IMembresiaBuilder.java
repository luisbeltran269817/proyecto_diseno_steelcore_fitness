/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package patronBuilder;

import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.util.List;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IMembresiaBuilder {
    
    /**
     * metodo para settear la sucursal elegida
     * @param dto
     * @return 
     */
    public IMembresiaBuilder setSucursal(SucursalDTO dto);
    
    /**
     * metodo para settear el plan elegido
     * @param dto
     * @return 
     */
    public IMembresiaBuilder setPlan(PlanDTO dto);
    
    /**
     * metodo para recorrer una lista de amenidades y asignarlas
     * a la membresia que se esta construyendo
     * @param amenidades
     * @return 
     */
    public IMembresiaBuilder setExtras(List<AmenidadDTO> amenidades);
    
    /**
     * metodo para settear entrenador
     * @param dto
     * @return 
     */
    public IMembresiaBuilder setEntrenador(EntrenadorDTO dto);
    
    /**
     * metodo para settear el horario
     * @param dto
     * @return 
     */
    public IMembresiaBuilder setHorario(HorarioDTO dto);
    
    /**
     * metodo para asignar el cliente, recibe su correo
     * @param correo
     * @return 
     */
    public IMembresiaBuilder setCliente(String correo);
    
    /**
     * metodo para asignar el metodo de pago
     * @param metodo
     * @return 
     */
    public IMembresiaBuilder setMetodoPago(String metodo);
    
    /**
     * metodo que construye la membresia
     * @return 
     */
    public MembresiaDTO build();
}