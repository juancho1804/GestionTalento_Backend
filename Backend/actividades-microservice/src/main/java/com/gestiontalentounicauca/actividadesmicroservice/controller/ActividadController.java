package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/actividades")
@RestController
public class ActividadController {

    @Autowired
    private IActividadService actividadService;


    @PostMapping
    public ResponseEntity<ActividadResponseDTO> createActividad(@RequestBody ActividadRequestDTO actividadRequestDTO){
        ActividadResponseDTO actividadResponseDTO = actividadService.crearActividad(actividadRequestDTO);
        return new ResponseEntity<>(actividadResponseDTO,null, HttpStatus.OK);
    }


}
