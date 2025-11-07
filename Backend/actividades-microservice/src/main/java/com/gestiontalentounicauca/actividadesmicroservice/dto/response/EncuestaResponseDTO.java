package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncuestaResponseDTO {
    private Long idParticipante;
    private Long idActividad;
    private int calificacion;
}
