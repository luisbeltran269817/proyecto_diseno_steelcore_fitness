package infraestructura;

import dtos.SucursalDTO;
import fachada.IMapa;
import fachada.Mapa;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author julian izaguirre
 */
public class PresentacionInfra {

    /**
     * Listener propio de este módulo.
     * Al ser una interfaz funcional separada de IMapa, las capas superiores
     * no dependen del módulo Mapa para registrar clicks en marcadores.
     */
    @FunctionalInterface
    public interface OnMarcadorClickListener {
        void onMarcadorClick(String idSucursal);
    }

    private final IMapa mapa;

    public PresentacionInfra() {
        this.mapa = Mapa.getInstancia();
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
