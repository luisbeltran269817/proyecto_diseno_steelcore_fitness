package Fachada;

import Control.ControlAcceso;
import dtosControlDeAcceso.ClaseDTO;
import dtosControlDeAcceso.EntrenadorDTO;
import dtosControlDeAcceso.ResultadoAccesoDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import com.sun.net.httpserver.HttpServer;

/**
 * Fachada principal que usan las pantallas para hablar con el sistema de acceso.
 * Guarda el contexto de la visita activa (sesion de un socio en el gimnasio)
 * para que el CoordinadorAcceso no necesite almacenar ningun estado.
 *
 * @author julian izaguirre
 */
public class FachadaControlAcceso implements Icontrolacceso {

    private static FachadaControlAcceso instancia;
    private final ControlAcceso control;

    // Este es el unico lugar donde vive el estado de la sesion de acceso.

    private ResultadoAccesoDTO visitaActiva;

    // ── Servidor QR

    private HttpServer servidorQR;
    private static final int PUERTO_QR = 8787;

    private FachadaControlAcceso() {
        this.control = new ControlAcceso();
    }

    public static FachadaControlAcceso getInstancia() {
        if (instancia == null) {
            instancia = new FachadaControlAcceso();
        }
        return instancia;
    }

    public void configurarSucursal(String idSucursal) {
        control.setIdSucursalLocal(idSucursal);
    }

    @Override
    public String getIdVisitaActiva() {
        return visitaActiva != null ? visitaActiva.getIdVisita() : null;
    }

    @Override
    public String getIdClienteActivo() {
        return visitaActiva != null ? visitaActiva.getIdCliente() : null;
    }

    @Override
    public String getIdPlanActivo() {
        return visitaActiva != null ? visitaActiva.getIdPlan() : null;
    }

    @Override
    public boolean isPlanIncluyeEntrenador() {
        return visitaActiva != null && visitaActiva.isPlanIncluyeEntrenador();
    }

    @Override
    public boolean isPlanIncluyeClases() {
        return visitaActiva != null && visitaActiva.isPlanIncluyeClases();
    }

    @Override
    public ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException {
        ResultadoAccesoDTO resultado = control.procesarQR(codigoQR);
        this.visitaActiva = resultado; // la fachada es duena del estado
        return resultado;
    }

    /**
     * Registra area general usando la visita que guardamos al procesar el QR.
     */
    @Override
    public void registrarAreaGeneral() throws AccesoDenegadoException {
        control.registrarAreaGeneral(getIdVisitaActiva());
    }

    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException {
        return control.obtenerEntrenadoresDisponibles(idSucursal);
    }

    /**
     * Asigna entrenador usando el contexto de la visita activa que ya guardamos.
     */
    @Override
    public void asignarEntrenador(String idEntrenador, String idHorario)
            throws AccesoDenegadoException {
        control.asignarEntrenador(
                getIdVisitaActiva(),
                getIdClienteActivo(),
                idEntrenador,
                idHorario);
    }

    /**
     * Obtiene clases usando el plan y cliente de la visita activa.
     */
    @Override
    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal)
            throws AccesoDenegadoException {
        return control.obtenerClasesPorPlan(
                idSucursal,
                getIdPlanActivo(),
                getIdClienteActivo(),
                isPlanIncluyeClases());
    }

    /**
     * Inscribe usando el contexto de visita activa.
     */
    @Override
    public void inscribirClase(String idClase) throws AccesoDenegadoException {
        control.inscribirClase(getIdVisitaActiva(), idClase, getIdClienteActivo());
    }

    // ── Servidor QR

    public String iniciarServidorQR(byte[] qrPng) {
        detenerServidorQR();
        try {
            servidorQR = HttpServer.create(new InetSocketAddress("0.0.0.0", PUERTO_QR), 0);
            servidorQR.createContext("/qr", exchange -> {
                exchange.getResponseHeaders().set("Content-Type", "image/png");
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, qrPng.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(qrPng); }
            });
            servidorQR.createContext("/", exchange -> {
                String html = "<html><body style='margin:0;background:#1a1a3e;"
                        + "display:flex;justify-content:center;align-items:center;"
                        + "min-height:100vh'>"
                        + "<img src='/qr' style='max-width:95vmin;max-height:95vmin;"
                        + "border-radius:12px'></body></html>";
                byte[] resp = html.getBytes("UTF-8");
                exchange.getResponseHeaders().set("Content-Type", "text/html;charset=UTF-8");
                exchange.sendResponseHeaders(200, resp.length);
                try (OutputStream os = exchange.getResponseBody()) { os.write(resp); }
            });
            servidorQR.setExecutor(null);
            servidorQR.start();
            String ip  = obtenerIPLocal();
            String url = "http://" + ip + ":" + PUERTO_QR;
            System.out.println("[QR-Server] " + url);
            return url;
        } catch (IOException e) {
            System.err.println("[QR-Server] No se pudo iniciar: " + e.getMessage());
            return null;
        }
    }

    public void detenerServidorQR() {
        if (servidorQR != null) { servidorQR.stop(0); servidorQR = null; }
    }

    public byte[] obtenerQRDelServidor() {
        try {
            java.net.URL url = new java.net.URL("http://localhost:" + PUERTO_QR + "/qr");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(150);
            conn.setReadTimeout(150);
            if (conn.getResponseCode() == 200) return conn.getInputStream().readAllBytes();
        } catch (Exception ignorada) {}
        return null;
    }

    private String obtenerIPLocal() {
        try {
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;
                Enumeration<java.net.InetAddress> addrs = iface.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    java.net.InetAddress addr = addrs.nextElement();
                    if (addr instanceof Inet4Address) return addr.getHostAddress();
                }
            }
        } catch (Exception ignorada) {}
        return "localhost";
    }
}