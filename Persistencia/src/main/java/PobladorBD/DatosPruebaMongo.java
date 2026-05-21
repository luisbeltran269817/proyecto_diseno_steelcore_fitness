package PobladorBD;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import dominios.AmenidadPojo.TipoAmenidad;
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
import java.util.ArrayList;
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
 * Clientes de prueba:
 *   cliente@gmail.com  / 123  →  Plan P001 (Mensual)    — clases SI, entrenador NO
 *   premium@gmail.com  / 123  →  Plan P002 (Premium)    — clases SI, entrenador SI
 *
 * @author Tungs
 */
public class DatosPruebaMongo {

    public static void main(String[] args) {
        poblarBD();
    }

    public static void poblarBD() {

        MongoDatabase bd = MongoConexion.obtenerBaseDatos();

        // ── Limpiar colecciones ───────────────────────────────────────────────
        bd.getCollection("sucursales").deleteMany(new Document());
        bd.getCollection("entrenadores").deleteMany(new Document());
        bd.getCollection("clientes").deleteMany(new Document());
        bd.getCollection("membresias").deleteMany(new Document());
        bd.getCollection("visitas").deleteMany(new Document());
        bd.getCollection("clases").deleteMany(new Document());

        // ── Amenidades ───────────────────────────────────────────────────────
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

        AmenidadPojo entrenadorAmenidad = new AmenidadPojo();
        entrenadorAmenidad.setIdAmenidad("A003");
        entrenadorAmenidad.setNombre("Entrenador Personal");
        entrenadorAmenidad.setDescripcion("Sesiones con entrenador personal");
        entrenadorAmenidad.setTipo(TipoAmenidad.EXTRA);
        entrenadorAmenidad.setCosto(400.0);

        // ── Planes ───────────────────────────────────────────────────────────
        // P001 — Mensual: clases SI, entrenador NO
        PlanPojo mensual = new PlanPojo();
        mensual.setIdPlan("P001");
        mensual.setNombre("Mensual");
        mensual.setDescripcion("Plan mensual con acceso a clases grupales");
        mensual.setPrecio(599.0);
        mensual.setMesesDuracion(1);
        mensual.setAmenidades(List.of(pesas));

        // P002 — Premium: clases SI, entrenador SI  (ControlAcceso.planIncluyeEntrenador detecta "P002")
        PlanPojo premium = new PlanPojo();
        premium.setIdPlan("P002");
        premium.setNombre("Premium");
        premium.setDescripcion("Plan premium con clases y entrenador personal incluido");
        premium.setPrecio(999.0);
        premium.setMesesDuracion(1);
        premium.setAmenidades(List.of(pesas, entrenadorAmenidad));

        // ── Sucursales ───────────────────────────────────────────────────────
        SucursalPojo sucursal1 = new SucursalPojo();
        sucursal1.setIdSucursal("S001");
        sucursal1.setNombre("Centro Hermosillo");
        sucursal1.setCiudad("Hermosillo");
        sucursal1.setColonia("Centro");
        sucursal1.setCalle("Solidaridad");
        sucursal1.setCodigoPostal("83000");
        sucursal1.setLatitud(29.0729);
        sucursal1.setLongitud(-110.9559);
        sucursal1.setPlanes(List.of(mensual, premium));
        sucursal1.setAmenidadesSucursal(List.of(pesas, alberca, entrenadorAmenidad));

        SucursalPojo sucursal2 = new SucursalPojo();
        sucursal2.setIdSucursal("S002");
        sucursal2.setNombre("Plaza Sur Obregón");
        sucursal2.setCiudad("Ciudad Obregón");
        sucursal2.setColonia("Centro");
        sucursal2.setCalle("Miguel Alemán");
        sucursal2.setCodigoPostal("85000");
        sucursal2.setLatitud(27.4863);
        sucursal2.setLongitud(-109.9306);
        sucursal2.setPlanes(List.of(mensual, premium));
        sucursal2.setAmenidadesSucursal(List.of(pesas));

        MongoCollection<Document> colSucursales = bd.getCollection("sucursales");
        colSucursales.insertOne(SucursalPersistenciaMapper.toDocument(sucursal1));
        colSucursales.insertOne(SucursalPersistenciaMapper.toDocument(sucursal2));

        // ── Horarios (8 AM – 7 PM, todos los días) ───────────────────────────
        List<HorarioPojo> horarios = new ArrayList<>();
        String[] dias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};
        int contadorHorario = 1;
        for (int hora = 8; hora < 19; hora++) {
            for (String dia : dias) {
                HorarioPojo h = new HorarioPojo();
                h.setIdHorario(String.format("H%03d", contadorHorario++));
                h.setNombreDia(dia);
                h.setHoraInicio(LocalTime.of(hora, 0));
                h.setHoraFin(LocalTime.of(hora + 1, 0));
                h.setDisponible(true);
                horarios.add(h);
            }
        }

        // ── Entrenadores ─────────────────────────────────────────────────────
        MongoCollection<Document> colEntrenadores = bd.getCollection("entrenadores");

