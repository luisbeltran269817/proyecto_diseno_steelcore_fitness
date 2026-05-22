/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.ClienteDAO;
import DAOs.EjercicioDAO;
import DAOs.MembresiaDAO;
import DAOs.PlantillaRutinaDAO;
import Excepciones.NegocioException;
import dominios.CitaPojo;
import dominios.ClientePojo;
import dominios.DetalleRutinaPojo;
import dominios.EjercicioPojo;
import dominios.MembresiaActivaPojo;
import dominios.MembresiaPojo;
import dominios.RutinaPojo;
import dtos.CitaDTO;
import dtos.ClienteDTO;
import dtos.DetalleRutinaDTO;
import dtos.EjercicioDTO;
import dtos.MembresiaDTO;
import dtos.RutinaDTO;
import dtosReportes.FiltrosReporteDTO;
import excepciones.PersistenciaException;
import interfaces.IClienteBO;
import interfaces.IClienteDAO;
import interfaces.IEjercicioDAO;
import interfaces.IMembresiaDAO;
import interfaces.IPlantillaRutinaDAO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappersBO.CitaMapper;
import mappersBO.ClienteMapper;
import mappersBO.DetalleRutinaMapper;
import mappersBO.EjercicioMapper;
import mappersBO.MembresiaMapper;
import mappersBO.RutinaMapper;

/**
 *
 * @author julian izaguirre
 */
public class ClienteBO implements IClienteBO {

    private final IClienteDAO clienteDAO;
    private final IMembresiaDAO membresiaDAO;
    private final IEjercicioDAO ejercicioDAO;
    private final IPlantillaRutinaDAO plantillaDAO;
    private static final Logger logger = Logger.getLogger(ClienteBO.class.getName());

    public ClienteBO() {
        this.clienteDAO = new ClienteDAO();
        this.membresiaDAO= new MembresiaDAO();
        this.ejercicioDAO = new EjercicioDAO();
        this.plantillaDAO = new PlantillaRutinaDAO();
    }

