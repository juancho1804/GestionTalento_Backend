package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.*;
import org.springframework.stereotype.Component;

@Component
public class ActividadFactory {

    public Actividad crearActividad(ActividadRequestDTO dto, Plan plan, Participante encargado) {
        Actividad actividad;

        Long planId = dto.getPlanId();

        if (planId == 1) {
            actividad = new ActividadIncentivos();

        } else if (planId == 2) {
            ActividadBienestar bienestar = new ActividadBienestar();
            bienestar.setCampoAdicionalBienestar(dto.getCampoAdicionalBienestar());
            actividad = bienestar;

        } else if (planId == 3) {
            ActividadCapacitacion capacitacion = new ActividadCapacitacion();
            capacitacion.setCampoAdicionalCapacitacion(dto.getCampoAdicionalCapacitacion());
            actividad = capacitacion;

        } else {
            throw new RuntimeException("Tipo de plan no válido para crear actividad");
        }

        actividad.setNombre(dto.getNombre());
        actividad.setEncargado(encargado);
        actividad.setPlan(plan);

        return actividad;
    }


}
