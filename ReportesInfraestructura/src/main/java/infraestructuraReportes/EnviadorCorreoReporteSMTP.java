/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package infraestructuraReportes;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * Servicio de infraestructura encargado de enviar reportes PDF por correo
 * electrónico mediante SMTP.
 *
 * Esta implementación utiliza Jakarta Mail con configuración SMTP. Por defecto
 * está preparada para Gmail, usando el host smtp.gmail.com y el puerto 587 con
 * STARTTLS.
 *
 * Los datos sensibles del remitente no se escriben directamente en el código.
 * Deben configurarse como variables de entorno:
 *
 * REPORTES_EMAIL: correo remitente.
 * REPORTES_PASSWORD: contraseña de aplicación del correo remitente.
 *
 * Esta clase pertenece a la capa de infraestructura porque se comunica con un
 * servicio externo de correo electrónico.
 *
 * @author Noelia E.N.
 */
public class EnviadorCorreoReporteSMTP implements IEnviadorCorreoReporte {

    private static final String HOST_SMTP = "smtp.gmail.com";
    private static final String PUERTO_SMTP = "587";

    private final String correoRemitente;
    private final String passwordRemitente;

    /**
     * Constructor del enviador de correo.
     *
     * Obtiene el correo remitente y la contraseña desde variables de entorno
     * para evitar guardar credenciales directamente en el código fuente.
     */
    public EnviadorCorreoReporteSMTP() {
        this.correoRemitente = System.getenv("REPORTES_EMAIL");
        this.passwordRemitente = System.getenv("REPORTES_PASSWORD");
    }

    /**
     * Envía un reporte PDF por correo electrónico.
     *
     * El método valida la configuración, el correo destino y el archivo PDF.
     * Después crea una sesión SMTP, construye el mensaje, adjunta el archivo y
     * lo envía mediante Transport.send().
     *
     * @param correoDestino correo electrónico del destinatario.
     * @param archivoPDF archivo PDF que se enviará como adjunto.
     * @throws Exception si falta configuración, el archivo no existe o falla el
     * envío del correo.
     */
    @Override
    public void enviar(String correoDestino, File archivoPDF) throws Exception {
        validarDatos(correoDestino, archivoPDF);

        Properties propiedades = crearPropiedadesSMTP();

        Session sesion = Session.getInstance(propiedades, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoRemitente, passwordRemitente);
            }
        });

        MimeMessage mensaje = crearMensaje(sesion, correoDestino, archivoPDF);

        Transport.send(mensaje);
    }

    /**
     * Crea las propiedades necesarias para conectarse al servidor SMTP.
     *
     * @return propiedades SMTP configuradas.
     */
    private Properties crearPropiedadesSMTP() {
        Properties propiedades = new Properties();

        propiedades.put("mail.smtp.host", HOST_SMTP);
        propiedades.put("mail.smtp.port", PUERTO_SMTP);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.ssl.trust", HOST_SMTP);

        return propiedades;
    }

    /**
     * Construye el mensaje de correo con asunto, cuerpo y archivo adjunto.
     *
     * @param sesion sesión SMTP activa.
     * @param correoDestino correo electrónico del destinatario.
     * @param archivoPDF archivo PDF que se adjuntará.
     * @return mensaje MIME listo para enviarse.
     * @throws Exception si ocurre un error al construir el mensaje.
     */
    private MimeMessage crearMensaje(Session sesion, String correoDestino, File archivoPDF) throws Exception {
        MimeMessage mensaje = new MimeMessage(sesion);

        mensaje.setFrom(new InternetAddress(correoRemitente));
        mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
        mensaje.setSubject("Reporte financiero - SteelCore Fitness", "UTF-8");

        MimeBodyPart cuerpo = new MimeBodyPart();
        cuerpo.setText(
                "Hola,\n\n"
                + "Adjunto se encuentra el reporte financiero generado desde SteelCore Fitness.\n\n"
                + "Saludos.",
                "UTF-8"
        );

        MimeBodyPart adjunto = new MimeBodyPart();
        adjunto.attachFile(archivoPDF);
        adjunto.setFileName(archivoPDF.getName());

        Multipart contenido = new MimeMultipart();
        contenido.addBodyPart(cuerpo);
        contenido.addBodyPart(adjunto);

        mensaje.setContent(contenido);

        return mensaje;
    }

    /**
     * Valida la configuración del correo, el destinatario y el archivo PDF.
     *
     * @param correoDestino correo electrónico del destinatario.
     * @param archivoPDF archivo PDF a enviar.
     */
    private void validarDatos(String correoDestino, File archivoPDF) {
        if (correoRemitente == null || correoRemitente.isBlank()) {
            throw new IllegalStateException("No está configurada la variable de entorno REPORTES_EMAIL.");
        }

        if (passwordRemitente == null || passwordRemitente.isBlank()) {
            throw new IllegalStateException("No está configurada la variable de entorno REPORTES_PASSWORD.");
        }

        if (correoDestino == null || correoDestino.isBlank()) {
            throw new IllegalArgumentException("Debe ingresar un correo destino.");
        }

        if (!correoDestino.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("El correo destino no tiene un formato válido.");
        }

        if (archivoPDF == null) {
            throw new IllegalArgumentException("No se recibió el archivo PDF del reporte.");
        }

        if (!archivoPDF.exists()) {
            throw new IllegalArgumentException("El archivo PDF del reporte no existe.");
        }

        if (!archivoPDF.isFile()) {
            throw new IllegalArgumentException("La ruta del PDF no corresponde a un archivo válido.");
        }
    }
}
