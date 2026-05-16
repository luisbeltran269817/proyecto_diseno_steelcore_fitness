/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PobladorBD;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import dominios.AmenidadPojo.TipoAmenidad;
import dominios.CitaPojo;
import dominios.CitaPojo.EstadoCitaPojo;
import dominios.ClientePojo;
import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import dominios.MembresiaActivaPojo;
import dominios.MembresiaPojo;
import dominios.MembresiaPojo.EstadoMembresiaPojo;
import dominios.PagoPojo;
import dominios.PagoPojo.EstadoPagoPojo;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import dominios.UsuarioPojo;
import dominios.UsuarioPojo.RolUsuarioPojo;
import dominios.VisitaPojo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import mappersPersistencia.ClientePersistenciaMapper;
import mappersPersistencia.EntrenadorPersistenciaMapper;
import mappersPersistencia.MembresiaPersistenciaMapper;
import mappersPersistencia.SucursalPersistenciaMapper;
import mappersPersistencia.VisitaPersistenciaMapper;
import org.bson.Document;

/**
 * Poblador de datos de prueba para MongoDB.
 * 
 * @author Tungs
 */
public class DatosPruebaMongo {

    public static void main(String[] args) {
        poblarBD();
    }

    public static void poblarBD() {

        MongoDatabase bd = MongoConexion.obtenerBaseDatos();

        /*
         * Limpiar colecciones
         */
        bd.getCollection("sucursales").deleteMany(new Document());
        bd.getCollection("entrenadores").deleteMany(new Document());
        bd.getCollection("clientes").deleteMany(new Document());
        bd.getCollection("membresias").deleteMany(new Document());
        bd.getCollection("visitas").deleteMany(new Document());

        /*
         * =========================
         * AMENIDADES (solo como objetos en memoria, NO se persisten solas)
         * =========================
         */
        AmenidadPojo pesas = new AmenidadPojo();
        pesas.setIdAmenidad("A001");
        pesas.setNombre("Pesas");
        pesas.setDescripcion("Área de pesas");
        pesas.setTipo(TipoAmenidad.BASICA);
        pesas.setCosto(0.0);

        AmenidadPojo alberca = new AmenidadPojo();
        alberca.setIdAmenidad("A002");
        alberca.setNombre("Alberca");
        alberca.setDescripcion("Acceso a alberca");
        alberca.setTipo(TipoAmenidad.EXTRA);
        alberca.setCosto(250.0);

        /*
         * =========================
         * PLANES
         * =========================
         */
        PlanPojo mensual = new PlanPojo();
        mensual.setIdPlan("P001");
        mensual.setNombre("Mensual");
        mensual.setDescripcion("Plan mensual");
        mensual.setPrecio(599.0);
        mensual.setMesesDuracion(1);
        mensual.setAmenidades(List.of(pesas));

        /*
         * =========================
         * SUCURSALES (amenidades embebidas aquí)
         * =========================
         */
        SucursalPojo sucursal1 = new SucursalPojo();
        sucursal1.setIdSucursal("S001");
        sucursal1.setNombre("Centro Hermosillo");
        sucursal1.setCiudad("Hermosillo");
        sucursal1.setColonia("Centro");
        sucursal1.setCalle("Solidaridad");
        sucursal1.setCodigoPostal("83000");
        sucursal1.setLatitud(29.0729);
        sucursal1.setLongitud(-110.9559);
        sucursal1.setPlanes(List.of(mensual));
        sucursal1.setAmenidadesSucursal(List.of(pesas, alberca));

        SucursalPojo sucursal2 = new SucursalPojo();
        sucursal2.setIdSucursal("S002");
        sucursal2.setNombre("Plaza Sur Obregón");
        sucursal2.setCiudad("Ciudad Obregón");
        sucursal2.setColonia("Centro");
        sucursal2.setCalle("Miguel Alemán");
        sucursal2.setCodigoPostal("85000");
        sucursal2.setLatitud(27.4863);
        sucursal2.setLongitud(-109.9306);
        sucursal2.setPlanes(List.of(mensual));
        sucursal2.setAmenidadesSucursal(List.of(pesas));

        MongoCollection<Document> colSucursales = bd.getCollection("sucursales");
        colSucursales.insertOne(SucursalPersistenciaMapper.toDocument(sucursal1));
        colSucursales.insertOne(SucursalPersistenciaMapper.toDocument(sucursal2));

        /*
         * =========================
         * HORARIOS
         * =========================
         */
        HorarioPojo horario = new HorarioPojo();
        horario.setIdHorario("H001");
        horario.setNombreDia("Lunes");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(18, 0));
        horario.setDisponible(true);

        /*
         * =========================
         * ENTRENADORES
         * =========================
         */
        EntrenadorPojo entrenador = new EntrenadorPojo();
        entrenador.setIdEntrenador("E001");
        entrenador.setNombre("Carlos Menchaca");
        entrenador.setIdSucursal("S001");
        entrenador.setHorarios(List.of(horario));

