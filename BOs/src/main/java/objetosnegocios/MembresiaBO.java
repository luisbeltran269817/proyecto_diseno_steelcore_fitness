/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.CompraDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author julian izaguirre
 */
public class MembresiaBO {
    private final AlmacenComprarMembresiaMock almacen;
 
    public MembresiaBO() {
        this.almacen = AlmacenComprarMembresiaMock.getInstancia();
    }
 
    /**
     * Crea y activa una nueva membresía a partir de los datos de la compra.
     * @param dto Datos de la compra.
     * @param plan Plan contratado (resuelto por PlanBO).
     * @param sucursal Sucursal elegida (resuelta por SucursalBO).
     * @return MembresiaDTO activa con su código QR generado.
     */
    public MembresiaDTO crearMembresia(CompraDTO dto, PlanDTO plan, SucursalDTO sucursal) {
        MembresiaDTO membresia = new MembresiaDTO();
 
        membresia.setIdMembresia("MEM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        membresia.setIdSocio(dto.getIdSocio());
        membresia.setIdPlan(plan.getIdPlan());
        membresia.setNombrePlan(plan.getNombre());
        membresia.setIdSucursal(sucursal.getIdSucursal());
        membresia.setNombreSucursal(sucursal.getNombre());
        membresia.setFechaInicio(LocalDate.now());
        membresia.setFechaFin(LocalDate.now().plusMonths(plan.getMesesVigencia()));
        membresia.setEstado("ACTIVA");
        membresia.setIncluyeEntrenador(plan.isIncluyeEntrenador());
        membresia.setCodigoQR(generarCodigoQR(dto.getIdSocio(), membresia.getIdMembresia()));
 
        almacen.getMembresias().put(dto.getIdSocio(), membresia);
 
        return membresia;
    }
 
    public MembresiaDTO obtenerPorSocio(String idSocio) {
        return almacen.getMembresias().get(idSocio);
    }
 
    public boolean estaVigente(String idSocio) {
        MembresiaDTO m = obtenerPorSocio(idSocio);
        if (m == null) return false;
        return "ACTIVA".equals(m.getEstado())
            && LocalDate.now().isBefore(m.getFechaFin());
    }
 
    private String generarCodigoQR(String idSocio, String idMembresia) {
        return "QR-" + idSocio + "-" + idMembresia;
    }
}
