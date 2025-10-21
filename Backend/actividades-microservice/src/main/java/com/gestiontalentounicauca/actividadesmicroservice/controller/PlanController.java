package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
