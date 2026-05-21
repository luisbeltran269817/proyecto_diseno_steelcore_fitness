/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package inventarioMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.BajasInventarioDTO;
import dtosInventarioMantenimiento.MantenimientoDTO;
import dtosInventarioMantenimiento.MantenimientoPiezaDTO;
import dtosInventarioMantenimiento.MaquinaDTO;
import dtosInventarioMantenimiento.MaquinaDTO.EstadoMaquinaDTO;
import dtosInventarioMantenimiento.PiezaDTO;
import dtosInventarioMantenimiento.TecnicoDTO;
import dtosInventarioMantenimiento.UltimoMantenimientoDTO;
import excepciones.InventarioMantenimientoException;
import interfacesMantenimiento.IMantenimientoBO;
import interfacesMantenimiento.IMaquinaBO;
import interfacesMantenimiento.IPiezaBO;
import interfacesMantenimiento.ITecnicoBO;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import mantenimientoBuilder.MantenimientoBuilder;
import objetosNegocioMantenimiento.MantenimientoBO;
import objetosNegocioMantenimiento.MaquinaBO;
import objetosNegocioMantenimiento.PiezaBO;
import objetosNegocioMantenimiento.TecnicoBO;

/**
 *
 * @author Tungs
 */
public class ControlInventarioMantenimiento {
    private final IMaquinaBO maquinaBO;
    private final IPiezaBO piezaBO;
    private final ITecnicoBO tecnicoBO;
    private final IMantenimientoBO mantenimientoBO;
    
    public ControlInventarioMantenimiento() {
        this.maquinaBO = new MaquinaBO();
        this.piezaBO = new PiezaBO();
        this.tecnicoBO = new TecnicoBO();
        this.mantenimientoBO = new MantenimientoBO();
    }
    
