package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import excepciones.PersistenciaException;
import mappersPersistencia.CitaPersistenciaMapper;
import mappersPersistencia.ClientePersistenciaMapper;
import mappersPersistencia.MembresiaActivaPersistenciaMapper;
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
public class ClienteDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<ClientePersistenciaMapper> mockedClienteMapper;
    private MockedStatic<MembresiaActivaPersistenciaMapper> mockedMembresiaMapper;
    private MockedStatic<CitaPersistenciaMapper> mockedCitaMapper;
    private ClienteDAO clienteDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("clientes")).thenReturn(mockCollection);

        mockedClienteMapper = mockStatic(ClientePersistenciaMapper.class);
        mockedMembresiaMapper = mockStatic(MembresiaActivaPersistenciaMapper.class);
        mockedCitaMapper = mockStatic(CitaPersistenciaMapper.class);

        clienteDAO = new ClienteDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedCitaMapper.close();
        mockedMembresiaMapper.close();
        mockedClienteMapper.close();
        mockedConexion.close();
    }

    // ── buscarPorCorreo ──────────────────────────────────────────────────────

    @Test
    public void testBuscarPorCorreo_clienteExistente_retornaClientePojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document mockDoc = new Document("usuario", new Document("correo", "test@test.com"));
        ClientePojo pojoDummy = new ClientePojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(mockDoc);
        mockedClienteMapper.when(() -> ClientePersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        ClientePojo resultado = clienteDAO.buscarPorCorreo("test@test.com");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorCorreo_clienteNoExistente_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        ClientePojo resultado = clienteDAO.buscarPorCorreo("noexiste@test.com");

        assertNull(resultado);
    }

    // ── actualizar ───────────────────────────────────────────────────────────

    @Test
    public void testActualizar_clienteNulo_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> clienteDAO.actualizar(null));
    }

    @Test
    public void testActualizar_clienteValido_llamaReplaceOne() throws PersistenciaException {
        ClientePojo cliente = new ClientePojo();
        cliente.setIdCliente("abc123");
        mockedClienteMapper.when(() -> ClientePersistenciaMapper.toDocument(any(ClientePojo.class)))
                .thenReturn(new Document("_id", "abc123"));

        clienteDAO.actualizar(cliente);

        verify(mockCollection, times(1)).replaceOne(any(Bson.class), any(Document.class));
    }

    // ── obtenerMembresiaActiva ───────────────────────────────────────────────

    @Test
    public void testObtenerMembresiaActiva_sinCliente_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        MembresiaActivaPojo resultado = clienteDAO.obtenerMembresiaActiva("noexiste@test.com");

        assertNull(resultado);
    }

    @Test
    public void testObtenerMembresiaActiva_sinMembresia_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document docSinMembresia = new Document("usuario", new Document("correo", "x@x.com"));
        // No tiene campo "membresiaActiva"

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(docSinMembresia);

        MembresiaActivaPojo resultado = clienteDAO.obtenerMembresiaActiva("x@x.com");

        assertNull(resultado);
    }

    // ── guardarCitaBienvenida ────────────────────────────────────────────────

    @Test
    public void testGuardarCitaBienvenida_citaNula_lanzaExcepcion() {
        assertThrows(PersistenciaException.class,
                () -> clienteDAO.guardarCitaBienvenida("test@test.com", null));
    }

    @Test
    public void testGuardarCitaBienvenida_citaValida_llamaUpdateOne() throws PersistenciaException {
        CitaPojo cita = new CitaPojo();
        mockedCitaMapper.when(() -> CitaPersistenciaMapper.toDocument(any(CitaPojo.class)))
                .thenReturn(new Document("fecha", "2025-01-01"));

        clienteDAO.guardarCitaBienvenida("test@test.com", cita);

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }
}