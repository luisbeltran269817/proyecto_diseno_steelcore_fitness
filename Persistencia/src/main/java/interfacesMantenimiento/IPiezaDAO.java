/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfacesMantenimiento;

import dominios_mantenimiento.PiezaPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IPiezaDAO {
    
    public PiezaPojo obtenerPieza(String idPieza) throws PersistenciaException;

    public void actualizarStock(String idPieza, int stock) throws PersistenciaException;

    public List<PiezaPojo> mostrarPiezas() throws PersistenciaException;
    
    boolean hayStockSuficiente(String idPieza, int cantidad) throws PersistenciaException;
}
