package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import excepciones.PersistenciaException;
import mappersPersistencia.SucursalPersistenciaMapper;
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
public class SucursalDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<SucursalPersistenciaMapper> mockedMapper;
    private SucursalDAO sucursalDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("sucursales")).thenReturn(mockCollection);

        mockedMapper = mockStatic(SucursalPersistenciaMapper.class);

        sucursalDAO = new SucursalDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }

    // ── obtenerSucursales ────────────────────────────────────────────────────

    @Test
    public void testObtenerSucursales_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);
        Document doc = new Document("_id", "s1");
        SucursalPojo pojoDummy = new SucursalPojo();

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(doc);
        mockedMapper.when(() -> SucursalPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<SucursalPojo> resultado = sucursalDAO.obtenerSucursales();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mockCollection, times(1)).find();
    }

    @Test
    public void testObtenerSucursales_vacia_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<SucursalPojo> resultado = sucursalDAO.obtenerSucursales();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // ── buscarPorId ──────────────────────────────────────────────────────────

    @Test
    public void testBuscarPorId_sucursalEncontrada_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "s1");
        SucursalPojo pojoDummy = new SucursalPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> SucursalPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        SucursalPojo resultado = sucursalDAO.buscarPorId("s1");

        assertNotNull(resultado);
    }

    @Test
    public void testBuscarPorId_noEncontrada_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        SucursalPojo resultado = sucursalDAO.buscarPorId("invalido");

        assertNull(resultado);
    }

    // ── obtenerPlanesSucursal ────────────────────────────────────────────────

    @Test
    public void testObtenerPlanesSucursal_sucursalInexistente_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        List<PlanPojo> resultado = sucursalDAO.obtenerPlanesSucursal("s_inexistente");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    public void testObtenerPlanesSucursal_sucursalExiste_retornaPlanes() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "s1");

        List<PlanPojo> planesDummy = new ArrayList<>();
        planesDummy.add(new PlanPojo());

        SucursalPojo pojoDummy = new SucursalPojo();
        pojoDummy.setPlanes(planesDummy);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> SucursalPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<PlanPojo> resultado = sucursalDAO.obtenerPlanesSucursal("s1");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    // ── obtenerAmenidadesDePlan ──────────────────────────────────────────────

    @Test
    public void testObtenerAmenidadesDePlan_planNoEncontrado_retornaListaVacia() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<AmenidadPojo> resultado = sucursalDAO.obtenerAmenidadesDePlan("plan_inexistente");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}