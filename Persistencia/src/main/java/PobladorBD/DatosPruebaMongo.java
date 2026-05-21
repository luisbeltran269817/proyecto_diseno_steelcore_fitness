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
