/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testsMantenimiento;

import DAOsMantenimiento.TecnicoDAO;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominios_mantenimiento.HorarioTecnicoPojo;
import dominios_mantenimiento.TecnicoPojo;
import excepciones.PersistenciaException;
import java.time.LocalTime;
import java.util.List;
import mappersMantenimientoPersistencia.TecnicoPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Tungs
 */
public class TecnicoDAOTest {
    
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<TecnicoPersistenciaMapper> mockedMapper;
    private TecnicoDAO tecnicoDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("tecnicos")).thenReturn(mockCollection);

        mockedMapper = mockStatic(TecnicoPersistenciaMapper.class);

        tecnicoDAO = new TecnicoDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }


    @Test
    public void testObtenerTecnico_idValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "T001");
        TecnicoPojo pojoDummy = new TecnicoPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        TecnicoPojo resultado = tecnicoDAO.obtenerTecnico("T001");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerTecnico_noEncontrado_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        TecnicoPojo resultado = tecnicoDAO.obtenerTecnico("NO_EXISTE");

        assertNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerTecnico_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> tecnicoDAO.obtenerTecnico(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerTecnico_idVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> tecnicoDAO.obtenerTecnico(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerTecnicos_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc1 = new Document("_id", "T001");
        Document doc2 = new Document("_id", "T002");

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false);
        when(mockCursor.next()).thenReturn(doc1, doc2);

        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(new TecnicoPojo());

        List<TecnicoPojo> resultado = tecnicoDAO.obtenerTecnicos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockCollection, times(1)).find();
        mockedMapper.verify(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)), times(2));
    }


    @Test
    public void testTieneHorarioDisponible_horarioDisponible_retornaTrue() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "T001");

        HorarioTecnicoPojo horario = new HorarioTecnicoPojo();
        horario.setIdHorario("H001");
        horario.setNombreDia("Lunes");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(16, 0));
        horario.setDisponible(true);

        TecnicoPojo tecnico = new TecnicoPojo();
        tecnico.setIdTecnico("T001");
        tecnico.setNombre("Luis");
        tecnico.setHorarios(List.of(horario));

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(tecnico);

        boolean resultado = tecnicoDAO.tieneHorarioDisponible(
                "T001",
                "Lunes",
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertTrue(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testTieneHorarioDisponible_fueraDeHorario_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "T001");

        HorarioTecnicoPojo horario = new HorarioTecnicoPojo();
        horario.setIdHorario("H001");
        horario.setNombreDia("Lunes");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(16, 0));
        horario.setDisponible(true);

        TecnicoPojo tecnico = new TecnicoPojo();
        tecnico.setIdTecnico("T001");
        tecnico.setNombre("Luis");
        tecnico.setHorarios(List.of(horario));

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(tecnico);

        boolean resultado = tecnicoDAO.tieneHorarioDisponible(
                "T001",
                "Lunes",
                LocalTime.of(15, 30),
                LocalTime.of(16, 30)
        );

        assertFalse(resultado);
    }

    @Test
    public void testTieneHorarioDisponible_diaDiferente_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "T001");

        HorarioTecnicoPojo horario = new HorarioTecnicoPojo();
        horario.setIdHorario("H001");
        horario.setNombreDia("Lunes");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(16, 0));
        horario.setDisponible(true);

        TecnicoPojo tecnico = new TecnicoPojo();
        tecnico.setIdTecnico("T001");
        tecnico.setNombre("Luis");
        tecnico.setHorarios(List.of(horario));

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(tecnico);

        boolean resultado = tecnicoDAO.tieneHorarioDisponible(
                "T001",
                "Martes",
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(resultado);
    }

    @Test
    public void testTieneHorarioDisponible_horarioNoDisponible_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "T001");

        HorarioTecnicoPojo horario = new HorarioTecnicoPojo();
        horario.setIdHorario("H001");
        horario.setNombreDia("Lunes");
        horario.setHoraInicio(LocalTime.of(8, 0));
        horario.setHoraFin(LocalTime.of(16, 0));
        horario.setDisponible(false);

        TecnicoPojo tecnico = new TecnicoPojo();
        tecnico.setIdTecnico("T001");
        tecnico.setNombre("Luis");
        tecnico.setHorarios(List.of(horario));

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> TecnicoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(tecnico);

        boolean resultado = tecnicoDAO.tieneHorarioDisponible(
                "T001",
                "Lunes",
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(resultado);
    }

    @Test
    public void testTieneHorarioDisponible_tecnicoNoExiste_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        boolean resultado = tecnicoDAO.tieneHorarioDisponible(
                "NO_EXISTE",
                "Lunes",
                LocalTime.of(10, 0),
                LocalTime.of(11, 0)
        );

        assertFalse(resultado);
    }

    @Test
    public void testTieneHorarioDisponible_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.tieneHorarioDisponible(null, "Lunes", LocalTime.of(10, 0), LocalTime.of(11, 0))
        );
    }

    @Test
    public void testTieneHorarioDisponible_diaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.tieneHorarioDisponible("T001", null, LocalTime.of(10, 0), LocalTime.of(11, 0))
        );
    }

    @Test
    public void testTieneHorarioDisponible_horaInicioNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.tieneHorarioDisponible("T001", "Lunes", null, LocalTime.of(11, 0))
        );
    }

    @Test
    public void testTieneHorarioDisponible_horaFinNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.tieneHorarioDisponible("T001", "Lunes", LocalTime.of(10, 0), null)
        );
    }


    @Test
    public void testActualizarEstadoHorario_valido_llamaUpdateOne() throws PersistenciaException {
        UpdateResult updateResult = mock(UpdateResult.class);

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(1L);

        tecnicoDAO.actualizarEstadoHorario("T001", "H001", false);

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarEstadoHorario_idTecnicoNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.actualizarEstadoHorario(null, "H001", false)
        );

        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarEstadoHorario_idHorarioNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.actualizarEstadoHorario("T001", null, false)
        );

        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarEstadoHorario_noEncontrado_lanzaExcepcion() {
        UpdateResult updateResult = mock(UpdateResult.class);

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(0L);

        assertThrows(PersistenciaException.class, () ->
                tecnicoDAO.actualizarEstadoHorario("T999", "H999", false)
        );

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }
}
