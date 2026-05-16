package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.EntrenadorPojo;
import dominios.HorarioPojo;
import excepciones.PersistenciaException;
import mappersPersistencia.EntrenadorPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author luiscarlosbeltran
 */
public class EntrenadorDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<EntrenadorPersistenciaMapper> mockedMapper;
    private EntrenadorDAO entrenadorDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("entrenadores")).thenReturn(mockCollection);

        mockedMapper = mockStatic(EntrenadorPersistenciaMapper.class);

        entrenadorDAO = new EntrenadorDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }

    // ── buscarPorId ──────────────────────────────────────────────────────────

    @Test
    public void testBuscarPorId_entrenadorEncontrado_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "123").append("nombre", "Pepe");
        EntrenadorPojo pojoDummy = new EntrenadorPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> EntrenadorPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        EntrenadorPojo resultado = entrenadorDAO.buscarPorId("123");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorId_entrenadorNoExiste_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        EntrenadorPojo resultado = entrenadorDAO.buscarPorId("999");

        assertNull(resultado);
    }

    // ── obtenerPorSucursal ───────────────────────────────────────────────────

    @Test
    public void testObtenerPorSucursal_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);
        Document doc = new Document("_id", "e1").append("idSucursal", "s1");
        EntrenadorPojo pojoDummy = new EntrenadorPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(doc);
        mockedMapper.when(() -> EntrenadorPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<EntrenadorPojo> resultado = entrenadorDAO.obtenerPorSucursal("s1");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testObtenerPorSucursal_sinEntrenadores_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<EntrenadorPojo> resultado = entrenadorDAO.obtenerPorSucursal("s_inexistente");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── obtenerHorariosEntrenador ────────────────────────────────────────────

    @Test
    public void testObtenerHorarios_entrenadorExiste_retornaHorarios() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "123");
        List<HorarioPojo> horariosDummy = new ArrayList<>();
        horariosDummy.add(new HorarioPojo());

        EntrenadorPojo pojoDummy = new EntrenadorPojo();
        pojoDummy.setHorarios(horariosDummy);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> EntrenadorPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<HorarioPojo> resultado = entrenadorDAO.obtenerHorariosEntrenador("123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    public void testObtenerHorarios_entrenadorNoExiste_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        List<HorarioPojo> resultado = entrenadorDAO.obtenerHorariosEntrenador("999");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}