/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mappersBO;

import dominioAcceso.ClasePojo;
import dtosControlDeAcceso.ClaseDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Pasa los datos de la clase de dominio a DTO
 *
 * @author julian izaguirre
 */
public class ClaseAccesoMapper {

    /**
     * Convierte un objeto de clase a DTO calculando el cupo disponible
     * 
     * @param pojo Clase de la base de datos
     * @return DTO para la pantalla
     */
    public static ClaseDTO toDTO(ClasePojo pojo) {
        if (pojo == null) return null;

        ClaseDTO dto = new ClaseDTO();
        dto.setIdClase(pojo.getIdClase());
        dto.setNombre(pojo.getNombre());
        dto.setIdSucursal(pojo.getIdSucursal());
        dto.setDiaSemana(pojo.getDiaSemana());
        dto.setHorario(pojo.getHoraInicio());
        dto.setCupoMaximo(pojo.getCupoMaximo());
        dto.setCupoDisponible(pojo.getCupoMaximo() - pojo.getCupoActual());
        return dto;
    }

    /**
     * Convierte una lista completa de clases a DTOs
     * 
     * @param pojos Lista de clases de base de datos
     * @return Lista de DTOs lista para la vista
     */
    public static List<ClaseDTO> toDTOList(List<ClasePojo> pojos) {
        List<ClaseDTO> lista = new ArrayList<>();
        if (pojos != null) {
            for (ClasePojo pojo : pojos) {
                lista.add(toDTO(pojo));
            }
        }
        return lista;
    }
}
