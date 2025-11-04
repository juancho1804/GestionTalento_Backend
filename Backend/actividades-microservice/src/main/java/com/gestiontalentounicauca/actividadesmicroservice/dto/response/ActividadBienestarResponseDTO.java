package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ActividadBienestarResponseDTO extends ActividadResponseDTO{
    private String campoAdicionalBienestar;
}
