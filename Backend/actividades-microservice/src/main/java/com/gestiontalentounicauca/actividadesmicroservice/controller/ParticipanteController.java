package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Participante;
import com.gestiontalentounicauca.actividadesmicroservice.service.ParticipanteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/participantes")
@RestController
public class ParticipanteController {

    @Autowired
    private ParticipanteServiceImpl participanteService;


    @PostMapping
    public ParticipanteResponseDTO saveParticipante(@RequestBody ParticipanteRequestDTO participanteRequestDTO){
        return participanteService.crearParticipante(participanteRequestDTO);
    }
}
