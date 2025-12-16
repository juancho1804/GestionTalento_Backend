package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        name = "EncuestaResponse",
        description = "Información de la encuesta registrada en el sistema"
)
public class EncuestaResponseDTO {

    @Schema(
            description = "ID del participante que respondió la encuesta",
            example = "15"
    )
    private Long idParticipante;

    @Schema(
            description = "ID de la actividad evaluada",
            example = "8"
    )
    private Long idActividad;

    @Schema(
            description = "Calificación registrada en la encuesta",
            example = "4"
    )
    private int calificacion;
}
