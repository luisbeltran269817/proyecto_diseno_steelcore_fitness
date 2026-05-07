/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import objetosnegocios.MembresiaBO;
import objetosnegocios.PlanBO;
import dtos.MembresiaDTO;
import dtos.MembresiaDTO.EstadoMembresia;
import dtos.PeticionPagoGenDTO;
import dtos.PlanDTO;
import dtos.RespuestaPagoGenDTO;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
import fachada.FachadaPagoMembresiaStripe;
import fachada.IPagoMembresiaStripe;
import interfaces.IAmenidadBO;
import interfaces.ICitaBO;
import interfaces.IClienteBO;
import interfaces.IEntrenadorBO;
import interfaces.IHorarioBO;
import interfaces.IMembresiaBO;
import interfaces.IPlanBO;
import interfaces.ISucursalBO;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import objetosnegocios.AmenidadBO;
import objetosnegocios.CitaBO;
import objetosnegocios.ClienteBO;
import objetosnegocios.EntrenadorBO;
import objetosnegocios.HorarioBO;
import objetosnegocios.SucursalBO;
import patronBuilder.MembresiaBuilder;

/**
 *
 * @author julian izaguirre
 */
public class ControlComprarMembresia {

    private final IPlanBO planBO;
    private final ISucursalBO sucursalBO;
    private final IAmenidadBO amenidadBO;
    private final IMembresiaBO membresiaBO;
    private final IClienteBO clienteBO;
    private final IEntrenadorBO entrenadorBO;
    private final IHorarioBO horarioBO;
    private final ICitaBO citaBO;
    private final IPagoMembresiaStripe pagoFachada;

    public ControlComprarMembresia() {
        this.planBO = new PlanBO();
        this.sucursalBO = new SucursalBO();
        this.amenidadBO = new AmenidadBO();
        this.membresiaBO = new MembresiaBO();
        this.clienteBO = new ClienteBO();
        this.entrenadorBO = new EntrenadorBO();
        this.horarioBO = new HorarioBO();
        this.citaBO = new CitaBO();
        this.pagoFachada = new FachadaPagoMembresiaStripe();
    }

    public List<SucursalDTO> obtenerSucursales() {
        return sucursalBO.obtenerTodas();
    }

    public List<PlanDTO> obtenerPlanes(String idSucursal) {
        return planBO.obtenerPorSucursal(idSucursal);
    }

    public List<AmenidadDTO> obtenerAmenidadesDePlan(String idPlan) {
        PlanDTO plan = planBO.buscarPorId(idPlan);
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
            String idSucursal, List<AmenidadDTO> extras, String tokenTarjeta) {

        if (tieneMembresiaActiva(idCliente)) {
            throw new RuntimeException("El cliente ya tiene una membresía activa");
        }

        double total = calcularTotal(idPlan, extras);

        PeticionPagoGenDTO peticion = new PeticionPagoGenDTO();
        peticion.setMonto(total);
        peticion.setDescripcion("Compra de membresía SteelCore");
        peticion.setTokenTarjeta(tokenTarjeta);

        RespuestaPagoGenDTO respuesta = pagoFachada.procesarPago(peticion);
        if (!respuesta.isExitoso()) {
            throw new RuntimeException("Pago rechazado: " + respuesta.getMensaje());
        }

        return crearMembresia(idCliente, idPlan, idSucursal, extras);
    }

    private MembresiaDTO crearMembresia(String idCliente, String idPlan,
            String idSucursal, List<AmenidadDTO> extras) {

        PlanDTO plan = planBO.buscarPorId(idPlan);
        SucursalDTO sucursal = sucursalBO.buscarPorId(idSucursal);

        MembresiaDTO m = new MembresiaBuilder()
                .setCliente(idCliente)
                .setPlan(plan)
                .setSucursal(sucursal)
                .setExtras(extras)
                .setMetodoPago("Tarjeta")
                .build();

        m.setIdMembresia(UUID.randomUUID().toString());
        m.setFechaTramite(LocalDateTime.now());
        m.setFechaCaducidad(LocalDateTime.now().plusMonths(plan.getMesesDuracion()));
        m.setEstado(MembresiaDTO.EstadoMembresia.ACTIVA);
        m.setCodigoQR("steelcore://acceso/" + m.getIdMembresia());

        membresiaBO.guardar(m);

        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        cliente.getMembresias().add(m);
        clienteBO.actualizar(cliente);

        return m;
    }

