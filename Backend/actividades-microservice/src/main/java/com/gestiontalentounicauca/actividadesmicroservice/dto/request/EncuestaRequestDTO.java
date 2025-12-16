package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

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
        name = "EncuestaRequest",
        description = "Datos necesarios para registrar una encuesta realizada por un participante"
)
public class EncuestaRequestDTO {

    @Schema(
            description = "ID del participante que responde la encuesta",
            example = "15"
    )
    private Long idParticipante;

    @Schema(
            description = "Calificación otorgada por el participante (por ejemplo de 1 a 5)",
            example = "4",
            minimum = "1",
            maximum = "5"
    )
    private int calificacion;
}
