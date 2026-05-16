/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PantallasComprarMembresia;

import Controladores.IControladorAplicacion;
import Excepciones.NegocioException;
import Utilerias.Boton;
import Utilerias.Colores;
import Utilerias.PantallaBase;
import Utilerias.Tabla;
import dtos.CitaDTO;
import dtos.MembresiaDTO;
import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Tungs
 */
public class PantallaPerfilUsuario extends PantallaBase {
    private Tabla tablaVisitas;
    private Boton btnMembresia;
    private Boton btnQr;
    private Boton btnCita;

    private JLabel lblNombre;
    private JLabel lblEstado;
    private JLabel lblPlan;
    private JLabel lblVencimiento;

    public PantallaPerfilUsuario(IControladorAplicacion controlador) {
        super(controlador);

        setTitle("Perfil de Usuario");
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        inicializarComponentes();
        cargarDatos();

        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {

        JPanel root = new JPanel(new BorderLayout(24, 24));
        root.setOpaque(false);
        root.setBorder(new EmptyBorder(28, 36, 28, 36));

        root.add(crearPanelHeader(), BorderLayout.NORTH);
        root.add(crearPanelCentro(), BorderLayout.CENTER);
        root.add(crearPanelAcciones(), BorderLayout.EAST);

        add(root);
    }

    private JPanel crearPanelHeader() {

        JPanel card = crearCard(0, 110);
        card.setLayout(new BorderLayout(20, 0));
        card.setBorder(new EmptyBorder(20, 24, 20, 24));

        JPanel avatar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.ACENTO);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };

        avatar.setOpaque(false);
        avatar.setPreferredSize(new Dimension(56, 56));
        avatar.setLayout(new GridBagLayout());
        JLabel lblIniciales = new JLabel("?");
        lblIniciales.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblIniciales.setForeground(Colores.TEXTO_PRINCIPAL);
        avatar.add(lblIniciales);

        lblNombre = new JLabel();
        lblNombre.setFont(Colores.FUENTE_TITULO);
        lblNombre.setForeground(Colores.TEXTO_PRINCIPAL);

        lblEstado = new JLabel();
        lblEstado.setFont(Colores.FUENTE_BOTON_SM);
        lblEstado.setForeground(new Color(94, 220, 153));

        JPanel nombreEstado = new JPanel();
        nombreEstado.setOpaque(false);
        nombreEstado.setLayout(new BoxLayout(nombreEstado, BoxLayout.Y_AXIS));
        nombreEstado.add(lblNombre);
        nombreEstado.add(Box.createVerticalStrut(4));
        nombreEstado.add(lblEstado);

        lblPlan = new JLabel();
        lblVencimiento = new JLabel();
        lblPlan.setFont(Colores.FUENTE_LABEL);
        lblVencimiento.setFont(Colores.FUENTE_LABEL);
        lblPlan.setForeground(Colores.TEXTO_SECUNDARIO);
        lblVencimiento.setForeground(Colores.TEXTO_SECUNDARIO);