    @Override
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        try {
            List<ClientePojo> pojos = clienteDAO.obtenerClientes();
            List<ClienteDTO> clientes = new ArrayList<>();
            for (ClientePojo pojo : pojos) {
                ClienteDTO dto = ClienteMapper.toDTO(pojo);
                clientes.add(dto);
            }
            logger.log(Level.INFO, "Los clientes se obtuvieron correctamente");
            return clientes;
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener clientes", e);
            throw new NegocioException("Error al obtener clientes");
        }
    }

    @Override
    public ClienteDTO buscarPorCorreo(String correo) throws NegocioException {
        try {
            ClientePojo pojo = clienteDAO.buscarPorCorreo(correo);
            logger.log(Level.INFO, "Cliente encontrado correctamente");
            return ClienteMapper.toDTO(pojo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al buscar cliente", e);
            throw new NegocioException("Error al buscar cliente");
        }
    }

    @Override
    public void actualizar(ClienteDTO cliente) throws NegocioException {
        try {
            if (cliente == null) {
                throw new NegocioException("El cliente no puede ser null");
            }
            ClientePojo pojo = ClienteMapper.toPojo(cliente);
            clienteDAO.actualizar(pojo);
            logger.log(Level.INFO, "Cliente actualizado correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al actualizar cliente", e);
            throw new NegocioException("Error al actualizar cliente");
        }
    }

    @Override
    public MembresiaDTO obtenerMembresiaActiva(String correo) throws NegocioException {
        try {

            MembresiaActivaPojo snapshot = clienteDAO.obtenerMembresiaActiva(correo);
            if (snapshot == null) {
                return null;
            }
            MembresiaPojo pojo = membresiaDAO.buscarPorId(snapshot.getIdMembresia());
            if (pojo == null) {
                return null;
            }
            logger.log(Level.INFO, "Membresía activa encontrada");
            return MembresiaMapper.toDTO(pojo);

        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al obtener membresía activa", e);
            throw new NegocioException(
                    "Error al obtener membresía activa");
        }
    }

    @Override
    public void guardarCitaBienvenida(String correo, CitaDTO cita) throws NegocioException {
        try {
            if (correo == null || correo.isBlank()) {
                throw new NegocioException("El correo no puede estar vacío");
            }
            if (cita == null) {
                throw new NegocioException("La cita no puede ser null");
            }
            CitaPojo pojo = CitaMapper.toPojo(cita);
            clienteDAO.guardarCitaBienvenida(correo, pojo);
            logger.log(Level.INFO, "Cita guardada correctamente");
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al guardar cita", e);
            throw new NegocioException("Error al guardar cita");
        }
    }

    @Override
    public void eliminarMembresiaActiva(String correo) throws NegocioException {
        try {
            clienteDAO.eliminarMembresiaActiva(correo);
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al eliminar la meembresía activa", e);
            throw new NegocioException("Error al eliminar membresía activa", e);
        }
    }
    
    @Override
    public List<RutinaDTO> obtenerRutinas(String correo) throws NegocioException {
        try {
            List<RutinaPojo> rutinaPojos = clienteDAO.obtenerRutinas(correo);
            List<RutinaDTO> rutinasDTO = new ArrayList<>();
            
            for (RutinaPojo rutinaPojo:rutinaPojos) {
                RutinaDTO rutinaDTO = RutinaMapper.toDTO(rutinaPojo);
                if (rutinaPojo.getDetalles() != null) {
                    List<DetalleRutinaDTO> detallesCompleto = new ArrayList<>();
                    for (DetalleRutinaPojo detallePojo : rutinaPojo.getDetalles()) {
                        DetalleRutinaDTO detalleDTO = DetalleRutinaMapper.toDTO(detallePojo);
                        
                        if (detallePojo.getIdsEjercicios() != null && !detallePojo.getIdsEjercicios().isEmpty()) {
                            List<EjercicioPojo> ejercicioPojos = ejercicioDAO.obtenerPorListaIds(detallePojo.getIdsEjercicios());
                            List<EjercicioDTO> ejerciciosDTO = new ArrayList<>();
                            for (EjercicioPojo ejercicioPojo : ejercicioPojos) {
                                ejerciciosDTO.add(EjercicioMapper.toDTO(ejercicioPojo));
                            }
                            detalleDTO.setEjercicios(ejerciciosDTO);
                        }
                        detallesCompleto.add(detalleDTO);
                    }
                    rutinaDTO.setDetalles(detallesCompleto);
                }
                rutinasDTO.add(rutinaDTO);
            }
            return rutinasDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener rutinas");
        }
    }
    
    @Override
    public RutinaDTO guardarRutina(String correo, RutinaDTO rutinaDTO) throws NegocioException {
        try {
            RutinaPojo pojo = RutinaMapper.toPojo(rutinaDTO);
            clienteDAO.guardarRutina(correo, pojo);
            return rutinaDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al guardar rutina");
        }
    }
    
    @Override
    public boolean existeRutinaConNombre(String correo, String nombre) throws NegocioException {
        try {
            return clienteDAO.existeRutinaConNombre(correo, nombre);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al verificar nombre de rutina");
        }
    }
    
    @Override
    public RutinaDTO actualizarRutina(String correo, RutinaDTO rutinaDTO) throws NegocioException {
        try {
            RutinaPojo pojo = RutinaMapper.toPojo(rutinaDTO);
            clienteDAO.actualizarRutina(correo, pojo);
            return rutinaDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al actualizar rutina");
        }
    }
    
    @Override
    public boolean borrarRutina(String correo, String nombre) throws NegocioException {
        try {
            boolean borrado = clienteDAO.borrarRutina(correo, nombre);
            return borrado;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al borrar rutina");
        }
    }
    
    @Override
    public String obtenerIdSucursalMembresiaActiva(String correo) throws NegocioException {
        try {
            if (correo == null || correo.isBlank()) {
                throw new NegocioException("El correo no puede estar vacío o ser nulo");
            }
            
            String idSucursal = clienteDAO.obtenerIdSucursalMembresiaActiva(correo);
            if (idSucursal == null) {
                logger.log(Level.WARNING, "No se encontro");
                return null;
            }
            
            logger.log(Level.INFO, "Sucursal de la membresia activa obtenida correctamente");
            return idSucursal;
            
        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error ayudaaaa", e);
            throw new NegocioException("Error al obtener la sucursal de la membresia activa", e);
        }
    }
    
    @Override
    public RutinaDTO obtenerPlantilla(String nombre) throws NegocioException {
        try {
            RutinaPojo plantillaPojo = plantillaDAO.obtenerPlantilla(nombre);
            if (plantillaPojo == null) return null;
            
            RutinaDTO rutinaDTO = RutinaMapper.toDTO(plantillaPojo);
            if (plantillaPojo.getDetalles() != null) {
                List<DetalleRutinaDTO> detallesEnriquecidos = new ArrayList<>();
                for (DetalleRutinaPojo detallePojo : plantillaPojo.getDetalles()) {
                    DetalleRutinaDTO detalleDTO = DetalleRutinaMapper.toDTO(detallePojo);
                    if (detallePojo.getIdsEjercicios() != null && !detallePojo.getIdsEjercicios().isEmpty()) {
                        List<EjercicioPojo> ejercicioPojos = ejercicioDAO.obtenerPorListaIds(detallePojo.getIdsEjercicios());
                        List<EjercicioDTO> ejerciciosDTO = new ArrayList<>();
                        for (EjercicioPojo ep : ejercicioPojos) {
                            ejerciciosDTO.add(EjercicioMapper.toDTO(ep));
                        }
                        detalleDTO.setEjercicios(ejerciciosDTO);
                    }
                    detallesEnriquecidos.add(detalleDTO);
                }
                rutinaDTO.setDetalles(detallesEnriquecidos);
            }
            
            rutinaDTO.setFechaCreacion(LocalDateTime.now());
            return rutinaDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener plantilla");
        }
    }

    /**
     * Consulta citas de bienvenida aplicando los filtros seleccionados para
     * reportes.
     *
     * Convierte las fechas del DTO de filtros a LocalDateTime para consultar
     * desde el inicio del día hasta el final del día. Después convierte las
     * citas POJO a DTO para que puedan ser usadas por la capa de negocio de
     * reportes.
     *
     * @param filtros filtros seleccionados por el administrador.
     * @return lista de citas que cumplen con los filtros.
     * @throws NegocioException si los filtros son inválidos o falla la
     * consulta.
     */
    @Override
    public List<CitaDTO> consultarCitasParaReportes(FiltrosReporteDTO filtros) throws NegocioException {
        try {
            if (filtros == null) {
                throw new NegocioException("Debe ingresar filtros para consultar citas.");
            }

            if (filtros.getFechaInicio() == null || filtros.getFechaFin() == null) {
                throw new NegocioException("Debe ingresar fecha de inicio y fecha de fin.");
            }

            if (filtros.getFechaFin().isBefore(filtros.getFechaInicio())) {
                throw new NegocioException("La fecha final no puede ser menor que la fecha inicial.");
            }

            List<CitaPojo> pojos = clienteDAO.consultarCitasParaReportes(
                    filtros.getFechaInicio().atStartOfDay(),
                    filtros.getFechaFin().atTime(LocalTime.MAX),
                    filtros.getSucursal(),
                    filtros.getEntrenador()
            );

            List<CitaDTO> citas = new ArrayList<>();

            for (CitaPojo pojo : pojos) {
                citas.add(CitaMapper.toDTO(pojo));
            }

            return citas;

        } catch (PersistenciaException e) {
            logger.log(Level.SEVERE, "Error al consultar citas para reportes", e);
            throw new NegocioException("Error al consultar citas para reportes", e);
        }
    }

}
