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
public class ActividadMapper {


    public ActividadResponseDTO toResponse(Actividad actividad, ParticipanteResponseDTO encargado, PlanResponseDTO plan, List<ParticipanteResponseDTO> participantes) {

        ActividadResponseDTO actividadResponseDTO = ActividadResponseDTO.builder().id(actividad.getId())
                .nombre(actividad.getNombre()).encargado(encargado).idOrientador(actividad.getIdOrientador())
                .plan(plan).participantes(participantes).build();
        return actividadResponseDTO;
    }



}
