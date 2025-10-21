package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;

import java.util.List;

public interface IActividadService {
    ActividadResponseDTO crearActividad(ActividadRequestDTO actividadRequestDTO);
    ActividadResponseDTO actualizarActividad(Long id, ActividadRequestDTO actividadRequestDTO);
    Boolean eliminarActividad(Long id);
    ActividadResponseDTO getActividad(Long id);
    List<ActividadResponseDTO> getActividades();


}
