package Clase_Control;

import Excepciones.NegocioException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import objetosnegocios.MembresiaBO;
import dtos.MembresiaDTO;
import dtos.MembresiaDTO.EstadoMembresia;
import dtos.PagoDTO;
import dtos.PeticionPagoGenDTO;
import dtos.PlanDTO;
import dtos.RespuestaPagoGenDTO;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
import fachada.FachadaPagoMembresiaStripe;
import fachada.IPagoMembresiaStripe;
import interfaces.IAmenidadBO;
import interfaces.IClienteBO;
import interfaces.IEntrenadorBO;
import interfaces.IMembresiaBO;
import interfaces.ISucursalBO;
import interfaces.IVisitaBO;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import objetosnegocios.AmenidadBO;
import objetosnegocios.ClienteBO;
import objetosnegocios.EntrenadorBO;
import objetosnegocios.SucursalBO;
import objetosnegocios.VisitaBO;
import patronBuilder.MembresiaBuilder;

/**
 * 
 * @author julian izaguirre
 */
public class ControlComprarMembresia {

    private final ISucursalBO sucursalBO;
    private final IAmenidadBO amenidadBO;
    private final IMembresiaBO membresiaBO;
    private final IClienteBO clienteBO;
    private final IEntrenadorBO entrenadorBO;
    private final IVisitaBO visitaBO;
    private final IPagoMembresiaStripe pagoFachada;

    public ControlComprarMembresia() {
        this.sucursalBO = new SucursalBO();
        this.amenidadBO = new AmenidadBO();
        this.membresiaBO = new MembresiaBO();
        this.clienteBO = new ClienteBO();
        this.entrenadorBO = new EntrenadorBO();
        this.visitaBO = new VisitaBO();
        this.pagoFachada = new FachadaPagoMembresiaStripe();
    }

    public List<SucursalDTO> obtenerSucursales() throws NegocioException {
        return sucursalBO.obtenerSucursales();
    }

    public List<PlanDTO> obtenerPlanes(String idSucursal) throws NegocioException {
        return sucursalBO.obtenerPlanesDeSucursal(idSucursal);
    }

