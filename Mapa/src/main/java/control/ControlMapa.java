package control;

import dtos.SucursalDTO;
import excepciones.MapaException;
import fachada.IMapa;
import fachada.Mapa;
import java.util.List;
import javax.swing.JComponent;

/**
 *
 * @author julian izaguirre
 */
public class ControlMapa {
private final IMapa mapa;
 
    public ControlMapa() throws MapaException {
        this.mapa = Mapa.getInstancia();
        if (this.mapa == null) {
            throw new MapaException("El Mapa no esta disponible");
        }
    }
 
    public JComponent getComponente() throws MapaException {
        try {
            JComponent comp = mapa.getComponente();
            if (comp == null) throw new MapaException(
                "El componente del mapa es nulo. Verifica que PresentacionInfra " +
                "haya llamado Mapa.getInstancia().registrar(...) antes.");
            return comp;
        } catch (MapaException e) {
            throw e;
        } catch (Exception e) {
            throw new MapaException("No se pudo obtener el componente del mapa.", e);
        }
    }
 
    public void colocarMarcadores(List<SucursalDTO> sucursales) throws MapaException {
        if (sucursales == null)
            throw new MapaException("La lista de sucursales no puede ser nula.");
        try {
            mapa.colocarMarcadores(sucursales);
        } catch (Exception e) {
            throw new MapaException("Error al colocar los marcadores en el mapa.", e);
        }
    }
 
    public void resaltarMarcador(String idSucursal) throws MapaException {
        if (idSucursal == null || idSucursal.isBlank())
            throw new MapaException("El id de sucursal no puede ser nulo o vacio.");
        try {
            mapa.resaltarMarcador(idSucursal);
        } catch (Exception e) {
            throw new MapaException(
                "Error al resaltar el marcador de la sucursal: " + idSucursal, e);
        }
    }
 
    public void centrarEn(double lat, double lng) throws MapaException {
        validarCoordenadas(lat, lng);
        try {
            mapa.centrarEn(lat, lng);
        } catch (Exception e) {
            throw new MapaException(
                "Error al centrar el mapa en (" + lat + ", " + lng + ").", e);
        }
    }
 
    public void mostrarUbicacionUsuario(double lat, double lng) throws MapaException {
        validarCoordenadas(lat, lng);
        try {
            mapa.mostrarUbicacionUsuario(lat, lng);
        } catch (Exception e) {
            throw new MapaException(
                "Error al mostrar la ubicacion del usuario en el mapa.", e);
        }
    }
 
    public void setOnMarcadorClickListener(IMapa.OnMarcadorClickListener listener) {
        mapa.setOnMarcadorClickListener(listener);
    }
 
    private void validarCoordenadas(double lat, double lng) throws MapaException {
        if (lat < -90 || lat > 90)
            throw new MapaException(
                "Latitud invalida: " + lat + ". Debe estar entre -90 y 90.");
        if (lng < -180 || lng > 180)
            throw new MapaException(
                "Longitud invalida: " + lng + ". Debe estar entre -180 y 180.");
    }
}