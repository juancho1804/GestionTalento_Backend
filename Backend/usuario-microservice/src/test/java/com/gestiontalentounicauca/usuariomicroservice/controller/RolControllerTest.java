package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.RolResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import com.gestiontalentounicauca.usuariomicroservice.service.IRolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IRolService rolService;

    @Test
    void testCrearRol() throws Exception {

        // Request DTO
        RolRequestDTO request = RolRequestDTO.builder()
                .rol(EnumRol.ADMIN)
                .build();

        // Response DTO
        RolResponseDTO response = RolResponseDTO.builder()
                .rol(EnumRol.ADMIN)
                .build();

        when(rolService.crearRol(any())).thenReturn(response);

        mockMvc.perform(post("/rol")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rol").value("ADMIN"));
    }
}
