package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.VisitaPojo;
import excepciones.PersistenciaException;
import mappersPersistencia.VisitaPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author luiscarlosbeltran
 */
public class VisitaDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<VisitaPersistenciaMapper> mockedMapper;
    private VisitaDAO visitaDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("visitas")).thenReturn(mockCollection);

        mockedMapper = mockStatic(VisitaPersistenciaMapper.class);

        visitaDAO = new VisitaDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }

    // ── guardar ──────────────────────────────────────────────────────────────

    @Test
    public void testGuardar_exitoso_retornaVisitaConIdCliente() throws PersistenciaException {
        VisitaPojo visita = new VisitaPojo();
        Document docDummy = new Document("idCliente", "cliente123");
        VisitaPojo visitaRetorno = new VisitaPojo();
        visitaRetorno.setIdCliente("cliente123");

        mockedMapper.when(() -> VisitaPersistenciaMapper.toDocument(any(VisitaPojo.class)))
                .thenReturn(docDummy);
        mockedMapper.when(() -> VisitaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(visitaRetorno);

        VisitaPojo resultado = visitaDAO.guardar("cliente123", visita);

        assertNotNull(resultado);
        assertEquals("cliente123", resultado.getIdCliente());
        verify(mockCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    public void testGuardar_idClienteNulo_lanzaExcepcion() {
        VisitaPojo visita = new VisitaPojo();
        assertThrows(PersistenciaException.class, () -> visitaDAO.guardar(null, visita));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    @Test
    public void testGuardar_idClienteBlanco_lanzaExcepcion() {
        VisitaPojo visita = new VisitaPojo();
        assertThrows(PersistenciaException.class, () -> visitaDAO.guardar("   ", visita));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    @Test
    public void testGuardar_visitaNula_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> visitaDAO.guardar("cliente123", null));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    // ── obtenerPorCliente ────────────────────────────────────────────────────

    @Test
    public void testObtenerPorCliente_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);
        Document doc = new Document("idCliente", "cliente123");
        VisitaPojo pojoDummy = new VisitaPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(doc);
        mockedMapper.when(() -> VisitaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<VisitaPojo> resultado = visitaDAO.obtenerPorCliente("cliente123");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerPorCliente_sinVisitas_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<VisitaPojo> resultado = visitaDAO.obtenerPorCliente("cliente_sin_visitas");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}