    /**
     * Método supremo que registra una solicitud de mantenimiento
     * @param idMaquina el id de la máquina 
     * @param idTecnico el id del técnico
     * @param descripcion la descripcion del mantenimiento
     * @param fechaProgramada la fecha programada
     * @param piezas las piezas necesarias
     * @return la solicitud de mantenimiento
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public MantenimientoDTO registrarSolicitudMantenimiento(String idMaquina, String idTecnico, String descripcion, LocalDateTime fechaProgramada, List<MantenimientoPiezaDTO> piezas) throws InventarioMantenimientoException {
        try {
            //Ahora si tiene validaciones profe se nos habían olvidado, recordará que las llamadas a los BOs concuerdan con las del diagrama
            //Salvo por el actualizar maquina, pues en ese punto aún no contemplábamos la snapshot del último mantenimiento en la entidad máquina
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new InventarioMantenimientoException("El id de la máquina es obligatorio");
            }
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new InventarioMantenimientoException("El id del técnico es obligatorio");
            }
            if (fechaProgramada == null) {
                throw new InventarioMantenimientoException("La fecha programada es obligatoria");
            }
  
            MaquinaDTO maquina = maquinaBO.obtenerMaquina(idMaquina);
            
            if (maquina == null) {
                throw new InventarioMantenimientoException("La máquina no existe");
            }
            
            if (mantenimientoBO.tieneMantenimientoActivo(idMaquina)) {
                throw new InventarioMantenimientoException("La máquina ya tiene un mantenimiento activo");
            }
            TecnicoDTO tecnico = tecnicoBO.obtenerTecnico(idTecnico);
            
            if (tecnico == null) {
                throw new InventarioMantenimientoException("No se encontró al técnico");
            }
            String nombreDia = obtenerNombreDia(fechaProgramada);
            LocalTime horaInicio = fechaProgramada.toLocalTime();
            LocalTime horaFin = horaInicio.plusHours(1);

            boolean disponible = tecnicoBO.tieneHorarioDisponible(idTecnico, nombreDia, horaInicio, horaFin);
            
            if (!disponible) {
                throw new InventarioMantenimientoException("El técnico no tiene horario disponible para esa fecha");
            }
            if (piezas != null) {
                for (MantenimientoPiezaDTO piezaUsada : piezas) {
                    if (piezaUsada == null) {
                        throw new InventarioMantenimientoException("La lista de piezas contiene un elemento inválido");
                    }
                    if (piezaUsada.getCantidad() <= 0) {
                        throw new InventarioMantenimientoException("La cantidad de piezas debe ser mayor a cero");
                    }
                    boolean hayStock = piezaBO.hayStockSuficiente(piezaUsada.getIdPieza(), piezaUsada.getCantidad());
                    if (!hayStock) {
                        throw new InventarioMantenimientoException("No hay stock suficiente para la pieza: " + piezaUsada.getIdPieza());
                    }
                }
            }
            String idMantenimiento = UUID.randomUUID().toString();
            for (MantenimientoPiezaDTO piezaUsada : piezas) {
                piezaUsada.setIdMantenimiento(idMantenimiento);
                if (piezaUsada.getIdMantenimientoPiezaDTO() == null || piezaUsada.getIdMantenimientoPiezaDTO().isBlank()) {
                    piezaUsada.setIdMantenimientoPiezaDTO(UUID.randomUUID().toString());
                }
            }
            //El mantenimiento builder
            MantenimientoDTO mantenimiento = new MantenimientoBuilder()
                    .setIdMantenimiento(idMantenimiento)
                    .setMaquina(maquina)
                    .setTecnico(tecnico)
                    .setDescripcion(descripcion)
                    .setFechaProgramada(fechaProgramada)
                    .setEstado(MantenimientoDTO.EstadoMantenimientoDTO.PENDIENTE)
                    .setPiezas(piezas)
                    .build();
            mantenimientoBO.registrarMantenimiento(mantenimiento);
           
            if (piezas != null) {
                for (MantenimientoPiezaDTO piezaUsada : piezas) {
                    PiezaDTO pieza = piezaBO.obtenerPieza(piezaUsada.getIdPieza());
                    if (pieza == null) {
                        throw new InventarioMantenimientoException("No se encontró la pieza: " + piezaUsada.getIdPieza());
                    }
                    int nuevoStock = pieza.getStock() - piezaUsada.getCantidad();
                    piezaBO.actualizarStock(pieza.getIdPieza(), nuevoStock);
                }
            }
            //La snapshot
            UltimoMantenimientoDTO ultimo = new UltimoMantenimientoDTO();
            ultimo.setIdMantenimiento(mantenimiento.getIdMantenimiento());
            ultimo.setFecha(mantenimiento.getFechaProgramada());
            ultimo.setEstado(mantenimiento.getEstado());
            maquina.setUltimoMantenimiento(ultimo);
            maquinaBO.actualizarMaquina(maquina);
            return mantenimiento;
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No se pudo realizar la solicitud de mantenimiento");
        }
    }

    /**
     * Método auxiliar para calcular que día cae cierta fecha
     * @param fecha la fecha a usar
     * @return una cadena de texto con el Día
     */
    private String obtenerNombreDia(LocalDateTime fecha) {
        return switch (fecha.getDayOfWeek()) {
            case MONDAY -> "Lunes";
            case TUESDAY -> "Martes";
            case WEDNESDAY -> "Miércoles";
            case THURSDAY -> "Jueves";
            case FRIDAY -> "Viernes";
            case SATURDAY -> "Sábado";
            case SUNDAY -> "Domingo";
            default -> "";
        };
    }
    
    /**
     * Método que obtiene todas las máquinas
     * @return una lista con todas las máquinas
     * @throws InventarioMantenimientoException  si ocurre un error
     */
    public List<MaquinaDTO> obtenerMaquinas() throws InventarioMantenimientoException {
        try {
            return maquinaBO.obtenerMaquinas();
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible obtener las máquinas", ex);
        }
    }

    /**
     * Método que obtiene todas las piezas
     * @return una lista de piezas
     * @throws InventarioMantenimientoException si truena ou
     */
    public List<PiezaDTO> obtenerPiezas() throws InventarioMantenimientoException {
        try {
            return piezaBO.mostrarPiezas();
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible obtener las piezas", ex);
        }
    }

    /**
     * Método que obtiene todos los técnicos
     * @return una lista de tecnicoDTO
     * @throws InventarioMantenimientoException si ocurre un error 
     */
    public List<TecnicoDTO> obtenerTecnicos() throws InventarioMantenimientoException {
        try {
            return tecnicoBO.obtenerTecnicos();
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible obtener los técnicos", ex);
        }
    }

