package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Evidencia;

public class EvidenciaMapper {

    /*
    public Evidencia toEntity(EvidenciaRequestDTO dto) {
        Evidencia evidencia = Evidencia.builder().rutaArchivo(dto.getRutaArchivo())
                .build();
        return evidencia;
    }

     */

    public EvidenciaResponseDTO toResponse(Evidencia evidencia) {
        EvidenciaResponseDTO evidenciaResponseDTO = EvidenciaResponseDTO.builder().idEvidencia(evidencia.getId())
                .idActividad(evidencia.getActividad().getId()).rutaArchivo(evidencia.getRutaArchivo()).build();
        return evidenciaResponseDTO;
    }
}
