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
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.VisitaDTO;
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
    
    public ControlComprarMembresia() {
        this.planBO = new PlanBO();
        this.sucursalBO = new SucursalBO();
        this.amenidadBO = new AmenidadBO();
        this.membresiaBO = new MembresiaBO();
        this.clienteBO = new ClienteBO();
        this.entrenadorBO = new EntrenadorBO();
        this.horarioBO = new HorarioBO();
        this.citaBO = new CitaBO();
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
    
}