        MongoCollection<Document> colEntrenadores = bd.getCollection("entrenadores");
        colEntrenadores.insertOne(EntrenadorPersistenciaMapper.toDocument(entrenador));

        /*
         * =========================
         * MEMBRESÍA
         * =========================
         */
        String idMembresia = UUID.randomUUID().toString();

        MembresiaPojo membresia = new MembresiaPojo();
        membresia.setIdMembresia(idMembresia);
        membresia.setIdCliente("CL001");
        membresia.setIdPlan("P001");
        membresia.setIdSucursal("S001");
        membresia.setAmenidadesExtra(List.of(alberca));
        membresia.setMetodoPago("Tarjeta");
        membresia.setMontoPagado(849.0);
        membresia.setFechaTramite(LocalDateTime.now());
        membresia.setFechaCaducidad(LocalDateTime.now().plusMonths(1));
        membresia.setEstado(EstadoMembresiaPojo.ACTIVA);

        String fechaVigencia = membresia.getFechaCaducidad()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        String urlQR = "https://steelcorefitness.com/acceso"
                + "?id=" + idMembresia
                + "&cliente=CL001"
                + "&plan=P001"
                + "&sucursal=S001"
                + "&vigencia=" + fechaVigencia;
        membresia.setCodigoQR(urlQR);

        PagoPojo pago = new PagoPojo();
        pago.setIdPago("PG001");
        pago.setIdCliente("CL001");
        pago.setMonto(849.0);
        pago.setEstado(EstadoPagoPojo.COMPLETADO);
        pago.setFecha(LocalDateTime.now());
        membresia.setPago(pago);

        MongoCollection<Document> colMembresias = bd.getCollection("membresias");
        colMembresias.insertOne(MembresiaPersistenciaMapper.toDocument(membresia));

        /*
         * =========================
         * CITA BIENVENIDA (ID con UUID real)
         * =========================
         */
        CitaPojo cita = new CitaPojo();
        cita.setIdCita(UUID.randomUUID().toString());
        cita.setIdCliente("CL001");
        cita.setIdEntrenador("E001");
        cita.setIdSucursal("S001");
        cita.setIdHorario("H001");
        cita.setFechaHora(LocalDateTime.now().plusDays(1));
        cita.setEstado(EstadoCitaPojo.CONFIRMADA);
        cita.setNotas("Primera cita de bienvenida");

        /*
         * =========================
         * CLIENTE (contraseña hasheada con BCrypt)
         * =========================
         */
        // Hash de la contraseña "123" — el login comparará con BCrypt.verify()
        String contrasenhaHasheada = BCrypt.withDefaults()
                .hashToString(12, "123".toCharArray());

        UsuarioPojo usuario = new UsuarioPojo();
        usuario.setCorreo("cliente@gmail.com");
        usuario.setNombre("Juan Leonel");
        usuario.setContraseña(contrasenhaHasheada);   // ← ya hasheada
        usuario.setRol(RolUsuarioPojo.CLIENTE);

        MembresiaActivaPojo snapshot = new MembresiaActivaPojo();
        snapshot.setIdMembresia(idMembresia);
        snapshot.setIdPlan("P001");
        snapshot.setFechaCaducidad(membresia.getFechaCaducidad());
        snapshot.setEstado(EstadoMembresiaPojo.ACTIVA);

        ClientePojo cliente = new ClientePojo();
        cliente.setIdCliente("CL001");
        cliente.setUsuario(usuario);
        cliente.setApellidoPaterno("Bojorquez");
        cliente.setApellidoMaterno("Terán");
        cliente.setFechaNacimiento(LocalDate.of(2003, 5, 10));
        cliente.setCurp("LELJ030510HSRXXX01");
        cliente.setMembresiaActiva(snapshot);
        cliente.setCitaBienvenida(cita);   // ← cita guardada en el cliente

        MongoCollection<Document> colClientes = bd.getCollection("clientes");
        colClientes.insertOne(ClientePersistenciaMapper.toDocument(cliente));

        /*
         * =========================
         * VISITAS
         * =========================
         */
        VisitaPojo visita = new VisitaPojo();
        visita.setIdVisita("V001");
        visita.setIdCliente("CL001");
        visita.setIdSucursal("S001");
        visita.setFechaHora(LocalDateTime.now().minusDays(1));

        MongoCollection<Document> colVisitas = bd.getCollection("visitas");
        colVisitas.insertOne(VisitaPersistenciaMapper.toDocument(visita));

        System.out.println("Datos de prueba insertados correctamente.");
        System.out.println("Usuario: cliente@gmail.com  |  Contraseña: 123 (hasheada en BD)");
    }
}
