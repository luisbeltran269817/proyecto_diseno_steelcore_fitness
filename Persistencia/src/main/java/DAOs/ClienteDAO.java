/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import conexion.MongoConexion;
import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.MembresiaActivaPojo;
import excepciones.PersistenciaException;
import interfaces.IClienteDAO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersPersistencia.CitaPersistenciaMapper;
import mappersPersistencia.ClientePersistenciaMapper;
import mappersPersistencia.MembresiaActivaPersistenciaMapper;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Clase DAO para clientes
 *
 * @author luiscarlosbeltran
 */
public class ClienteDAO implements IClienteDAO {

    private static final Logger logger = Logger.getLogger(ClienteDAO.class.getName());
    private final MongoCollection<Document> coleccion;

    public ClienteDAO() {
        this.coleccion = MongoConexion.obtenerBaseDatos().getCollection("clientes");
    }

    /**
     * Método que obtiene todos loc clientes de la BD
     *
     * @return una lista de pojos pojudos
     * @throws PersistenciaException so ocurre un error
     */
    @Override
    public List<ClientePojo> obtenerClientes() throws PersistenciaException {
        try {
            List<ClientePojo> lista = new ArrayList<>();
            FindIterable<Document> docs = coleccion.find();
            for (Document doc : docs) {
                ClientePojo pojo = ClientePersistenciaMapper.toPojo(doc);
                lista.add(pojo);
            }
            logger.log(Level.INFO, "Clientes obtenidos correctamente");
            return lista;
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al obtener clientes", e);
            throw new PersistenciaException("Error al obtener clientes");
        }
    }

    /**
     * Método que busca un cliente por su correo en la BD
     *
     * @param correo el correo del cliente
     * @return el pojo del cliente si lo encuentra, null en caso contrario
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public ClientePojo buscarPorCorreo(String correo) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("usuario.correo", correo)).first();
            if (doc == null) {
                return null;
            }
            logger.log(Level.INFO, "Cliente encontrado correctamente");
            return ClientePersistenciaMapper.toPojo(doc);
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al buscar cliente", e);
            throw new PersistenciaException("Error al buscar cliente");
        }
    }

    /**
     * Método que actualiza un cliente en la BD
     *
     * @param cliente el cliente nuevo
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void actualizar(ClientePojo cliente) throws PersistenciaException {
        try {
            if (cliente == null) {
                throw new PersistenciaException("El cliente no puede ser null");
            }
            Document doc = ClientePersistenciaMapper.toDocument(cliente);
            coleccion.replaceOne(Filters.eq("_id", cliente.getIdCliente()), doc);
            logger.log(Level.INFO, "Cliente actualizado correctamente");
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al actualizar cliente", e);
            throw new PersistenciaException("Error al actualizar cliente");
        }
    }

    /**
     * Método que obtiene la membresía activa de un cliente
     *
     * @param correo el correo del cliente por que se buscará
     * @return un pojo que contiene la membresia activa
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public MembresiaActivaPojo obtenerMembresiaActiva(String correo) throws PersistenciaException {
        try {
            Document doc = coleccion.find(Filters.eq("usuario.correo", correo)).first();
            if (doc == null) {
                return null;
            }
            Document membresiaDoc = (Document) doc.get("membresiaActiva");
            if (membresiaDoc == null) {
                return null;
            }
            logger.log(Level.INFO, "Membresía activa obtenida correctamente");
            return MembresiaActivaPersistenciaMapper.toPojo(membresiaDoc);
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al obtener membresía activa", e);
            throw new PersistenciaException("Error al obtener membresía activa");
        }
    }

    /**
     * Método que guarda la cita de bienvenida de un cliente
     *
     * @param correo el correo del cliente
     * @param cita la cita de bienvenida a guardar
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public void guardarCitaBienvenida(String correo, CitaPojo cita) throws PersistenciaException {
        try {
            if (cita == null) {
                throw new PersistenciaException("La cita no puede ser null");
            }
            Document citaDoc = CitaPersistenciaMapper.toDocument(cita);
            coleccion.updateOne(Filters.eq("usuario.correo", correo), Updates.set("citaBienvenida", citaDoc));
            logger.log(Level.INFO, "Cita de bienvenida guardada correctamente");
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al guardar cita de bienvenida", e);
            throw new PersistenciaException("Error al guardar cita de bienvenida");
        }
    }

    /**
     * Metodo para eliminar la membresia activa de un cliente
     *
     * @param correo el correo para buscar al cliente al que se le eliminara la
     * membresia
     * @throws PersistenciaException
     */
    @Override
    public void eliminarMembresiaActiva(String correo) throws PersistenciaException {
        try {
            coleccion.updateOne(Filters.eq("usuario.correo", correo), Updates.unset("membresiaActiva")
            );
            logger.log(Level.INFO, "Snapshot de membresía eliminado");
        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al eliminar membresía activa", e);
            throw new PersistenciaException(
                    "Error al eliminar membresía activa");
        }
    }

    /**
     * Consulta citas de bienvenida embebidas en los clientes usando filtros
     * para reportes.
     *
     * La cita se encuentra dentro del documento del cliente como
     * "citaBienvenida". Se filtra por fecha, sucursal y entrenador cuando los
     * filtros son proporcionados.
     *
     * Las fechas se comparan como texto porque se almacenan con
     * LocalDateTime.toString(), formato ISO que mantiene orden cronológico.
     *
     * @param fechaInicio fecha inicial del periodo.
     * @param fechaFin fecha final del periodo.
     * @param idSucursal id de la sucursal, puede ser null o vacío.
     * @param idEntrenador id del entrenador, puede ser null o vacío.
     * @return lista de citas encontradas.
     * @throws PersistenciaException si ocurre un error durante la consulta.
     */
    @Override
    public List<CitaPojo> consultarCitasParaReportes(
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            String idSucursal,
            String idEntrenador
    ) throws PersistenciaException {
        try {
            List<Bson> filtros = new ArrayList<>();

            filtros.add(Filters.exists("citaBienvenida", true));

            if (fechaInicio != null && fechaFin != null) {
                filtros.add(Filters.gte("citaBienvenida.fechaHora", fechaInicio.toString()));
                filtros.add(Filters.lte("citaBienvenida.fechaHora", fechaFin.toString()));
            }

            if (idSucursal != null && !idSucursal.isBlank()) {
                filtros.add(Filters.eq("citaBienvenida.idSucursal", idSucursal));
            }

            if (idEntrenador != null && !idEntrenador.isBlank()) {
                filtros.add(Filters.eq("citaBienvenida.idEntrenador", idEntrenador));
            }

            /*
         * Para desempeño de entrenadores contamos citas confirmadas o completadas.
         * Si quieres contar solo las atendidas, deja únicamente COMPLETADA.
             */
            filtros.add(Filters.in("citaBienvenida.estado", "CONFIRMADA", "COMPLETADA"));

            Bson filtroFinal = Filters.and(filtros);

            List<CitaPojo> citas = new ArrayList<>();

            FindIterable<Document> docs = coleccion.find(filtroFinal);

            for (Document doc : docs) {
                Document citaDoc = (Document) doc.get("citaBienvenida");

                if (citaDoc != null) {
                    CitaPojo cita = CitaPersistenciaMapper.toPojo(citaDoc);
                    citas.add(cita);
                }
            }

            logger.log(Level.INFO, "Citas para reportes consultadas correctamente");
            return citas;

        } catch (MongoException e) {
            logger.log(Level.SEVERE, "Error al consultar citas para reportes", e);
            throw new PersistenciaException("Error al consultar citas para reportes");
        }
    }
}
