package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ParticipanteRequestDTO {
    private String cedula;
    private Long idActividad;
}
