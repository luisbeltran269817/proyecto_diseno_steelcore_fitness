/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosAcceso;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import conexion.MongoConexion;
import dominioAcceso.VisitaAccesoPojo;
import excepciones.PersistenciaException;
import mappersAcceso.VisitaAccesoPersistenciaMapper;
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
 * @author julian izaguirre
 */
public class VisitaAccesoDAOTest {

    private MongoDatabase mockDatabase;
    private MongoCollection<Document> mockCollection;
    private MockedStatic<MongoConexion> mockedConexion;
    private MockedStatic<VisitaAccesoPersistenciaMapper> mockedMapper;
    private VisitaAccesoDAO visitaAccesoDAO;

    @BeforeEach
    public void setUp() {
        mockDatabase = mock(MongoDatabase.class);
        mockCollection = mock(MongoCollection.class);

        mockedConexion = mockStatic(MongoConexion.class);
        mockedConexion.when(MongoConexion::obtenerBaseDatos).thenReturn(mockDatabase);
        
        // Cuidado aquí: el DAO le pega a la colección "visitas", no a "visitasAcceso"
        when(mockDatabase.getCollection("visitas")).thenReturn(mockCollection);

        mockedMapper = mockStatic(VisitaAccesoPersistenciaMapper.class);
        visitaAccesoDAO = new VisitaAccesoDAO();
    }

    @AfterEach
    public void tearDown() {
        mockedConexion.close();
        mockedMapper.close();
    }

    @Test
    public void guardar_VisitaValida_InsertaYRetorna() throws PersistenciaException {
        // Preparamos los datos del socio entrando por los torniquetes
        VisitaAccesoPojo nuevaVisita = new VisitaAccesoPojo();
        nuevaVisita.setIdVisita("VISITA-HOY-001");
        Document docVisita = new Document("_id", "VISITA-HOY-001").append("estado", "REGISTRADA");

        mockedMapper.when(() -> VisitaAccesoPersistenciaMapper.toDocument(nuevaVisita))
                .thenReturn(docVisita);

        VisitaAccesoPojo resultado = visitaAccesoDAO.guardar(nuevaVisita);

        // Verificamos que no se pierdan datos y que Mongo reciba la instrucción
        assertEquals(nuevaVisita, resultado);
        verify(mockCollection, times(1)).insertOne(docVisita);
    }

    @Test
    public void guardar_ObjetoNulo_RevientaRapido() {
        // Si desde la lógica de arriba mandan un null, el DAO debe atraparlo antes de ir a BD
        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            visitaAccesoDAO.guardar(null);
        });

        assertEquals("La visita no puede ser null", error.getMessage());
        verify(mockCollection, never()).insertOne(any(Document.class));
    }

    @Test
    public void guardar_FallaConexionMongo_LanzaPersistenciaException() {
        VisitaAccesoPojo nuevaVisita = new VisitaAccesoPojo();
        Document docVisita = new Document();
        
        mockedMapper.when(() -> VisitaAccesoPersistenciaMapper.toDocument(nuevaVisita))
                .thenReturn(docVisita);
        
        // Simulamos que al hacer insertOne, Mongo tira una excepción (ej. fallo de red)
        doThrow(new MongoException("Fallo en la red")).when(mockCollection).insertOne(docVisita);

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            visitaAccesoDAO.guardar(nuevaVisita);
        });

        assertEquals("Error al registrar la visita de acceso", error.getMessage());
    }

    @Test
    public void actualizarServicio_TodoBien_MandaUpdate() throws PersistenciaException {
        // Simulamos que el socio pidió toallas o equipo
        when(mockCollection.updateOne(any(Bson.class), any(Bson.class)))
                .thenReturn(UpdateResult.acknowledged(1, 1L, null));

        assertDoesNotThrow(() -> {
            visitaAccesoDAO.actualizarServicio(
                "VISITA-HOY-001", 
                "PRESTAMO-TOALLA", 
                "TOALLA-ID-5432"
            );
        });

        // Verificamos que sí se llamó el método de actualizar
        verify(mockCollection, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }

    @Test
    public void actualizarServicio_FalloDeBD_LanzaExcepcion() {
        // Si el motor de la BD truena justo al asignar el servicio
        when(mockCollection.updateOne(any(Bson.class), any(Bson.class)))
                .thenThrow(new MongoException("Permisos denegados en BD"));

        PersistenciaException error = assertThrows(PersistenciaException.class, () -> {
            visitaAccesoDAO.actualizarServicio(
                "VISITA-HOY-005", 
                "RENTA-LOCKER", 
                "LOCKER-A12"
            );
        });

        assertEquals("Error al actualizar el servicio de la visita", error.getMessage());
    }
}