        EntrenadorPojo entrenador1 = new EntrenadorPojo();
        entrenador1.setIdEntrenador("E001");
        entrenador1.setNombre("Carlos Menchaca");
        entrenador1.setIdSucursal("S001");
        entrenador1.setHorarios(horarios);
        colEntrenadores.insertOne(EntrenadorPersistenciaMapper.toDocument(entrenador1));

        EntrenadorPojo entrenador2 = new EntrenadorPojo();
        entrenador2.setIdEntrenador("E002");
        entrenador2.setNombre("Sofía Rendón");
        entrenador2.setIdSucursal("S001");
        entrenador2.setHorarios(horarios);
        colEntrenadores.insertOne(EntrenadorPersistenciaMapper.toDocument(entrenador2));

        // ── Hash contraseña compartida "123" ──────────────────────────────────
        String hashPass = BCrypt.withDefaults().hashToString(12, "123".toCharArray());
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // ════════════════════════════════════════════════════════════════════
        // CLIENTE 1: cliente@gmail.com — Plan P001 Mensual (clases SI, entrenador NO)
        // ════════════════════════════════════════════════════════════════════
        String idMem1 = UUID.randomUUID().toString();

        MembresiaPojo mem1 = new MembresiaPojo();
        mem1.setIdMembresia(idMem1);
        mem1.setIdCliente("CL001");
        mem1.setIdPlan("P001");
        mem1.setIdSucursal("S001");
        mem1.setAmenidadesExtra(List.of(alberca));
        mem1.setMetodoPago("Tarjeta");
        mem1.setMontoPagado(849.0);
        mem1.setFechaTramite(LocalDateTime.now());
        mem1.setFechaCaducidad(LocalDateTime.now().plusMonths(1));
        mem1.setEstado(EstadoMembresiaPojo.ACTIVA);
        mem1.setCodigoQR("https://steelcorefitness.com/acceso?id=" + idMem1
                + "&cliente=CL001&plan=P001&sucursal=S001&vigencia="
                + mem1.getFechaCaducidad().format(fmt));
        PagoPojo pago1 = new PagoPojo();
        pago1.setIdPago("PG001");
        pago1.setIdCliente("CL001");
        pago1.setMonto(849.0);
        pago1.setEstado(EstadoPagoPojo.COMPLETADO);
        pago1.setFecha(LocalDateTime.now());
        mem1.setPago(pago1);

        UsuarioPojo usuario1 = new UsuarioPojo();
        usuario1.setCorreo("cliente@gmail.com");
        usuario1.setNombre("Juan Leonel");
        usuario1.setContraseña(hashPass);
        usuario1.setRol(RolUsuarioPojo.CLIENTE);

        MembresiaActivaPojo snap1 = new MembresiaActivaPojo();
        snap1.setIdMembresia(idMem1);
        snap1.setIdPlan("P001");
        snap1.setFechaCaducidad(mem1.getFechaCaducidad());
        snap1.setEstado(EstadoMembresiaPojo.ACTIVA);

        ClientePojo cliente1 = new ClientePojo();
        cliente1.setIdCliente("CL001");
        cliente1.setUsuario(usuario1);
        cliente1.setApellidoPaterno("Bojorquez");
        cliente1.setApellidoMaterno("Terán");
        cliente1.setFechaNacimiento(LocalDate.of(2003, 5, 10));
        cliente1.setCurp("LELJ030510HSRXXX01");
        cliente1.setMembresiaActiva(snap1);

        // ════════════════════════════════════════════════════════════════════
        // CLIENTE 2: premium@gmail.com — Plan P002 Premium (clases SI, entrenador SI)
        // ════════════════════════════════════════════════════════════════════
        String idMem2 = UUID.randomUUID().toString();

        MembresiaPojo mem2 = new MembresiaPojo();
        mem2.setIdMembresia(idMem2);
        mem2.setIdCliente("CL002");
        mem2.setIdPlan("P002");
        mem2.setIdSucursal("S001");
        mem2.setAmenidadesExtra(new ArrayList<>());
        mem2.setMetodoPago("Tarjeta");
        mem2.setMontoPagado(999.0);
        mem2.setFechaTramite(LocalDateTime.now());
        mem2.setFechaCaducidad(LocalDateTime.now().plusMonths(1));
        mem2.setEstado(EstadoMembresiaPojo.ACTIVA);
        mem2.setCodigoQR("https://steelcorefitness.com/acceso?id=" + idMem2
                + "&cliente=CL002&plan=P002&sucursal=S001&vigencia="
                + mem2.getFechaCaducidad().format(fmt));
        PagoPojo pago2 = new PagoPojo();
        pago2.setIdPago("PG002");
        pago2.setIdCliente("CL002");
        pago2.setMonto(999.0);
        pago2.setEstado(EstadoPagoPojo.COMPLETADO);
        pago2.setFecha(LocalDateTime.now());
        mem2.setPago(pago2);

