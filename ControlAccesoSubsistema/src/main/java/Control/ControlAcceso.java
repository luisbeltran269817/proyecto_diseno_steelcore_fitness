/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Excepciones.NegocioException;
import Fachada.Icontrolacceso.AccesoDenegadoException;
import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.MembresiaDTO.EstadoMembresia;
import dtos.VisitaDTO;
import Fachada.Icontrolacceso.ResultadoAccesoDTO;
import dtos.SucursalDTO;
import interfaces.IClienteBO;
import interfaces.IMembresiaBO;
import interfaces.ISucursalBO;
import interfaces.IVisitaBO;
import objetosnegocios.ClienteBO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.VisitaBO;
import java.time.LocalDateTime;
import java.util.List;
import objetosnegocios.SucursalBO;


/**
 *
 * @author julian izaguirre
 */
public class ControlAcceso {
    private final IClienteBO   clienteBO;
    private final IMembresiaBO membresiaBO;
    private final ISucursalBO  sucursalBO;
    private final IVisitaBO    visitaBO;
    private String idSucursalLocal;

    public ControlAcceso() {
        this.clienteBO   = new ClienteBO();
        this.membresiaBO = new MembresiaBO();
        this.sucursalBO  = new SucursalBO();
        this.visitaBO    = new VisitaBO();
        this.idSucursalLocal = null; // sin restricción por defecto
    }

    /**
     * Configura la sucursal de este terminal.
     * Llamar desde FachadaControlAcceso si se desea validar sucursal.
     */
    public void setIdSucursalLocal(String idSucursal) {
        this.idSucursalLocal = idSucursal;
    }

    /**
     * Procesa el código QR escaneado en recepción.
     *
     * @param codigoQR string extraído del QR (es el campo codigoQR de MembresiaDTO)
     * @return ResultadoAccesoDTO con cliente, membresía y visita ya registrada
     * @throws AccesoDenegadoException si cualquier validación falla
     */
    //ARREGLAR LO DEL MANEJO DE EXCEPCIONES
    public ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException, NegocioException {
        if (codigoQR == null || codigoQR.isBlank()) {
            throw new AccesoDenegadoException("Código QR vacío o inválido.");
        }

        MembresiaDTO membresia = buscarMembresiaPorQR(codigoQR);
        if (membresia == null) {
            throw new AccesoDenegadoException(
                "El código QR no corresponde a ningún socio registrado.");
        }

        if (membresia.getEstado() != EstadoMembresia.ACTIVA) {
            String razon = switch (membresia.getEstado()) {
                case VENCIDA    -> "Membresía vencida.";
                case CANCELADA  -> "Membresía cancelada.";
                default         -> "Membresía no activa.";
            };
            throw new AccesoDenegadoException(razon);
        }

        if (membresia.getFechaCaducidad() != null
                && membresia.getFechaCaducidad().isBefore(LocalDateTime.now())) {
            throw new AccesoDenegadoException(
                "Membresía vencida el "
                + membresia.getFechaCaducidad().toLocalDate() + ".");
        }

        if (idSucursalLocal != null
                && membresia.getIdSucursal() != null
                && !idSucursalLocal.equals(membresia.getIdSucursal())) {

            String nombreSucursalRegistrada = resolverNombreSucursal(membresia.getIdSucursal());
            throw new AccesoDenegadoException(
                "Esta membresía pertenece a la sucursal " + nombreSucursalRegistrada
                + ".\nNo tiene acceso a esta ubicación.");
        }

        ClienteDTO cliente = clienteBO.buscarPorCorreo(membresia.getIdCliente());
        if (cliente == null) {
            throw new AccesoDenegadoException("Socio no encontrado en el sistema.");
        }

        String idSucursalParaRegistro = (idSucursalLocal != null)
                ? idSucursalLocal
                : membresia.getIdSucursal();
        VisitaDTO  vis = new VisitaDTO();
        vis.setGimnasio("Menchaca");
        vis.setCalle("Lo");
        vis.setColonia("siento");
        vis.setCiudad("Mucho");
        //Menchaca tendrás que arreglar esto, ya es muy tarde y nisiquiera e probado que persistencia funcione bien ayudaaaaa
        VisitaDTO visita = visitaBO.guardar(membresia.getIdCliente(), idSucursalLocal, vis);


        return new ResultadoAccesoDTO(cliente, membresia, visita);
    }

    /**
     * Busca una membresia por su codigoQR.
     * Soporta dos formatos:
     *   1. URL completa: https://steelcorefitness.com/acceso?id=<idMembresia>&...
     *      -> extrae el parametro "id" y busca por idMembresia directamente (O(1))
     *   2. Cualquier otro string -> comparacion exacta con m.getCodigoQR()
     */
    private MembresiaDTO buscarMembresiaPorQR(String codigoQR) throws NegocioException {
        // Intento rapido: si es la URL canonica, extraer el ?id= y buscar directo
        String idExtraido = extraerIdDeUrl(codigoQR);
        if (idExtraido != null) {
            MembresiaDTO m = membresiaBO.buscarPorId(idExtraido);
            if (m != null) return m;
        }//Menchaca tendrás que agregar ese método tú
        
        
        // Fallback: busqueda por coincidencia exacta del campo codigoQR
        //for (ClienteDTO c : clienteBO.obtenerClientes()) {
            //List<MembresiaDTO> lista = membresiaBO.obtenerPorCliente(c.getCorreo());
            //for (MembresiaDTO m : lista) {
              //  if (codigoQR.equals(m.getCodigoQR())) {
                //    return m;
                //}
            //}
        //}
        return null;
    }

    /**
     * Extrae el parametro "id" de una URL tipo
     * https://steelcorefitness.com/acceso?id=M001&cliente=...
     * Devuelve null si la URL no tiene ese parametro.
     */
    private String extraerIdDeUrl(String url) {
        if (url == null || !url.contains("?id=")) return null;
        try {
            int inicio = url.indexOf("?id=") + 4;
            int fin = url.indexOf("&", inicio);
            return (fin < 0) ? url.substring(inicio) : url.substring(inicio, fin);
        } catch (Exception e) {
            return null;
        }
    }

    private String resolverNombreSucursal(String idSucursal) {
        try {
            SucursalDTO s = sucursalBO.buscarPorId(idSucursal);
            return (s != null && s.getNombre() != null) ? s.getNombre() : idSucursal;
        } catch (Exception e) {
            return idSucursal;
        }
    }

}