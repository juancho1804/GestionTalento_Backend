package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;


import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Encuesta;
import org.springframework.stereotype.Component;

@Component
public class EncuestaMapper {

    public EncuestaMapper() {}

    public EncuestaResponseDTO toResponseDTO(Encuesta encuesta) {
        EncuestaResponseDTO encuestaResponseDTO = EncuestaResponseDTO.builder()
                .idActividad(encuesta.getParticipante().getActividad().getId())
                .idParticipante(encuesta.getParticipante().getId())
                .calificacion(encuesta.getCalificacion())
                .build();
        return encuestaResponseDTO;
    }
}
