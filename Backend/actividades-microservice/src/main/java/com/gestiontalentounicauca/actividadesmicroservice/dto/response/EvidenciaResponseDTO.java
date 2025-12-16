package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Schema(
        name = "EvidenciaResponse",
        description = "Información de la evidencia almacenada en el sistema"
)
public class EvidenciaResponseDTO {

    @Schema(
            description = "ID único de la evidencia",
            example = "5"
    )
    private Long idEvidencia;

    @Schema(
            description = "Ruta donde se almacena el archivo de evidencia",
            example = "/uploads/evidencias/evidencia_5.pdf"
    )
    private String rutaArchivo;

    @Schema(
            description = "ID de la actividad asociada a la evidencia",
            example = "12"
    )
    private Long idActividad;
}
