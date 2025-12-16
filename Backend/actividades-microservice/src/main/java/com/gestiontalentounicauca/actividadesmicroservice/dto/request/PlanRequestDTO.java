package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(
        name = "PlanRequest",
        description = "Datos necesarios para crear o actualizar un plan"
)
public class PlanRequestDTO {

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
            description = "Presupuesto asignado al plan",
            example = "15000000"
    )
    private Float presupuestoAsignado;
}
