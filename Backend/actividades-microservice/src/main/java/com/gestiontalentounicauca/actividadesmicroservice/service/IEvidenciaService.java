package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IEvidenciaService {
    EvidenciaResponseDTO crearEvidencia(EvidenciaRequestDTO evidenciaRequestDTO, MultipartFile file) throws IOException;
    EvidenciaResponseDTO actualizarEvidencia(Long id, EvidenciaRequestDTO evidenciaRequestDTO);
    Boolean eliminarEvidencia(Long id);
    EvidenciaResponseDTO getEvidencia(Long id);
    List<EvidenciaResponseDTO> getEvidencias();
}
