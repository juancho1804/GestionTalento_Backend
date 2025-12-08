package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IMotivoPagoService;
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

@WebMvcTest(MotivoPagoController.class)
class MotivoPagoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMotivoPagoService motivoPagoService;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------- POST ----------
    @Test
    void crearMotivoPago_debeRetornar201() throws Exception {
        MotivoPagoRequestDTO request = new MotivoPagoRequestDTO();
        request.setIdTipoMotivoPago(1L);
        request.setNombreTipoMotivoPago("Mensualidad");
        request.setMonto(20000.0);
        request.setIdActividad(10L);

        MotivoPagoResponseDTO response = MotivoPagoResponseDTO.builder()
                .id(5L)
                .idTipoMotivoPago(1L)
                .montoAsignado(20000.0)
                .idActividad(10L)
                .build();

        Mockito.when(motivoPagoService.crearMotivoPago(Mockito.any())).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/motivopago")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.idTipoMotivoPago").value(1L))
                .andExpect(jsonPath("$.montoAsignado").value(20000.0))
                .andExpect(jsonPath("$.idActividad").value(10L));
    }

    // ---------- PUT ----------
    @Test
    void actualizarMotivoPago_debeRetornar200() throws Exception {

        MotivoPagoRequestDTO request = new MotivoPagoRequestDTO();
        request.setIdTipoMotivoPago(2L);
        request.setNombreTipoMotivoPago("Extra");
        request.setMonto(30000.0);
        request.setIdActividad(22L);

        MotivoPagoResponseDTO response = MotivoPagoResponseDTO.builder()
                .id(5L)
                .idTipoMotivoPago(2L)
                .montoAsignado(30000.0)
                .idActividad(22L)
                .build();

        Mockito.when(motivoPagoService.actualizarMotivoPago(Mockito.eq(5L), Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/motivopago/5")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.idTipoMotivoPago").value(2L))
                .andExpect(jsonPath("$.montoAsignado").value(30000.0))
                .andExpect(jsonPath("$.idActividad").value(22L));
    }

    // ---------- DELETE ----------
    @Test
    void eliminarMotivoPago_debeRetornar200YTrue() throws Exception {

        Mockito.when(motivoPagoService.eliminarMotivoPago(8L)).thenReturn(true);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/motivopago?id=8")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    // ---------- GET BY ID ----------
    @Test
    void getMotivoPago_debeRetornar200() throws Exception {

        MotivoPagoResponseDTO response = MotivoPagoResponseDTO.builder()
                .id(3L)
                .idTipoMotivoPago(7L)
                .montoAsignado(50000.0)
                .idActividad(100L)
                .build();

        Mockito.when(motivoPagoService.encontrarPorId(3L)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/motivopago/3")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.idTipoMotivoPago").value(7L))
                .andExpect(jsonPath("$.montoAsignado").value(50000.0))
                .andExpect(jsonPath("$.idActividad").value(100L));
    }

    // ---------- GET ALL ----------
    @Test
    void obtenerTodos_debeRetornarLista() throws Exception {

        List<MotivoPagoResponseDTO> lista = List.of(
                MotivoPagoResponseDTO.builder()
                        .id(1L).idTipoMotivoPago(10L).montoAsignado(1000.0).idActividad(1L).build(),
                MotivoPagoResponseDTO.builder()
                        .id(2L).idTipoMotivoPago(11L).montoAsignado(2000.0).idActividad(2L).build()
        );

        Mockito.when(motivoPagoService.listarMotivoPagos()).thenReturn(lista);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/motivopago")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}
