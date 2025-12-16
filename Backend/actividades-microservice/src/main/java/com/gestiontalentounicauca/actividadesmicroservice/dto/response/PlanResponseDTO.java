package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
@Schema(
        name = "PlanResponse",
        description = "Información del plan retornada por el sistema"
)
public class PlanResponseDTO {

    @Schema(
            description = "Nombre del plan",
            example = "Plan de Capacitación 2025"
    )
    private String nombre;

    @Schema(
            description = "Fecha de inicio del plan",
            example = "2025-01-15",
            type = "string",
            format = "date"
    )
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;

    @Schema(
            description = "Fecha de finalización del plan",
            example = "2025-12-31",
            type = "string",
            format = "date"
    )
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    @Schema(
            description = "Presupuesto asignado al plan",
            example = "15000000"
    )
    private Float presupuestoAsignado;

    @Schema(
            description = "Presupuesto ejecutado hasta el momento",
            example = "7250000"
    )
    private Float presupuestoEjecutado;
}
