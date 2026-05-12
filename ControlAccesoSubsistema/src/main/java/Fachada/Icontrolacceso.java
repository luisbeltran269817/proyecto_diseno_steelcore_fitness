/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fachada;

import dtos.ClienteDTO;
import dtos.MembresiaDTO;
import dtos.VisitaDTO;

/**
 *
 * @author julian izaguirre
 */
public interface Icontrolacceso {
    
    ResultadoAccesoDTO procesarQR(String codigoQR) throws AccesoDenegadoException;
 
    class AccesoDenegadoException extends Exception {
        private final String motivo;
 
        public AccesoDenegadoException(String motivo) {
            super(motivo);
            this.motivo = motivo;
        }
 
        public String getMotivo() {
            return motivo;
        }
    }
    
    class ResultadoAccesoDTO {
        private final ClienteDTO cliente;
        private final MembresiaDTO membresia;
        private final VisitaDTO visitaRegistrada;
 
        public ResultadoAccesoDTO(ClienteDTO cliente,MembresiaDTO membresia, VisitaDTO visitaRegistrada) {
            this.cliente = cliente;
            this.membresia = membresia;
            this.visitaRegistrada = visitaRegistrada;
        }
 
        public ClienteDTO getCliente() { 
            return cliente; 
        }
        public MembresiaDTO getMembresia() { 
            return membresia; 
        }
        public VisitaDTO getVisitaRegistrada() { 
            return visitaRegistrada; 
        }
    }
}
