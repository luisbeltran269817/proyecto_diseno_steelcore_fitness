/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import mappersPersistencia.AmenidadPersistenciaMapper;
import org.bson.Document;
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
public class AmenidadDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<AmenidadPersistenciaMapper> mockedMapper;
    private AmenidadDAO amenidadDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("amenidades")).thenReturn(mockCollection);

        // Mockeamos el mapper estático para evitar NullPointer en campos del pojo/document
        mockedMapper = mockStatic(AmenidadPersistenciaMapper.class);

        amenidadDAO = new AmenidadDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }

    @Test
    public void testAgregar_llamaInsertOne() {
        // El mapper devuelve un Document vacío para no explotar
        mockedMapper.when(() -> AmenidadPersistenciaMapper.toDocument(any(AmenidadPojo.class)))
                .thenReturn(new Document("nombre", "Alberca"));

        AmenidadPojo amenidad = new AmenidadPojo();

        amenidadDAO.agregar(amenidad);

        verify(mockCollection, times(1)).insertOne(any(Document.class));
    }

    @Test
    public void testConsultarTodas_retornaLista() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);
        Document docMock = new Document("nombre", "Alberca");
        AmenidadPojo pojoDummy = new AmenidadPojo();

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, false);
        when(mockCursor.next()).thenReturn(docMock);

        // El mapper toPojo devuelve un pojo dummy para no explotar por campos nulos
        mockedMapper.when(() -> AmenidadPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        List<AmenidadPojo> resultado = amenidadDAO.ConsultarTodas();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(mockCollection, times(1)).find();
    }

    @Test
    public void testConsultarTodas_sinDocumentos_retornaListaVacia() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);

        when(mockCollection.find()).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(false);

        List<AmenidadPojo> resultado = amenidadDAO.ConsultarTodas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}