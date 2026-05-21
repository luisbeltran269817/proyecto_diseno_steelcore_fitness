/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dominios.RutinaPojo;
import excepciones.PersistenciaException;

/**
 *
 * @author luiscarlosbeltran
 */
public interface IPlantillaRutinaDAO {
    public RutinaPojo obtenerPlantilla(String nombre) throws PersistenciaException;
}
