/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;


/**
 * DTO del socio que accede al gimnasio.
 * Se construye a partir del QR escaneado en recepción.
 *
 * @author julian izaguirre
 */
public class SocioDTO {

    private String idSocio;
    private String nombreCompleto;
    private String codigoQR;
    private String idMembresia;

    public SocioDTO() {
    }

    public SocioDTO(String idSocio, String nombreCompleto, String codigoQR, String idMembresia) {
        this.idSocio = idSocio;
        this.nombreCompleto = nombreCompleto;
        this.codigoQR = codigoQR;
        this.idMembresia = idMembresia;
    }

    public String getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(String idSocio) {
        this.idSocio = idSocio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }

    public String getIdMembresia() {
        return idMembresia;
    }

    public void setIdMembresia(String idMembresia) {
        this.idMembresia = idMembresia;
    }

    @Override
    public String toString() {
        return "SocioDTO{" + "idSocio=" + idSocio + ", nombreCompleto=" + nombreCompleto + ", codigoQR=" + codigoQR + ", idMembresia=" + idMembresia + '}';
    }
}
