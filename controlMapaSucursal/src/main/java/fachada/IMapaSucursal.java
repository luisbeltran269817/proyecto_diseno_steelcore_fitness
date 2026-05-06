/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.SucursalDTO;
import fachada.IMapa.OnMarcadorClickListener;
import java.util.List;
import javax.swing.JComponent;
import org.jxmapviewer.JXMapViewer;

/**
 *
 * @author julian izaguirre
 */
public interface IMapaSucursal {

    JComponent getComponenteMapa();
 
    List<SucursalDTO> iniciarMapa();
 
    SucursalDTO onMarcadorClickeado(String idSucursal);
    
    void actualizarUbicacion(double lat, double lng);
 
    void setOnMarcadorClickListener(OnMarcadorClickListener listener);
 
    void centrarMapaEn(double lat, double lng);
 
    List<SucursalDTO> filtrarPorCiudad(String ciudad);
 
    List<SucursalDTO> filtrarPorColonia(String colonia);
 
    SucursalDTO verSucursalMasCercana(double lat, double lng);
    
    void ubicarUsuarioAutomaticamente();
}
