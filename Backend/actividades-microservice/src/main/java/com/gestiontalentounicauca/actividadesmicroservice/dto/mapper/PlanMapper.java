package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoPlan;

public class PlanMapper {
    public PlanMapper() {}
    public Plan toEntity(PlanRequestDTO planRequestDTO) {
        TipoPlan tipoPlan =null;

        for(TipoPlan tp : TipoPlan.values()) {
            if(planRequestDTO.getNombre().equalsIgnoreCase(tp.toString())) {
                tipoPlan = tp;
                break;
            }
        }

        if(tipoPlan == null) {
            throw new RuntimeException("No se encontro el tipo de plan");
        }

        return Plan.builder().tipoPlan(tipoPlan).fechaInicio(planRequestDTO.getFechaInicio())
                .fechaFin(planRequestDTO.getFechaInicio().plusYears(1)).presupuestoAsignado(planRequestDTO.getPresupuestoAsignado())
                .presupuestoEjecutado(0f).build();
    }

    public PlanResponseDTO toResponse(Plan plan) {
        return PlanResponseDTO.builder().nombre(plan.getTipoPlan().name()).fechaInicio(plan.getFechaInicio())
                .fechaFin(plan.getFechaFin()).presupuestoAsignado(plan.getPresupuestoAsignado()).
                presupuestoEjecutado(plan.getPresupuestoEjecutado()).build();

    }

}
