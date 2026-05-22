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
import dtosReportes.ReporteDTO;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class PantallaExportarReporte extends PantallaBase {

    private final IControlReportes controlReportes;
    private final ReporteDTO reporte;

    private PantallaVistaPreviaReporte panelHojaReporte;

    public PantallaExportarReporte(IControlReportes controlReportes,
            IControladorAplicacion controladorAplicacion,
            ReporteDTO reporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.reporte = reporte;
        setTitle("Exportar Reporte");
        inicializarComponentes();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearScrollReporte(), BorderLayout.CENTER);
        root.add(crearAcciones(), BorderLayout.SOUTH);

        add(root);
    }

    private JScrollPane crearScrollReporte() {
        panelHojaReporte = new PantallaVistaPreviaReporte(reporte);

        JScrollPane scroll = new JScrollPane(panelHojaReporte);
        scroll.setBorder(null);
        scroll.setPreferredSize(new Dimension(760, 620));
        scroll.getViewport().setBackground(Colores.FONDO_PRINCIPAL);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);

        return scroll;
    }

    private JPanel crearAcciones() {
        JPanel acciones = new JPanel(new BorderLayout());
        acciones.setOpaque(false);

        Boton btnDescargar = crearBoton("Descargar", Boton.Variante.PRIMARIO);
        btnDescargar.addActionListener(e -> controlReportes.exportar());

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaOpcionesReporte());

        acciones.add(btnDescargar, BorderLayout.WEST);
        acciones.add(btnCancelar, BorderLayout.EAST);

        return acciones;
    }
}
