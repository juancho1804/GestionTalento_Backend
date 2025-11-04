package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.*;
import com.gestiontalentounicauca.actividadesmicroservice.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ActividadFactory {

    private final Map<TipoPlan, Supplier<Actividad>> actividadMap = Map.of(
            TipoPlan.INCENTIVOS, ActividadIncentivos::new,
            TipoPlan.BIENESTAR, ActividadBienestar::new,
            TipoPlan.CAPACITACION, ActividadCapacitacion::new
    );


    public Actividad crearActividad(ActividadRequestDTO dto, Plan plan, Participante encargado) {
        Supplier<Actividad> constructor = actividadMap.get(plan.getTipoPlan());
        if (constructor == null)
            throw new IllegalArgumentException("Tipo de plan no válido: " + plan.getTipoPlan());

        Actividad actividad = constructor.get();
        actividad.setNombre(dto.getNombre());
        actividad.setEncargado(encargado);
        actividad.setPlan(plan);

        // set campos adicionales solo si aplica
        if (actividad instanceof ActividadBienestar bienestar) {
            bienestar.setCampoAdicionalBienestar(dto.getCampoAdicionalBienestar());
        } else if (actividad instanceof ActividadCapacitacion capacitacion) {
            capacitacion.setCampoAdicionalCapacitacion(dto.getCampoAdicionalCapacitacion());
        }

        return actividad;
    }

    public ActividadResponseDTO toResponse(Actividad actividad, ParticipanteResponseDTO encargado, PlanResponseDTO plan, List<ParticipanteResponseDTO> participantes) {
        if(actividad instanceof ActividadBienestar bienestar){
            return ActividadBienestarResponseDTO.builder()
                    .nombre(actividad.getNombre())
                    .encargado(encargado)
                    .plan(plan)
                    .participantes(participantes)
                    .campoAdicionalBienestar(bienestar.getCampoAdicionalBienestar())
                    .build();
        }else if(actividad instanceof ActividadCapacitacion capacitacion){
            return ActividadCapacitacionResponseDTO.builder()
                    .nombre(actividad.getNombre())
                    .encargado(encargado)
                    .plan(plan)
                    .participantes(participantes)
                    .campoAdicionalCapacitacion(capacitacion.getCampoAdicionalCapacitacion())
                    .build();
        } else if (actividad instanceof ActividadIncentivos incentivos) {
            return ActividadIncentivosResponseDTO.builder()
                    .nombre(actividad.getNombre())
                    .encargado(encargado)
                    .plan(plan)
                    .participantes(participantes)
                    .build();
        }else{
            throw new RuntimeException("Tipo de actividad desconocido");
        }
    }


}
