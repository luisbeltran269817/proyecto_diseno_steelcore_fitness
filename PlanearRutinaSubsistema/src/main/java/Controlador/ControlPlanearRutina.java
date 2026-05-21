/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Excepciones.NegocioException;
import dtos.DetalleRutinaDTO;
import dtos.EjercicioDTO;
import dtos.EntrenadorDTO;
import dtos.RutinaDTO;
import interfaces.IClienteBO;
import interfaces.IEjercicioBO;
import interfaces.IEntrenadorBO;
import java.util.List;
import objetosnegocios.ClienteBO;
import objetosnegocios.EjercicioBO;
import objetosnegocios.EntrenadorBO;

/**
 *
 * @author luiscarlosbeltran
 */
public class ControlPlanearRutina {
    //desde el clienteBO se añadiran rutina y detallerutina ya que son embebidas
    private final IClienteBO clienteBO;
    private final IEjercicioBO ejercicioBO;
    private final IEntrenadorBO entrenadorBO;
    
    //descomentariar al agregar
    //private final IEjercicioBO ejercicioBO

    public ControlPlanearRutina() {
        this.clienteBO = new ClienteBO();
        this.ejercicioBO = new EjercicioBO();
        this.entrenadorBO = new EntrenadorBO();
    }
    
    public List<RutinaDTO> obtenerRutinas(String correo) throws NegocioException{
        return clienteBO.obtenerRutinas(correo);
    }
    
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular) throws NegocioException{
        return ejercicioBO.recuperarEjercicios(grupoMuscular);
    }
    
    public RutinaDTO guardarRutina(String correo, RutinaDTO rutina) throws NegocioException {
        //si se va a guardar una rutina con el mismo nombre nomas la actualiza
        //asi al entrar a ver los detalles si no se mueve el nombre se actualiza, si se cambia el nombre crea una nueva
        //misma logica aplica para crear deesde cero la rutina. nombre= : actualizar. nombre!= : insertar nueva
        if (clienteBO.existeRutinaConNombre(correo, rutina.getNombre())) {
            return clienteBO.actualizarRutina(correo, rutina);
        }
        if (clienteBO.obtenerRutinas(correo).size() >= 5) {
            throw new NegocioException("No puede tener mas de 5 rutinas a la vez");
        }
        if (!validarDescanso(rutina)) {
            throw new NegocioException("Por motivos de seguridad, debe incluir al menos 1 dia de descanso en su rutina");
        }
        if (!validarGrupos(rutina)) {
            throw new NegocioException("Por motivos de seguridad, no se puede tener el mismo grupo muscular 2 dias seguidos");
        }
        
        return clienteBO.guardarRutina(correo, rutina);
    }
    
    
    /**
     * metodo que recorre el rutinadto y valida que haya al menos un dia de descanso
     * @param rutina
     * @return 
     */
    private boolean validarDescanso(RutinaDTO rutina) {
        if (rutina.getDetalles() == null) return false;
        for (DetalleRutinaDTO detalle : rutina.getDetalles()) {
            if ("Descanso".equals(detalle.getGrupoMuscular())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * metodo que recorre el rutinadto y busca que no este el mismo grupo muscular 2 dias seguidos
     * PEROOOO ignora descansos
     * @param rutina
     * @return 
     */
    private boolean validarGrupos(RutinaDTO rutina) {
        if (rutina.getDetalles() == null) return true;
        List<DetalleRutinaDTO> detalles = rutina.getDetalles();
        for (int i = 0; i < detalles.size() - 1; i++) {
            String grupoActual = detalles.get(i).getGrupoMuscular();
            String grupoSiguiente = detalles.get(i + 1).getGrupoMuscular();
            if (grupoActual != null && !grupoActual.isBlank() && !"Descanso".equals(grupoActual) && grupoActual.equals(grupoSiguiente)) {
                return false;
            }
        }
        //y como al final no use CircularDoubleLinkedList pues una comparacion extra de lunes y domingo
        String grupoDomingo = detalles.get(detalles.size() - 1).getGrupoMuscular();
        String grupoLunes = detalles.get(0).getGrupoMuscular();
        if (grupoDomingo != null && !grupoDomingo.isBlank() && !"Descanso".equals(grupoDomingo) && grupoDomingo.equals(grupoLunes)) {
            return false;
        }
    return true;
    }

    public boolean borrarRutina(String correo, String nombre) throws NegocioException {
        return clienteBO.borrarRutina(correo, nombre);
    }
    
    public String obtenerIdSucursalMembresiaActiva(String correo) throws NegocioException{
        return clienteBO.obtenerIdSucursalMembresiaActiva(correo);
    }
    
    //aprovecho para meter este metodo aqui
    //ya que en el coordinador general de la aplicacion no se usa
    //y lo ocupare para mostrar un nombre de entrenador en vez de su id
    //en la pantallavistarutina al mostrar al entrenador asignaod
    //para que no salga por ejemplo "Entrenador: E001"
    public EntrenadorDTO obtenerEntrenadorPorId(String id) throws NegocioException{
        return entrenadorBO.buscarPorId(id);
    }
    
    public RutinaDTO obtenerPlantilla(String nombre) throws NegocioException{
        return clienteBO.obtenerPlantilla(nombre);
    }
}
