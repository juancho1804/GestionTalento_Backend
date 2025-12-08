package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEvidenciaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EvidenciaController.class)
public class EvidenciaControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEvidenciaService evidenciaService;

    @Autowired
    private ObjectMapper objectMapper;

    // ----------------------------------------------------
    // TEST: GUARDAR EVIDENCIA (Multipart)
    // ----------------------------------------------------
    @Test
    void testGuardarEvidencia() throws Exception {

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.jpg",
                "image/jpeg",
                "contenido-falso".getBytes()
        );

        EvidenciaResponseDTO response = EvidenciaResponseDTO.builder()
                .idEvidencia(1L)
                .rutaArchivo("/uploads/test.jpg")
                .idActividad(10L)
                .build();

        // Mock del service
        when(evidenciaService.crearEvidencia(any(EvidenciaRequestDTO.class), any()))
                .thenReturn(response);

        mockMvc.perform(multipart("/evidencias")
                        .file(mockFile)
                        .param("actividadId", "10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEvidencia").value(1))
                .andExpect(jsonPath("$.rutaArchivo").value("/uploads/test.jpg"))
                .andExpect(jsonPath("$.idActividad").value(10));
    }

    // ----------------------------------------------------
    // TEST: ELIMINAR EVIDENCIA
    // ----------------------------------------------------
    @Test
    void testEliminarEvidencia() throws Exception {

        when(evidenciaService.eliminarEvidencia(10L)).thenReturn(true);

        mockMvc.perform(delete("/evidencias")
                        .param("actividadId", "10"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ----------------------------------------------------
    // TEST: OBTENER EVIDENCIA POR ID
    // ----------------------------------------------------
    @Test
    void testGetEvidencia() throws Exception {

        EvidenciaResponseDTO response = EvidenciaResponseDTO.builder()
                .idEvidencia(5L)
                .rutaArchivo("/uploads/e.jpeg")
                .idActividad(20L)
                .build();

        when(evidenciaService.getEvidencia(5L)).thenReturn(response);

        mockMvc.perform(get("/evidencias/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEvidencia").value(5))
                .andExpect(jsonPath("$.rutaArchivo").value("/uploads/e.jpeg"))
                .andExpect(jsonPath("$.idActividad").value(20));
    }

    // ----------------------------------------------------
    // TEST: OBTENER TODAS LAS EVIDENCIAS
    // ----------------------------------------------------
    @Test
    void testGetAllEvidencias() throws Exception {

        EvidenciaResponseDTO evidencia = EvidenciaResponseDTO.builder()
                .idEvidencia(1L)
                .rutaArchivo("/uploads/abc.jpg")
                .idActividad(30L)
                .build();

        List<EvidenciaResponseDTO> lista = Collections.singletonList(evidencia);

        when(evidenciaService.getEvidencias()).thenReturn(lista);

        mockMvc.perform(get("/evidencias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEvidencia").value(1))
                .andExpect(jsonPath("$[0].rutaArchivo").value("/uploads/abc.jpg"))
                .andExpect(jsonPath("$[0].idActividad").value(30));
    }
}
