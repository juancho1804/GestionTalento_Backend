package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IActividadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> updateActividad(@PathVariable Long id, @RequestBody ActividadRequestDTO actividadRequestDTO){
        ActividadResponseDTO actividadResponseDTO =  actividadService.actualizarActividad(id, actividadRequestDTO);
        return new ResponseEntity<>(actividadResponseDTO,null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteActividad(@RequestParam Long id){
        return new ResponseEntity<>(actividadService.eliminarActividad(id),null,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> getActividad(@PathVariable Long id){
        return new ResponseEntity<>(actividadService.getActividad(id),null, HttpStatus.OK);
    }



}
