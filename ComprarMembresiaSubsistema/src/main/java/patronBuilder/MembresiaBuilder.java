/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronBuilder;

import dtos.AmenidadDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.PagoDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * patron builder para facilitar la creacion de membresias
 * metodos documentados en su interfaz: IMembresiaBuilder
 * @author luiscarlosbeltran
 */
public class MembresiaBuilder implements IMembresiaBuilder {
    
    private String idSucursal;
    private String idPlan;
    private List<AmenidadDTO> extrasContratados;
    private String idCliente;
    private String metodoPago;
    private Double montoPagado;
    private PagoDTO pago;
    
    //constructor que empieza con extras vacios y el monto pagado en 0
    public MembresiaBuilder() {
        this.extrasContratados = new ArrayList<>();
        this.montoPagado = 0.0;
    }

    @Override
    public MembresiaBuilder setSucursal(SucursalDTO dto) {
        this.idSucursal = dto.getIdSucursal();
        return this;
    }
    
    @Override
    public MembresiaBuilder setPlan(PlanDTO dto) {
        this.idPlan = dto.getIdPlan();
        
        if (dto.getPrecio() != null) {
            this.montoPagado += dto.getPrecio();
        }
        return this;
    }
    
    @Override
    public MembresiaBuilder setExtras(List<AmenidadDTO> ams) {
        this.extrasContratados = ams;
        
        if (ams != null) {
            for (AmenidadDTO a : ams) {
                if (a.getCosto() != null) {
                    this.montoPagado += a.getCosto();
                }
            }
        }
        return this;
    }
    
    
    @Override
    public MembresiaBuilder setCliente(String correo) {
        this.idCliente = correo;
        return this;
    }
    
    @Override
    public MembresiaBuilder setMetodoPago(String metodo) {
        this.metodoPago = metodo;
        return this;
    }
    @Override
    public MembresiaBuilder setPago(PagoDTO pago){
        this.pago= pago;
        return this;
    }
    
    
    @Override
    public MembresiaDTO build() {
        MembresiaDTO dto = new MembresiaDTO();
        dto.setIdSucursal(idSucursal);
        dto.setIdPlan(idPlan);
        dto.setAmenidadesExtra(extrasContratados);
        dto.setIdCliente(idCliente);
        dto.setMetodoPago(metodoPago);
        dto.setMontoPagado(montoPagado);
        dto.setPago(pago);
        return dto;
    }
}