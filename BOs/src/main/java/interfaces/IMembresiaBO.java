/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import Excepciones.NegocioException;
import dtos.MembresiaDTO;
import dtosReportes.FiltrosReporteDTO;
import java.util.List;

/**
 *
 * @author Tungs
 */
public interface IMembresiaBO {

    public void guardar(MembresiaDTO membresia) throws NegocioException;

    public void actualizar(MembresiaDTO membresia) throws NegocioException;

    public MembresiaDTO buscarPorId(String idMembresia) throws NegocioException;

    public MembresiaDTO buscarPorCodigoQR(String codigoQR) throws NegocioException;

    /**
     * Consulta membresías aplicando filtros de reportes.
     *
     * @param filtros filtros seleccionados para generar el reporte.
     * @return lista de membresías que cumplen con los filtros.
     * @throws NegocioException si los filtros son inválidos o ocurre un error
     * de persistencia.
     */
    public List<MembresiaDTO> consultarParaReportes(FiltrosReporteDTO filtros) throws NegocioException;
}
