package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ActividadResponseDTO {

    private String nombre;
    private ParticipanteResponseDTO encargado;
    private PlanResponseDTO plan;
    private List<ParticipanteResponseDTO> participantes;


}
