/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import dtos.AmenidadDTO;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.EntrenadorDTO;
import dtos.HorarioDTO;
import dtos.MembresiaDTO;
import dtos.MembresiaDTO.EstadoMembresia;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * almacen temporal, antes en BO y ahroa en DAO
 * @author julian izaguirre
 */
public class AlmacenComprarMembresiaMock {
    private static AlmacenComprarMembresiaMock instancia;

    public static AlmacenComprarMembresiaMock getInstancia() {
        if (instancia == null) {
            instancia = new AlmacenComprarMembresiaMock();
        }
        return instancia;
    }
    
    private final Map<String, PlanDTO> planes;
    private final Map<String, SucursalDTO> sucursales;
    private final Map<String, MembresiaDTO> membresias;
    private final Map<String, UsuarioDTO> usuarios;
    private final Map<String, List<VisitaDTO>> visitas;

    private final Map<String, EntrenadorDTO> entrenadores;
    private final Map<String, CitaDTO> citas;

    private final Map<String, AmenidadDTO> amenidades;
    private final Map<String, HorarioDTO> horarios;

    private AlmacenComprarMembresiaMock() {
        planes = new LinkedHashMap<>();
        sucursales = new LinkedHashMap<>();
        membresias = new LinkedHashMap<>();
        usuarios = new LinkedHashMap<>();
        visitas = new LinkedHashMap<>();
        entrenadores = new LinkedHashMap<>();
        citas = new LinkedHashMap<>();

        amenidades = new LinkedHashMap<>();
        horarios = new LinkedHashMap<>();

        poblarDatos();
    }
    private void poblarDatos() {
        AmenidadDTO pesas = new AmenidadDTO();
        pesas.setIdAmenidad("A001");
        pesas.setNombre("Pesas");
        pesas.setTipo(AmenidadDTO.TipoAmenidad.BASICA);
        pesas.setCosto(0.0);

        AmenidadDTO alberca = new AmenidadDTO();
        alberca.setIdAmenidad("A002");
        alberca.setNombre("Alberca");
        alberca.setTipo(AmenidadDTO.TipoAmenidad.EXTRA);
        alberca.setCosto(250.0);

        amenidades.put(pesas.getIdAmenidad(), pesas);
        amenidades.put(alberca.getIdAmenidad(), alberca);
        
        PlanDTO mensual = new PlanDTO();
        mensual.setIdPlan("P001");
        mensual.setNombre("Mensual");
        mensual.setPrecio(599.0);
        mensual.setMesesDuracion(1);

        mensual.setAmenidades(List.of(pesas));

        planes.put(mensual.getIdPlan(), mensual);

        SucursalDTO sucursal = new SucursalDTO();
        sucursal.setIdSucursal("S001");
        sucursal.setNombre("Centro");
        sucursal.setCiudad("Hermosillo");
        sucursal.setColonia("Centro");

        sucursal.setPlanes(List.of(mensual));

        sucursales.put(sucursal.getIdSucursal(), sucursal);

        HorarioDTO h1 = new HorarioDTO();
        h1.setIdHorario("H001");
        h1.setIdEntrenador("E001"); 
        h1.setInicio(LocalDateTime.now().plusDays(1).withHour(10));
        h1.setFin(LocalDateTime.now().plusDays(1).withHour(11));
        h1.setDisponible(true);

        horarios.put(h1.getIdHorario(), h1);

        EntrenadorDTO entrenador = new EntrenadorDTO();
        entrenador.setIdEntrenador("E001");
        entrenador.setNombre("Carlos");

        entrenador.setSucursales(List.of(sucursal));
        entrenador.setHorarios(List.of(h1));

        entrenadores.put(entrenador.getIdEntrenador(), entrenador);

        ClienteDTO cliente = new ClienteDTO();
        cliente.setCorreo("cliente@gmail.com");
        cliente.setContraseña("123");
        cliente.setNombre("Juan Leonel");
        cliente.setRol(UsuarioDTO.Rol.CLIENTE);

        cliente.setMembresias(new ArrayList<>());

        usuarios.put(cliente.getCorreo(), cliente);

 
        MembresiaDTO m = new MembresiaDTO();
        m.setIdMembresia("M001");
        m.setIdCliente(cliente.getCorreo());

        m.setIdPlan("P001");
        m.setIdSucursal("S001");

        m.setAmenidadesExtra(List.of(alberca));

        m.setEstado(EstadoMembresia.ACTIVA);
        m.setFechaTramite(LocalDateTime.now().minusDays(2));
        m.setFechaCaducidad(LocalDateTime.now().plusMonths(1));

        membresias.put(m.getIdMembresia(), m);

        cliente.getMembresias().add(m);

        CitaDTO cita = new CitaDTO();
        cita.setIdCita("C001");
        cita.setIdCliente(cliente.getCorreo());
        cita.setIdEntrenador("E001");
        cita.setIdSucursal("S001");
        cita.setIdHorario("H001");
        cita.setEstado(CitaDTO.EstadoCita.COMPLETADA);

        citas.put(cita.getIdCita(), cita);

        cliente.setIdCitaBienvenida("C001");

        VisitaDTO v1 = new VisitaDTO();
        v1.setGimnasio(sucursal.getNombre());
        v1.setCalle("Calle X");
        v1.setColonia("Centro");
        v1.setCiudad("Hermosillo");
        v1.setFechaHora(LocalDateTime.now().minusDays(1));

        visitas.put(cliente.getCorreo(), new ArrayList<>(List.of(v1)));
    }

    public Map<String, PlanDTO> getPlanes() {
        return planes;
    }

    public Map<String, SucursalDTO> getSucursales() {
        return sucursales;
    }

    public Map<String, MembresiaDTO> getMembresias() {
        return membresias;
    }

    public Map<String, UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public Map<String, List<VisitaDTO>> getVisitas() {
        return visitas;
    }

    public Map<String, EntrenadorDTO> getEntrenadores() {
        return entrenadores;
    }

    public Map<String, CitaDTO> getCitas() {
        return citas;
    }

    public Map<String, AmenidadDTO> getAmenidades() {
        return amenidades;
    }

    public Map<String, HorarioDTO> getHorarios() {
        return horarios;
    }

    
    
    
    
}
