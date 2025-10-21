package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;

import java.util.List;

public interface IPlanService {
    PlanResponseDTO crearPlan(PlanRequestDTO planRequestDTO);
    PlanResponseDTO actualizarPlan(Long id, PlanRequestDTO planRequestDTO);
    Boolean eliminarPlan(Long id);
    PlanResponseDTO getPlan(Long id);
    List<PlanResponseDTO> getPlanes();
}
