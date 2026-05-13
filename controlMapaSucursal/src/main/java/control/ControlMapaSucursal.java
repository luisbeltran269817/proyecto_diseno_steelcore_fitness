/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import Excepciones.NegocioException;
import dtos.SucursalDTO;
import fachada.IMapa;
import fachada.IMapa.OnMarcadorClickListener;
import fachada.Mapa;
import interfaces.ISucursalBO;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import objetosnegocios.SucursalBO;

/**
 *
 * @author julian izaguirre
 */
public class ControlMapaSucursal {
    private final ISucursalBO sucursalBO;
    private final IMapa mapa;
    private List<SucursalDTO> sucursales;
    private SucursalDTO sucursalSeleccionada;

    public ControlMapaSucursal() {
        this.sucursalBO = new SucursalBO(); 
        this.mapa = Mapa.getInstancia(); 
        this.sucursales = new ArrayList<>();
//        obtenerUbicacionEnSegundoPlano(); 
    }

    public JComponent getComponenteMapa() {
        return mapa.getComponente();
    }

    public List<SucursalDTO> iniciarMapa() throws NegocioException{
        sucursales = sucursalBO.obtenerSucursales();
        mapa.colocarMarcadores(sucursales);
        return new ArrayList<>(sucursales);
    }

    public SucursalDTO onMarcadorClickeado(String idSucursal) {
        for (SucursalDTO s : sucursales) {
            if (s.getIdSucursal().equals(idSucursal)) {
                sucursalSeleccionada = s;
                mapa.resaltarMarcador(idSucursal);
                return s;
            }
        }
        return null;
    }

    public void actualizarUbicacion(double lat, double lng) {
        mapa.mostrarUbicacionUsuario(lat, lng);
        mapa.centrarEn(lat, lng);
    }

    public void centrarMapaEn(double lat, double lng) {
        mapa.centrarEn(lat, lng);
    }

    public void setOnMarcadorClickListener(OnMarcadorClickListener listener) {
        mapa.setOnMarcadorClickListener(listener);
    }

    public List<SucursalDTO> filtrarPorCiudad(String ciudad) throws NegocioException {
        if (sucursales.isEmpty()) iniciarMapa();
        List<SucursalDTO> filtradas = sucursales.stream()
            .filter(s -> s.getCiudad().equalsIgnoreCase(ciudad))
            .collect(Collectors.toList());
        mapa.colocarMarcadores(filtradas);
        return filtradas;
    }

    public List<SucursalDTO> filtrarPorColonia(String colonia) throws NegocioException {
        if (sucursales.isEmpty()) iniciarMapa();
        List<SucursalDTO> filtradas = sucursales.stream()
            .filter(s -> s.getColonia().equalsIgnoreCase(colonia))
            .collect(Collectors.toList());
        mapa.colocarMarcadores(filtradas);
        return filtradas;
    }

    public SucursalDTO verSucursalMasCercana(double lat, double lng) throws NegocioException {
        if (sucursales.isEmpty()) iniciarMapa();
        SucursalDTO masCercana = null;
        double menorDistancia = Double.MAX_VALUE;
        for (SucursalDTO s : sucursales) {
            double d = haversine(lat, lng, s.getLatitud(), s.getLongitud());
            if (d < menorDistancia) {
                menorDistancia = d;
                masCercana = s;
            }
        }
        if (masCercana != null) onMarcadorClickeado(masCercana.getIdSucursal());
        return masCercana;
    }

    private double haversine(double lat1, double lng1, double lat2, double lng2) {
        final double R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }

    public void ubicarUsuarioAutomaticamente() {
        Thread hilo = new Thread(() -> {
            try {
                URL url = new URL("http://ip-api.com/json/?fields=lat,lon");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(4_000);
                con.setReadTimeout(4_000);

                StringBuilder sb = new StringBuilder();
                try (Scanner sc = new Scanner(con.getInputStream())) {
                    while (sc.hasNextLine()) sb.append(sc.nextLine());
                }

                double lat = extraerDouble(sb.toString(), "lat");
                double lon = extraerDouble(sb.toString(), "lon");

                if (lat != 0) {
                    SwingUtilities.invokeLater(() -> actualizarUbicacion(lat, lon));
                }
            } catch (Exception e) {
                System.out.println("[Geo] No se pudo obtener ubicación: " + e.getMessage());
            }
        }, "hilo-geo-mapa");
        hilo.setDaemon(true);
        hilo.start();
    }

    private double extraerDouble(String json, String clave) {
        try {
            int idx = json.indexOf("\"" + clave + "\":");
            if (idx < 0) return 0;
            int s = idx + clave.length() + 3;
            int e = json.indexOf(",", s);
            if (e < 0) e = json.indexOf("}", s);
            return Double.parseDouble(json.substring(s, e).trim());
        } catch (Exception ex) {
            return 0;
        }
    }
}
