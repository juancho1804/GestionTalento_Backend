package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EncuestaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEncuestaService;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EncuestaController.class)
public class EncuestaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IEncuestaService encuestaService;


    @Test
    void testSaveEncuesta() throws Exception {
        EncuestaRequestDTO req = EncuestaRequestDTO.builder()
                .idParticipante(10L)
                .calificacion(5)
                .build();

        EncuestaResponseDTO res = EncuestaResponseDTO.builder()
                .idParticipante(10L)
                .idActividad(99L)
                .calificacion(5)
                .build();

        when(encuestaService.crearEncuesta(req)).thenReturn(res);

        mockMvc.perform(post("/encuestas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idParticipante").value(10L))
                .andExpect(jsonPath("$.idActividad").value(99L))
                .andExpect(jsonPath("$.calificacion").value(5));
    }

    @Test
    void testActualizarEncuesta() throws Exception {
        EncuestaRequestDTO req = EncuestaRequestDTO.builder()
                .idParticipante(20L)
                .calificacion(4)
                .build();

        EncuestaResponseDTO res = EncuestaResponseDTO.builder()
                .idParticipante(20L)
                .idActividad(12L)
                .calificacion(4)
                .build();

        when(encuestaService.actualizarEncuesta(1L, req)).thenReturn(res);

        mockMvc.perform(put("/encuestas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParticipante").value(20L))
                .andExpect(jsonPath("$.idActividad").value(12L))
                .andExpect(jsonPath("$.calificacion").value(4));
    }

    @Test
    void testGetEncuesta() throws Exception {
        EncuestaResponseDTO res = EncuestaResponseDTO.builder()
                .idParticipante(15L)
                .idActividad(7L)
                .calificacion(3)
                .build();

        when(encuestaService.getEncuesta(1L)).thenReturn(res);

        mockMvc.perform(get("/encuestas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParticipante").value(15L))
                .andExpect(jsonPath("$.idActividad").value(7L))
                .andExpect(jsonPath("$.calificacion").value(3));
    }

    @Test
    void testGetAllEncuestas() throws Exception {
        EncuestaResponseDTO res = EncuestaResponseDTO.builder()
                .idParticipante(15L)
                .idActividad(7L)
                .calificacion(3)
                .build();

        List<EncuestaResponseDTO> lista = Collections.singletonList(res);

        when(encuestaService.getEncuestas()).thenReturn(lista);

        mockMvc.perform(get("/encuestas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idParticipante").value(15L))
                .andExpect(jsonPath("$[0].idActividad").value(7L))
                .andExpect(jsonPath("$[0].calificacion").value(3));
    }

    @Test
    void testDeleteEncuesta() throws Exception {
        when(encuestaService.eliminarEncuesta(1L)).thenReturn(true);

        mockMvc.perform(delete("/encuestas?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}