    /**
     * Si opcurre un error
     * @return una lista de mantenimientos
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public List<MantenimientoDTO> obtenerMantenimientos() throws InventarioMantenimientoException {
        try {
            return mantenimientoBO.obtenerMantenimientos();
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible obtener los mantenimientos", ex);
        }
    }
    
    /**
     * Métotdo que registra una máquina 
     * @param idSucursal la sucursal en la que se registrará
     * @param modelo el modelo de la máquina
     * @param tipo el tipo de la máquina
     * @param estado el estado inicial de la máquina
     * @return la máquina insertada
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public MaquinaDTO registrarMaquina(String idSucursal,String modelo,String tipo,MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        try {
            if (idSucursal == null || idSucursal.isBlank()) {
                throw new InventarioMantenimientoException("La sucursal es obligatoria");
            }
            if (modelo == null || modelo.isBlank()) {
                throw new InventarioMantenimientoException("El modelo de la máquina es obligatorio");
            }
            if (tipo == null || tipo.isBlank()) {
                throw new InventarioMantenimientoException("El tipo de máquina es obligatorio");
            }
            if (estado == null) {
                throw new InventarioMantenimientoException("El estado de la máquina es obligatorio");
            }
            MaquinaDTO maquina = new MaquinaDTO();
            maquina.setIdMaquina(UUID.randomUUID().toString());
            maquina.setIdSucursal(idSucursal);
            maquina.setModelo(modelo);
            maquina.setTipo(tipo);
            maquina.setEstado(estado);
            maquina.setUltimoMantenimiento(null);
            maquinaBO.registrarMaquina(maquina);
            return maquina;
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible registrar la máquina", ex);
        }
    }
    /**
     * Método que actualiza una máquina en la BD
     * @param idMaquina el id de la máquina a actualizar
     * @param idSucursal el id de la sucursal 
     * @param modelo el modelo de la maquina
     * @param tipo el tipo
     * @param estado el estado
     * @throws InventarioMantenimientoException Si ocurrió un error 
     */
    public void actualizarMaquina(String idMaquina, String idSucursal, String modelo, String tipo, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new InventarioMantenimientoException("El id de la máquina es obligatorio");
            }

            if (idSucursal == null || idSucursal.isBlank()) {
                throw new InventarioMantenimientoException("La sucursal es obligatoria");
            }

            if (modelo == null || modelo.isBlank()) {
                throw new InventarioMantenimientoException("El modelo de la máquina es obligatorio");
            }

            if (tipo == null || tipo.isBlank()) {
                throw new InventarioMantenimientoException("El tipo de máquina es obligatorio");
            }

            if (estado == null) {
                throw new InventarioMantenimientoException("El estado de la máquina es obligatorio");
            }

            MaquinaDTO maquinaExistente = maquinaBO.obtenerMaquina(idMaquina);

            if (maquinaExistente == null) {
                throw new InventarioMantenimientoException("La máquina no existe");
            }

            maquinaExistente.setIdSucursal(idSucursal);
            maquinaExistente.setModelo(modelo);
            maquinaExistente.setTipo(tipo);
            maquinaExistente.setEstado(estado);

