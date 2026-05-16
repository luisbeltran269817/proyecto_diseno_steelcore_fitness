/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.MembresiaDTO;

/**
 *
 * @author Tungs
 */
public interface IMembresiaBO {
    public void guardar(MembresiaDTO membresia) throws NegocioException;
    public void actualizar(MembresiaDTO membresia) throws NegocioException;
    public MembresiaDTO buscarPorId(String idMembresia) throws NegocioException;
    public MembresiaDTO buscarPorCodigoQR(String codigoQR) throws NegocioException;
}
