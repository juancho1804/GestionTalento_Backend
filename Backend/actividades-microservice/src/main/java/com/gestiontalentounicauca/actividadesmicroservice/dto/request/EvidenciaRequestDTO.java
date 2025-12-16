package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(
        name = "EvidenciaRequest",
        description = "Datos necesarios para asociar una evidencia a una actividad"
)
public class EvidenciaRequestDTO {

    @Schema(
            description = "ID de la actividad a la que pertenece la evidencia",
            example = "12"
    )
    private Long idActividad;
}
