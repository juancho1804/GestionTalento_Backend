package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(
        name = "ParticipanteRequest",
        description = "Datos necesarios para registrar un participante en una actividad"
)
public class ParticipanteRequestDTO {

    @Schema(
            description = "Cédula del usuario que participará en la actividad",
            example = "1088123456"
    )
    private String cedula;

    @Schema(
            description = "ID de la actividad a la que se asigna el participante",
            example = "20"
    )
    private Long idActividad;
}
