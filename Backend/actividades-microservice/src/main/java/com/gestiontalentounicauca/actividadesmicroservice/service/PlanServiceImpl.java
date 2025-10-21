package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.PlanMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.exception.PresupuestoNoValido;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
import com.gestiontalentounicauca.actividadesmicroservice.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public Boolean eliminarPlan(Long id) {
        return null;
    }

    @Override
    public PlanResponseDTO getPlan(Long id) {
        return null;
    }

    @Override
    public List<PlanResponseDTO> getPlanes() {
        return List.of();
    }
}
