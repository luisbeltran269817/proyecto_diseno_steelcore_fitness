/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronestrategia;



/**
 *
 * @author julian izaguirre
 */
public class EstrategiaPlanSemestral /**implements IEstrategiaPlan*/ {
 /**
    private static final double PRECIO_BASE   = 599.0 * 6;
    private static final double PRECIO_FINAL  = 2999.0; 
    private static final double DESCUENTO_PCT = 1 - (PRECIO_FINAL / PRECIO_BASE);
 
    @Override
    public ResultadoDTO validarCompra(CompraDTO dto) {
        if (dto.getIdSocio() == null || dto.getIdSocio().isBlank()) {
            return ResultadoDTO.fallido("Se requiere ID del socio");
        }
        if (dto.getIdSucursal() == null || dto.getIdSucursal().isBlank()) {
            return ResultadoDTO.fallido("Se requiere seleccionar una sucursal");
        }
        return ResultadoDTO.exitoso("Validación semestral correcta", null);
    }
 
    @Override
    public double calcularMonto() {
        return PRECIO_FINAL;
    }
 
    @Override
    public ResultadoDTO procesarCompra(CompraDTO dto) {
        dto.setMonto(PRECIO_FINAL);
        return ResultadoDTO.exitoso(
            String.format("Plan semestral activado, descuento aplicado: %.0f%%.",
                DESCUENTO_PCT * 100), null);
    }
    */
}