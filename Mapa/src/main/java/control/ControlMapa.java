/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import dtos.SucursalDTO;
import fachada.IMapa;
import fachada.PainterMapa;
import fachada.TileManager;
import fachada.VisorMapa;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Coordina VisorMapa, TileManager y PainterMapa.
 * 
 * @author julian izaguirre
 */
public class ControlMapa {
    private final VisorMapa visor;
    private final PainterMapa painter;
    private IMapa.OnMarcadorClickListener clickListener;
 
    public ControlMapa() {
        TileManager tileManager = new TileManager();
        visor   = new VisorMapa();
        painter = new PainterMapa();
        visor.getWidget().setTileFactory(tileManager.getFactory());
 
        visor.getWidget().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JXMapViewer mv = visor.getWidget();
                String id = painter.detectarClick(
                    e.getPoint(),
                    mv.getViewportBounds(),
                    mv.getTileFactory(),
                    mv.getZoom()
                );
                if (id != null) {
                    painter.setActivo(id);
                    painter.aplicar(mv);
                    if (clickListener != null) {
                        clickListener.onMarcadorClick(id);
                    }
                }
            }
        });
    }
 
    public JXMapViewer getComponente() {
        return visor.getWidget();
    }
 
    public void colocarMarcadores(List<SucursalDTO> sucursales) {
        painter.setMarcadores(sucursales);
        visor.ajustarZoomATodos(painter.getPosiciones());
        painter.aplicar(visor.getWidget());
    }
 
    public void resaltarMarcador(String idSucursal) {
        painter.setActivo(idSucursal);
        GeoPosition pos = painter.getPosicion(idSucursal);
        if (pos != null) visor.centrarEn(pos);
        painter.aplicar(visor.getWidget());
    }
 
    public void centrarEn(double lat, double lng) {
        visor.centrarEn(new GeoPosition(lat, lng));
    }
 
    public void mostrarUbicacionUsuario(double lat, double lng) {
        painter.setUbicacionUsuario(lat, lng);
        painter.aplicar(visor.getWidget());
    }
 
    public void setOnMarcadorClickListener(IMapa.OnMarcadorClickListener listener) {
        this.clickListener = listener;
    }
}
