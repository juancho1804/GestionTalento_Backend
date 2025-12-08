package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IParticipanteService;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ParticipanteController.class)
class ParticipanteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IParticipanteService participanteService;

    // ---------- POST ----------
    @Test
    void guardarParticipante_debeRetornar201() throws Exception {

        ParticipanteRequestDTO request = ParticipanteRequestDTO.builder()
                .cedula("123456")
                .idActividad(10L)
                .build();

        UsuarioResponseDTO usuario = UsuarioResponseDTO.builder()
                .id(99L)
                .nombre("Juan")
                .apellido("Ceron")
                .correo("juan@test.com")
                .build();

        ParticipanteResponseDTO response = ParticipanteResponseDTO.builder()
                .idParticipante(1L)
                .usuario(usuario)
                .idActividad(10L)
                .build();

        Mockito.when(participanteService.crearParticipante(Mockito.any())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/participantes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idParticipante").value(1L))
                .andExpect(jsonPath("$.usuario.id").value(99L))
                .andExpect(jsonPath("$.usuario.nombre").value("Juan"))
                .andExpect(jsonPath("$.idActividad").value(10L));
    }

    // ---------- PUT ----------
    @Test
    void actualizarParticipante_debeRetornar201() throws Exception {

        ParticipanteRequestDTO request = ParticipanteRequestDTO.builder()
                .cedula("555555")
                .idActividad(40L)
                .build();

        UsuarioResponseDTO usuario = UsuarioResponseDTO.builder()
                .id(88L)
                .nombre("Ana")
                .apellido("Lopez")
                .correo("ana@test.com")
                .build();

        ParticipanteResponseDTO response = ParticipanteResponseDTO.builder()
                .idParticipante(20L)
                .usuario(usuario)
                .idActividad(40L)
                .build();

        Mockito.when(participanteService.actualizarParticipante(Mockito.eq(20L), Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/participantes/20")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idParticipante").value(20L))
                .andExpect(jsonPath("$.usuario.id").value(88L))
                .andExpect(jsonPath("$.usuario.nombre").value("Ana"))
                .andExpect(jsonPath("$.idActividad").value(40L));
    }

    // ---------- GET ALL ----------
    @Test
    void obtenerParticipantes_debeRetornar200() throws Exception {

        List<ParticipanteResponseDTO> lista = List.of(
                ParticipanteResponseDTO.builder()
                        .idParticipante(1L)
                        .usuario(UsuarioResponseDTO.builder().id(10L).nombre("Pedro").build())
                        .idActividad(100L)
                        .build(),
                ParticipanteResponseDTO.builder()
                        .idParticipante(2L)
                        .usuario(UsuarioResponseDTO.builder().id(20L).nombre("Maria").build())
                        .idActividad(200L)
                        .build()
        );

        Mockito.when(participanteService.listarParticipantes()).thenReturn(lista);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/participantes")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].idParticipante").value(1L))
                .andExpect(jsonPath("$[1].idParticipante").value(2L));
    }

    // ---------- GET BY ID ----------
    @Test
    void obtenerParticipantePorId_debeRetornar200() throws Exception {

        ParticipanteResponseDTO response = ParticipanteResponseDTO.builder()
                .idParticipante(5L)
                .usuario(UsuarioResponseDTO.builder().id(50L).nombre("Luis").build())
                .idActividad(300L)
                .build();

        Mockito.when(participanteService.encontrarPorId(5L)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/participantes/5")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idParticipante").value(5L))
                .andExpect(jsonPath("$.usuario.id").value(50L))
                .andExpect(jsonPath("$.idActividad").value(300L));
    }

    // ---------- DELETE ----------
    @Test
    void eliminarParticipante_debeRetornar200YTrue() throws Exception {

        Mockito.when(participanteService.eliminarParticipante(7L)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/participantes?id=7")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
