package com.gestiontalentounicauca.actividadesmicroservice.config;


import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoMotivoPago;
import com.gestiontalentounicauca.actividadesmicroservice.repository.TipoMotivoPagoRepository;
import com.gestiontalentounicauca.actividadesmicroservice.service.IPlanService;
import com.gestiontalentounicauca.actividadesmicroservice.service.PlanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("!test")
public class DataInitializer implements CommandLineRunner {

    private final IPlanService planService;
    @Autowired
    private  TipoMotivoPagoRepository tipoMotivoPagoRepository;
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
