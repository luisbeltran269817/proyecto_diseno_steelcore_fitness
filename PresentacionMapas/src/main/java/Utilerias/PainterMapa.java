/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilerias;

import dtos.SucursalDTO;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.GeoPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;
import org.jxmapviewer.viewer.TileFactory;

/**
 * pintar elementos sobre el mapa.
 * Maneja el punto azul del usuario y los marcadores de sucursales.
 * 
 * @author julian izaguirre
 */
public class PainterMapa {
 private double latUsuario = 0, lngUsuario = 0;
    private final Map<String, EntradaMarcador> marcadores = new LinkedHashMap<>();
    private String idActivo = null;

    public void setUbicacionUsuario(double lat, double lng) {
        latUsuario = lat;
        lngUsuario = lng;
    }

    public void setMarcadores(List<SucursalDTO> sucursales) {
        marcadores.clear();
        idActivo = null;
        for (SucursalDTO s : sucursales) {
            if (s.getLatitud() == null || s.getLongitud() == null
                    || s.getLatitud() == 0.0 || s.getLongitud() == 0.0) continue;
            marcadores.put(s.getIdSucursal(),
                new EntradaMarcador(s,
                    new GeoPosition(s.getLatitud(), s.getLongitud())));
        }
    }

    public void setActivo(String idSucursal) { idActivo = idSucursal; }

    public Set<GeoPosition> getPosiciones() {
        Set<GeoPosition> set = new HashSet<>();
        for (EntradaMarcador em : marcadores.values()) set.add(em.pos);
        return set;
    }

    public GeoPosition getPosicion(String id) {
        EntradaMarcador em = marcadores.get(id);
        return em != null ? em.pos : null;
    }

    public String detectarClick(Point click, Rectangle viewport,
                                TileFactory factory, int zoom) {
        for (EntradaMarcador em : marcadores.values()) {
            Point2D pt = factory.geoToPixel(em.pos, zoom);
            int mx = (int)(pt.getX() - viewport.getX());
            int my = (int)(pt.getY() - viewport.getY());
            if (click.distance(mx, my) <= 14)
                return em.sucursal.getIdSucursal();
        }
        return null;
    }

    public void aplicar(JXMapViewer mapViewer) {
        List<org.jxmapviewer.painter.Painter<JXMapViewer>> painters = new ArrayList<>();

        if (latUsuario != 0) {
            final double lat = latUsuario, lng = lngUsuario;
            painters.add((g, map, w, h) -> {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
                Point2D pt = map.getTileFactory()
                    .geoToPixel(new GeoPosition(lat, lng), map.getZoom());
                Rectangle vp = map.getViewportBounds();
                int x = (int)(pt.getX() - vp.getX());
                int y = (int)(pt.getY() - vp.getY());
                g2.setColor(new Color(33, 150, 243, 80));
                g2.fill(new Ellipse2D.Double(x-10, y-10, 20, 20));
                g2.setColor(new Color(33, 150, 243));
                g2.fill(new Ellipse2D.Double(x-6, y-6, 12, 12));
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                g2.draw(new Ellipse2D.Double(x-6, y-6, 12, 12));
                g2.dispose();
            });
        }

        final String activoSnap = idActivo;
        painters.add((g, map, w, h) -> {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            Rectangle vp = map.getViewportBounds();
            for (EntradaMarcador em : marcadores.values()) {
                Point2D pt = map.getTileFactory().geoToPixel(em.pos, map.getZoom());
                int x = (int)(pt.getX() - vp.getX());
                int y = (int)(pt.getY() - vp.getY());
                boolean activo = em.sucursal.getIdSucursal().equals(activoSnap);
                int sz      = activo ? 22 : 16;
                Color fondo = activo ? new Color(255,215,0) : new Color(106,13,173);
                Color borde = activo ? new Color(106,13,173) : Color.WHITE;

                g2.setColor(new Color(0,0,0,60));
                g2.fill(new Ellipse2D.Double(x-sz/2+2, y-sz/2+2, sz, sz));
                g2.setColor(fondo);
                g2.fill(new Ellipse2D.Double(x-sz/2, y-sz/2, sz, sz));
                g2.setColor(borde);
                g2.setStroke(new BasicStroke(2.5f));
                g2.draw(new Ellipse2D.Double(x-sz/2, y-sz/2, sz, sz));

                if (activo) {
                    String label = em.sucursal.getNombre();
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 11));
                    FontMetrics fm = g2.getFontMetrics();
                    int lw = fm.stringWidth(label);
                    g2.setColor(new Color(255,215,0,220));
                    g2.fillRoundRect(x-lw/2-4, y+sz/2+2, lw+8, 16, 6, 6);
                    g2.setColor(new Color(22,33,62));
                    g2.drawString(label, x-lw/2, y+sz/2+13);
                }
            }
            g2.dispose();
        });

        mapViewer.setOverlayPainter(new CompoundPainter<>(painters));
        mapViewer.repaint();
    }

    private static class EntradaMarcador {
        final SucursalDTO sucursal;
        final GeoPosition pos;
        EntradaMarcador(SucursalDTO s, GeoPosition p) { sucursal = s; pos = p; }
    }
}
