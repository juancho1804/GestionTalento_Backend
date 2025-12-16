package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "ParticipanteResponse",
        description = "Información del participante registrado en una actividad"
)
public class ParticipanteResponseDTO {

    @Schema(
            description = "ID único del participante",
            example = "7"
    )
    private Long idParticipante;

    @Schema(
            description = "Información del usuario asociado al participante"
    )
    private UsuarioResponseDTO usuario;

    @Schema(
            description = "ID de la actividad asociada al participante",
            example = "20"
    )
    private Long idActividad;
}
