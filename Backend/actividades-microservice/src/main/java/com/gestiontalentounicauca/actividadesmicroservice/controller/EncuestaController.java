package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EncuestaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEncuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/encuestas")
public class EncuestaController {

    @Autowired
    private IEncuestaService encuestaService;


    @PostMapping
    public ResponseEntity<EncuestaResponseDTO> saveEncuesta(@RequestBody EncuestaRequestDTO encuesta) {
        return new ResponseEntity<>(encuestaService.crearEncuesta(encuesta),null, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO>actualizarEncuesta(@PathVariable Long id, @RequestBody EncuestaRequestDTO encuesta) {
        return new ResponseEntity<>(encuestaService.actualizarEncuesta(id, encuesta), null,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> getEncuesta(@PathVariable Long id){
        return new ResponseEntity<>(encuestaService.getEncuesta(id), null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EncuestaResponseDTO>> getAllEncuestas(){
        List<EncuestaResponseDTO> encuestaResponseDTOS = encuestaService.getEncuestas();
        return new ResponseEntity<>(encuestaResponseDTOS, null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteEncuesta(@RequestParam Long id){
        return new ResponseEntity<>(encuestaService.eliminarEncuesta(id), null, HttpStatus.OK);
    }


}
