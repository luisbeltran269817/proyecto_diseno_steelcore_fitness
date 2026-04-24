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
import dtos.AmenidadDTO;
import dtos.InicioSesionDTO;
import dtos.MembresiaDTO;
import dtos.PlanDTO;
import dtos.SucursalDTO;
import dtos.UsuarioDTO;

import dtos.VisitaDTO;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author luiscarlosbeltran
 */
public class ControladorAplicacion implements IControladorAplicacion {
    
    private static ControladorAplicacion instancia;

    // Fachadas
    private final IInicioSesion inicioSesionFachada;
    private final IComprarMembresia compraFachada;

    // Pantallas
    private PantallaBienvenida pantallaBienvenida;
    private PantallaInicioSesion pantallaInicioSesion;
    private PantallaPerfilUsuario pantallaPerfil;

    private PantallaSeleccionSucursal pantallaSucursal;
    private PantallaSeleccionPlan pantallaPlan;
    private PantallaResumenCompra pantallaResumen;
    private PantallaConfirmacionExito pantallaExito;

    //Estas variables se utilizarán para guardar estados entre pantallas
    private UsuarioDTO usuarioActual;
    private SucursalDTO sucursalSeleccionada;
    private PlanDTO planSeleccionado;
    private List<AmenidadDTO> extrasSeleccionados;

    private ControladorAplicacion() {
        this.inicioSesionFachada = new FachadaInicioSesion();
        this.compraFachada = new FachadaComprarMembresia();
    }

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
        cerrarPantallas();
        pantallaBienvenida = new PantallaBienvenida(this);
        pantallaBienvenida.setVisible(true);
    }

    @Override
    public void irAInicioSesion() {
        cerrarPantallas();
        pantallaInicioSesion = new PantallaInicioSesion(this);
        pantallaInicioSesion.setVisible(true);
    }
    
    @Override
    public void irAPerfilUsuario() {
        cerrarPantallas();
        if (pantallaPerfil == null) {
            pantallaPerfil = new PantallaPerfilUsuario(this);
        } else {
            pantallaPerfil.cargarDatos(); 
        }
        pantallaPerfil.setVisible(true);
    }
    @Override
    public void irASeleccionSucursal() {
        cerrarPantallas();

        if (pantallaSucursal == null) {
            pantallaSucursal = new PantallaSeleccionSucursal(this);
        }
        pantallaSucursal.setVisible(true);
    }
    
    
    //Cosas de negocio
    
    @Override
    public void iniciarSesion(String correo, String contrasena) throws Exception {
        this.usuarioActual = inicioSesionFachada.iniciarSesion(correo, contrasena);

        if (usuarioActual.getRol() == UsuarioDTO.Rol.ADMIN) {
            // Más al rato
        } else {
            irAPerfilUsuario();
        }
    }
    
    @Override
    public boolean tieneMembresiaActiva() {
        if (usuarioActual == null) return false;
        return compraFachada.tieneMembresiaActiva(usuarioActual.getCorreo());
    }
    
    @Override
    public void iniciarCompraMembresia() {
        cerrarPantallas();
        pantallaSucursal = new PantallaSeleccionSucursal(this);
        pantallaSucursal.setVisible(true);
    }
    
    @Override
    public void cerrarSesion() {
        usuarioActual = null;
        sucursalSeleccionada = null;
        planSeleccionado = null;
        extrasSeleccionados = null;
        irABienvenida();
    }

    private void cerrarPantallas() {
        if (pantallaBienvenida != null) {
            pantallaBienvenida.dispose();
        }
        if (pantallaInicioSesion != null) {
            pantallaInicioSesion.dispose();
        }
        if (pantallaPerfil != null) {
            pantallaPerfil.dispose();
        }

    }

    
    @Override
    public UsuarioDTO getUsuarioActual() {
        return usuarioActual;
    }

    @Override
    public MembresiaDTO obtenerMembresiaActiva() {
        if (usuarioActual == null) {
            return null;
        }
        return compraFachada.obtenerMembresiaActiva(usuarioActual.getCorreo());
    }
    
    @Override
    public List<VisitaDTO> obtenerHistorial() {
        if (usuarioActual == null) {
            return new ArrayList<>();
        }
        return compraFachada.obtenerHistorial(usuarioActual.getCorreo());
    }
    
    @Override
    public void cancelarMembresia() {
        if (usuarioActual == null) {
            return;
        }
        compraFachada.cancelarMembresia(usuarioActual.getCorreo());
    }
    
    @Override
    public List<SucursalDTO> obtenerSucursales() {
        return compraFachada.obtenerSucursales();
    }
    
    @Override
    public void seleccionarSucursal(SucursalDTO sucursal) {
        this.sucursalSeleccionada = sucursal;
    }

    
    
    
    
}