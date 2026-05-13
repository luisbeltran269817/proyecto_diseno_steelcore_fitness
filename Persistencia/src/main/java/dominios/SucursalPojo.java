/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios;

import java.util.List;

/**
 *
 * @author Tungs
 */
public class SucursalPojo {
    private String idSucursal;
    
    private String nombre;

    private String calle;

    private String colonia;

    private String ciudad;

    private String codigoPostal;

    private Double latitud;

    private Double longitud;

    private List<PlanPojo> planes;
    
    private List<AmenidadPojo> amenidadesSucursal;

    public SucursalPojo(String idSucursal, String nombre, String calle, String colonia, String ciudad, String codigoPostal, Double latitud, Double longitud, List<PlanPojo> planes, List<AmenidadPojo> amenidadesSucursal) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.calle = calle;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.latitud = latitud;
        this.longitud = longitud;
        this.planes = planes;
        this.amenidadesSucursal = amenidadesSucursal;
    }

    public SucursalPojo() {
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

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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

    public List<PlanPojo> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanPojo> planes) {
        this.planes = planes;
    }

    public List<AmenidadPojo> getAmenidadesSucursal() {
        return amenidadesSucursal;
    }

    public void setAmenidadesSucursal(List<AmenidadPojo> amenidadesSucursal) {
        this.amenidadesSucursal = amenidadesSucursal;
    }
    
    
}
