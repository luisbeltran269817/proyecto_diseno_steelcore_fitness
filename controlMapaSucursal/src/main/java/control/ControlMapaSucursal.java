/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dtos.SucursalDTO;
import fachada.IMapaSucursal;
import fachada.MapaSucursal;
import java.util.List;
import org.jxmapviewer.JXMapViewer;

/**
 *
 * @author julian izaguirre
 */
public class ControlMapaSucursal {
    private static ControlMapaSucursal instancia;
    private final IMapaSucursal mapaSucursal;

    private ControlMapaSucursal() {
        this.mapaSucursal = MapaSucursal.getInstancia();
    }

    public static ControlMapaSucursal getInstancia() {
        if (instancia == null) instancia = new ControlMapaSucursal();
        return instancia;
    }

    public JXMapViewer getComponenteMapa() {
        return mapaSucursal.getComponenteMapa();
    }

    public List<SucursalDTO> iniciarMapa() {
        return mapaSucursal.obtenerSucursales();
    }
    
    public SucursalDTO onMarcadorClickeado(String idSucursal) {
        return mapaSucursal.seleccionarSucursal(idSucursal);
    }

    public List<SucursalDTO> filtrarPorCiudad(String ciudad) {
        return mapaSucursal.filtrarPorCiudad(ciudad);
    }

    public List<SucursalDTO> filtrarPorColonia(String colonia) {
        return mapaSucursal.filtrarPorColonia(colonia);
    }

    public SucursalDTO verSucursalMasCercana(double lat, double lng) {
        return mapaSucursal.encontrarMasCercana(lat, lng);
    }

    public void actualizarUbicacion(double lat, double lng) {
        mapaSucursal.actualizarUbicacionUsuario(lat, lng);
    }

    public SucursalDTO confirmarSeleccion() {
        return mapaSucursal.getSucursalSeleccionada();
    }

    public void setOnMarcadorClickListener(IMapaSucursal.OnMarcadorClickListener l) {
        mapaSucursal.setOnMarcadorClickListener(l);
    }
}
