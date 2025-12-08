package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IMotivoPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<MotivoPagoResponseDTO> actualizarMotivoPago(@PathVariable Long id,@RequestBody MotivoPagoRequestDTO motivoPagoRequestDTO){
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.actualizarMotivoPago(id, motivoPagoRequestDTO);
        return new ResponseEntity<>(motivoPagoResponseDTO, null, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> eliminarMotivoPago(@RequestParam Long id){
        return new ResponseEntity<>(motivoPagoService.eliminarMotivoPago(id), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotivoPagoResponseDTO> getMotivoPago(@PathVariable Long id){
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.encontrarPorId(id);
        return new ResponseEntity<>(motivoPagoResponseDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MotivoPagoResponseDTO>> findAll(){
        return new ResponseEntity<>(motivoPagoService.listarMotivoPagos(), HttpStatus.OK);
    }


}
