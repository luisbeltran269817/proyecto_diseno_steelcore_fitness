/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosnegocios;

import DAOs.EjercicioDAO;
import Excepciones.NegocioException;
import dominios.EjercicioPojo;
import dtos.EjercicioDTO;
import excepciones.PersistenciaException;
import interfaces.IEjercicioBO;
import interfaces.IEjercicioDAO;
import java.util.ArrayList;
import java.util.List;
import mappersBO.EjercicioMapper;

/**
 *
 * @author luiscarlosbeltran
 */
public class EjercicioBO implements IEjercicioBO {
    private final IEjercicioDAO ejercicioDAO;
    
    public EjercicioBO() {
        this.ejercicioDAO = new EjercicioDAO();
    }
    
    @Override
    public List<EjercicioDTO> recuperarEjercicios(String grupoMuscular) throws NegocioException {
        try {
            List<EjercicioPojo> pojos = ejercicioDAO.recuperarEjercicios(grupoMuscular);
            List<EjercicioDTO> dtos = new ArrayList<>();
            for (EjercicioPojo pojo : pojos) {
                dtos.add(EjercicioMapper.toDTO(pojo));
            }
            return dtos;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al recuperar ejercicios");
        }
    }
}
