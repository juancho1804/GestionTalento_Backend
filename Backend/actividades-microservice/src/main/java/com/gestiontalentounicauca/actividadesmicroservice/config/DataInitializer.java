package com.gestiontalentounicauca.actividadesmicroservice.config;


import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IPlanService;
import com.gestiontalentounicauca.actividadesmicroservice.service.PlanServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IPlanService planService;
    public DataInitializer(PlanServiceImpl planService) {
        this.planService = planService;
    }
    @Override
    public void run(String... args) throws Exception {
        PlanRequestDTO planRequestDTO = new PlanRequestDTO();
        planRequestDTO.setNombre("capacitacion");
        planRequestDTO.setFechaInicio(LocalDate.now());
        planRequestDTO.setPresupuestoAsignado(10F);

        planService.crearPlan(planRequestDTO);

        planRequestDTO.setNombre("incentivos");
        planService.crearPlan(planRequestDTO);

        planRequestDTO.setNombre("bienestar");
        planService.crearPlan(planRequestDTO);

        System.out.println("Planes insertados exitosamente...");
    }
}
