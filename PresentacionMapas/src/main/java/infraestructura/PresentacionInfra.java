package infraestructura;

import Utilerias.PainterMapa;
import Utilerias.TileManager;
import Utilerias.VisorMapa;
import dtos.SucursalDTO;
import fachada.IMapa;
import fachada.Mapa;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

/**
 * Puente entre los componentes visuales del mapa (VisorMapa, TileManager,
 * PainterMapa) y el singleton Mapa que usa el resto de la aplicación.
 *
 * @author julian izaguirre
 */
public class PresentacionInfra {
 
    @FunctionalInterface
    public interface OnMarcadorClickListener {
        void onMarcadorClick(String idSucursal);
    }
 
    private final IMapa       mapa;
    private final VisorMapa   visor;
    private final PainterMapa painter;
 
    public PresentacionInfra() {
        // 1. Proveedor de tiles (CartoDB dark)
        TileManager tileManager = new TileManager();
 
        // 2. Widget JXMapViewer con TileFactory ya configurado
        this.visor   = new VisorMapa(tileManager.getFactory());
        this.painter = new PainterMapa();
 
        // 3. Registrar lambdas en el singleton — usa Mapa.CoordConsumer
        //    para los pares (lat, lng) y así evitar el error de autoboxing
        //    que daba el compilador con BiConsumer<Double,Double>.
        this.mapa = Mapa.getInstancia();
        mapa.registrar(
            visor::getWidget,
 
            sucursales -> {
                painter.setMarcadores(sucursales);
                painter.aplicar(visor.getWidget());
                SwingUtilities.invokeLater(() ->
                    visor.ajustarZoomATodos(painter.getPosiciones()));
            },
 
            /* resaltarMarcador  */ id -> {
                painter.setActivo(id);
                painter.aplicar(visor.getWidget());
            },
 
            /* centrarEn         */ (lat, lng) ->
                SwingUtilities.invokeLater(() ->
                    visor.centrarEn(
                        new org.jxmapviewer.viewer.GeoPosition(lat, lng))),
 
            /* mostrarUsuario    */ (lat, lng) -> {
                painter.setUbicacionUsuario(lat, lng);
                painter.aplicar(visor.getWidget());
            },
 
            /* setListener       */ listener ->
                visor.getWidget().addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        org.jxmapviewer.JXMapViewer mv = visor.getWidget();
                        String id = painter.detectarClick(
                            e.getPoint(),
                            mv.getViewportBounds(),
                            mv.getTileFactory(),
                            mv.getZoom());
                        if (id != null) listener.onMarcadorClick(id);
                    }
                })
        );
    }
 
    public JComponent getComponenteMapa() {
        return mapa.getComponente();
    }
 
    public void colocarMarcadores(List<SucursalDTO> sucursales) {
        mapa.colocarMarcadores(sucursales);
    }
 
    public void resaltarMarcador(String idSucursal) {
        mapa.resaltarMarcador(idSucursal);
    }
 
    public void actualizarUbicacion(double lat, double lng) {
        mapa.mostrarUbicacionUsuario(lat, lng);
        mapa.centrarEn(lat, lng);
    }
 
    public void centrarMapaEn(double lat, double lng) {
        mapa.centrarEn(lat, lng);
    }
 
    public void setOnMarcadorClickListener(OnMarcadorClickListener listener) {
        mapa.setOnMarcadorClickListener(listener::onMarcadorClick);
    }
}