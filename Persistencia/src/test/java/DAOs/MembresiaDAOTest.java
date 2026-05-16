package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.MembresiaPojo;
import excepciones.PersistenciaException;
import mappersPersistencia.MembresiaPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 *
 * @author luiscarlosbeltran
 */
public class MembresiaDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<MembresiaPersistenciaMapper> mockedMapper;
    private MembresiaDAO membresiaDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("membresias")).thenReturn(mockCollection);

        mockedMapper = mockStatic(MembresiaPersistenciaMapper.class);

        membresiaDAO = new MembresiaDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }

    // ── guardar ──────────────────────────────────────────────────────────────

    @Test
    public void testGuardar_membresiaValida_llamaInsertOne() throws PersistenciaException {
        MembresiaPojo membresia = new MembresiaPojo();
        mockedMapper.when(() -> MembresiaPersistenciaMapper.toDocument(any(MembresiaPojo.class)))
                .thenReturn(new Document("idMembresia", "m1"));

        membresiaDAO.guardar(membresia);

        verify(mockCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    public void testGuardar_membresiaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> membresiaDAO.guardar(null));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    // ── buscarPorId ──────────────────────────────────────────────────────────

    @Test
    public void testBuscarPorId_encontrada_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "m1");
        MembresiaPojo pojoDummy = new MembresiaPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> MembresiaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        MembresiaPojo resultado = membresiaDAO.buscarPorId("m1");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorId_noEncontrada_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        MembresiaPojo resultado = membresiaDAO.buscarPorId("inexistente");

        assertNull(resultado);
    }

    // ── buscarPorCodigoQR ────────────────────────────────────────────────────

    @Test
    public void testBuscarPorCodigoQR_codigoNulo_retornaNull() throws PersistenciaException {
        MembresiaPojo resultado = membresiaDAO.buscarPorCodigoQR(null);
        assertNull(resultado);
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorCodigoQR_codigoValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("codigoQR", "QR123");
        MembresiaPojo pojoDummy = new MembresiaPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> MembresiaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        MembresiaPojo resultado = membresiaDAO.buscarPorCodigoQR("QR123");

        assertNotNull(resultado);
    }

    // ── actualizar ───────────────────────────────────────────────────────────

    @Test
    public void testActualizar_membresiaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> membresiaDAO.actualizar(null));
        verify(mockCollection, never()).replaceOne(any(Bson.class), any(Document.class));
    }

    @Test
    public void testActualizar_membresiaValida_llamaReplaceOne() throws PersistenciaException {
        MembresiaPojo membresia = new MembresiaPojo();
        membresia.setIdMembresia("m1");
        mockedMapper.when(() -> MembresiaPersistenciaMapper.toDocument(any(MembresiaPojo.class)))
                .thenReturn(new Document("_id", "m1"));

        membresiaDAO.actualizar(membresia);

        verify(mockCollection, times(1)).replaceOne(any(Bson.class), any(Document.class));
    }
}