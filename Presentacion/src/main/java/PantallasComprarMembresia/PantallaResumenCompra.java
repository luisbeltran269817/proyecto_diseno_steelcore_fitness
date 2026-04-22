/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Utilerias.Boton;
import Utilerias.CampoTexto;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import dtos.CompraDTO;
import dtos.PlanDTO;
import dtos.ResultadoDTO;
import dtos.SucursalDTO;
import fachada.FachadaComprarMembresia;
import fachada.IComprarMembresia;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author julian izaguirre
 */
public class PantallaResumenCompra extends PantallaBase{
    
    private IComprarMembresia subsistema;
    private CompraDTO compraActual;
    
    private SucursalDTO sucursal;
    private PlanDTO plan;
    private JLabel lblTotal;

    public PantallaResumenCompra(IControladorAplicacion controlador, SucursalDTO sucursal, PlanDTO plan) {
        super(controlador);
        this.sucursal = sucursal;
        this.plan = plan;
        this.subsistema = new FachadaComprarMembresia();
        this.compraActual = new CompraDTO();
        this.compraActual.setIdSucursal(sucursal.getIdSucursal());
        this.compraActual.setIdPlan(plan.getIdPlan());
        this.compraActual.setIdSocio(controlador.obtenerPerfil().getCorreo());

        setTitle("Resumen de Compra - SteelCore Fitness");
        inicializarComponentes();
        calcularTotal();
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        JPanel fondo = new JPanel(new GridBagLayout());
        fondo.setBackground(Colores.FONDO_PRINCIPAL);
        setContentPane(fondo);

        JPanel card = crearCard(480, 500);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(40, 40, 40, 40));

        JLabel titulo = new JLabel("Resumen de tu Compra");
        titulo.setFont(Colores.FUENTE_TITULO);
        titulo.setForeground(Colores.TEXTO_PRINCIPAL); // Título claro
        titulo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblInfo = new JLabel("<html><center>Gimnasio: <b>" + sucursal.getNombre() + "</b><br>Plan: <b>" + plan.getNombre() + "</b></center></html>");
        lblInfo.setAlignmentX(CENTER_ALIGNMENT);
        lblInfo.setForeground(Colores.TEXTO_SECUNDARIO); // <--- COLOR AGREGADO

        lblTotal = new JLabel("Calculando total...");
        lblTotal.setFont(Colores.FUENTE_TITULO);
        lblTotal.setForeground(Colores.TEXTO_PRINCIPAL);
        lblTotal.setAlignmentX(CENTER_ALIGNMENT);
        
        // <--- TEXTO EXTRAÍDO A VARIABLE PARA PONERLE COLOR --->
        JLabel lblMetodo = new JLabel("Método de Pago (Simulación Stripe)");
        lblMetodo.setForeground(Colores.TEXTO_SECUNDARIO); 
        lblMetodo.setAlignmentX(CENTER_ALIGNMENT);

        CampoTexto txtTarjeta = new CampoTexto("Número de Tarjeta", "1234 5678 9101 1121");
        txtTarjeta.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

        Boton btnPagar = crearBoton("Confirmar y Pagar", Boton.Variante.PRIMARIO);
        Boton btnCancelar = crearBoton("Cancelar Operación", Boton.Variante.SECUNDARIO);

        btnPagar.addActionListener(e -> onPagar());
        btnCancelar.addActionListener(e -> {
            setVisible(false);
            controlador.irAPerfilUsuario();
        });

        card.add(titulo);
        card.add(Box.createVerticalStrut(20));
        card.add(lblInfo);
        card.add(Box.createVerticalStrut(20));
        card.add(lblTotal);
        card.add(Box.createVerticalStrut(30));
        card.add(lblMetodo); // Agregamos la etiqueta con color
        card.add(txtTarjeta);
        card.add(Box.createVerticalStrut(40));
        card.add(btnPagar);
        card.add(Box.createVerticalStrut(10));
        card.add(btnCancelar);

        fondo.add(card);
    }

    private void calcularTotal() {
        ResultadoDTO resultado = subsistema.generarContrato(compraActual);
        if (resultado.isExito()) {
            lblTotal.setText(resultado.getMensaje());
        } else {
            lblTotal.setText("Error al calcular el precio");
        }
    }

    private void onPagar() {
        ResultadoDTO resultadoFinal = subsistema.confirmarCompra(compraActual);
        
        if (resultadoFinal.isExito()) {
            setVisible(false);
            controlador.PantallaExito(resultadoFinal);
        } else {
            JOptionPane.showMessageDialog(this, resultadoFinal.getMensaje(), "Error de Pago", JOptionPane.ERROR_MESSAGE);
        }
    }
}
