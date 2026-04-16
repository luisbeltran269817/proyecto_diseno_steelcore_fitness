/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import dtos.UsuarioDTO;
import dtos.VisitaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IComprarMembresia {
    
    public UsuarioDTO obtenerPerfil(String correo);

    List<VisitaDTO> obtenerHistorial(String correo);

    public void cancelarMembresia(String correo);

    public void adquirirMembresia(String correo);
    
}
