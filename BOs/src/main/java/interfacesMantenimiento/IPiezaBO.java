/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import Excepciones.NegocioException;
import dtosInventarioMantenimiento.PiezaDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IPiezaBO {
    public PiezaDTO obtenerPieza(String idPieza) throws NegocioException;
    public void actualizarStock(String idPieza, int stock) throws NegocioException;
    public List<PiezaDTO> mostrarPiezas() throws NegocioException;
    public boolean hayStockSuficiente(String idPieza, int cantidad) throws NegocioException;
}
