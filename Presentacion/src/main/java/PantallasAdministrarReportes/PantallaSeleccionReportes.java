/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasAdministrarReportes;

import Controladores.IControladorAplicacion;
import ControladoresReportes.IControlReportes;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtosReportes.TipoReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Noelia E.N
 */
public class PantallaSeleccionReportes extends PantallaBase {

    private final IControlReportes controlReportes;

    public PantallaSeleccionReportes(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        setTitle("Selección de Reportes");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearHeader(), BorderLayout.NORTH);
        root.add(crearCentro(), BorderLayout.CENTER);
        root.add(crearAcciones(), BorderLayout.SOUTH);

        add(root);
    }

    private JPanel crearHeader() {
        JPanel header = crearCard(0, 90);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JLabel titulo = new JLabel("REPORTES");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        header.add(titulo, BorderLayout.CENTER);

        return header;
    }

    private JPanel crearCentro() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setOpaque(false);

        JPanel card = crearCard(520, 330);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(26, 34, 26, 34));

        JPanel lista = new JPanel(new GridLayout(5, 1, 0, 12));
        lista.setOpaque(false);

        lista.add(crearBotonReporte("Reporte general", TipoReporteDTO.GENERAL));
        lista.add(crearBotonReporte("Reporte de ventas de membresías", TipoReporteDTO.VENTAS_MEMBRESIAS));
        lista.add(crearBotonReporte("Reporte de ingresos", TipoReporteDTO.INGRESOS));
        lista.add(crearBotonReporte("Reporte por sucursal", TipoReporteDTO.POR_SUCURSAL));
        lista.add(crearBotonReporte("Reporte de desempeño de entrenadores", TipoReporteDTO.DESEMPENO_ENTRENADORES));

        card.add(lista, BorderLayout.CENTER);

        JPanel centrador = new JPanel(new java.awt.GridBagLayout());
        centrador.setOpaque(false);
        centrador.add(card);

        contenedor.add(centrador, BorderLayout.CENTER);

        return contenedor;
    }

    private Boton crearBotonReporte(String texto, TipoReporteDTO tipoReporte) {
        Boton boton = crearBoton(texto, Boton.Variante.SECUNDARIO);
        boton.setPreferredSize(new Dimension(420, 42));
        boton.addActionListener(e -> controlReportes.notificarTipoSeleccionado(tipoReporte));
        return boton;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnRegresar = crearBoton("Regresar", Boton.Variante.SECUNDARIO);
        btnRegresar.addActionListener(e -> controlReportes.irAPantallaPrincipalReportes());

        acciones.add(btnRegresar, BorderLayout.WEST);

        return acciones;
    }
}