        JPanel metricas = new JPanel(new GridLayout(1, 2, 20, 6));
        metricas.setOpaque(false);
        metricas.add(crearMiniCard("Plan", lblPlan));
        metricas.add(crearMiniCard("Vencimiento", lblVencimiento));

        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 0));
        izquierda.setOpaque(false);
        izquierda.add(avatar);
        izquierda.add(nombreEstado);

        card.add(izquierda, BorderLayout.CENTER);
        card.add(metricas, BorderLayout.EAST);

        this.lblIniciales = lblIniciales;

        return card;
    }

    private JPanel crearMiniCard(String etiqueta, JLabel lblValor) {

        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Colores.FONDO_CAMPO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(new EmptyBorder(8, 14, 8, 14));
        p.setPreferredSize(new Dimension(180, 48));

        JLabel lbl = new JLabel(etiqueta.toUpperCase());
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lbl.setForeground(Colores.TEXTO_PLACEHOLDER);

        lblValor.setFont(Colores.FUENTE_CAMPO);
        lblValor.setForeground(Colores.TEXTO_PRINCIPAL);

        p.add(lbl);
        p.add(Box.createVerticalStrut(2));
        p.add(lblValor);

        return p;
    }

    private JPanel crearPanelCentro() {

        String[] columnas = {"Gimnasio", "Calle", "Colonia", "Ciudad", "Fecha"};
        tablaVisitas = new Tabla("Historial de Visitas", columnas);
        JTable table = tablaVisitas.getTabla();
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value,
                    boolean isSelected, boolean hasFocus, int row, int col) {

                Component c = super.getTableCellRendererComponent(
                        t, value, isSelected, hasFocus, row, col);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0
                            ? Colores.FONDO_CAMPO
                            : new Color(58, 40, 130));
                }
                c.setForeground(isSelected ? Color.WHITE : Colores.TEXTO_PRINCIPAL);
                return c;
            }
        });

        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(tablaVisitas, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel crearPanelAcciones() {

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(0, 12, 0, 0));
        panel.setPreferredSize(new Dimension(220, 0));

        btnQr = crearBoton("Mi Código QR", Boton.Variante.SECUNDARIO);
        btnQr.addActionListener(e -> {
            // El socio ve su propio QR para mostrarlo en recepción
            dispose();
            controlador.irAQR();
        });

        btnMembresia = crearBoton("", Boton.Variante.PRIMARIO);
        btnMembresia.addActionListener(e -> manejarMembresia());

        btnCita = crearBoton("", Boton.Variante.SECUNDARIO);
        btnCita.addActionListener(e -> manejarCita());

        Boton btnRefrescar = crearBoton("Actualizar", Boton.Variante.SECUNDARIO);
        btnRefrescar.addActionListener(e -> cargarDatos());

        Boton btnSalir = crearBoton("Cerrar Sesión", Boton.Variante.SECUNDARIO);

        btnSalir.addActionListener(e -> {
            dispose();
            controlador.irAInicioSesion();
        });

        Boton btnRecepcion = crearBoton("Módulo Recepción", Boton.Variante.SECUNDARIO);
        btnRecepcion.addActionListener(e -> {
            // Abre el scanner QR de recepción
            dispose();
            controlador.irAModuloRecepcion();
        });

        panel.add(Box.createVerticalGlue());
        panel.add(btnQr);
        panel.add(Box.createVerticalStrut(14));
        panel.add(btnRecepcion);
        //panel.add(btnSucursal);
        panel.add(Box.createVerticalStrut(14));
        panel.add(btnMembresia);
        panel.add(Box.createVerticalStrut(14));
        panel.add(btnCita);
        panel.add(Box.createVerticalStrut(14));
        panel.add(btnRefrescar);
        panel.add(Box.createVerticalStrut(14));
        panel.add(btnSalir);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    public void cargarDatos() {

         try {

            UsuarioDTO usuario =
                    controlador.getUsuarioActual();

            String nombre = usuario.getNombre();

            lblNombre.setText(nombre);

            String[] partes =
                    nombre.trim().split("\\s+");

            String iniciales =
                    partes.length >= 2
                    ? "" + partes[0].charAt(0)
                    + partes[1].charAt(0)
                    : nombre.substring(
                            0,
                            Math.min(2, nombre.length()));

            lblIniciales.setText(
                    iniciales.toUpperCase());

            boolean activa =
                    controlador.tieneMembresiaActiva();

            btnQr.setVisible(activa);

            if (activa) {
                MembresiaDTO m = controlador.obtenerMembresiaActiva();
                lblEstado.setText(
                        "● Membresía activa");
                lblEstado.setForeground(
                        new Color(94, 220, 153));
                lblPlan.setText(
                        m.getIdPlan());
                lblVencimiento.setText(
                        m.getFechaCaducidad()
                                .toLocalDate()
                                .toString());
            } else {
                lblEstado.setText(
                        "○ Sin membresía");
                lblEstado.setForeground(
                        new Color(220, 94, 94));
                lblPlan.setText("—");
                lblVencimiento.setText("—");
            }
            actualizarBoton();
            actualizarBotonCita();
            cargarVisitas();
        } catch (NegocioException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible cargar el perfil.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarVisitas() {
        try{
            tablaVisitas.limpiar();
            //formato para que se vea bonita la fecha
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            List<VisitaDTO> visitas = controlador.obtenerHistorial();
            for (VisitaDTO v : visitas) {
                tablaVisitas.agregarFila(new Object[]{
                    v.getGimnasio(),
                    v.getCalle(),
                    v.getColonia(),
                    v.getCiudad(),
                    v.getFechaHora().format(formato)
                });
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible cargar el historial.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarBoton() {
        try{
            btnMembresia.setText(controlador.tieneMembresiaActiva() ? "Cancelar Membresía" : "Adquirir Membresía");
        } catch (NegocioException ex) {
            btnMembresia.setText(
                    "Adquirir Membresía");
        }
    }

    private void actualizarBotonCita() {
        try{
            boolean tieneMembresia = controlador.tieneMembresiaActiva();
            btnCita.setVisible(tieneMembresia);
            if (!tieneMembresia) {
                return;
            }
            boolean tieneCita = controlador.tieneCitaBienvenida();
            btnCita.setText(
                    tieneCita
                            ? "Consultar Cita"
                            : "Agendar Cita"
            );
        }catch (NegocioException ex) {
            btnCita.setVisible(false);
        }
    }

    private void manejarMembresia() {
        try{ 
            if (controlador.tieneMembresiaActiva()) {

                int opcion = JOptionPane.showConfirmDialog(
                        this, "¿Desea cancelar su membresaía?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    controlador.cancelarMembresia();
                    cargarDatos();
                }
            } else {
                controlador.irASeleccionSucursal();
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible completar la operación.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manejarCita() {
        try{
            if (controlador.tieneCitaBienvenida()) {
                CitaDTO cita = controlador.obtenerCitaBienvenida();
                String mensaje
                        = """
                          Ya tienes una cita agendada.
                          
                          ID cita: """ + cita.getIdCita() + "\n"
                        + "Entrenador: " + cita.getIdEntrenador() + "\n"
                        + "Sucursal: " + cita.getIdSucursal() + "\n"
                        + "Fecha y hora: " + cita.getFechaHora();

                JOptionPane.showMessageDialog(
                        this,
                        mensaje,
                        "Cita de Bienvenida",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } else {
                int opcion = JOptionPane.showConfirmDialog(
                        this,
                        "No tienes una cita de bienvenida.\n\n¿Deseas agendar una?",
                        "Agendar cita",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (opcion == JOptionPane.YES_OPTION) {
                    controlador.irAAgendarCitaDesdePerfil();
                }
            }
        }catch (NegocioException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "No fue posible consultar la cita.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }    
    }

    private JLabel lblIniciales;
}