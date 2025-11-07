package com.gestiontalentounicauca.actividadesmicroservice.service;



import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EncuestaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IEncuestaService {
    EncuestaResponseDTO crearEncuesta(EncuestaRequestDTO encuestaRequestDTO);
    EncuestaResponseDTO actualizarEncuesta(Long id, EncuestaRequestDTO encuestaRequestDTO);
    Boolean eliminarEncuesta(Long id);
    EncuestaResponseDTO getEncuesta(Long id);
    List<EncuestaResponseDTO> getEncuestas();
}
