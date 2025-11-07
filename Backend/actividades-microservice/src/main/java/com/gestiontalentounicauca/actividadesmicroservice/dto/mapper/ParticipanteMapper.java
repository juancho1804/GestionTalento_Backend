package com.gestiontalentounicauca.actividadesmicroservice.dto.mapper;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Actividad;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.service.client.UsuarioResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ParticipanteMapper {
    public Participante toEntity(ParticipanteRequestDTO request, UsuarioResponseDTO usuario, Actividad actividad) {
        Participante participante = new Participante();
        participante.setActividad(actividad);
        participante.setIdUsuario(usuario.getId());
        return participante;
    }

    public ParticipanteResponseDTO toResponse(Participante participante, UsuarioResponseDTO usuario) {
        return ParticipanteResponseDTO.builder()
                .idParticipante(participante.getId())
                .usuario(usuario)
                .idActividad(participante.getActividad().getId())
                .build();
    }
}

