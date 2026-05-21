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
 * Mapper entre ClasePojo (dominio) y ClaseDTO (caso individual de acceso).
 *
 * ClasePojo tiene horaInicio (LocalTime) y el DTO también, así que
 * la conversión es directa.  cupoDisponible = cupoMaximo - cupoActual.
 *
 * @author julian izaguirre
 */
public class ClaseAccesoMapper {
 
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
