/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Fachada.FachadaInicioSesion;
import Fachada.IInicioSesion;
import PantallasComprarMembresia.PantallaBienvenida;
import PantallasComprarMembresia.PantallaConfirmacionExito;
import PantallasComprarMembresia.PantallaPerfilUsuario;
import PantallasComprarMembresia.PantallaResumenCompra;
import PantallasComprarMembresia.PantallaSeleccionPlan;
import PantallasComprarMembresia.PantallaSeleccionSucursal;
import PantallasComprarMembresia.PantallaVerPerfil;
import PantallasInicioSesion.PantallaInicioSesion;
import dtos.InicioSesionDTO;
import dtos.PlanDTO;
import dtos.ResultadoDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;

import dtos.VisitaDTO;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author luiscarlosbeltran
 */
public class ControladorAplicacion implements IControladorAplicacion {
    
    private static ControladorAplicacion instancia;
    //Las interfaces de los subsistemas
    private final IInicioSesion inicioSesionFachada;
    private  final IComprarMembresia compraMembresiaFachada;
    
    //Las pantallas
    private PantallaBienvenida pantallaBienvenida;
    private PantallaInicioSesion pantallaInicioSesion;
    private PantallaPerfilUsuario pantallaPerfil;
    private PantallaVerPerfil pantallaDetallesPerfil;
    private PantallaSeleccionSucursal pantallaSeleccionSucursal;
    private PantallaResumenCompra pantallaResumenCompra;
    private PantallaSeleccionPlan pantallaSeleccionPlan;
    private PantallaConfirmacionExito pantallaConfirmacionExito;
    

    //El dto de usuario, que utilizaremos para mantener la sesion activa
    UsuarioDTO usuarioActual;
    
    //Aquí inicializamos las fachadas declaradas arriba
    private ControladorAplicacion() {
        this.inicioSesionFachada = new FachadaInicioSesion();
        this.compraMembresiaFachada= new FachadaComprarMembresia();
    }
    
    //Singleton
    public static ControladorAplicacion getInstancia() {
        if (instancia == null) {
            instancia = new ControladorAplicacion();
        }
        return instancia;
    }

    public void iniciar() {
        irABienvenida();
    }

    @Override
    public void irABienvenida() {
        ocultarTodo();
        if (pantallaBienvenida == null) {
            pantallaBienvenida = new PantallaBienvenida(this);
        }
        pantallaBienvenida.setVisible(true);
    }

    @Override
    public void irAInicioSesion() {
        ocultarTodo();
        if (pantallaInicioSesion == null) {
            pantallaInicioSesion = new PantallaInicioSesion(this);
        }
        pantallaInicioSesion.setVisible(true);
    }

    @Override
    public void irAPerfilUsuario() {
        ocultarTodo();
        if (pantallaPerfil == null) {
            //pantallaPerfil = new PantallaPerfilUsuario(this);
        }

        //pantallaPerfil.setVisible(true);
    }
    
    //Estos dos son métodos de pura lógica
    @Override
    public UsuarioDTO obtenerPerfil() {
        return compraMembresiaFachada.obtenerPerfil(usuarioActual.getCorreo());
    }
    
    @Override
    public List<VisitaDTO> obtenerHistorial() {
        return compraMembresiaFachada.obtenerHistorial(usuarioActual.getCorreo());
    }

    /**
     * Método que oculta todo, no pos si
     */
    private void ocultarTodo() {
        if (pantallaBienvenida != null) {
            pantallaBienvenida.setVisible(false);
        }
        if (pantallaInicioSesion != null) {
            pantallaInicioSesion.dispose();
        }
    }

    /**
     * metodo para ver los detalles del perfil del usuario actual
     */
    @Override
    public void verPerfil() {
        ocultarTodo();
        if (pantallaDetallesPerfil == null) {
            pantallaDetallesPerfil = new PantallaVerPerfil(this);
        }

        pantallaDetallesPerfil.setVisible(true);
    }
    
    
    // mejorado
    @Override
    public void iniciarSesion(InicioSesionDTO dto) {
        // El dto ya viene validado desde la pantalla
        // Consultamos el perfil completo del usuario
        usuarioActual = compraMembresiaFachada.obtenerPerfil(dto.getCorreo());
 
        if (usuarioActual == null) {
            irABienvenida();
            return;
        }
            irABienvenida();
            irAPerfilUsuario();
        
    }

    @Override
    public void SeleccionSucursal() {
        ocultarTodo();
        pantallaSeleccionSucursal = new PantallaSeleccionSucursal(this);
        pantallaSeleccionSucursal.setVisible(true);
    }

    @Override
    public void SeleccionPlan(SucursalDTO sucursal) {
         ocultarTodo();
         if (pantallaSeleccionPlan == null) {
             pantallaSeleccionPlan = new PantallaSeleccionPlan(this, sucursal);
         } else {
             pantallaSeleccionPlan = new PantallaSeleccionPlan(this, sucursal); 
         }
         pantallaSeleccionPlan.setVisible(true);
    }

    @Override
    public void ResumenCompra(SucursalDTO sucursal, PlanDTO plan) {
         ocultarTodo();
         pantallaResumenCompra = new PantallaResumenCompra(this, sucursal, plan);
         pantallaResumenCompra.setVisible(true);
    }

    @Override
    public void PantallaExito(ResultadoDTO resultado) {
         ocultarTodo();
         pantallaConfirmacionExito = new PantallaConfirmacionExito(this, resultado);
         pantallaConfirmacionExito.setVisible(true);
    }
    
    
    
    
}