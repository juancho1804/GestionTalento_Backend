package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IMotivoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motivopago")
public class MotivoPagoController {

    @Autowired
    private IMotivoPagoService motivoPagoService;

    @PostMapping
    public ResponseEntity<MotivoPagoResponseDTO> crearMotivoPago(@RequestBody MotivoPagoRequestDTO motivoPagoRequestDTO){
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.crearMotivoPago(motivoPagoRequestDTO);
        return new ResponseEntity<>(motivoPagoResponseDTO, null, HttpStatus.CREATED);
    }


}
