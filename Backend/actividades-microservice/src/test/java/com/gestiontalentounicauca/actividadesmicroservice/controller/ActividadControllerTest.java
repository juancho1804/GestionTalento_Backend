package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.mapper.PlanMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IActividadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@WebMvcTest(ActividadController.class)
public class ActividadControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IActividadService actividadService;


    @Test
    void testCrearActividad() throws Exception {
        // Datos de entrada
        ActividadRequestDTO request = ActividadRequestDTO.builder()
                .nombre("Actividad 1")
                .build();

        // Lo que el servicio debería devolver
        ActividadResponseDTO response = ActividadResponseDTO.builder()
                .id(1L)
                .nombre("Actividad 1")
                .build();

        when(actividadService.crearActividad(any())).thenReturn(response);

        mockMvc.perform(post("/actividades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Actividad 1"));
    }

    @Test
    void testGetActividad() throws Exception {
        ActividadResponseDTO response = ActividadResponseDTO.builder()
                .id(5L)
                .nombre("Actividad Prueba")
                .build();

        when(actividadService.getActividad(5L)).thenReturn(response);

        mockMvc.perform(get("/actividades/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.nombre").value("Actividad Prueba"));
    }

    @Test
    void testActualizarActividad() throws Exception {
        ActividadRequestDTO request = ActividadRequestDTO.builder()
                .nombre("Actividad Actualizada")
                .build();

        ActividadResponseDTO response = ActividadResponseDTO.builder()
                .id(10L)
                .nombre("Actividad Actualizada")
                .build();

        when(actividadService.actualizarActividad(eq(10L), any())).thenReturn(response);

        mockMvc.perform(put("/actividades/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.nombre").value("Actividad Actualizada"));
    }

    @Test
    void testEliminarActividad() throws Exception {
        when(actividadService.eliminarActividad(7L)).thenReturn(true);

        mockMvc.perform(delete("/actividades?id=7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }







}
