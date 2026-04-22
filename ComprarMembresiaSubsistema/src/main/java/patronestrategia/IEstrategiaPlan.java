/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package patronestrategia;

import dtos.CompraDTO;
import dtos.ResultadoDTO;

/**
 *
 * @author julian izaguirre
 */
public interface IEstrategiaPlan {
    public ResultadoDTO validarCompra(CompraDTO dto);

    public double calcularMonto();
 
    public ResultadoDTO procesarCompra(CompraDTO dto);
}
