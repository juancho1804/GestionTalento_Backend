package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.PlanMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.exception.PresupuestoNoValido;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoPlan;
import com.gestiontalentounicauca.actividadesmicroservice.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements IPlanService {

    @Autowired
    private PlanRepository planRepository;

    private PlanMapper planMapper = new PlanMapper();

    @Override
    public PlanResponseDTO crearPlan(PlanRequestDTO planRequestDTO) {
        if (planRequestDTO.getPresupuestoAsignado() < 1) {
            throw new PresupuestoNoValido("El presupuesto debe ser mayor que 0 ");
        }
        Plan plan = planRepository.save(planMapper.toEntity(planRequestDTO));
        return planMapper.toResponse(plan);
    }

    @Override
    public PlanResponseDTO actualizarPlan(Long id, PlanRequestDTO planRequestDTO) {
        Plan planModel = planRepository.findById(id).orElse(null);
        if (planModel == null) {
            throw new RuntimeException("No se encontro el plan");
        }
        if (planRequestDTO.getPresupuestoAsignado() < 1) {
            throw new PresupuestoNoValido("El presupuesto debe ser mayor que 0 ");
        }

        for(TipoPlan tp : TipoPlan.values()) {
            if(planRequestDTO.getNombre().equalsIgnoreCase(tp.toString())) {
                planModel.setTipoPlan(tp);
                break;
            }
        }

        planModel.setFechaInicio(planRequestDTO.getFechaInicio());
        planModel.setFechaFin(planRequestDTO.getFechaInicio().plusYears(1));
        if(planRequestDTO.getPresupuestoAsignado() < 1) {
            throw new RuntimeException("El presupuesto debe ser mayor que 0 ");
        }
        planModel.setPresupuestoAsignado(planRequestDTO.getPresupuestoAsignado());

        return planMapper.toResponse(planRepository.save(planModel));
    }

    @Override
    public Boolean eliminarPlan(Long id) {

        if(id == null || !planRepository.existsById(id)) {
            throw new RuntimeException("No se encontró el plan");
        }
        planRepository.deleteById(id);

        return true;
    }

    @Override
    public PlanResponseDTO getPlan(Long id) {
        Plan planModel = planRepository.findById(id).orElse(null);
        if (planModel == null) {
            throw new RuntimeException("No se encontro el plan");
        }
        return planMapper.toResponse(planModel);
    }

    @Override
    public List<PlanResponseDTO> getPlanes() {
        List<Plan> planModels = planRepository.findAll();

        return planModels.stream()
                .map(planMapper::toResponse)
                .collect(Collectors.toList());
    }
}
