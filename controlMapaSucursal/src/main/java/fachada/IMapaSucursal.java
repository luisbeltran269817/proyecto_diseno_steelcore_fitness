/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.SucursalDTO;
import java.util.List;
import javax.swing.JComponent;

/**
 * Fachada del subsistema controlMapaSucursal.
 * No expone ningún tipo de la capa de Infraestructura (Mapa / JXMapViewer).
 *
 * @author julian izaguirre
 */
public interface IMapaSucursal {

    /**
     * Listener propio de este subsistema para notificar clicks en marcadores.
     * Definido aquí para que Presentación no necesite importar nada del módulo Mapa.
     */
    @FunctionalInterface
    interface OnMarcadorSucursalClickListener {
        void onMarcadorClick(String idSucursal);
    }

    JComponent getComponenteMapa();

    List<SucursalDTO> iniciarMapa();

    SucursalDTO onMarcadorClickeado(String idSucursal);

    void actualizarUbicacion(double lat, double lng);

    void setOnMarcadorClickListener(OnMarcadorSucursalClickListener listener);

    void centrarMapaEn(double lat, double lng);

    List<SucursalDTO> filtrarPorCiudad(String ciudad);

    List<SucursalDTO> filtrarPorColonia(String colonia);

    SucursalDTO verSucursalMasCercana(double lat, double lng);

    void ubicarUsuarioAutomaticamente();
}