    /**
     * Genera los bytes PNG del código QR para una membresía.
     *
     * El contenido del QR es el token de acceso almacenado en la membresía
     * (campo codigoQR), con el esquema "steelcore://acceso/{idMembresia}". El
     * empleado escanea este QR con la misma app de escritorio para validar el
     * acceso del cliente.
     *
     * @param idMembresia identificador de la membresía a codificar
     * @return bytes PNG listos para ser convertidos en ImageIcon, o null si
     * falla
     */
    public byte[] generarQRMembresia(String idMembresia) {
        MembresiaDTO m = membresiaBO.buscarPorId(idMembresia);
        String contenido = (m != null && m.getCodigoQR() != null)
                ? m.getCodigoQR()
                : "steelcore://acceso/" + idMembresia;

        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 300, 300);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out);
            return out.toByteArray();

        } catch (WriterException | java.io.IOException e) {
            System.err.println("[QR] Error al generar QR: " + e.getMessage());
            return null;
        }
    }

    // ── Entrenadores / Horarios / Citas ───────────────────────────────────────
    public List<EntrenadorDTO> obtenerEntrenadores(String idSucursal) {
        return entrenadorBO.obtenerPorSucursal(idSucursal);
    }

    public List<HorarioDTO> obtenerHorarios(String idEntrenador) {
        return horarioBO.obtenerDisponiblesPorEntrenador(idEntrenador);
    }

    public CitaDTO agendarCitaBienvenida(String idCliente, String idEntrenador,
            String idSucursal, String idHorario) {

        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        if (cliente.getIdCitaBienvenida() != null) {
            throw new RuntimeException("El cliente ya cuenta con una cita de bienvenida");
        }

        HorarioDTO horario = horarioBO.buscarPorId(idHorario);
        if (!horario.isDisponible()) {
            throw new RuntimeException("Horario no disponible");
        }

        CitaDTO cita = new CitaDTO();
        cita.setIdCita(UUID.randomUUID().toString());
        cita.setIdCliente(idCliente);
        cita.setIdEntrenador(idEntrenador);
        cita.setIdSucursal(idSucursal);
        cita.setIdHorario(idHorario);
        cita.setFechaHora(horario.getInicio());
        cita.setEstado(CitaDTO.EstadoCita.PENDIENTE);

        citaBO.guardar(cita);

        horario.setDisponible(false);
        horarioBO.actualizar(horario);

        cliente.setIdCitaBienvenida(cita.getIdCita());
        clienteBO.actualizar(cliente);

        return cita;
    }

    public boolean hayHorariosDisponibles(String idEntrenador) {
        return !obtenerHorarios(idEntrenador).isEmpty();
    }

    public CitaDTO obtenerCitaBienvenida(String idCliente) {
        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        if (cliente == null || cliente.getIdCitaBienvenida() == null) {
            return null;
        }
        return citaBO.buscarPorId(cliente.getIdCitaBienvenida());
    }

    // ── Membresía activa / historial ──────────────────────────────────────────
    public boolean tieneMembresiaActiva(String idCliente) {
        return obtenerMembresiaActiva(idCliente) != null;
    }

    public MembresiaDTO obtenerMembresiaActiva(String idCliente) {
        for (MembresiaDTO m : membresiaBO.obtenerPorCliente(idCliente)) {
            if (m.getEstado() == EstadoMembresia.ACTIVA) {
                return m;
            }
        }
        return null;
    }

    public List<VisitaDTO> obtenerHistorial(String idCliente) {
        return clienteBO.obtenerHistorial(idCliente);
    }

    public void cancelarMembresia(String idCliente) {
        MembresiaDTO m = obtenerMembresiaActiva(idCliente);
        if (m != null) {
            m.setEstado(EstadoMembresia.CANCELADA);
            membresiaBO.actualizar(m);
        }
    }

    // ── Cálculo de precio ─────────────────────────────────────────────────────
    public double calcularTotal(String idPlan, List<AmenidadDTO> extras) {
        PlanDTO plan = planBO.buscarPorId(idPlan);
        double base = (plan != null && plan.getPrecio() != null) ? plan.getPrecio() : 0.0;
        double extra = (extras != null)
                ? extras.stream().mapToDouble(AmenidadDTO::getCosto).sum()
                : 0.0;
        return base + extra;
    }

}
