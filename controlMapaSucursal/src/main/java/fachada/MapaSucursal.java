package fachada;

import dtos.SucursalDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jxmapviewer.JXMapViewer;
import DAOs.*;

public class MapaSucursal implements IMapaSucursal {
    private static MapaSucursal instancia;
    private final IMapa mapa;
    private List<SucursalDTO> sucursales;
    private SucursalDTO sucursalSeleccionada;

    private MapaSucursal() {
        this.sucursales = new ArrayList<>();
        this.mapa = Mapa.getInstancia();
    }

    public static MapaSucursal getInstancia() {
        if (instancia == null) instancia = new MapaSucursal();
        return instancia;
    }

    @Override
    public JXMapViewer getComponenteMapa() {
        return mapa.getComponente();
    }

    @Override
    public List<SucursalDTO> obtenerSucursales() {
        List<SucursalDTO> lista = new ArrayList<>(AlmacenComprarMembresiaMock.getInstancia()
                .getSucursales().values()
        );
        mapa.colocarMarcadores(lista);
        return lista;
    }

    @Override
    public List<SucursalDTO> filtrarPorCiudad(String ciudad) {
        List<SucursalDTO> filtradas = sucursales.stream()
            .filter(s -> s.getCiudad().equalsIgnoreCase(ciudad))
            .collect(Collectors.toList());
        mapa.colocarMarcadores(filtradas);
        return filtradas;
    }

    @Override
    public List<SucursalDTO> filtrarPorColonia(String colonia) {
        List<SucursalDTO> filtradas = sucursales.stream()
            .filter(s -> s.getColonia().equalsIgnoreCase(colonia))
            .collect(Collectors.toList());
        mapa.colocarMarcadores(filtradas);
        return filtradas;
    }

    @Override
    public SucursalDTO seleccionarSucursal(String idSucursal) {
        for (SucursalDTO s : sucursales) {
            if (s.getIdSucursal().equals(idSucursal)) {
                sucursalSeleccionada = s;
                mapa.resaltarMarcador(idSucursal);
                return s;
            }
        }
        return null;
    }

    @Override
    public SucursalDTO getSucursalSeleccionada() {
        return sucursalSeleccionada;
    }

    @Override
    public void actualizarUbicacionUsuario(double lat, double lng) {
        mapa.mostrarUbicacionUsuario(lat, lng);
        mapa.centrarEn(lat, lng);
    }

    @Override
    public void setOnMarcadorClickListener(OnMarcadorClickListener listener) {
        mapa.setOnMarcadorClickListener(listener::onMarcadorClick);
    }

    @Override
    public SucursalDTO encontrarMasCercana(double lat, double lng) {
        if (sucursales.isEmpty()) obtenerSucursales();
        SucursalDTO masCercana = null;
        double menorDistancia = Double.MAX_VALUE;
        for (SucursalDTO s : sucursales) {
            double d = haversine(lat, lng, s.getLatitud(), s.getLongitud());
            if (d < menorDistancia) { menorDistancia = d; masCercana = s; }
        }
        if (masCercana != null) seleccionarSucursal(masCercana.getIdSucursal());
        return masCercana;
    }

    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat/2)*Math.sin(dLat/2)
                 + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLng/2)*Math.sin(dLng/2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    }
    
    /**
     * quitarlo mas adelante
     */
//    private void cargarSucursalesDePrueba() {
//        sucursales.add(new SucursalDTO("1", "SteelCore Centro",
//            "Calle Guerrero 400", "Centro", "Cd. Obregón", "85000",
//            27.4863, -109.9306, null));
//        sucursales.add(new SucursalDTO("2", "SteelCore Norte",
//            "Blvd. Morelos 1200", "Villa ITSON", "Cd. Obregón", "85130",
//            27.5050, -109.9450, null));
//        sucursales.add(new SucursalDTO("3", "SteelCore Sur",
//            "Av. Jalisco 800", "Col. Esperanza", "Cd. Obregón", "85060",
//            27.4700, -109.9200, null));
//    }
}