package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;

public class PlanMapper {
    public PlanMapper() {}
    public Plan toEntity(PlanRequestDTO planRequestDTO) {
        System.out.println(planRequestDTO.toString());

        return Plan.builder().nombre(planRequestDTO.getNombre()).fechaInicio(planRequestDTO.getFechaInicio())
                .fechaFin(planRequestDTO.getFechaInicio().plusYears(1)).presupuestoAsignado(planRequestDTO.getPresupuestoAsignado())
                .presupuestoPrevisto(0f).presupuestoEjecutado(0f).build();
    }

    public PlanResponseDTO toResponse(Plan plan) {
        return PlanResponseDTO.builder().nombre(plan.getNombre()).fechaInicio(plan.getFechaInicio())
                .fechaFin(plan.getFechaFin()).presupuestoAsignado(plan.getPresupuestoAsignado()).
                presupuestoEjecutado(plan.getPresupuestoEjecutado()).presupuestoPrevisto(plan.getPresupuestoPrevisto()).build();

    }

}
