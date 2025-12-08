package com.gestiontalentounicauca.actividadesmicroservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Plan;
import com.gestiontalentounicauca.actividadesmicroservice.model.TipoPlan;
import com.gestiontalentounicauca.actividadesmicroservice.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class PlanControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        planRepository.deleteAll();
    }

    @Test
    void crearPlan_exitoso() throws Exception {
        PlanRequestDTO request = new PlanRequestDTO();
        request.setNombre("INCENTIVOS");
        request.setFechaInicio(LocalDate.now());
        request.setPresupuestoAsignado(1f);

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/planes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("INCENTIVOS"));
    }

    @Test
    void listarPlanes_exitoso() throws Exception {
        Plan plan= Plan.builder()
                .tipoPlan(TipoPlan.INCENTIVOS)
                .fechaInicio(LocalDate.now())
                .fechaFin(LocalDate.now().plusDays(1))
                .presupuestoAsignado(10f)
                .build();


        planRepository.save(plan);

        mockMvc.perform(get("/planes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("INCENTIVOS"));
    }

    @Test
    void obtenerPlanPorId_exitoso() throws Exception {
        Plan plan = planRepository.save(
                Plan.builder()
                        .tipoPlan(TipoPlan.BIENESTAR)
                        .fechaInicio(LocalDate.now())
                        .fechaFin(LocalDate.now().plusDays(7))
                        .presupuestoAsignado(200f)
                        .presupuestoEjecutado(0f)
                        .build()
        );

        mockMvc.perform(get("/planes/" + plan.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("BIENESTAR"));
    }

    @Test
    void actualizarPlan_exitoso() throws Exception {
        Plan plan = planRepository.save(
                Plan.builder()
                        .tipoPlan(TipoPlan.INCENTIVOS)
                        .fechaInicio(LocalDate.now())
                        .fechaFin(LocalDate.now().plusDays(3))
                        .presupuestoAsignado(20f)
                        .presupuestoEjecutado(5f)
                        .build()
        );

        PlanRequestDTO updateRequest = new PlanRequestDTO();
        updateRequest.setNombre("CAPACITACION");
        updateRequest.setFechaInicio(LocalDate.now());
        updateRequest.setPresupuestoAsignado(500f);

        String json = objectMapper.writeValueAsString(updateRequest);

        mockMvc.perform(put("/planes/" + plan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("CAPACITACION"))
                .andExpect(jsonPath("$.presupuestoAsignado").value(500.0));
    }

    @Test
    void eliminarPlan_exitoso() throws Exception {
        Plan plan = planRepository.save(
                Plan.builder()
                        .tipoPlan(TipoPlan.BIENESTAR)
                        .fechaInicio(LocalDate.now())
                        .fechaFin(LocalDate.now().plusDays(10))
                        .presupuestoAsignado(100f)
                        .presupuestoEjecutado(10f)
                        .build()
        );

        mockMvc.perform(delete("/planes?id=" + plan.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }



}