            maquinaBO.actualizarMaquina(maquinaExistente);

        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible actualizar la máquina", ex);
        }
    }
    
    /**
     * Método que da de baja una máquina en la BD
     * @param idMaquina el id de la máquina 
     * @param motivo el motivo o razón de la baja
     * @throws InventarioMantenimientoException  si ocurre un error
     */
    public void darBajaMaquina(String idMaquina, String motivo) throws InventarioMantenimientoException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new InventarioMantenimientoException("El id de la máquina es obligatorio");
            }

            if (motivo == null || motivo.isBlank()) {
                throw new InventarioMantenimientoException("El motivo de baja es obligatorio");
            }

            MaquinaDTO maquina = maquinaBO.obtenerMaquina(idMaquina);

            if (maquina == null) {
                throw new InventarioMantenimientoException("La máquina no existe");
            }

            if (maquina.getEstado() == MaquinaDTO.EstadoMaquinaDTO.INACTIVO) {
                throw new InventarioMantenimientoException("La máquina ya está dada de baja");
            }

            BajasInventarioDTO baja = new BajasInventarioDTO();

            baja.setIdBaja(UUID.randomUUID().toString());
            baja.setMotivo(motivo);
            baja.setFechaBaja(LocalDateTime.now());
            baja.setTipo("MAQUINA");
            baja.setIdMaquina(idMaquina);
            baja.setIdPieza(null);

            maquinaBO.registrarBaja(idMaquina, baja);

        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible dar de baja la máquina", ex);
        }
    }
    /**
     * Método que valida si un técnico tiene horario disponible
     * @param idTecnico el id del técnico
     * @param fechaProgramada la fecha programada
     * @return verdadero si tiene horario disponible, falso en caso contrario
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public boolean tecnicoTieneHorarioDisponible(String idTecnico, LocalDateTime fechaProgramada) throws InventarioMantenimientoException {
        try {
            if (idTecnico == null || idTecnico.isBlank()) {
                throw new InventarioMantenimientoException("El técnico es obligatorio");
            }

            if (fechaProgramada == null) {
                throw new InventarioMantenimientoException("La fecha programada es obligatoria");
            }

            String nombreDia = obtenerNombreDia(fechaProgramada);
            LocalTime horaInicio = fechaProgramada.toLocalTime();
            LocalTime horaFin = horaInicio.plusHours(1);

            return tecnicoBO.tieneHorarioDisponible(idTecnico, nombreDia, horaInicio, horaFin);

        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible validar el horario del técnico", ex);
        }
    }
    
    /**
     * Método que valida el stock de una pieza
     * @param idPieza la pieza a validar
     * @param cantidad la cantidad a validar
     * @return verdadero si hay stock suficiente, falso en caso contrario
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public boolean hayStockSuficiente(String idPieza, int cantidad) throws InventarioMantenimientoException {
        try {
            if (idPieza == null || idPieza.isBlank()) {
                throw new InventarioMantenimientoException("El id de la pieza es obligatorio");
            }

            if (cantidad <= 0) {
                throw new InventarioMantenimientoException("La cantidad debe ser mayor a cero");
            }
            return piezaBO.hayStockSuficiente(idPieza, cantidad);

        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible validar el stock de la pieza", ex);
        }
    }
    
    /**
     * Método que filtra máquinas por sucursal y estado
     * @param idSucursal el id de la sucursal
     * @param estado el estado de la máquina
     * @return una lista de máquinasDTO
     * @throws InventarioMantenimientoException 
     */
    public List<MaquinaDTO> filtrarMaquinas(String idSucursal, MaquinaDTO.EstadoMaquinaDTO estado) throws InventarioMantenimientoException {
        try {
            return maquinaBO.filtrarMaquinas(idSucursal, estado);
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible filtrar las máquinas", ex);
        }
    }
    
    /**
     * Método que valida que una máquina sea apta para una programación de mantenimiento, así me ahorro métodos del diagrama de secuencia
     * @param idMaquina el id de la máquina validar
     * @return verdadero si la máquina es válida, falso en caso contrario
     * @throws InventarioMantenimientoException si ocurre un error
     */
    public boolean validarMaquinaParaProgramarMantenimiento(String idMaquina) throws InventarioMantenimientoException {
        try {
            if (idMaquina == null || idMaquina.isBlank()) {
                throw new InventarioMantenimientoException("El campo de la máquina no puede ser nulo");
            }
            MaquinaDTO maquina = maquinaBO.obtenerMaquina(idMaquina);
            if (maquina == null) {
                throw new InventarioMantenimientoException("La máquina seleccionada no existe");
            }
            if (maquina.getEstado() == MaquinaDTO.EstadoMaquinaDTO.INACTIVO) {
                throw new InventarioMantenimientoException("No se puede programar mantenimiento a una máquina dada de baja");
            }
            if (mantenimientoBO.tieneMantenimientoActivo(idMaquina)) {
                throw new InventarioMantenimientoException("Esta máquina ya cuenta con un mantenimiento activo");
            }
            return true;
        } catch (NegocioException ex) {
            throw new InventarioMantenimientoException("No fue posible validar la máquina seleccionada", ex);
        }
    }

}
