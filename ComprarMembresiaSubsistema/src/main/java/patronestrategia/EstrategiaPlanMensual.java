/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronestrategia;

import dtos.CompraDTO;
import dtos.ResultadoDTO;

/**
 *
 * @author julian izaguirre
 */
public class EstrategiaPlanMensual implements IEstrategiaPlan {
    
    private static final double PRECIO = 599.0;
 
    @Override
    public ResultadoDTO validarCompra(CompraDTO dto) {
        if (dto.getIdSocio() == null || dto.getIdSocio().isBlank()) {
            return ResultadoDTO.fallido("Se requiere ID del socio");
        }
        if (dto.getIdSucursal() == null || dto.getIdSucursal().isBlank()) {
            return ResultadoDTO.fallido("Se requiere seleccionar una sucursal");
        }
        return ResultadoDTO.exitoso("Validación mensual correcta", null);
    }
 
    @Override
    public double calcularMonto() {
        return PRECIO;
    }
 
    @Override
    public ResultadoDTO procesarCompra(CompraDTO dto) {
        dto.setMonto(PRECIO);
        return ResultadoDTO.exitoso(
            "Plan mensual activado tienes acceso por un mes", null);
    }
    
}
