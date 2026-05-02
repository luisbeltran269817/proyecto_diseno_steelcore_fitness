/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.SucursalDTO;
import java.util.List;
import org.jxmapviewer.JXMapViewer;

/**
 *
 * @author julian izaguirre
 */
public interface IMapaSucursal {
    JXMapViewer getComponenteMapa();

    List<SucursalDTO> obtenerSucursales();

    List<SucursalDTO> filtrarPorCiudad(String ciudad);
    List<SucursalDTO> filtrarPorColonia(String colonia);

    SucursalDTO seleccionarSucursal(String idSucursal);

    SucursalDTO getSucursalSeleccionada();
    SucursalDTO encontrarMasCercana(double lat, double lng);

    void actualizarUbicacionUsuario(double lat, double lng);

    void setOnMarcadorClickListener(OnMarcadorClickListener listener);

    @FunctionalInterface
    interface OnMarcadorClickListener {
        void onMarcadorClick(String idSucursal);
    }
}
