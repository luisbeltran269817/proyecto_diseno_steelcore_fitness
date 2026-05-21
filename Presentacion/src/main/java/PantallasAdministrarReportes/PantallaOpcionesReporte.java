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
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PantallaOpcionesReporte extends PantallaBase {

    private final IControlReportes controlReportes;
    private final ReporteDTO reporte;

    public PantallaOpcionesReporte(IControlReportes controlReportes,
                                   IControladorAplicacion controladorAplicacion,
                                   ReporteDTO reporte) {
        super(controladorAplicacion);
        this.controlReportes = controlReportes;
        this.reporte = reporte;
        setTitle("Opciones del Reporte");
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

        add(root);
    }

    private JPanel crearHeader() {
        JPanel header = crearCard(0, 80);
        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(16, 28, 16, 28));

        JLabel titulo = new JLabel("REPORTE COMPLETO");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        header.add(titulo, BorderLayout.CENTER);

        return header;
    }

    private JPanel crearCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setOpaque(false);

        PantallaVistaPreviaReporte vista = new PantallaVistaPreviaReporte(reporte);
        centro.add(vista, BorderLayout.CENTER);

        JPanel menu = crearCard(380, 300);
        menu.setLayout(new GridLayout(5, 1, 0, 10));
        menu.setBorder(new EmptyBorder(28, 34, 28, 34));

        Boton btnImprimir = crearBoton("Imprimir", Boton.Variante.PRIMARIO);
        btnImprimir.addActionListener(e -> controlReportes.irAPantallaImprimir());

        Boton btnExportar = crearBoton("Exportar", Boton.Variante.SECUNDARIO);
        btnExportar.addActionListener(e -> controlReportes.irAPantallaExportar());

        Boton btnEnviar = crearBoton("Enviar por correo", Boton.Variante.SECUNDARIO);
        btnEnviar.addActionListener(e -> controlReportes.irAPantallaEnviarPorCorreo());

        Boton btnNuevo = crearBoton("Generar uno nuevo", Boton.Variante.SECUNDARIO);
        btnNuevo.addActionListener(e -> controlReportes.irAPantallaSeleccionReportes());

        Boton btnCancelar = crearBoton("Cancelar", Boton.Variante.SECUNDARIO);
        btnCancelar.addActionListener(e -> controlReportes.irAPantallaReporte());

        menu.add(btnImprimir);
        menu.add(btnExportar);
        menu.add(btnEnviar);
        menu.add(btnNuevo);
        menu.add(btnCancelar);

        centro.add(menu, BorderLayout.EAST);

        return centro;
    }
}
