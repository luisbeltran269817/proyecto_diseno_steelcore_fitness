/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import dtos.PeticionPagoGenDTO;
import dtos.RespuestaPagoGenDTO;
import dtosInfraestructura.PeticionPagoDTO;
import dtosInfraestructura.RespuestaPagoDTO;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tungs
 */
public class ControlPagoMembresiaStripe {

    public ControlPagoMembresiaStripe() {
       //Guardamos la clave en un archivo de variables de entorno .env, más tarde pondré el archivo en un gitIgnore
       Dotenv dotenv = Dotenv.configure().directory("C:/NetBeans/proyecto_diseno_steelcore_fitness/PagoMembresiaStripeSubsistema").load();
       Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");
    }
    
    /**
     * Método que se conecta con la API de Stripe para procesar el pago (y mapea dtos)
     * @param solicitudGen la soliticur o petición para el serivdor de la API, contiene datos esenciales para la transaccion
     * @return una respuesta del servidor de la API, ya sea si se concretó el pago o no
     */
    public RespuestaPagoGenDTO ejecutarCobro(PeticionPagoGenDTO solicitudGen) {
        //Aquí mapeamos de el dto del proyecto normal al de infraestructura
        PeticionPagoDTO solicitud = new PeticionPagoDTO();
        solicitud.setMonto(solicitudGen.getMonto());
        solicitud.setDescripcion(solicitudGen.getDescripcion());
        solicitud.setTokenTarjeta(solicitudGen.getTokenTarjeta());
        RespuestaPagoDTO respuestaInfra = new RespuestaPagoDTO();
        try {
            //Por alguna razón el stripe solo acepta montos en la mínia denominación posble
            //Nota 10 minutos después: Stripe y otras pasarelas de pagos manejan la denominación más pequeña
            //para evitar errores de redondeo, como el precio de los planes se calcula en pesos se tiene que convertir a centavos
            int montoCentavos = (int) Math.round(solicitud.getMonto() * 100);
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("amount", montoCentavos);
            parametros.put("currency", "mxn");
            parametros.put("description", solicitud.getDescripcion());
            parametros.put("source", solicitud.getTokenTarjeta()); 
            //Aquí es donde se realiza el cobro (charge == cobro en inglés)
            Charge cargo = Charge.create(parametros);
            respuestaInfra.setExitoso(true);
            respuestaInfra.setIdTransaccion(cargo.getId());
        } catch (StripeException e) {
            respuestaInfra.setExitoso(false);
            if (e.getStripeError() != null) {
                respuestaInfra.setMensajeError(e.getStripeError().getMessage());
            } else {
                respuestaInfra.setMensajeError("Error en el pago");
            }
        } catch (Exception e) {
            respuestaInfra.setExitoso(false);
            respuestaInfra.setMensajeError("Error interno: " + e.getMessage());
        }
        //La respuesta pasa del dto de infraestructura al dto normal
        RespuestaPagoGenDTO respuestaGen = new RespuestaPagoGenDTO();
        respuestaGen.setExitoso(respuestaInfra.isExitoso());
        if (respuestaInfra.isExitoso()) {
            //Si el pago es exitoso se guarda el id de la transaccion
            respuestaGen.setMensaje("Pago exitoso. ID: " + respuestaInfra.getIdTransaccion());
            respuestaGen.setIdTransaccion(respuestaInfra.getIdTransaccion()); 
        } else {
            respuestaGen.setMensaje(respuestaInfra.getMensajeError());
        }
        return respuestaGen;
    }


}
