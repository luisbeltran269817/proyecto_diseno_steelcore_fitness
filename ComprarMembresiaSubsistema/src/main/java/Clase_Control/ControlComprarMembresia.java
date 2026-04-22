/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import objetosnegocios.MembresiaBO;
import objetosnegocios.PlanBO;
import dtos.CompraDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.ResultadoDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import objetosnegocios.ClienteBO;
import objetosnegocios.SucursalBO;
import objetosnegocios.VisitaBO;
import patronestrategia.FabricaEstrategiasPlan;
import patronestrategia.IEstrategiaPlan;

/**
 *
 * @author julian izaguirre
 */
public class ControlComprarMembresia {
    
    private final PlanBO planBO;
    private final SucursalBO sucursalBO;
    private final MembresiaBO membresiaBO;
    private final ClienteBO socioBO; 
    private final VisitaBO visitaBO;
 
    public ControlComprarMembresia() {
        this.planBO      = new PlanBO();
        this.sucursalBO  = new SucursalBO();
        this.membresiaBO = new MembresiaBO();
        this.socioBO     = new ClienteBO();
        this.visitaBO    = new VisitaBO();
    }
    
    //Ver esto Despues
    public UsuarioDTO obtenerPerfil(String correo) {
        return  new UsuarioDTO() {};//usuarioBO.obtenerPorCorreo(correo)
    }

    public List<VisitaDTO> obtenerHistorial(String correo) {
        List<VisitaDTO> visitas= new ArrayList<>();
        return visitas; //visitaBO.obtenerPorCorreo(correo)
    }
 
    public List<PlanDTO> obtenerPlanes() {
        return planBO.obtenerTodos();
    }
 
    public PlanDTO obtenerDetallePlan(String idPlan) {
        return planBO.buscarPorId(idPlan);
    }
 
    public List<SucursalDTO> obtenerSucursales() {
        return sucursalBO.obtenerTodas();
    }
 
    // generar qr
    
    public ResultadoDTO generarContrato(CompraDTO dto) {

        if (!planBO.esPlanValido(dto.getIdPlan())) {
            return ResultadoDTO.fallido("Plan no encontrado: " + dto.getIdPlan());
        }

        if (!sucursalBO.esSucursalValida(dto.getIdSucursal())) {
            return ResultadoDTO.fallido("Sucursal no encontrada: " + dto.getIdSucursal());
        }
 
        IEstrategiaPlan estrategia = FabricaEstrategiasPlan.obtener(dto.getIdPlan());

        ResultadoDTO validacion = estrategia.validarCompra(dto);
        if (!validacion.isExito()) {
            return validacion;
        }
 
        double monto = estrategia.calcularMonto();
        dto.setMonto(monto);
 
        String folioContrato = "CONT-" + UUID.randomUUID()
            .toString().substring(0, 8).toUpperCase();
 
        return ResultadoDTO.exitoso(
            "Contrato generado. Monto a pagar: $" + monto, folioContrato);
    }
 

    public ResultadoDTO confirmarCompra(CompraDTO dto) {
        PlanDTO plan = planBO.buscarPorId(dto.getIdPlan());
        SucursalDTO sucursal = sucursalBO.buscarPorId(dto.getIdSucursal());
 
        if (plan == null || sucursal == null) {
            return ResultadoDTO.fallido("Datos de compra inválidos.");
        }

        IEstrategiaPlan estrategia = FabricaEstrategiasPlan.obtener(dto.getIdPlan());
        ResultadoDTO procesamiento = estrategia.procesarCompra(dto);
 
        if (!procesamiento.isExito()) {
            return procesamiento;
        }

        MembresiaDTO membresia = membresiaBO.crearMembresia(dto, plan, sucursal);
        String folioTxn = "TXN-" + UUID.randomUUID()
            .toString().substring(0, 8).toUpperCase();
        
        return ResultadoDTO.exitoso(
            procesamiento.getMensaje() + " QR generado: " + membresia.getCodigoQR(),
            folioTxn);
    }
    
}