        UsuarioPojo usuario2 = new UsuarioPojo();
        usuario2.setCorreo("premium@gmail.com");
        usuario2.setNombre("Ana Lucía");
        usuario2.setContraseña(hashPass);
        usuario2.setRol(RolUsuarioPojo.CLIENTE);

        MembresiaActivaPojo snap2 = new MembresiaActivaPojo();
        snap2.setIdMembresia(idMem2);
        snap2.setIdPlan("P002");
        snap2.setFechaCaducidad(mem2.getFechaCaducidad());
        snap2.setEstado(EstadoMembresiaPojo.ACTIVA);

        ClientePojo cliente2 = new ClientePojo();
        cliente2.setIdCliente("CL002");
        cliente2.setUsuario(usuario2);
        cliente2.setApellidoPaterno("Ramírez");
        cliente2.setApellidoMaterno("Soto");
        cliente2.setFechaNacimiento(LocalDate.of(2000, 8, 22));
        cliente2.setCurp("RASA000822MSONXX02");
        cliente2.setMembresiaActiva(snap2);

        // Insertar membresías y clientes
        MongoCollection<Document> colMembresias = bd.getCollection("membresias");
        colMembresias.insertOne(MembresiaPersistenciaMapper.toDocument(mem1));
        colMembresias.insertOne(MembresiaPersistenciaMapper.toDocument(mem2));

        MongoCollection<Document> colClientes = bd.getCollection("clientes");
        colClientes.insertOne(ClientePersistenciaMapper.toDocument(cliente1));
        colClientes.insertOne(ClientePersistenciaMapper.toDocument(cliente2));

        // ── Visitas históricas ────────────────────────────────────────────────
        MongoCollection<Document> colVisitas = bd.getCollection("visitas");

        VisitaPojo visita1 = new VisitaPojo();
        visita1.setIdVisita("V001");
        visita1.setIdCliente("CL001");
        visita1.setIdSucursal("S001");
        visita1.setFechaHora(LocalDateTime.now().minusDays(3));
        colVisitas.insertOne(VisitaPersistenciaMapper.toDocument(visita1));

        VisitaPojo visita2 = new VisitaPojo();
        visita2.setIdVisita("V002");
        visita2.setIdCliente("CL002");
        visita2.setIdSucursal("S001");
        visita2.setFechaHora(LocalDateTime.now().minusDays(1));
        colVisitas.insertOne(VisitaPersistenciaMapper.toDocument(visita2));

        // ── Clases ───────────────────────────────────────────────────────────
        // P001: clases grupales básicas
        // P002: clases premium (misma lista, pero asociadas a P002 también)
        MongoCollection<Document> colClases = bd.getCollection("clases");

        String[] nombresP001 = {"Yoga", "Spinning", "Zumba", "Pilates", "Boxeo"};
        String[] horasP001   = {"08:00", "09:00", "10:00", "11:00", "17:00"};
        String[] diasP001    = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
        for (int i = 0; i < nombresP001.length; i++) {
            Document clase = new Document("_id", "CL00" + (i + 1))
                    .append("nombre",     nombresP001[i])
                    .append("idSucursal", "S001")
                    .append("idPlan",     "P001")
                    .append("diaSemana",  diasP001[i])
                    .append("horaInicio", horasP001[i])
                    .append("cupoMaximo", 20)
                    .append("cupoActual", 0)
                    .append("inscritos",  new ArrayList<>());
            colClases.insertOne(clase);
        }

        // Clases exclusivas P002 (nombres diferentes para distinguirlas en pantalla)
        String[] nombresP002 = {"Crossfit", "TRX", "Funcional Avanzado"};
        String[] horasP002   = {"07:00", "12:00", "18:00"};
        String[] diasP002    = {"Lunes", "Miercoles", "Viernes"};
        for (int i = 0; i < nombresP002.length; i++) {
            Document clase = new Document("_id", "CL00" + (i + 6))
                    .append("nombre",     nombresP002[i])
                    .append("idSucursal", "S001")
                    .append("idPlan",     "P002")
                    .append("diaSemana",  diasP002[i])
                    .append("horaInicio", horasP002[i])
                    .append("cupoMaximo", 15)
                    .append("cupoActual", 0)
                    .append("inscritos",  new ArrayList<>());
            colClases.insertOne(clase);
        }

        System.out.println("=================================================");
        System.out.println("Datos de prueba insertados correctamente.");
        System.out.println("-------------------------------------------------");
        System.out.println("CLIENTE MENSUAL (P001)  → clases SI, entrenador NO");
        System.out.println("  correo : cliente@gmail.com");
        System.out.println("  pass   : 123");
        System.out.println("  QR id  : " + idMem1);
        System.out.println("-------------------------------------------------");
        System.out.println("CLIENTE PREMIUM (P002)  → clases SI, entrenador SI");
        System.out.println("  correo : premium@gmail.com");
        System.out.println("  pass   : 123");
        System.out.println("  QR id  : " + idMem2);
        System.out.println("=================================================");
    }
}
