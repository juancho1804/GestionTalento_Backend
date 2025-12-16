package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "ActividadResponse",
        description = "Información de la actividad retornada por el sistema"
)
public class ActividadResponseDTO {

    @Schema(
            description = "ID de la actividad",
            example = "10"
    )
    private Long id;

    @Schema(
            description = "Nombre de la actividad",
            example = "Taller de liderazgo"
    )
    private String nombre;

    @Schema(
            description = "ID del encargado de la actividad",
            example = "5"
    )
    private Long idEncargado;

    @Schema(
            description = "ID del orientador de la actividad",
            example = "8"
    )
    private Long idOrientador;

    @Schema(
            description = "Plan asociado a la actividad"
    )
    private PlanResponseDTO plan;

    @Schema(
            description = "Lista de participantes asociados a la actividad"
    )
    private List<ParticipanteResponseDTO> participantes;
}
