package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "ActividadRequest",
        description = "Datos necesarios para crear o actualizar una actividad"
)
public class ActividadRequestDTO {

    @Schema(
            description = "ID del plan al que pertenece la actividad",
            example = "1"
    )
    private Long planId;

    @Schema(
            description = "Nombre de la actividad",
            example = "Taller de liderazgo"
    )
    private String nombre;

    @Schema(
            description = "Cédula del encargado de la actividad",
            example = "1234567890"
    )
    private String cedulaEncargado;

    @Schema(
            description = "Cédula del orientador de la actividad",
            example = "0987654321"
    )
    private String cedulaOrientador;
}
