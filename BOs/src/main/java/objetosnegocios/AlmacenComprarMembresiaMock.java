/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author julian izaguirre
 */
public class AlmacenComprarMembresiaMock {
    private static AlmacenComprarMembresiaMock instancia;
 
    public static AlmacenComprarMembresiaMock getInstancia() {
        if (instancia == null) {
            instancia = new AlmacenComprarMembresiaMock();
        }
        return instancia;
    }
 
    private final Map<String, PlanDTO> planes;
    private final Map<String, SucursalDTO> sucursales;
    private final Map<String, MembresiaDTO> membresias;
 
    private AlmacenComprarMembresiaMock() {
        planes = new LinkedHashMap<>();
        sucursales = new LinkedHashMap<>();
        membresias = new LinkedHashMap<>();
        poblarDatos();
    }
 
    private void poblarDatos() {
        planes.put("P001", new PlanDTO(
            "P001", "MENSUAL",   599.0,
            "Acceso mensual sin contrato largo",
            false, 1));
 
        planes.put("P002", new PlanDTO(
            "P002", "SEMESTRAL", 2999.0,
            "6 meses con 16% de descuento",
            false, 6));
 
        planes.put("P003", new PlanDTO(
            "P003", "ANUAL",     4999.0,
            "12 meses + entrenador personal incluido",
            true, 12));
 
        sucursales.put("S001", new SucursalDTO(
            "S001", "SteelCore Centro",
            "Hermosillo", "Centro", 29.0729, -110.9559));
 
        sucursales.put("S002", new SucursalDTO(
            "S002", "SteelCore Norte",
            "Hermosillo", "Olivares", 29.1000, -110.9400));
 
        sucursales.put("S003", new SucursalDTO(
            "S003", "SteelCore Sur",
            "Hermosillo", "Pitic", 29.0400, -110.9600));
    }
 
    public Map<String, PlanDTO> getPlanes(){ 
        return planes; 
    }
    
    public Map<String, SucursalDTO> getSucursales(){ 
        return sucursales; 
    }
    
    public Map<String, MembresiaDTO> getMembresias(){ 
        return membresias; 
    }
    
    
}
