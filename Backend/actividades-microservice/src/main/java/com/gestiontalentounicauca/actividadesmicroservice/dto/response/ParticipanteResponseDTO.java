package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParticipanteResponseDTO {
    private Long idParticipante;
    private UsuarioResponseDTO usuario;
    private Long idActividad;
}
