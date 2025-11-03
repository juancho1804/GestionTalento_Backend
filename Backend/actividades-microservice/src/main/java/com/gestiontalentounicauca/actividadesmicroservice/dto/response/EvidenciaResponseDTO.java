package com.gestiontalentounicauca.actividadesmicroservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EvidenciaResponseDTO {
    private Long idEvidencia;
    private String rutaArchivo;
    private Long idActividad;
}