    public List<AmenidadDTO> obtenerAmenidadesDePlan(String idPlan) throws NegocioException {
        PlanDTO plan = sucursalBO.buscarPlanPorId(idPlan);
        if (plan == null || plan.getAmenidades() == null) {
            return new ArrayList<>();
        }
        return plan.getAmenidades();
    }

    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return amenidadBO.obtenerTodas().stream()
                .filter(a -> a.getTipo() == AmenidadDTO.TipoAmenidad.EXTRA)
                .toList();
    }

    public MembresiaDTO comprarMembresia(String idCliente, String idPlan,
            String idSucursal, List<AmenidadDTO> extras, String tokenTarjeta) throws NegocioException {

        if (tieneMembresiaActiva(idCliente)) {
            throw new NegocioException("El cliente ya tiene una membresía activa");
        }

        double total = calcularTotal(idPlan, extras);

        PeticionPagoGenDTO peticion = new PeticionPagoGenDTO();
        peticion.setMonto(total);
        peticion.setDescripcion("Compra de membresía SteelCore");
        peticion.setTokenTarjeta(tokenTarjeta);
        RespuestaPagoGenDTO respuesta = pagoFachada.procesarPago(peticion);
        if (!respuesta.isExitoso()) {
            throw new NegocioException("Pago rechazado: " + respuesta.getMensaje());
        }
        PagoDTO pago = new PagoDTO();
        pago.setIdPago(UUID.randomUUID().toString());
        pago.setIdCliente(idCliente);
        pago.setMonto(total);
        pago.setMetodoPago("Tarjeta");
        pago.setEstado(PagoDTO.EstadoPago.COMPLETADO);
        pago.setFecha(LocalDateTime.now());
        return crearMembresia(idCliente, idPlan, idSucursal, extras, pago);
    }

    private MembresiaDTO crearMembresia(String idCliente, String idPlan, String idSucursal,
            List<AmenidadDTO> extras, PagoDTO pago) throws NegocioException {

        PlanDTO plan = sucursalBO.buscarPlanPorId(idPlan);
        SucursalDTO sucursal = sucursalBO.buscarPorId(idSucursal);

        MembresiaDTO m = new MembresiaBuilder()
                .setCliente(idCliente)
                .setPlan(plan)
                .setSucursal(sucursal)
                .setExtras(extras)
                .setMetodoPago("Tarjeta")
                .setPago(pago)
                .build();
        m.setIdMembresia(UUID.randomUUID().toString());
        m.setFechaTramite(LocalDateTime.now());
        m.setFechaCaducidad(LocalDateTime.now().plusMonths(plan.getMesesDuracion()));
        m.setEstado(MembresiaDTO.EstadoMembresia.ACTIVA);

        String fechaVigencia = m.getFechaCaducidad()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String urlQR = "https://steelcorefitness.com/acceso"
                + "?id=" + m.getIdMembresia()
                + "&cliente=" + idCliente
                + "&plan=" + idPlan
                + "&sucursal=" + idSucursal
                + "&vigencia=" + fechaVigencia;
        m.setCodigoQR(urlQR);

        membresiaBO.guardar(m);

        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        cliente.getMembresias().add(m);
        clienteBO.actualizar(cliente);

        return m;
    }

    public byte[] generarQRMembresia(String idMembresia) throws NegocioException {
        MembresiaDTO m = membresiaBO.buscarPorId(idMembresia);
        String contenido = (m != null && m.getCodigoQR() != null)
                ? m.getCodigoQR()
                : "https://steelcorefitness.com/acceso?id=" + idMembresia;

        try {
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 2);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 400, 400, hints);

            MatrixToImageConfig config = new MatrixToImageConfig(0xFF000000, 0xFFFFFFFF);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out, config);
            return out.toByteArray();

        } catch (WriterException | java.io.IOException e) {
            System.err.println("[QR] Error al generar QR: " + e.getMessage());
            return null;
        }
    }

    public List<EntrenadorDTO> obtenerEntrenadores(String idSucursal) throws NegocioException {
        return entrenadorBO.obtenerPorSucursal(idSucursal);
    }

    public List<HorarioDTO> obtenerHorarios(String idEntrenador) throws NegocioException {
        return entrenadorBO.obtenerHorariosEntrenador(idEntrenador);
    }

    public CitaDTO agendarCitaBienvenida(String idCliente, String idEntrenador,
            String idSucursal, String idHorario) throws NegocioException {

        CitaDTO cita = new CitaDTO();
        cita.setIdCita(UUID.randomUUID().toString());
        cita.setIdCliente(idCliente);
        cita.setIdEntrenador(idEntrenador);
        cita.setIdSucursal(idSucursal);
        cita.setIdHorario(idHorario);
        cita.setFechaHora(LocalDateTime.now());
        cita.setEstado(CitaDTO.EstadoCita.PENDIENTE);
        clienteBO.guardarCitaBienvenida(idCliente, cita);

        return cita;
    }

    public boolean hayHorariosDisponibles(String idEntrenador) throws NegocioException {
        return !obtenerHorarios(idEntrenador).isEmpty();
    }

    public CitaDTO obtenerCitaBienvenida(String idCliente) throws NegocioException {
        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        if (cliente == null || cliente.getCitaBienvenida() == null) {
            return null;
        }
        return cliente.getCitaBienvenida();
    }

    public boolean tieneMembresiaActiva(String idCliente) throws NegocioException {
        return obtenerMembresiaActiva(idCliente) != null;
    }

    public MembresiaDTO obtenerMembresiaActiva(String idCliente) throws NegocioException {
        MembresiaDTO membresiaActiva = clienteBO.obtenerMembresiaActiva(idCliente);
        if (membresiaActiva == null) {
            return null;
        }
        return membresiaActiva;
    }

    public List<VisitaDTO> obtenerHistorial(String correo) throws NegocioException {
        ClienteDTO cliente = clienteBO.buscarPorCorreo(correo);
        if (cliente == null) {
            return new ArrayList<>();
        }
        return visitaBO.obtenerPorCliente(cliente.getIdCliente());
    }

    public void cancelarMembresia(String idCliente) throws NegocioException {
        MembresiaDTO m = obtenerMembresiaActiva(idCliente);
        if (m != null) {
            m.setEstado(EstadoMembresia.CANCELADA);
            membresiaBO.actualizar(m);
            clienteBO.eliminarMembresiaActiva(idCliente);
        }
    }

    public double calcularTotal(String idPlan, List<AmenidadDTO> extras) throws NegocioException {
        PlanDTO plan = sucursalBO.buscarPlanPorId(idPlan);
        double base = (plan != null && plan.getPrecio() != null) ? plan.getPrecio() : 0.0;
        double extra = (extras != null)
                ? extras.stream().mapToDouble(AmenidadDTO::getCosto).sum()
                : 0.0;
        return base + extra;
    }
}