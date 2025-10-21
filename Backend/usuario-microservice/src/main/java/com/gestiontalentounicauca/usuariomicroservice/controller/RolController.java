package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.RolResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.service.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rol")
public class RolController {

    @Autowired
    private IRolService rolService;

    @PostMapping
    public ResponseEntity<RolResponseDTO> crearRol(@RequestBody RolRequestDTO rolRequestDTO) {
        RolResponseDTO rolResponseDTO = rolService.crearRol(rolRequestDTO);
        return new ResponseEntity<>(rolResponseDTO,null, HttpStatus.CREATED);
    }

}
