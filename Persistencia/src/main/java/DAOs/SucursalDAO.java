/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import conexion.MongoConexion;
import dominios.AmenidadPojo;
import dominios.PlanPojo;
import dominios.SucursalPojo;
import excepciones.PersistenciaException;
import interfaces.ISucursalDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import mappersPersistencia.SucursalPersistenciaMapper;
import org.bson.Document;

/**
 * Clase DAO para sucursal
 * @author luiscarlosbeltran
 */
public class SucursalDAO implements ISucursalDAO{
    private MongoCollection<Document> coleccion;
    private static final System.Logger LOG = System.getLogger(SucursalDAO.class.getName());
    
    public SucursalDAO() {
        this.coleccion =MongoConexion.obtenerBaseDatos().getCollection("sucursales");
    }
    /**
     * Metodo para obtener todas las sucursales
     * @return List<SucursalPojo> las sucursales encontradas
     * @throws PersistenciaException 
     */
    @Override
    public List<SucursalPojo>obtenerSucursales() throws PersistenciaException {
        try {
            List<SucursalPojo> lista =new ArrayList<>();
            FindIterable<Document> docs =coleccion.find();
            for (Document doc : docs) {
                lista.add(SucursalPersistenciaMapper.toPojo(doc));
            }
            return lista;
        } catch (MongoException ex) {
            throw new PersistenciaException("Error al consultar sucursales");
        }
    }
    /**
     * Metodo buscar una sucursal usando su id
     * @param idSucursal el id de la sucursal a buscar
     * @return SucursalPojo de la sucursal si se encontro, null en caso contrario
     * @throws PersistenciaException 
     */
    @Override
    public SucursalPojo buscarPorId( String idSucursal) throws PersistenciaException {
        try{
            Document doc =coleccion.find(Filters.eq("_id",idSucursal)).first();
            if (doc == null) {
                return null;
            }
            return SucursalPersistenciaMapper.toPojo(doc);
        }catch(MongoException ex){
            throw new PersistenciaException("Error al buscar Sucursal");
        }
    }
    /**
     * Metodo para obtener los planes de una sucursal
     * @param idSucursal la sucursal de la que se quieren obtener los planes
     * @return List<PlanPojo> si se encontro la sucursal, o lista vacia si no
     * @throws PersistenciaException 
     */
    @Override
    public List<PlanPojo>obtenerPlanesSucursal(String idSucursal) throws PersistenciaException {
        try {
            SucursalPojo sucursal =buscarPorId(idSucursal);
            if (sucursal == null) {
                return new ArrayList<>();
            }
            return sucursal.getPlanes();
        } catch (MongoException ex) {
            throw new PersistenciaException("Ocurrió un Mongo error");
        }catch(PersistenciaException ex){
            throw new PersistenciaException("Si estás leyendo esto tengo hambre");
        }
    }
    /**
     * metodo para buscar un plan por su id
     * @param idPlan el id del plan a buscar
     * @return PlanPojo si se encuentra, null si no
     * @throws PersistenciaException 
     */
    @Override
    public PlanPojo buscarPlanPorId(String idPlan)throws PersistenciaException {
        try {
            List<SucursalPojo> sucursales = obtenerSucursales();
            for (SucursalPojo sucursal: sucursales) {
                if (sucursal.getPlanes() != null) {
                    for (PlanPojo plan: sucursal.getPlanes()) {
                        if (plan.getIdPlan().equals(idPlan)) {
                            LOG.log(System.Logger.Level.INFO, idPlan + " Plan encontrado");
                            return plan;
                        }
                    }
                }
            }
            LOG.log(System.Logger.Level.ALL, "No se encontró el plan");
            return null;
        } catch (MongoException ex) {
            LOG.log(System.Logger.Level.ERROR,  "Error al buscar plan" );
            throw new PersistenciaException("Error al buscar plan");
        }
    }
    /**
     * metodo para obtener las amenidades de un plan
     * @param idPlan el plan del cual se quieren obtener las amenidades
     * @return List<AmenidadPojo> si se encontro el plan, lista vacia si no
     * @throws PersistenciaException 
     */
    @Override
    public List<AmenidadPojo> obtenerAmenidadesDePlan(String idPlan) throws PersistenciaException {
        try {
            FindIterable<Document> docs= coleccion.find();
            for (Document doc : docs) {
                SucursalPojo sucursal= SucursalPersistenciaMapper.toPojo(doc);
                if (sucursal.getPlanes() == null) {
                    continue;
                }
                for (PlanPojo plan: sucursal.getPlanes()) {
                    if (plan.getIdPlan().equals(idPlan)) {
                        return plan.getAmenidades();
                    }
                }
            }
            return new ArrayList<>();

        } catch (MongoException e) {
            throw new PersistenciaException("Error al obtener amenidades del plan");
        }
    }
}
