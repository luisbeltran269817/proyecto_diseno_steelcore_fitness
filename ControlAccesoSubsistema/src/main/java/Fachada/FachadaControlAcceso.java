/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 * Fachada principal que usan las pantallas para hablar con el sistema de acceso
 *
 * @author julian izaguirre
 */
public class FachadaControlAcceso implements Icontrolacceso {

    private static FachadaControlAcceso instancia;
    private final ControlAcceso control;

    private HttpServer servidorQR;
    private static final int PUERTO_QR = 8787;

    /**
     * Constructor privado para asegurarnos de que solo exista una fachada
     */
    private FachadaControlAcceso() {
        this.control = new ControlAcceso();
    }

    /** 
     * Te da la unica instancia de la fachada para todo el programa
     * 
     * @return El objeto fachada
     */
    public static FachadaControlAcceso getInstancia() {
        if (instancia == null) {
            instancia = new FachadaControlAcceso();
        }
        return instancia;
    }

    /**
     * Guarda la sucursal actual para no dejar entrar a gente de otras ubicaciones
     *
     * @param idSucursal Identificador de la sucursal local
     */
    public void configurarSucursal(String idSucursal) {
        control.setIdSucursalLocal(idSucursal);
    }

    /**
     * Delega el escaneo del codigo al controlador principal
     * 
     * @param codigoQR Texto del QR
     * @return Los datos de la visita
     * @throws AccesoDenegadoException Si el acceso se rechaza
     */
    @Override
    public ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException {
        return control.procesarQR(codigoQR);
    }

    /**
     * Le avisa al controlador que el socio solo usara el area comun
     * 
     * @param idVisita Visita activa
     * @throws AccesoDenegadoException Si falla al guardar
     */
    @Override
    public void registrarAreaGeneral(String idVisita) throws AccesoDenegadoException {
        control.registrarAreaGeneral(idVisita);
    }

    /**
     * Pide al controlador la lista de entrenadores disponibles
     * 
     * @param idSucursal Sucursal actual
     * @return Lista de entrenadores
     * @throws AccesoDenegadoException Si hay un error en la consulta
     */
    @Override
    public List<EntrenadorDTO> obtenerEntrenadoresDisponibles(String idSucursal)
            throws AccesoDenegadoException {
        return control.obtenerEntrenadoresDisponibles(idSucursal);
    }

    /**
     * Manda la orden al controlador de apartarle el entrenador al socio
     * 
     * @param idVisita Visita activa
     * @param idCliente Identificador del socio
     * @param idEntrenador Entrenador seleccionado
     * @param idHorario Horario que aparto
     * @throws AccesoDenegadoException Si ocurre un problema al asignar
     */
    @Override
    public void asignarEntrenador(String idVisita, String idCliente,
                                   String idEntrenador, String idHorario)
            throws AccesoDenegadoException {
        control.asignarEntrenador(idVisita, idCliente, idEntrenador, idHorario);
    }

    /**
     * Le pide al controlador buscar las clases que puede tomar el socio
     * 
     * @param idSucursal Sucursal a consultar
     * @param idPlan Plan del socio
     * @param idCliente Socio a revisar
     * @param incluyeClases Permiso segun su plan
     * @return Lista de clases disponibles
     * @throws AccesoDenegadoException Si la membresia no deja
     */
    @Override
    public List<ClaseDTO> obtenerClasesPorPlan(String idSucursal, String idPlan,
                                                String idCliente, boolean incluyeClases)
            throws AccesoDenegadoException {
        return control.obtenerClasesPorPlan(idSucursal, idPlan, idCliente, incluyeClases);
    }

    /**
     * Pasa los datos al controlador para registrar al socio en una clase
     * 
     * @param idVisita Visita actual
     * @param idClase Clase elegida
     * @param idCliente Socio a inscribir
     * @throws AccesoDenegadoException Si la clase esta llena o falla
     */
    @Override
    public void inscribirClase(String idVisita, String idClase, String idCliente)
            throws AccesoDenegadoException {
        control.inscribirClase(idVisita, idClase, idCliente);
    }

    /**
     * Prende un mini servidor para que el celular del socio pueda ver la imagen del QR
     *
     * @param qrPng Imagen en bytes del QR
     * @return La url para conectarse
     */
    public String iniciarServidorQR(byte[] qrPng) {
        detenerServidorQR();
        try {
            servidorQR = HttpServer.create(new InetSocketAddress("0.0.0.0", PUERTO_QR), 0);

            servidorQR.createContext("/qr", exchange -> {
                exchange.getResponseHeaders().set("Content-Type", "image/png");
                exchange.getResponseHeaders().set("Cache-Control", "no-cache");
                exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
                exchange.sendResponseHeaders(200, qrPng.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(qrPng);
                }
            });

            servidorQR.createContext("/", exchange -> {
                String html = "<html><body style='margin:0;background:#1a1a3e;"
                        + "display:flex;justify-content:center;align-items:center;"
                        + "min-height:100vh'>"
                        + "<img src='/qr' style='max-width:95vmin;max-height:95vmin;"
                        + "border-radius:12px'>"
                        + "</body></html>";
                byte[] resp = html.getBytes("UTF-8");
                exchange.getResponseHeaders().set("Content-Type", "text/html;charset=UTF-8");
                exchange.sendResponseHeaders(200, resp.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(resp);
                }
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

    /** 
     * Apaga el servidor local del QR para liberar el puerto
     */
    public void detenerServidorQR() {
        if (servidorQR != null) {
            servidorQR.stop(0);
            servidorQR = null;
        }
    }

    /**
     * Baja la imagen del QR desde el servidor que levantamos
     *
     * @return Los bytes de la imagen o null si no se pudo conectar
     */
    public byte[] obtenerQRDelServidor() {
        try {
            java.net.URL url = new java.net.URL("http://localhost:" + PUERTO_QR + "/qr");
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(150);
            conn.setReadTimeout(150);
            if (conn.getResponseCode() == 200) {
                return conn.getInputStream().readAllBytes();
            }
        } catch (Exception ignorada) {}
        return null;
    }

    /**
     * Busca la direccion de la compu en la red para armar el link del servidor
     * 
     * @return La IP encontrada o localhost si falla
     */
    private String obtenerIPLocal() {
        try {
            Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
            while (ifaces.hasMoreElements()) {
                NetworkInterface iface = ifaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) continue;
                Enumeration<java.net.InetAddress> addrs = iface.getInetAddresses();
                while (addrs.hasMoreElements()) {
                    java.net.InetAddress addr = addrs.nextElement();
                    if (addr instanceof Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ignorada) {}
        return "localhost";
    }
}