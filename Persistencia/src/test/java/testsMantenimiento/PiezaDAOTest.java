/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testsMantenimiento;

import DAOsMantenimiento.PiezaDAO;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios_mantenimiento.PiezaPojo;
import excepciones.PersistenciaException;
import java.util.List;
import mappersMantenimientoPersistencia.PiezaPersistenciaMapper;
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
public class PiezaDAOTest {
    
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<PiezaPersistenciaMapper> mockedMapper;
    private PiezaDAO piezaDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("piezas")).thenReturn(mockCollection);

        mockedMapper = mockStatic(PiezaPersistenciaMapper.class);

        piezaDAO = new PiezaDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }


    @Test
    public void testObtenerPieza_idValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "PZ001");
        PiezaPojo pojoDummy = new PiezaPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        PiezaPojo resultado = piezaDAO.obtenerPieza("PZ001");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerPieza_noEncontrada_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        PiezaPojo resultado = piezaDAO.obtenerPieza("NO_EXISTE");

        assertNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerPieza_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> piezaDAO.obtenerPieza(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerPieza_idVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> piezaDAO.obtenerPieza(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }


    @Test
    public void testActualizarStock_valido_llamaUpdateOne() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "PZ001");

        PiezaPojo pieza = new PiezaPojo();
        pieza.setIdPieza("PZ001");
        pieza.setStock(10);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pieza);

        piezaDAO.actualizarStock("PZ001", 5);

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarStock_piezaNoEncontrada_lanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        assertThrows(PersistenciaException.class, () -> piezaDAO.actualizarStock("NO_EXISTE", 5));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarStock_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> piezaDAO.actualizarStock(null, 5));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testActualizarStock_stockNegativo_lanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "PZ001");

        PiezaPojo pieza = new PiezaPojo();
        pieza.setIdPieza("PZ001");

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pieza);

        assertThrows(PersistenciaException.class, () -> piezaDAO.actualizarStock("PZ001", -1));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testMostrarPiezas_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc1 = new Document("_id", "PZ001");
        Document doc2 = new Document("_id", "PZ002");

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false);
        when(mockCursor.next()).thenReturn(doc1, doc2);

        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(new PiezaPojo());

        List<PiezaPojo> resultado = piezaDAO.mostrarPiezas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockCollection, times(1)).find();
        mockedMapper.verify(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)), times(2));
    }


    @Test
    public void testHayStockSuficiente_stockDisponible_retornaTrue() throws PersistenciaException {
        FindIterable<Document> mockIterableObtener = mock(FindIterable.class);
        FindIterable<Document> mockIterableStock = mock(FindIterable.class);

        Document docPieza = new Document("_id", "PZ001");
        Document docStock = new Document("_id", "PZ001").append("stock", 10).append("estado", "ACTIVO");

        PiezaPojo pieza = new PiezaPojo();
        pieza.setIdPieza("PZ001");
        pieza.setStock(10);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterableObtener, mockIterableStock);
        when(mockIterableObtener.first()).thenReturn(docPieza);
        when(mockIterableStock.first()).thenReturn(docStock);

        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pieza);

        boolean resultado = piezaDAO.hayStockSuficiente("PZ001", 5);

        assertTrue(resultado);
        verify(mockCollection, times(2)).find(any(Bson.class));
    }

    @Test
    public void testHayStockSuficiente_stockInsuficiente_retornaFalse() throws PersistenciaException {
        FindIterable<Document> mockIterableObtener = mock(FindIterable.class);
        FindIterable<Document> mockIterableStock = mock(FindIterable.class);

        Document docPieza = new Document("_id", "PZ001");

        PiezaPojo pieza = new PiezaPojo();
        pieza.setIdPieza("PZ001");
        pieza.setStock(2);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterableObtener, mockIterableStock);
        when(mockIterableObtener.first()).thenReturn(docPieza);
        when(mockIterableStock.first()).thenReturn(null);

        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pieza);

        boolean resultado = piezaDAO.hayStockSuficiente("PZ001", 5);

        assertFalse(resultado);
        verify(mockCollection, times(2)).find(any(Bson.class));
    }

    @Test
    public void testHayStockSuficiente_piezaNoEncontrada_lanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        assertThrows(PersistenciaException.class, () -> piezaDAO.hayStockSuficiente("NO_EXISTE", 5));
    }

    @Test
    public void testHayStockSuficiente_cantidadCero_lanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "PZ001");

        PiezaPojo pieza = new PiezaPojo();
        pieza.setIdPieza("PZ001");

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);

        mockedMapper.when(() -> PiezaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pieza);

        assertThrows(PersistenciaException.class, () -> piezaDAO.hayStockSuficiente("PZ001", 0));
    }

    @Test
    public void testHayStockSuficiente_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> piezaDAO.hayStockSuficiente(null, 5));
    }
}
