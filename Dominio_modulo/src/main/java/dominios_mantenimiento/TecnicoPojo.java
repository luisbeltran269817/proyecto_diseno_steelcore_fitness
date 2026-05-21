/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominios_mantenimiento;

import java.util.List;

/**
 *
 * @author Tungs
 */
public class TecnicoPojo {
    
    private String idTecnico;
    private String nombre;

    private List<HorarioTecnicoPojo> horarios;
    
    public TecnicoPojo() {
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

    public List<HorarioTecnicoPojo> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<HorarioTecnicoPojo> horarios) {
        this.horarios = horarios;
    }

}
