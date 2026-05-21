/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;

import java.time.LocalDateTime;

/**
 * DTO que devuelve el sistema tras procesar el QR del socio.
 *
 * Es el objeto central del caso de uso: contiene todo lo que la
 * PantallaExpediente necesita para mostrar el estado del acceso.
 *
 * accesoConcedido = true  → membresía ACTIVA, visita registrada
 * accesoConcedido = false → membresía vencida / bloqueada / inactiva
 *
 * @author julian izaguirre
 */
public class ResultadoAccesoDTO {

    private boolean accesoConcedido;
    private String nombreSocio;
    private EstadoMembresia estadoMembresia;
    private String mensaje;
    private LocalDateTime fechaRegistroEntrada;
    private String idVisita;
    private String idCliente;
    private String idPlan;
    private String idSucursal;
    private boolean planIncluyeEntrenador;
    private boolean planIncluyeClases;

    public ResultadoAccesoDTO() {
    }

    public ResultadoAccesoDTO(boolean accesoConcedido, String nombreSocio, EstadoMembresia estadoMembresia, String mensaje, LocalDateTime fechaRegistroEntrada, String idVisita, String idCliente, String idPlan, boolean planIncluyeEntrenador, boolean planIncluyeClases) {
        this.accesoConcedido = accesoConcedido;
        this.nombreSocio = nombreSocio;
        this.estadoMembresia = estadoMembresia;
        this.mensaje = mensaje;
        this.fechaRegistroEntrada = fechaRegistroEntrada;
        this.idVisita = idVisita;
        this.idCliente = idCliente;
        this.idPlan = idPlan;
        this.planIncluyeEntrenador = planIncluyeEntrenador;
        this.planIncluyeClases = planIncluyeClases;
    }
    
    public static ResultadoAccesoDTO concedido(String nombreSocio, LocalDateTime entrada,
            String idVisita, String idCliente, String idPlan, String idSucursal,
            boolean incluyeEntrenador, boolean incluyeClases) {
        ResultadoAccesoDTO dto = new ResultadoAccesoDTO(
                true, nombreSocio, EstadoMembresia.ACTIVA,
                "Acceso registrado a las " + entrada.toLocalTime(),
                entrada, idVisita,
                idCliente, idPlan, incluyeEntrenador, incluyeClases);
        dto.idSucursal = idSucursal;
        return dto;
    }

    public static ResultadoAccesoDTO concedido(String nombreSocio,LocalDateTime entrada,String idVisita,String idCliente,String idPlan,boolean incluyeEntrenador,boolean incluyeClases) {
        return concedido(nombreSocio, entrada, idVisita, idCliente, idPlan, null, incluyeEntrenador, incluyeClases);
    }
 
    public static ResultadoAccesoDTO concedido(String nombreSocio,LocalDateTime entrada,String idVisita) {
        return concedido(nombreSocio, entrada, idVisita, null, null, null, false, false);
    }

    public static ResultadoAccesoDTO denegado(String nombreSocio,EstadoMembresia estado,String motivo) {
        return new ResultadoAccesoDTO(
                false, nombreSocio, estado, motivo,
                null, null, null, null, false, false);
    }

    public boolean isAccesoConcedido() {
        return accesoConcedido;
    }

    public void setAccesoConcedido(boolean accesoConcedido) {
        this.accesoConcedido = accesoConcedido;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public EstadoMembresia getEstadoMembresia() {
        return estadoMembresia;
    }

    public void setEstadoMembresia(EstadoMembresia estadoMembresia) {
        this.estadoMembresia = estadoMembresia;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFechaRegistroEntrada() {
        return fechaRegistroEntrada;
    }

    public void setFechaRegistroEntrada(LocalDateTime fechaRegistroEntrada) {
        this.fechaRegistroEntrada = fechaRegistroEntrada;
    }

    public String getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(String idVisita) {
        this.idVisita = idVisita;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(String idPlan) {
        this.idPlan = idPlan;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public boolean isPlanIncluyeEntrenador() {
        return planIncluyeEntrenador;
    }

    public void setPlanIncluyeEntrenador(boolean planIncluyeEntrenador) {
        this.planIncluyeEntrenador = planIncluyeEntrenador;
    }

    public boolean isPlanIncluyeClases() {
        return planIncluyeClases;
    }

    public void setPlanIncluyeClases(boolean planIncluyeClases) {
        this.planIncluyeClases = planIncluyeClases;
    }

    @Override
    public String toString() {
        return "ResultadoAccesoDTO{" + "accesoConcedido=" + accesoConcedido + ", nombreSocio=" + nombreSocio + ", estadoMembresia=" + estadoMembresia + ", mensaje=" + mensaje + ", fechaRegistroEntrada=" + fechaRegistroEntrada + ", idVisita=" + idVisita + ", idCliente=" + idCliente + ", idPlan=" + idPlan + ", planIncluyeEntrenador=" + planIncluyeEntrenador + ", planIncluyeClases=" + planIncluyeClases + '}';
    }
}
