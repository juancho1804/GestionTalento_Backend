package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.service.IUsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IUsuarioService usuarioService;

    // ------------------ CREAR USUARIO ------------------
    @Test
    void testCrearUsuario() throws Exception {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .cedula("123")
                .nombre("Juan")
                .build();

        UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .id(1L)
                .cedula("123")
                .nombre("Juan")
                .build();

        when(usuarioService.crearUsuario(any())).thenReturn(response);

        mockMvc.perform(post("/api/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cedula").value("123"))
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    // ------------------ ACTUALIZAR USUARIO ------------------
    @Test
    void testActualizarUsuario() throws Exception {
        UsuarioRequestDTO request = UsuarioRequestDTO.builder()
                .nombre("Juan Actualizado")
                .build();

        UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .id(5L)
                .nombre("Juan Actualizado")
                .build();

        when(usuarioService.actualizarUsuario(eq(5L), any())).thenReturn(response);

        mockMvc.perform(put("/api/usuario/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"));
    }

    // ------------------ ELIMINAR USUARIO ------------------
    @Test
    void testEliminarUsuario() throws Exception {
        when(usuarioService.eliminarUsuario(3L)).thenReturn(true);

        mockMvc.perform(delete("/api/usuario?id=3"))
                .andExpect(status().isNoContent());
    }

    // ------------------ OBTENER USUARIO POR ID ------------------
    @Test
    void testObtenerUsuarioPorId() throws Exception {
        UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .id(7L)
                .cedula("999")
                .nombre("Usuario Prueba")
                .build();

        when(usuarioService.obtenerUsuario(7L)).thenReturn(response);

        mockMvc.perform(get("/api/usuario/id/7"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7L))
                .andExpect(jsonPath("$.cedula").value("999"))
                .andExpect(jsonPath("$.nombre").value("Usuario Prueba"));
    }

    // ------------------ OBTENER USUARIO POR CÉDULA ------------------
    @Test
    void testObtenerUsuarioPorCedula() throws Exception {
        UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .id(8L)
                .cedula("123456")
                .nombre("Usuario Cedula")
                .build();

        when(usuarioService.buscarUsuarioPorCedula("123456")).thenReturn(response);

        mockMvc.perform(get("/api/usuario/cedula/123456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cedula").value("123456"))
                .andExpect(jsonPath("$.nombre").value("Usuario Cedula"));
    }

    // ------------------ LISTAR USUARIOS ------------------
    @Test
    void testListarUsuarios() throws Exception {
        UsuarioResponseDTO u1 = UsuarioResponseDTO.builder().id(1L).nombre("A").build();
        UsuarioResponseDTO u2 = UsuarioResponseDTO.builder().id(2L).nombre("B").build();

        when(usuarioService.listarUsuarios()).thenReturn(List.of(u1, u2));

        mockMvc.perform(get("/api/usuario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    // ------------------ IMPORTAR DESDE SHEETS ------------------
    @Test
    void testAgregarUsuariosDesdeSheets() throws Exception {
        UsuarioResponseDTO response = UsuarioResponseDTO.builder()
                .id(10L)
                .nombre("Desde Sheets")
                .build();

        when(usuarioService.agregarUsuarios("sheet123"))
                .thenReturn(List.of(response));

        mockMvc.perform(post("/api/usuario/2")
                        .param("spreadsheetId", "sheet123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Desde Sheets"));
    }
}
