/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import dtos.PagoDTO;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tungs
 */
public class PagoBO {
    private final Map<String, PagoDTO> pagos = new HashMap<>();

    public void guardar(PagoDTO pago) {
        pagos.put(pago.getIdPago(), pago);
    }
    
    public PagoDTO buscarPorId(String id) {
        return pagos.get(id);
    }
}
