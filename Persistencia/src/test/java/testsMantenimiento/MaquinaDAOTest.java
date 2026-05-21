/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testsMantenimiento;

import DAOsMantenimiento.MaquinaDAO;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominios_mantenimiento.BajaPojo;
import dominios_mantenimiento.MaquinaPojo;
import excepciones.PersistenciaException;
import java.util.List;
import mappersMantenimientoPersistencia.BajaPersistenciaMapper;
import mappersMantenimientoPersistencia.MaquinaPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
public class MaquinaDAOTest {
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<MaquinaPersistenciaMapper> mockedMaquinaMapper;
    private MockedStatic<BajaPersistenciaMapper> mockedBajaMapper;
    private MaquinaDAO maquinaDAO;
    
    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("maquinas")).thenReturn(mockCollection);

        mockedMaquinaMapper = mockStatic(MaquinaPersistenciaMapper.class);
        mockedBajaMapper = mockStatic(BajaPersistenciaMapper.class);

        maquinaDAO = new MaquinaDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedBajaMapper.close();
        mockedMaquinaMapper.close();
        mockedConexion.close();
    }


    @Test
    public void testRegistrarMaquina_maquinaValida_llamaInsertOne() throws PersistenciaException {
        MaquinaPojo maquina = new MaquinaPojo();
        maquina.setIdMaquina("MAQ001");

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toDocument(any(MaquinaPojo.class)))
                .thenReturn(new Document("_id", "MAQ001"));

        maquinaDAO.registrarMaquina(maquina);

        verify(mockCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    public void testRegistrarMaquina_maquinaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.registrarMaquina(null));
        verify(mockCollection, never()).insertOne(any(Document.class));
    }


    @Test
    public void testObtenerMaquina_idValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "MAQ001");
        MaquinaPojo pojoDummy = new MaquinaPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);
        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        MaquinaPojo resultado = maquinaDAO.obtenerMaquina("MAQ001");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerMaquina_noEncontrada_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        MaquinaPojo resultado = maquinaDAO.obtenerMaquina("NO_EXISTE");

        assertNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testObtenerMaquina_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.obtenerMaquina(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testObtenerMaquina_idVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.obtenerMaquina(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }


    @Test
    public void testObtenerMaquinas_retornaLista() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc1 = new Document("_id", "MAQ001");
        Document doc2 = new Document("_id", "MAQ002");

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false);
        when(mockCursor.next()).thenReturn(doc1, doc2);

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(new MaquinaPojo());

        List<MaquinaPojo> resultado = maquinaDAO.obtenerMaquinas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockCollection, times(1)).find();
        mockedMaquinaMapper.verify(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class)), times(2));
    }


    @Test
    public void testActualizarMaquina_maquinaValida_llamaReplaceOne() throws PersistenciaException {
        MaquinaPojo maquina = new MaquinaPojo();
        maquina.setIdMaquina("MAQ001");

        UpdateResult updateResult = mock(UpdateResult.class);

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toDocument(any(MaquinaPojo.class)))
                .thenReturn(new Document("_id", "MAQ001"));

        when(mockCollection.replaceOne(any(Bson.class), any(Document.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(1L);

        maquinaDAO.actualizarMaquina(maquina);

        verify(mockCollection, times(1)).replaceOne(any(Bson.class), any(Document.class));
    }

    @Test
    public void testActualizarMaquina_maquinaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.actualizarMaquina(null));
        verify(mockCollection, never()).replaceOne(any(Bson.class), any(Document.class));
    }

    @Test
    public void testActualizarMaquina_idNull_lanzaExcepcion() {
        MaquinaPojo maquina = new MaquinaPojo();
        maquina.setIdMaquina(null);

        assertThrows(PersistenciaException.class, () -> maquinaDAO.actualizarMaquina(maquina));
        verify(mockCollection, never()).replaceOne(any(Bson.class), any(Document.class));
    }

    @Test
    public void testActualizarMaquina_noEncontrada_lanzaExcepcion() {
        MaquinaPojo maquina = new MaquinaPojo();
        maquina.setIdMaquina("MAQ999");

        UpdateResult updateResult = mock(UpdateResult.class);

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toDocument(any(MaquinaPojo.class)))
                .thenReturn(new Document("_id", "MAQ999"));

        when(mockCollection.replaceOne(any(Bson.class), any(Document.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(0L);

        assertThrows(PersistenciaException.class, () -> maquinaDAO.actualizarMaquina(maquina));
        verify(mockCollection, times(1)).replaceOne(any(Bson.class), any(Document.class));
    }


    @Test
    public void testCambiarEstado_valido_llamaUpdateOne() throws PersistenciaException {
        UpdateResult updateResult = mock(UpdateResult.class);

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(1L);

        maquinaDAO.cambiarEstado("MAQ001", MaquinaPojo.EstadoMaquina.INACTIVO);

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testCambiarEstado_idNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.cambiarEstado(null, MaquinaPojo.EstadoMaquina.INACTIVO));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testCambiarEstado_estadoNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.cambiarEstado("MAQ001", null));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testCambiarEstado_noEncontrada_lanzaExcepcion() {
        UpdateResult updateResult = mock(UpdateResult.class);

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(0L);

        assertThrows(PersistenciaException.class, () -> maquinaDAO.cambiarEstado("MAQ999", MaquinaPojo.EstadoMaquina.INACTIVO));
        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }


    @Test
    public void testRegistrarBaja_valida_llamaUpdateOne() throws PersistenciaException {
        BajaPojo baja = new BajaPojo();
        UpdateResult updateResult = mock(UpdateResult.class);

        mockedBajaMapper.when(() -> BajaPersistenciaMapper.toDocument(any(BajaPojo.class)))
                .thenReturn(new Document("idBaja", "B001"));

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(1L);

        maquinaDAO.registrarBaja("MAQ001", baja);

        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testRegistrarBaja_idNull_lanzaExcepcion() {
        BajaPojo baja = new BajaPojo();

        assertThrows(PersistenciaException.class, () -> maquinaDAO.registrarBaja(null, baja));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testRegistrarBaja_bajaNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> maquinaDAO.registrarBaja("MAQ001", null));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void testRegistrarBaja_noEncontrada_lanzaExcepcion() {
        BajaPojo baja = new BajaPojo();
        UpdateResult updateResult = mock(UpdateResult.class);

        mockedBajaMapper.when(() -> BajaPersistenciaMapper.toDocument(any(BajaPojo.class)))
                .thenReturn(new Document("idBaja", "B001"));

        when(mockCollection.updateOne(any(Bson.class), any(Bson.class))).thenReturn(updateResult);
        when(updateResult.getMatchedCount()).thenReturn(0L);

        assertThrows(PersistenciaException.class, () -> maquinaDAO.registrarBaja("MAQ999", baja));
        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }


    @Test
    public void testFiltrarMaquinas_sinFiltros_retornaLista() throws PersistenciaException {
        AggregateIterable<Document> mockAggregate = mock(AggregateIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc1 = new Document("_id", "MAQ001");
        Document doc2 = new Document("_id", "MAQ002");

        when(mockCollection.aggregate(anyList())).thenReturn(mockAggregate);
        when(mockAggregate.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false);
        when(mockCursor.next()).thenReturn(doc1, doc2);

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(new MaquinaPojo());

        List<MaquinaPojo> resultado = maquinaDAO.filtrarMaquinas(null, null);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(mockCollection, times(1)).aggregate(anyList());
        mockedMaquinaMapper.verify(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class)), times(2));
    }

    @Test
    public void testFiltrarMaquinas_conEstadoYSucursal_retornaLista() throws PersistenciaException {
        AggregateIterable<Document> mockAggregate = mock(AggregateIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        Document doc = new Document("_id", "MAQ001");

        when(mockCollection.aggregate(anyList())).thenReturn(mockAggregate);
        when(mockAggregate.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(doc);

        mockedMaquinaMapper.when(() -> MaquinaPersistenciaMapper.toPojo(any(Document.class))).thenReturn(new MaquinaPojo());

        List<MaquinaPojo> resultado = maquinaDAO.filtrarMaquinas("S001", MaquinaPojo.EstadoMaquina.BUENAS_CONDICIONES);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mockCollection, times(1)).aggregate(anyList());
    }
    
}
