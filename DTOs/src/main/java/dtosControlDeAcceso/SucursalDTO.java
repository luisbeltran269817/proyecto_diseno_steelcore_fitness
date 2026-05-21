/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosControlDeAcceso;


/**
 * DTO de sucursal para el módulo de control de acceso.
 *
 * @author julian izaguirre
 */
public class SucursalDTO {

    private String idSucursal;
    private String nombre;
    private String ciudad;
    private String colonia;
    private String calle;
    private Double latitud;
    private Double longitud;

    public SucursalDTO() {
    }

    public SucursalDTO(String idSucursal, String nombre, String ciudad, String colonia, String calle, Double latitud, Double longitud) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.colonia = colonia;
        this.calle = calle;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(String idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    @Override
    public String toString() {
        return "SucursalDTO{" + "idSucursal=" + idSucursal + ", nombre=" + nombre + ", ciudad=" + ciudad + ", colonia=" + colonia + ", calle=" + calle + ", latitud=" + latitud + ", longitud=" + longitud + '}';
    }
}
