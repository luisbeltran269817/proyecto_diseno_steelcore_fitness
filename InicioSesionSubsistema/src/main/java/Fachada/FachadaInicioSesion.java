/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import objetosnegocios.SocioBO;
import dtos.InicioSesionDTO;
import java.time.LocalDate;

/**
 *
 * @author Tungs
 */
public class FachadaInicioSesion implements IInicioSesion {
    private SocioBO socioBO;
 
    public FachadaInicioSesion() {
        //Esto noni
        this.socioBO = new SocioBO(
            "USR-001", "Administrador", "Sistema",
            "admin", "000-000-0000",
            LocalDate.of(1990, 1, 1)
        );
    }
 
    @Override
    public InicioSesionDTO iniciarSesion(String usuario, String password) {
        //Aqui se manda a llamar al control, los Bos solo tienen listas staticas y metodos de acceso a esas listas.
        return socioBO.iniciarSesion(usuario, password);
    }
 
    @Override
    public void cerrarSesion(String token) {
        socioBO.cerrarSesion(token);
    }
 
    @Override
    public boolean validarToken(String token) {
        return socioBO.validarToken(token);
    }

    
}
