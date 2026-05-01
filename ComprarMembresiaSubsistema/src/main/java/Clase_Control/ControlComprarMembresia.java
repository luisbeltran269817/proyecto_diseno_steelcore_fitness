/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clase_Control;

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
    //Se conecta con el subsistema de el pago de membresia
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
        this.pagoFachada= new FachadaPagoMembresiaStripe();
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
        return amenidadBO.obtenerTodas().stream().filter(a -> a.getTipo() == AmenidadDTO.TipoAmenidad.EXTRA).toList();
    }
    
    public MembresiaDTO crearMembresia(String idCliente, String idPlan, String idSucursal, List<AmenidadDTO> extras) {
        PlanDTO plan         = planBO.buscarPorId(idPlan);
        SucursalDTO sucursal = sucursalBO.buscarPorId(idSucursal);

        MembresiaDTO m = new MembresiaBuilder().setCliente(idCliente).setPlan(plan).setSucursal(sucursal)
            .setExtras(extras).setMetodoPago("Tarjeta").build();

        m.setIdMembresia(UUID.randomUUID().toString());
        m.setFechaTramite(LocalDateTime.now());
        m.setFechaCaducidad(LocalDateTime.now().plusMonths(plan.getMesesDuracion()));
        m.setEstado(MembresiaDTO.EstadoMembresia.ACTIVA);
        m.setCodigoQR("QR-" + UUID.randomUUID());

        membresiaBO.guardar(m);

        ClienteDTO cliente = clienteBO.buscarPorCorreo(idCliente);
        cliente.getMembresias().add(m);
        clienteBO.actualizar(cliente);

        return m;
    }
    /**
     * Método que termina el flujo completo de compra de mebresia, conectandose con la API de Stripe y al final persisteiendo la membresia
     * @param idCliente el id del cliente que compra
     * @param idPlan el id del plan a contratar
     * @param idSucursal el id de la sucursal donde compras la membresia
     * @param extras las amenidades extra si es que se incluyen
     * @param tokenTarjeta el token de la tarjeta
     * @return la membresiaDTO creada
     */
    public MembresiaDTO comprarMembresia(String idCliente,String idPlan,String idSucursal,List<AmenidadDTO> extras,String tokenTarjeta) {
        //Se valida que el cleinte no cuente con una membresia activa
        if (tieneMembresiaActiva(idCliente)) {
            throw new RuntimeException("El cliente ya tiene una membresía activa");
        }
        //Calcula el total de la compra de membresia usando los extras
        double total = calcularTotal(idPlan, extras);
        //Creamos el DTO del pago (no el de infraestructura)
        PeticionPagoGenDTO peticion = new PeticionPagoGenDTO();
        peticion.setMonto(total);
        peticion.setDescripcion("Compra de membresía");
        peticion.setTokenTarjeta(tokenTarjeta);
        //manda a llamar a la fachada del pago para procesar el pago con Stripe
        RespuestaPagoGenDTO respuesta = pagoFachada.procesarPago(peticion);
        //Se valida si todo salió bien
        if (!respuesta.isExitoso()) {
            throw new RuntimeException("Pago rechazado: " + respuesta.getMensaje());
        }
        //Si fue exitoso se guarda
        MembresiaDTO membresia = crearMembresia(idCliente, idPlan, idSucursal, extras);
        //Regresamos la membresia agregada
        return membresia;
    }
    
    public List<EntrenadorDTO> obtenerEntrenadores(String idSucursal) {
       return entrenadorBO.obtenerPorSucursal(idSucursal);
    }
    
    public List<HorarioDTO> obtenerHorarios(String idEntrenador) {
        return horarioBO.obtenerDisponiblesPorEntrenador(idEntrenador);
    }
    
    public CitaDTO agendarCitaBienvenida(String idCliente,String idEntrenador,String idSucursal, String idHorario) {

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
    
    public boolean tieneMembresiaActiva(String idCliente) {
        return obtenerMembresiaActiva(idCliente) != null;
    }

    public MembresiaDTO obtenerMembresiaActiva(String idCliente) {

        List<MembresiaDTO> lista = membresiaBO.obtenerPorCliente(idCliente);

        for (MembresiaDTO m : lista) {
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
    
    /**
     * Método que calcula el total de una compra de membresia
     * @param idPlan el plan que se va a comprar
     * @param extras los extras que se agregarán al precio de la compra de membresía
     * @return el total de la compra
     */
    public double calcularTotal(String idPlan, List<AmenidadDTO> extras) {
        PlanDTO plan = planBO.buscarPorId(idPlan);
        double base = 0.0;
        if (plan != null && plan.getPrecio() != null) {
            base = plan.getPrecio();
        }
        double extra = 0.0;
        if (extras != null) {
            //Streams de la clase del profe Quiñonez
            extra = extras.stream().mapToDouble(AmenidadDTO::getCosto).sum();
        }
        return base + extra;
    }
    
    
    
    
}
