/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import java.util.Set;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * configurar y exponer el widget JXMapViewer
 * @author julian izaguirre
 */
public class VisorMapa {
    private final JXMapViewer mapViewer;

    public VisorMapa() {
        mapViewer = new JXMapViewer();
        mapViewer.addMouseWheelListener(
            new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapViewer));
        org.jxmapviewer.input.PanMouseInputListener pan =
            new org.jxmapviewer.input.PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(pan);
        mapViewer.addMouseMotionListener(pan);
        mapViewer.setCenterPosition(new GeoPosition(27.4863, -109.9306));
        mapViewer.setZoom(3);
    }

    public JXMapViewer getWidget() { return mapViewer; }

    public void centrarEn(GeoPosition pos) {
        mapViewer.setCenterPosition(pos);
    }

    public void ajustarZoomATodos(Set<GeoPosition> posiciones) {
        if (posiciones == null || posiciones.isEmpty()) {
            return; 
        }

        if (posiciones.size() > 1) {
            mapViewer.zoomToBestFit(posiciones, 0.9);
        }
        
        else if (posiciones.size() == 1) {
            GeoPosition unicaPosicion = posiciones.iterator().next();
            mapViewer.setCenterPosition(unicaPosicion);
            mapViewer.setZoom(5);
        }
    }
}
