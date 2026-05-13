/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
    //ARREGLADO
    public List<SucursalDTO> obtenerSucursales() throws NegocioException {
        return sucursalBO.obtenerSucursales();
    }
    //ARREGLADO
    public List<PlanDTO> obtenerPlanes(String idSucursal) {
        return planBO.obtenerPorSucursal(idSucursal);
    }
    //ARREGLADO
    public List<AmenidadDTO> obtenerAmenidadesDePlan(String idPlan) {
        PlanDTO plan = planBO.buscarPorId(idPlan);
        if (plan == null || plan.getAmenidades() == null) {
            return new ArrayList<>();
        }
        return plan.getAmenidades();
    }
    //ARREGLADO
    public List<AmenidadDTO> obtenerAmenidadesExtra() {
        return amenidadBO.obtenerTodas().stream()
                .filter(a -> a.getTipo() == AmenidadDTO.TipoAmenidad.EXTRA)
                .toList();
    }
    //FALTAN COSAS DE AQUÍ
    public MembresiaDTO comprarMembresia(String idCliente, String idPlan,
            String idSucursal, List<AmenidadDTO> extras, String tokenTarjeta) throws NegocioException {

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
            String idSucursal, List<AmenidadDTO> extras) throws NegocioException {

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

        String fechaVigencia = m.getFechaCaducidad()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String urlQR = "https://steelcorefitness.com/acceso"
                + "?id="       + m.getIdMembresia()
                + "&cliente="  + idCliente
                + "&plan="     + idPlan
                + "&sucursal=" + idSucursal
                + "&vigencia=" + fechaVigencia;
        m.setCodigoQR(urlQR);
        
        membresiaBO.guardar(m);

        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        cliente.getMembresias().add(m);
        clienteBO.actualizar(cliente);

        return m;
    }

    /**
     * Genera los bytes PNG del código QR para una membresía.
     *
     * El contenido del QR es la URL real almacenada en m.getCodigoQR(),
     * con formato https://steelcorefitness.com/acceso?id=...
     * Cualquier escáner de celular (Google Lens, cámara nativa) puede
     * abrirla directamente en el navegador.
     *
     * Mejoras aplicadas vs la versión anterior:
     *  - ErrorCorrectionLevel.H (30%): el QR sigue siendo legible aunque
     *    esté parcialmente tapado o sucio.
     *  - Margen 2 (antes era el default de 4): QR más grande en el mismo espacio.
     *  - Fondo blanco forzado: MatrixToImageConfig garantiza contraste máximo.
     *  - 400x400 px en lugar de 300x300: mejor resolución para escanear.
     *
     * @param idMembresia identificador de la membresía a codificar
     * @return bytes PNG listos para convertir en ImageIcon, o null si falla
     */
    public byte[] generarQRMembresia(String idMembresia) {
        // 1. Recuperar la URL almacenada en la membresía
        MembresiaDTO m = membresiaBO.buscarPorId(idMembresia);
        String contenido = (m != null && m.getCodigoQR() != null)
                ? m.getCodigoQR()
                : "https://steelcorefitness.com/acceso?id=" + idMembresia;

        try {
            // 2. Configurar hints para QR de máxima calidad
            Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
            // H = 30% de corrección de errores: sigue siendo legible parcialmente tapado
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            // Margen reducido a 2 para que el QR ocupe más espacio en el panel
            hints.put(EncodeHintType.MARGIN, 2);
            // Codificación UTF-8 explícita
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // 3. Generar la matriz
            QRCodeWriter writer = new QRCodeWriter();
            // 400x400 en lugar de 300x300 para mejor resolución al escanear
            BitMatrix matrix = writer.encode(contenido, BarcodeFormat.QR_CODE, 400, 400, hints);

            // 4. Renderizar: negro sobre fondo BLANCO (contraste máximo)
            //    MatrixToImageConfig(onColor, offColor) — ARGB
            MatrixToImageConfig config = new MatrixToImageConfig(
                    0xFF000000,  // módulos negros
                    0xFFFFFFFF   // fondo blanco
            );

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "PNG", out, config);
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
        // Devuelve la membresia ACTIVA con la fechaTramite mas reciente.
        // Antes devolvía la primera del LinkedHashMap (siempre M001 del mock).
        MembresiaDTO resultado = null;
        for (MembresiaDTO m : membresiaBO.obtenerPorCliente(idCliente)) {
            if (m.getEstado() != EstadoMembresia.ACTIVA) continue;
            if (resultado == null) {
                resultado = m;
            } else if (m.getFechaTramite() != null
                    && resultado.getFechaTramite() != null
                    && m.getFechaTramite().isAfter(resultado.getFechaTramite())) {
                resultado = m;
            }
        }
        return resultado;
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