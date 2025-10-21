package com.gestiontalentounicauca.usuariomicroservice.service;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.UsuarioRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.UsuarioResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IUsuarioService {
    UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioRequestDTO);
    UsuarioResponseDTO actualizarUsuario(Long id,UsuarioRequestDTO usuarioRequestDTO);
    boolean eliminarUsuario(Long id);
    UsuarioResponseDTO obtenerUsuario(Long id);
    List<UsuarioResponseDTO> listarUsuarios();
    List<UsuarioResponseDTO>agregarUsuarios(String spreadsheetId) throws IOException;
    UsuarioResponseDTO buscarUsuarioPorCedula(String cedula);
}
