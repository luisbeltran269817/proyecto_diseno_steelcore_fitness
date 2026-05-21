/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfacesMantenimiento;

import dominios_mantenimiento.BajaPojo;
import dominios_mantenimiento.MaquinaPojo;
import excepciones.PersistenciaException;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IMaquinaDAO {
    public void registrarMaquina(MaquinaPojo maquina) throws PersistenciaException;

    public MaquinaPojo obtenerMaquina(String idMaquina) throws PersistenciaException;

    public List<MaquinaPojo> obtenerMaquinas() throws PersistenciaException;

    public void actualizarMaquina(MaquinaPojo maquina) throws PersistenciaException;

    public void cambiarEstado(String idMaquina, MaquinaPojo.EstadoMaquina estado) throws PersistenciaException;

    void registrarBaja(String idMaquina, BajaPojo baja) throws PersistenciaException;
    
    public List<MaquinaPojo> filtrarMaquinas(String idSucursal, MaquinaPojo.EstadoMaquina estado) throws PersistenciaException;
}
