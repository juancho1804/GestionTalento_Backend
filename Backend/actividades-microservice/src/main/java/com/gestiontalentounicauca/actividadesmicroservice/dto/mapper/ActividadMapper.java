package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;

public class ActividadMapper {
    public ActividadMapper() {}

    public Actividad toEntity(ActividadRequestDTO actividadRequestDTO) {
        //Actividad actividad = Actividad.builder().nombre(actividadRequestDTO.getNombre()).build();
        //return actividad;
        return null;
    }

    public ActividadResponseDTO toResponse(Actividad actividad) {
        ActividadResponseDTO actividadResponseDTO = ActividadResponseDTO.builder()
                .nombre(actividad.getNombre()).build();
        return actividadResponseDTO;
    }
}
