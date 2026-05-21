/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosAcceso;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominioAcceso.ClasePojo;
import excepciones.PersistenciaException;
import mappersAcceso.ClasePersistenciaMapper;
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
 * @author julian izaguirre
 */
public class ClaseDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<ClasePersistenciaMapper> mockedMapper;
    private ClaseDAO claseDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        // Preparamos la conexión fake para que no intente pegarle al Mongo de verdad
        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("clases")).thenReturn(mockCollection);

        mockedMapper = mockStatic(ClasePersistenciaMapper.class);

        claseDAO = new ClaseDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedConexion.close();
        mockedMapper.close();
    }

    @Test
    public void obtenerPorSucursalYPlan_Exito_RetornaListaClases() throws PersistenciaException {
        // Configuramos la respuesta de Mongo como si encontrara 2 clases
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        MongoCursor<Document> mockCursor = mock(MongoCursor.class);
        Document docClase = new Document("nombre", "Clase de Crossfit - 6:00 AM");
        ClasePojo pojoFalso = mock(ClasePojo.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.iterator()).thenReturn(mockCursor);
        when(mockCursor.hasNext()).thenReturn(true, true, false); // Hay 2 resultados, luego termina
        when(mockCursor.next()).thenReturn(docClase);

        mockedMapper.when(() -> ClasePersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoFalso);

        List<ClasePojo> resultado = claseDAO.obtenerPorSucursalYPlan("SUC-OBREGON-CENTRO", "PLAN-GOLD");

        assertNotNull(resultado, "La lista no debería ser nula aunque no trajera datos");
        assertEquals(2, resultado.size(), "Deberían regresar las 2 clases encontradas");
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void obtenerPorSucursalYPlan_FallaMongo_LanzaExcepcion() {
        // Simulamos que Mongo se desconecta o hay un timeout
        when(mockCollection.find(any(Bson.class))).thenThrow(new MongoException("Timeout del servidor"));

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            claseDAO.obtenerPorSucursalYPlan("SUC-OBREGON-CENTRO", "PLAN-BASICO");
        });

        assertEquals("Error al consultar las clases disponibles", error.getMessage());
    }

    @Test
    public void buscarPorId_ExisteClase_RetornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document docClase = new Document("_id", "CLASE-ZUMBA-TARDES");
        ClasePojo pojoFalso = mock(ClasePojo.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(docClase);
        
        mockedMapper.when(() -> ClasePersistenciaMapper.toPojo(docClase)).thenReturn(pojoFalso);

        ClasePojo resultado = claseDAO.buscarPorId("CLASE-ZUMBA-TARDES");

        assertNotNull(resultado, "Debe regresar el objeto mapeado si la clase existe");
    }

    @Test
    public void buscarPorId_NoExisteClase_RetornaNull() throws PersistenciaException {
        // Cuando no existe en Mongo, first() devuelve null
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        ClasePojo resultado = claseDAO.buscarPorId("CLASE-INVENTADA");

        assertNull(resultado, "Si la clase no existe en BD, el DAO debe retornar null directo");
    }

    @Test
    public void inscribirSocio_TodoBien_ActualizaColeccion() throws PersistenciaException {
        // Preparamos todo para que la clase exista, tenga lugar y el socio sea nuevo ahí
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document docClase = new Document("_id", "CLASE-SPINNING-01");
        ClasePojo clasePerfecta = mock(ClasePojo.class);

        when(clasePerfecta.estaLlena()).thenReturn(false);
        when(clasePerfecta.estaInscrito("SOCIO-JULIAN-71")).thenReturn(false);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(docClase);
        mockedMapper.when(() -> ClasePersistenciaMapper.toPojo(docClase)).thenReturn(clasePerfecta);

        // Simulamos que el update responde bien
        when(mockCollection.updateOne(any(Bson.class), any(Bson.class)))
                .thenReturn(UpdateResult.acknowledged(1, 1L, null));

        // Ejecutamos (no debería tronar)
        assertDoesNotThrow(() -> {
            claseDAO.inscribirSocio("CLASE-SPINNING-01", "SOCIO-JULIAN-71");
        });

        // Verificamos que realmente se mandó la orden de update a Mongo
        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void inscribirSocio_ClaseInexistente_LanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null); // La clase no está

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            claseDAO.inscribirSocio("CLASE-FANTASMA", "SOCIO-JULIAN-71");
        });

        assertEquals("La clase no existe en el sistema.", error.getMessage());
        // Nos aseguramos de que ni de chiste intente actualizar Mongo
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void inscribirSocio_CupoLleno_LanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document docClase = new Document();
        ClasePojo claseLlena = mock(ClasePojo.class);

        when(claseLlena.estaLlena()).thenReturn(true); // ¡Pum! Ya no cabe nadie

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(docClase);
        mockedMapper.when(() -> ClasePersistenciaMapper.toPojo(docClase)).thenReturn(claseLlena);

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            claseDAO.inscribirSocio("CLASE-BOX-TARDE", "SOCIO-NUEVO-05");
        });

        assertTrue(error.getMessage().contains("cupo para esta clase ya está lleno"));
        verify(mockCollection, never()).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void inscribirSocio_SocioYaInscrito_LanzaExcepcion() {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document docClase = new Document();
        ClasePojo claseValidada = mock(ClasePojo.class);

        when(claseValidada.estaLlena()).thenReturn(false);
        when(claseValidada.estaInscrito("SOCIO-DISTRUIDO-01")).thenReturn(true); // Se le olvidó que ya apartó

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(docClase);
        mockedMapper.when(() -> ClasePersistenciaMapper.toPojo(docClase)).thenReturn(claseValidada);

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            claseDAO.inscribirSocio("CLASE-YOGA", "SOCIO-DISTRUIDO-01");
        });

        assertEquals("El socio ya está inscrito en esta clase.", error.getMessage());
    }
}