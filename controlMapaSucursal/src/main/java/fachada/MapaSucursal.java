package fachada;

import control.ControlMapaSucursal;
import dtos.SucursalDTO;
import java.util.List;
import javax.swing.JComponent;

public class MapaSucursal implements IMapaSucursal {
    private static MapaSucursal instancia;

    private final ControlMapaSucursal control;

    private MapaSucursal() {
        this.control = new ControlMapaSucursal();
    }

    public static MapaSucursal getInstancia() {
        if (instancia == null) {
            instancia = new MapaSucursal();
        }
        return instancia;
    }

    @Override
    public JComponent getComponenteMapa() {
        return control.getComponenteMapa();
    }

    @Override
    public List<SucursalDTO> iniciarMapa() {
        return control.iniciarMapa();
    }

    @Override
    public SucursalDTO onMarcadorClickeado(String idSucursal) {
        return control.onMarcadorClickeado(idSucursal);
    }

    @Override
    public void actualizarUbicacion(double lat, double lng) {
        control.actualizarUbicacion(lat, lng);
    }

    @Override
    public void setOnMarcadorClickListener(IMapaSucursal.OnMarcadorSucursalClickListener listener) {
        // Adapta el listener de alto nivel al listener interno del módulo Mapa
        control.setOnMarcadorClickListener(listener::onMarcadorClick);
    }

    @Override
    public void centrarMapaEn(double lat, double lng) {
        control.centrarMapaEn(lat, lng);
    }

    @Override
    public List<SucursalDTO> filtrarPorCiudad(String ciudad) {
        return control.filtrarPorCiudad(ciudad);
    }

    @Override
    public List<SucursalDTO> filtrarPorColonia(String colonia) {
        return control.filtrarPorColonia(colonia);
    }

    @Override
    public SucursalDTO verSucursalMasCercana(double lat, double lng) {
        return control.verSucursalMasCercana(lat, lng);
    }

    @Override
    public void ubicarUsuarioAutomaticamente() {
        control.ubicarUsuarioAutomaticamente();
    }
}