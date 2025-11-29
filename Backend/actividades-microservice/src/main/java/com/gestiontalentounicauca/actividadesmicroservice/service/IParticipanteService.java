package com.gestiontalentounicauca.actividadesmicroservice.service;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;

import java.io.IOException;
import java.util.List;

public interface IParticipanteService {
    ParticipanteResponseDTO crearParticipante(ParticipanteRequestDTO participanteRequestDTO);
    ParticipanteResponseDTO actualizarParticipante(Long idParticipante, ParticipanteRequestDTO participanteRequestDTO);
    Boolean eliminarParticipante(Long idParticipante);
    List<ParticipanteResponseDTO> listarParticipantes();
    ParticipanteResponseDTO encontrarPorId(Long idParticipante);
    ParticipanteResponseDTO encontrarPorCedula(String cedula);
}
