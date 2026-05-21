/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package testsMantenimiento;

import DAOsMantenimiento.AdminMantenimientoDAO;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import conexion.MongoConexion;
import dominios_mantenimiento.AdminMantenimientoPojo;
import excepciones.PersistenciaException;
import mappersMantenimientoPersistencia.AdminMantenimientoPersistenciaMapper;
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
public class AdminMantenimientoDAOTest {
    
    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<AdminMantenimientoPersistenciaMapper> mockedMapper;
    private AdminMantenimientoDAO adminDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        when(mockDatabase.getCollection("adminsMantenimiento")).thenReturn(mockCollection);

        mockedMapper = mockStatic(AdminMantenimientoPersistenciaMapper.class);

        adminDAO = new AdminMantenimientoDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedMapper.close();
        mockedConexion.close();
    }


    @Test
    public void testBuscarPorCorreo_correoValido_retornaPojo() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);
        Document doc = new Document("_id", "ADM001");
        AdminMantenimientoPojo pojoDummy = new AdminMantenimientoPojo();

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(doc);

        mockedMapper.when(() -> AdminMantenimientoPersistenciaMapper.toPojo(any(Document.class)))
                .thenReturn(pojoDummy);

        AdminMantenimientoPojo resultado = adminDAO.buscarPorCorreo("admin@gmail.com");

        assertNotNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorCorreo_noEncontrado_retornaNull() throws PersistenciaException {
        FindIterable<Document> mockIterable = mock(FindIterable.class);

        when(mockCollection.find(any(Bson.class))).thenReturn(mockIterable);
        when(mockIterable.first()).thenReturn(null);

        AdminMantenimientoPojo resultado = adminDAO.buscarPorCorreo("noexiste@gmail.com");

        assertNull(resultado);
        verify(mockCollection, times(1)).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorCorreo_correoNull_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> adminDAO.buscarPorCorreo(null));
        verify(mockCollection, never()).find(any(Bson.class));
    }

    @Test
    public void testBuscarPorCorreo_correoVacio_lanzaExcepcion() {
        assertThrows(PersistenciaException.class, () -> adminDAO.buscarPorCorreo(" "));
        verify(mockCollection, never()).find(any(Bson.class));
    }
}
