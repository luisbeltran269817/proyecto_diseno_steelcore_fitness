/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testsMantenimiento;

import DAOsMantenimiento.MantenimientoDAO;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios_mantenimiento.MantenimientoPojo;
import excepciones.PersistenciaException;
import java.util.List;
import mappersMantenimientoPersistencia.MantenimientoPersistenciaMapper;
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
public class MantenimientoDAOTest {
    
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<MantenimientoPersistenciaMapper> mockedMapper;
    private MantenimientoDAO mantenimientoDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("mantenimientos")).thenReturn(mockCollection);

        mockedMapper = mockStatic(MantenimientoPersistenciaMapper.class);

        mantenimientoDAO = new MantenimientoDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }


    @Test
    public void testRegistrarMantenimiento_valido_llamaInsertOne() throws PersistenciaException {
        MantenimientoPojo mantenimiento = new MantenimientoPojo();
        mantenimiento.setIdMantenimiento("MAN001");

        mockedMapper.when(() -> MantenimientoPersistenciaMapper.toDocument(any(MantenimientoPojo.class)))
                .thenReturn(new Document("_id", "MAN001"));

        mantenimientoDAO.registrarMantenimiento(mantenimiento);

        verify(mockCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    public void testRegistrarMantenimiento_null_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> mantenimientoDAO.registrarMantenimiento(null));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    @Test
    public void testObtenerMantenimiento_idValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "MAN001");
        MantenimientoPojo pojoDummy = new MantenimientoPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);

        mockedMapper.when(() -> MantenimientoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        MantenimientoPojo resultado = mantenimientoDAO.obtenerMantenimiento("MAN001");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerMantenimiento_noEncontrado_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        MantenimientoPojo resultado = mantenimientoDAO.obtenerMantenimiento("NO_EXISTE");

        assertNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerMantenimiento_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> mantenimientoDAO.obtenerMantenimiento(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerMantenimiento_idVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> mantenimientoDAO.obtenerMantenimiento(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerMantenimientos_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc1 = new Document("_id", "MAN001");
        Document doc2 = new Document("_id", "MAN002");

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false);
        when(mockCursor.next()).thenReturn(doc1, doc2);

        mockedMapper.when(() -> MantenimientoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(new MantenimientoPojo());

        List<MantenimientoPojo> resultado = mantenimientoDAO.obtenerMantenimientos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockCollection, times(1)).find();
        mockedMapper.verify(() -> MantenimientoPersistenciaMapper.toPojo(any(Document.class)), times(2));
    }

    @Test
    public void testTieneMantenimientoActivo_conMantenimientoActivo_retornaTrue() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "MAN001")
                .append("idMaquina", "MAQ001")
                .append("estado", MantenimientoPojo.EstadoMantenimiento.PENDIENTE.name());

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);

        boolean resultado = mantenimientoDAO.tieneMantenimientoActivo("MAQ001");

        assertTrue(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }
    
    @Test
    public void testTieneMantenimientoActivo_sinMantenimientoActivo_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        boolean resultado = mantenimientoDAO.tieneMantenimientoActivo("MAQ001");

        assertFalse(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testTieneMantenimientoActivo_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> mantenimientoDAO.tieneMantenimientoActivo(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testTieneMantenimientoActivo_idVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> mantenimientoDAO.tieneMantenimientoActivo(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }
}
