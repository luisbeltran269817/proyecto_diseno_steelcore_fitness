/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructuraReportes;

import java.io.File;

/**
 * Interfaz para el envío de reportes por correo electrónico.
 *
 * Define la operación necesaria para enviar un archivo PDF como adjunto a un
 * correo electrónico. Esta interfaz pertenece a la capa de infraestructura,
 * porque el envío de correos depende de servicios externos como SMTP, Gmail,
 * Outlook u otros proveedores.
 *
 * La capa de negocio utiliza esta interfaz para solicitar el envío del reporte
 * sin depender directamente de la implementación concreta del servicio de
 * correo.
 *
 * @author Noelia E.N.
 */
public interface IEnviadorCorreoReporte {

    /**
     * Envía un archivo PDF de reporte a un correo electrónico destino.
     *
     * @param correoDestino correo electrónico al que se enviará el reporte.
     * @param archivoPDF archivo PDF que se adjuntará al correo.
     * @throws Exception si el correo no es válido, el archivo no existe, no hay
     * configuración SMTP o ocurre un error durante el envío.
     */
    void enviar(String correoDestino, File archivoPDF) throws Exception;
}
