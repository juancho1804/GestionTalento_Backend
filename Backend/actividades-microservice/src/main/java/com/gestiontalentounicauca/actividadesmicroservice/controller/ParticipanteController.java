package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/participantes")
@RestController
public class ParticipanteController {

    @Autowired
    private IParticipanteService participanteService;


    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> guardarParticipante(@RequestBody ParticipanteRequestDTO participanteRequestDTO){
        ParticipanteResponseDTO participanteResponseDTO = participanteService.crearParticipante(participanteRequestDTO);
        return new ResponseEntity<>(participanteResponseDTO,null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> actualizarParticipante(@PathVariable Long id, @RequestBody ParticipanteRequestDTO participanteRequestDTO){
        ParticipanteResponseDTO participanteResponseDTO = participanteService.actualizarParticipante(id,participanteRequestDTO);
        return new ResponseEntity<>(participanteResponseDTO,null, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDTO>> obtenerParticipantes(){
        List<ParticipanteResponseDTO> participantes = participanteService.listarParticipantes();
        return new ResponseEntity<>(participantes, null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> obtenerParticipante(@PathVariable Long id){
        return new ResponseEntity<>(participanteService.encontrarPorId(id), null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> eliminarParticipante(@RequestParam Long id){
        return new ResponseEntity<>(participanteService.eliminarParticipante(id), null, HttpStatus.OK);
    }

}
