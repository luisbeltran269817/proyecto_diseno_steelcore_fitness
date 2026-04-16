/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Tungs
 */
public class PantallaPerfilUsuario extends PantallaBase {
    
    private UsuarioDTO usuario;
    private Tabla tablaVisitas;
    private Boton btnMembresia;

    public PantallaPerfilUsuario(IControladorAplicacion controlador) {
        super(controlador);
        setTitle("Perfil");
        inicializarComponentes();
        setVisible(true);
    }
    
    @Override
    protected void inicializarComponentes() {

        usuario = controlador.obtenerPerfil();

        JPanel contenedor = new JPanel(new BorderLayout(20, 20));
        contenedor.setOpaque(false);
        contenedor.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel header = crearCard(0, 120);
        header.setLayout(new GridLayout(2, 2, 10, 5));
        header.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel nombre = new JLabel("Usuario: " + usuario.getNombre());
        nombre.setForeground(Colores.TEXTO_PRINCIPAL);
        nombre.setFont(Colores.FUENTE_SUBTITULO);

        JLabel estado;
        if (usuario.getMembresiaActiva()) {
            estado = new JLabel("Estado: Activa");
        } else {
            estado = new JLabel("Estado: Inactiva");
        }
        estado.setForeground(Colores.TEXTO_SECUNDARIO);

        JLabel membresia;
        if (usuario.getMembresiaActiva()) {
            membresia = new JLabel("Membresía: " + usuario.getNombreMembresia());
        } else {
            membresia = new JLabel("Sin membresía activa");
        }
        membresia.setForeground(Colores.TEXTO_SECUNDARIO);

        header.add(nombre);
        header.add(estado);
        header.add(membresia);

        String[] columnas = {" Nombre del Gimnasio", "Calle", "Colonia", "Ciudad", "Fecha"};
        tablaVisitas = new Tabla("Historial de Visitas", columnas);

        cargarVisitas();

        JPanel acciones = new JPanel();
        acciones.setOpaque(false);
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));

        Boton btnMapa = crearBoton("Ver mapa", Boton.Variante.SECUNDARIO);
        Boton btnRegistrar = crearBoton("Registrar visita", Boton.Variante.PRIMARIO);
        btnMembresia = crearBoton("", Boton.Variante.PRIMARIO);
        actualizarBoton();

        Boton btnRegresar = crearBoton("← Volver", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> controlador.irABienvenida());

        acciones.add(btnMapa);
        acciones.add(Box.createVerticalStrut(15));
        acciones.add(btnRegistrar);
        acciones.add(Box.createVerticalStrut(15));
        acciones.add(btnMembresia);
        acciones.add(Box.createVerticalStrut(15));
        acciones.add(btnRegresar); 

        btnMembresia.addActionListener(e -> manejarMembresia());

        JPanel zonaSuperior = new JPanel(new BorderLayout());
        zonaSuperior.setOpaque(false);
        zonaSuperior.add(header, BorderLayout.CENTER);

        contenedor.add(zonaSuperior, BorderLayout.NORTH);
        contenedor.add(tablaVisitas, BorderLayout.CENTER);
        contenedor.add(acciones, BorderLayout.EAST);

        add(contenedor);
    }
    
    private void cargarVisitas() {
        tablaVisitas.limpiar();

        List<VisitaDTO> visitas = controlador.obtenerHistorial();

        for (VisitaDTO v : visitas) {
            tablaVisitas.agregarFila(new Object[]{
                    v.getGimnasio(),
                    v.getCalle(),
                    v.getColonia(),
                    v.getCiudad(),
                    v.getFechaHora().toString()
            });
        }
    }

    private void actualizarBoton() {
        if (usuario.getMembresiaActiva()) {
            btnMembresia.setText("Cancelar Membresía");
        } else {
            btnMembresia.setText("Adquirir Membresía");
        }
    }

    private void manejarMembresia() {

        if (usuario.getMembresiaActiva()) {
            //controlador.cancelarMembresia();
            usuario.setMembresiaActiva(false);
        } else {
            //controlador.adquirirMembresia();
            usuario.setMembresiaActiva(true);
        }

        actualizarBoton();
    }
}
