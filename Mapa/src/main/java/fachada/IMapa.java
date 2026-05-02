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
public interface IMapa {
    JXMapViewer getComponente();
    void colocarMarcadores(List<SucursalDTO> sucursales);
    void resaltarMarcador(String idSucursal);
    void centrarEn(double lat, double lng);
    void mostrarUbicacionUsuario(double lat, double lng);
    void setOnMarcadorClickListener(OnMarcadorClickListener listener);

    @FunctionalInterface
    interface OnMarcadorClickListener {
        void onMarcadorClick(String idSucursal);
    }
}
