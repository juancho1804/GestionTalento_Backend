package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ActividadResponseDTO {

    private String nombre;
    private ParticipanteResponseDTO encargado;
    private PlanResponseDTO plan;
    private List<ParticipanteResponseDTO> participantes;


}
