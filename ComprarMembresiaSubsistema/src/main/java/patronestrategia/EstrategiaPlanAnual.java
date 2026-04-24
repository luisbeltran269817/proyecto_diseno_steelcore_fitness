/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package patronestrategia;



/**
 *
 * @author julian izaguirre
 */
public class EstrategiaPlanAnual /**implements IEstrategiaPlan*/ {
 
    private static final double PRECIO = 4999.0;
    /**
    @Override
    public ResultadoDTO validarCompra(CompraDTO dto) {
        if (dto.getIdSocio() == null || dto.getIdSocio().isBlank()) {
            return ResultadoDTO.fallido("Se requiere ID del socio");
        }
        if (dto.getIdSucursal() == null || dto.getIdSucursal().isBlank()) {
            return ResultadoDTO.fallido("Se requiere seleccionar una sucursal");
        }
        if (dto.getIdInstructor() == null || dto.getIdInstructor().isBlank()) {
            return ResultadoDTO.fallido(
                "El plan anual incluye entrenador personal " +
                "Selecciona un instructor para continuar");
        }
        return ResultadoDTO.exitoso("Validación anual correcta", null);
    }
 
    @Override
    public double calcularMonto() {
        return PRECIO;
    }
 
    @Override
    public ResultadoDTO procesarCompra(CompraDTO dto) {
        dto.setMonto(PRECIO);
        return ResultadoDTO.exitoso(
            "Plan anual activado, entrenador " + dto.getIdInstructor()
            + " asignado por 12 meses", null);
    }
    */
}