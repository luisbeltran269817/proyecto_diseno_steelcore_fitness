package fachada;

import control.ControlMapa;
import dtos.SucursalDTO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Mapa implements IMapa {
    private static Mapa instancia;
    private final ControlMapa control;

    private Mapa() {
        this.control = new ControlMapa();
    }

    public static Mapa getInstancia() {
        if (instancia == null) instancia = new Mapa();
        return instancia;
    }

    @Override
    public JXMapViewer getComponente() {
        return control.getComponente();
    }

    @Override
    public void colocarMarcadores(List<SucursalDTO> sucursales) {
        control.colocarMarcadores(sucursales);
    }

    @Override
    public void resaltarMarcador(String idSucursal) {
        control.resaltarMarcador(idSucursal);
    }

    @Override
    public void centrarEn(double lat, double lng) {
        control.centrarEn(lat, lng);
    }

    @Override
    public void mostrarUbicacionUsuario(double lat, double lng) {
        control.mostrarUbicacionUsuario(lat, lng);
    }

    @Override
    public void setOnMarcadorClickListener(OnMarcadorClickListener listener) {
        control.setOnMarcadorClickListener(listener);
    }
}