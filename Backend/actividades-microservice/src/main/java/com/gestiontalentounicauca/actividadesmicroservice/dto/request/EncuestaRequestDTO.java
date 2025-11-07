package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EncuestaRequestDTO {
    private Long idParticipante;
    private int calificacion;
}
