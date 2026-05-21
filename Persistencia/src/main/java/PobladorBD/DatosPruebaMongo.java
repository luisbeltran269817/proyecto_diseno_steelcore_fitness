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
import dominios_mantenimiento.AdminMantenimientoPojo;
import dominios_mantenimiento.BajaPojo;
import dominios_mantenimiento.HorarioTecnicoPojo;
import dominios_mantenimiento.MantenimientoPiezaPojo;
import dominios_mantenimiento.MantenimientoPojo;
import dominios_mantenimiento.MaquinaPojo;
import dominios_mantenimiento.PiezaPojo;
import dominios_mantenimiento.TecnicoPojo;
import dominios_mantenimiento.UltimoMantenimientoPojo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mappersMantenimientoPersistencia.AdminMantenimientoPersistenciaMapper;
import mappersMantenimientoPersistencia.MantenimientoPersistenciaMapper;
import mappersMantenimientoPersistencia.MaquinaPersistenciaMapper;
import mappersMantenimientoPersistencia.PiezaPersistenciaMapper;
import mappersMantenimientoPersistencia.TecnicoPersistenciaMapper;
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
        bd.getCollection("ejercicios").deleteMany(new Document());
        bd.getCollection("plantillaRutina").deleteMany(new Document());


        bd.getCollection("adminsMantenimiento").deleteMany(new Document());
        bd.getCollection("maquinas").deleteMany(new Document());
        bd.getCollection("piezas").deleteMany(new Document());
        bd.getCollection("tecnicos").deleteMany(new Document());
        
        bd.getCollection("mantenimientos").deleteMany(new Document());
        AdminMantenimientoPojo admin = new AdminMantenimientoPojo();
        admin.setIdAdminMantenimiento("ADM-MANT-001");
        UsuarioPojo usuarioAdmin = new UsuarioPojo();
        usuarioAdmin.setCorreo("admin.mantenimiento@gmail.com");
        usuarioAdmin.setNombre("Administrador Mantenimiento");
        String hash = BCrypt.withDefaults().hashToString(12, "123".toCharArray());
        usuarioAdmin.setContraseña(hash);
        usuarioAdmin.setRol(RolUsuarioPojo.ADMIN);
        admin.setUsuario(usuarioAdmin);
        MongoConexion.obtenerBaseDatos()
                .getCollection("adminsMantenimiento")
                .insertOne(AdminMantenimientoPersistenciaMapper.toDocument(admin));
        /*
        * =========================
        * PIEZAS / REFACCIONES
        * =========================
        */
       PiezaPojo polea = new PiezaPojo();
       polea.setIdPieza("PZ001");
       polea.setNombre("Polea");
       polea.setStock(15);
       polea.setEstado(PiezaPojo.EstadoPieza.ACTIVO);

       PiezaPojo banda = new PiezaPojo();
       banda.setIdPieza("PZ002");
       banda.setNombre("Banda de caminadora");
       banda.setStock(8);
       banda.setEstado(PiezaPojo.EstadoPieza.ACTIVO);

       PiezaPojo tornillos = new PiezaPojo();
       tornillos.setIdPieza("PZ003");
       tornillos.setNombre("Paquete de tornillos");
       tornillos.setStock(40);
       tornillos.setEstado(PiezaPojo.EstadoPieza.ACTIVO);

       PiezaPojo sensor = new PiezaPojo();
       sensor.setIdPieza("PZ004");
       sensor.setNombre("Sensor de velocidad");
       sensor.setStock(5);
       sensor.setEstado(PiezaPojo.EstadoPieza.ACTIVO);

       PiezaPojo motor = new PiezaPojo();
       motor.setIdPieza("PZ005");
       motor.setNombre("Motor eléctrico");
       motor.setStock(2);
       motor.setEstado(PiezaPojo.EstadoPieza.ACTIVO);

       BajaPojo bajaPieza = new BajaPojo();
       bajaPieza.setIdBaja("BJPZ001");
       bajaPieza.setMotivo("Refacción obsoleta");
       bajaPieza.setFechaBaja(LocalDateTime.now().minusDays(10));
       bajaPieza.setTipo("PIEZA");

       PiezaPojo pantallaVieja = new PiezaPojo();
       pantallaVieja.setIdPieza("PZ006");
       pantallaVieja.setNombre("Pantalla antigua");
       pantallaVieja.setStock(0);
       pantallaVieja.setEstado(PiezaPojo.EstadoPieza.INACTIVO);
       pantallaVieja.setBaja(bajaPieza);

       MongoCollection<Document> colPiezas = bd.getCollection("piezas");

       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(polea));
       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(banda));
       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(tornillos));
       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(sensor));
       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(motor));
       colPiezas.insertOne(PiezaPersistenciaMapper.toDocument(pantallaVieja));


       /*
        * =========================
        * TÉCNICOS
        * =========================
        */
       HorarioTecnicoPojo horarioLuis1 = new HorarioTecnicoPojo();
       horarioLuis1.setIdHorario("HT001");
       horarioLuis1.setNombreDia("Lunes");
       horarioLuis1.setHoraInicio(LocalTime.of(8, 0));
       horarioLuis1.setHoraFin(LocalTime.of(16, 0));
       horarioLuis1.setDisponible(true);

       HorarioTecnicoPojo horarioLuis2 = new HorarioTecnicoPojo();
       horarioLuis2.setIdHorario("HT002");
       horarioLuis2.setNombreDia("Miércoles");
       horarioLuis2.setHoraInicio(LocalTime.of(8, 0));
       horarioLuis2.setHoraFin(LocalTime.of(16, 0));
       horarioLuis2.setDisponible(true);

       TecnicoPojo tecnicoLuis = new TecnicoPojo();
       tecnicoLuis.setIdTecnico("T001");
       tecnicoLuis.setNombre("Luis Hernández");
       tecnicoLuis.setHorarios(List.of(horarioLuis1, horarioLuis2));

       HorarioTecnicoPojo horarioAna1 = new HorarioTecnicoPojo();
       horarioAna1.setIdHorario("HT003");
       horarioAna1.setNombreDia("Martes");
       horarioAna1.setHoraInicio(LocalTime.of(10, 0));
       horarioAna1.setHoraFin(LocalTime.of(18, 0));
       horarioAna1.setDisponible(true);

       HorarioTecnicoPojo horarioAna2 = new HorarioTecnicoPojo();
       horarioAna2.setIdHorario("HT004");
       horarioAna2.setNombreDia("Jueves");
       horarioAna2.setHoraInicio(LocalTime.of(10, 0));
       horarioAna2.setHoraFin(LocalTime.of(18, 0));
       horarioAna2.setDisponible(true);

       TecnicoPojo tecnicoAna = new TecnicoPojo();
       tecnicoAna.setIdTecnico("T002");
       tecnicoAna.setNombre("Ana Mendoza");
       tecnicoAna.setHorarios(List.of(horarioAna1, horarioAna2));

       HorarioTecnicoPojo horarioJulian1 = new HorarioTecnicoPojo();
       horarioJulian1.setIdHorario("HT005");
       horarioJulian1.setNombreDia("Viernes");
       horarioJulian1.setHoraInicio(LocalTime.of(9, 0));
       horarioJulian1.setHoraFin(LocalTime.of(17, 0));
       horarioJulian1.setDisponible(true);

       HorarioTecnicoPojo horarioJulian2 = new HorarioTecnicoPojo();
       horarioJulian2.setIdHorario("HT006");
       horarioJulian2.setNombreDia("Sábado");
       horarioJulian2.setHoraInicio(LocalTime.of(9, 0));
       horarioJulian2.setHoraFin(LocalTime.of(14, 0));
       horarioJulian2.setDisponible(true);

       TecnicoPojo tecnicoJulian = new TecnicoPojo();
       tecnicoJulian.setIdTecnico("T003");
       tecnicoJulian.setNombre("Julián Menchaca");
       tecnicoJulian.setHorarios(List.of(horarioJulian1, horarioJulian2));

       MongoCollection<Document> colTecnicos = bd.getCollection("tecnicos");

       colTecnicos.insertOne(TecnicoPersistenciaMapper.toDocument(tecnicoLuis));
       colTecnicos.insertOne(TecnicoPersistenciaMapper.toDocument(tecnicoAna));
       colTecnicos.insertOne(TecnicoPersistenciaMapper.toDocument(tecnicoJulian));


       /*
        * =========================
        * MANTENIMIENTOS
        * =========================
        */
       MantenimientoPiezaPojo mp1 = new MantenimientoPiezaPojo();
       mp1.setIdMantenimientoPieza("MP001");
       mp1.setIdMantenimiento("MAN001");
       mp1.setIdPieza("PZ001");
       mp1.setCantidad(2);

       MantenimientoPiezaPojo mp2 = new MantenimientoPiezaPojo();
       mp2.setIdMantenimientoPieza("MP002");
       mp2.setIdMantenimiento("MAN001");
       mp2.setIdPieza("PZ003");
       mp2.setCantidad(6);

       MantenimientoPojo mantenimiento1 = new MantenimientoPojo();
       mantenimiento1.setIdMantenimiento("MAN001");
       mantenimiento1.setIdTecnico("T001");
       mantenimiento1.setIdMaquina("MAQ001");
       mantenimiento1.setDescripcion("Cambio de polea y ajuste general");
       mantenimiento1.setFechaProgramada(LocalDateTime.of(2026, 3, 11, 10, 0));
       mantenimiento1.setFechaInicio(LocalDateTime.of(2026, 3, 11, 10, 5));
       mantenimiento1.setFechaFin(LocalDateTime.of(2026, 3, 11, 11, 20));
       mantenimiento1.setEstado(MantenimientoPojo.EstadoMantenimiento.REALIZADO);
       mantenimiento1.setPiezas(List.of(mp1, mp2));

       MantenimientoPiezaPojo mp3 = new MantenimientoPiezaPojo();
       mp3.setIdMantenimientoPieza("MP003");
       mp3.setIdMantenimiento("MAN002");
       mp3.setIdPieza("PZ002");
       mp3.setCantidad(1);

       MantenimientoPojo mantenimiento2 = new MantenimientoPojo();
       mantenimiento2.setIdMantenimiento("MAN002");
       mantenimiento2.setIdTecnico("T002");
       mantenimiento2.setIdMaquina("MAQ002");
       mantenimiento2.setDescripcion("Revisión de banda y sistema de resistencia");
       mantenimiento2.setFechaProgramada(LocalDateTime.of(2026, 5, 20, 12, 0));
       mantenimiento2.setFechaInicio(null);
       mantenimiento2.setFechaFin(null);
       mantenimiento2.setEstado(MantenimientoPojo.EstadoMantenimiento.PENDIENTE);
       mantenimiento2.setPiezas(List.of(mp3));

       MantenimientoPojo mantenimiento3 = new MantenimientoPojo();
       mantenimiento3.setIdMantenimiento("MAN003");
       mantenimiento3.setIdTecnico("T003");
       mantenimiento3.setIdMaquina("MAQ003");
       mantenimiento3.setDescripcion("Máquina en espera por llegada de motor");
       mantenimiento3.setFechaProgramada(LocalDateTime.of(2026, 5, 22, 9, 30));
       mantenimiento3.setFechaInicio(LocalDateTime.of(2026, 5, 22, 9, 40));
       mantenimiento3.setFechaFin(null);
       mantenimiento3.setEstado(MantenimientoPojo.EstadoMantenimiento.ESPERA);
       mantenimiento3.setPiezas(new ArrayList<>());

       MongoCollection<Document> colMantenimientos = bd.getCollection("mantenimientos");

       colMantenimientos.insertOne(MantenimientoPersistenciaMapper.toDocument(mantenimiento1));
       colMantenimientos.insertOne(MantenimientoPersistenciaMapper.toDocument(mantenimiento2));
       colMantenimientos.insertOne(MantenimientoPersistenciaMapper.toDocument(mantenimiento3));


       /*
        * =========================
        * MÁQUINAS
        * =========================
        */
       UltimoMantenimientoPojo ultimo1 = new UltimoMantenimientoPojo();
       ultimo1.setIdMantenimiento("MAN001");
       ultimo1.setFecha(LocalDateTime.of(2026, 3, 11, 10, 0));
       ultimo1.setEstado(UltimoMantenimientoPojo.EstadoMantenimientoSnapshot.REALIZADO);

       MaquinaPojo maquina1 = new MaquinaPojo();
       maquina1.setIdMaquina("MAQ001");
       maquina1.setIdSucursal("S001");
       maquina1.setModelo("Estación Multifuncional Tayga ZX200");
       maquina1.setTipo("Resistencia");
       maquina1.setEstado(MaquinaPojo.EstadoMaquina.BUENAS_CONDICIONES);
       maquina1.setUltimoMantenimiento(ultimo1);

       UltimoMantenimientoPojo ultimo2 = new UltimoMantenimientoPojo();
       ultimo2.setIdMantenimiento("MAN002");
       ultimo2.setFecha(LocalDateTime.of(2026, 5, 20, 12, 0));
       ultimo2.setEstado(UltimoMantenimientoPojo.EstadoMantenimientoSnapshot.PENDIENTE);

       MaquinaPojo maquina2 = new MaquinaPojo();
       maquina2.setIdMaquina("MAQ002");
       maquina2.setIdSucursal("S002");
       maquina2.setModelo("Disco Olímpico de acero recubierto 20kg");
       maquina2.setTipo("Fuerza libre");
       maquina2.setEstado(MaquinaPojo.EstadoMaquina.MANTENIMIENTO_PREVENTVO);
       maquina2.setUltimoMantenimiento(ultimo2);

       UltimoMantenimientoPojo ultimo3 = new UltimoMantenimientoPojo();
       ultimo3.setIdMantenimiento("MAN003");
       ultimo3.setFecha(LocalDateTime.of(2026, 5, 22, 9, 30));
       ultimo3.setEstado(UltimoMantenimientoPojo.EstadoMantenimientoSnapshot.ESPERA);

       MaquinaPojo maquina3 = new MaquinaPojo();
       maquina3.setIdMaquina("MAQ003");
       maquina3.setIdSucursal("S001");
       maquina3.setModelo("Caminadora ProGear199");
       maquina3.setTipo("Cardio");
       maquina3.setEstado(MaquinaPojo.EstadoMaquina.MANTENIMIENTO_URGENTE);
       maquina3.setUltimoMantenimiento(ultimo3);

       MaquinaPojo maquina4 = new MaquinaPojo();
       maquina4.setIdMaquina("MAQ004");
       maquina4.setIdSucursal("S002");
       maquina4.setModelo("Bicicleta Spinning Xtreme");
       maquina4.setTipo("Cardio");
       maquina4.setEstado(MaquinaPojo.EstadoMaquina.BUENAS_CONDICIONES);
       maquina4.setUltimoMantenimiento(null);

       BajaPojo bajaMaquina = new BajaPojo();
       bajaMaquina.setIdBaja("BJMAQ001");
       bajaMaquina.setMotivo("Equipo dañado sin posibilidad de reparación");
       bajaMaquina.setFechaBaja(LocalDateTime.now().minusDays(5));
       bajaMaquina.setTipo("MAQUINA");

       MaquinaPojo maquina5 = new MaquinaPojo();
       maquina5.setIdMaquina("MAQ005");
       maquina5.setIdSucursal("S001");
       maquina5.setModelo("Elíptica PowerFit 3000");
       maquina5.setTipo("Cardio");
       maquina5.setEstado(MaquinaPojo.EstadoMaquina.INACTIVO);
       maquina5.setUltimoMantenimiento(null);
       maquina5.setBaja(bajaMaquina);

       MongoCollection<Document> colMaquinas = bd.getCollection("maquinas");

       colMaquinas.insertOne(MaquinaPersistenciaMapper.toDocument(maquina1));
       colMaquinas.insertOne(MaquinaPersistenciaMapper.toDocument(maquina2));
       colMaquinas.insertOne(MaquinaPersistenciaMapper.toDocument(maquina3));
       colMaquinas.insertOne(MaquinaPersistenciaMapper.toDocument(maquina4));
       colMaquinas.insertOne(MaquinaPersistenciaMapper.toDocument(maquina5));
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
        
        /*
        * =========================
        * EJERCICIOS
        * =========================
        */
        bd.getCollection("ejercicios").deleteMany(new Document());
        
        MongoCollection<Document> colEjercicios = bd.getCollection("ejercicios");
        
        colEjercicios.insertOne(new Document("_id", "EJ001").append("nombre", "Press de Banca").append("grupoMuscular", "Pecho").append("descripcion", "Ejercicio básico multiarticular para el desarrollo del pectoral."));
        
        colEjercicios.insertOne(new Document("_id", "EJ002").append("nombre", "Sentadilla Libre").append("grupoMuscular", "Pierna").append("descripcion", "Sentadilla profunda con barra olímpica enfocado en cuádriceps y glúteos."));
        
        colEjercicios.insertOne(new Document("_id", "EJ003").append("nombre", "Dominadas").append("grupoMuscular", "Espalda").append("descripcion", "Ejercicio de autocarga ideal para la amplitud de la espalda (dorsales)."));
        
        colEjercicios.insertOne(new Document("_id", "EJ004").append("nombre", "Aperturas con Mancuernas").append("grupoMuscular", "Pecho").append("descripcion", "Ejercicio de aislamiento ideal para estirar y enfocar las fibras del pectoral."));
        
        colEjercicios.insertOne(new Document("_id", "EJ005").append("nombre", "Prensa de Piernas").append("grupoMuscular", "Pierna").append("descripcion", "Ejercicio en máquina para trabajar cuádriceps e isquiotibiales de forma segura."));
        
        colEjercicios.insertOne(new Document("_id", "EJ006").append("nombre", "Remo con Barra").append("grupoMuscular", "Espalda").append("descripcion", "Ejercicio constructor de densidad para trabajar la zona media y alta de la espalda."));
        
        /*
        * =========================
        * PLANTILLAS DE RUTINA
        * =========================
        */
        bd.getCollection("plantillaRutina").deleteMany(new Document());
        
        MongoCollection<Document> colPlantillas = bd.getCollection("plantillaRutina");

        colPlantillas.insertOne(new Document("nombre", "Cardio")
            .append("detalles", List.of(
            new Document("nombreDia", "Lunes").append("grupoMuscular", "Pecho").append("ejercicios", List.of("EJ001", "EJ004")),
            new Document("nombreDia", "Martes").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Miércoles").append("grupoMuscular", "Pierna").append("ejercicios", List.of("EJ002", "EJ005")),
            new Document("nombreDia", "Jueves").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Viernes").append("grupoMuscular", "Espalda").append("ejercicios", List.of("EJ003", "EJ006")),
            new Document("nombreDia", "Sábado").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Domingo").append("grupoMuscular", "Descanso").append("ejercicios", List.of())
            )));
        
        colPlantillas.insertOne(new Document("nombre", "Perder peso")
            .append("detalles", List.of(
            new Document("nombreDia", "Lunes").append("grupoMuscular", "Pierna").append("ejercicios", List.of("EJ002", "EJ005")),
            new Document("nombreDia", "Martes").append("grupoMuscular", "Espalda").append("ejercicios", List.of("EJ003", "EJ006")),
            new Document("nombreDia", "Miércoles").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Jueves").append("grupoMuscular", "Pecho").append("ejercicios", List.of("EJ001", "EJ004")),
            new Document("nombreDia", "Viernes").append("grupoMuscular", "Pierna").append("ejercicios", List.of("EJ002")),
            new Document("nombreDia", "Sábado").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Domingo").append("grupoMuscular", "Descanso").append("ejercicios", List.of())
            )));

        colPlantillas.insertOne(new Document("nombre", "Cuerpo completo")
            .append("detalles", List.of(
            new Document("nombreDia", "Lunes").append("grupoMuscular", "Espalda").append("ejercicios", List.of("EJ003", "EJ006")),
            new Document("nombreDia", "Martes").append("grupoMuscular", "Pecho").append("ejercicios", List.of("EJ001", "EJ004")),
            new Document("nombreDia", "Miércoles").append("grupoMuscular", "Pierna").append("ejercicios", List.of("EJ002", "EJ005")),
            new Document("nombreDia", "Jueves").append("grupoMuscular", "Descanso").append("ejercicios", List.of()),
            new Document("nombreDia", "Viernes").append("grupoMuscular", "Pecho").append("ejercicios", List.of("EJ001")),
            new Document("nombreDia", "Sábado").append("grupoMuscular", "Espalda").append("ejercicios", List.of("EJ003")),
            new Document("nombreDia", "Domingo").append("grupoMuscular", "Descanso").append("ejercicios", List.of())
            )));

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
