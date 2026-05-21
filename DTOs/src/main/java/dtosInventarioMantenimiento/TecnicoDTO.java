/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosInventarioMantenimiento;

import java.util.List;

/**
 *
 * @author Tungs
 */
public class TecnicoDTO {
    private String idTecnico;
    private String nombre;
    private List<HorarioTecnicoDTO> horarios;

    public TecnicoDTO(String idTecnico, String nombre, List<HorarioTecnicoDTO> horarios) {
        this.idTecnico = idTecnico;
        this.nombre = nombre;
        this.horarios = horarios;
    }
    
    public TecnicoDTO() {
    }

    public String getIdTecnico() {
        return idTecnico;
    }
    
    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<HorarioTecnicoDTO> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioTecnicoDTO> horarios) {
        this.horarios = horarios;
    }
    
}
