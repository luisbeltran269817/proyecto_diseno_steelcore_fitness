/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author julian izaguirre
 */
public class ResultadoDTO {
    private boolean exito;
    private String mensaje;
    private String folio;
 
    public ResultadoDTO(boolean exito, String mensaje, String folio) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.folio = folio;
    }
    
    // Metodos factory aqui para mayor legibilidad en el código
    public static ResultadoDTO exitoso(String mensaje, String folio) {
        return new ResultadoDTO(true, mensaje, folio);
    }
 
    public static ResultadoDTO fallido(String mensaje) {
        return new ResultadoDTO(false, mensaje, null);
    }
    
    public boolean isExito(){
        return exito; 
    }
    
    public void setExito(boolean v){ 
        this.exito = v; 
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    } 
}
