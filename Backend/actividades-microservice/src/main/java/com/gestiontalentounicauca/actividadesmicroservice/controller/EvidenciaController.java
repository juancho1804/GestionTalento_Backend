package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.model.Evidencia;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEvidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/evidencias")
public class EvidenciaController {
    @Autowired
    private IEvidenciaService evidenciaService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvidenciaResponseDTO> guardarEvidencia(@RequestParam("actividadId") Long id, @RequestPart("file") MultipartFile file) throws IOException {
        EvidenciaRequestDTO evidencia = EvidenciaRequestDTO.builder().idActividad(id).build();
        EvidenciaResponseDTO evidenciaResponseDTO = evidenciaService.crearEvidencia(evidencia,file);

        return new ResponseEntity<>(evidenciaResponseDTO,null, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<Boolean> eliminarEvidencia(@RequestParam("actividadId") Long id){
        return new ResponseEntity<>(evidenciaService.eliminarEvidencia(id),null, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvidenciaResponseDTO>getEvidencia(@PathVariable Long id){
        return new ResponseEntity<>(evidenciaService.getEvidencia(id),null, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EvidenciaResponseDTO>> getAllEvidencias(){
        return new ResponseEntity<>(evidenciaService.getEvidencias(),null, HttpStatus.OK);
    }
}
