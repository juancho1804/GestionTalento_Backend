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
@Builder
public class ActividadResponseDTO {

    private Long id;
    private String nombre;
    private Long idEncargado;
    private Long idOrientador;
    private PlanResponseDTO plan;
    private List<ParticipanteResponseDTO> participantes;


}
