package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planes")
public class PlanController {

    @Autowired
    private IPlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponseDTO>crearPlan(@RequestBody PlanRequestDTO planRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.crearPlan(planRequestDTO);
        return new ResponseEntity<>(planResponseDTO, null,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> actualizarPlan(@PathVariable Long id, @RequestBody PlanRequestDTO planRequestDTO) {
        return new ResponseEntity<>(planService.actualizarPlan(id, planRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> eliminarPlan(@RequestParam Long id) {
        return new ResponseEntity<>(planService.eliminarPlan(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> listarPlanes() {
        return new ResponseEntity<>(planService.getPlanes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO>encontrarPlan(@PathVariable Long id){
        return new ResponseEntity<>(planService.getPlan(id), HttpStatus.OK);
    }
}
