package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.PlanRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.PlanResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/planes")
@Tag(
        name = "Planes",
        description = "Gestión de planes del sistema"
)
public class PlanController {

    @Autowired
    private IPlanService planService;

    @Operation(
            summary = "Crear plan",
            description = "Permite crear un nuevo plan."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PlanResponseDTO> crearPlan(
            @Parameter(description = "Información del plan a crear")
            @RequestBody PlanRequestDTO planRequestDTO
    ) {
        PlanResponseDTO planResponseDTO = planService.crearPlan(planRequestDTO);
        return new ResponseEntity<>(planResponseDTO, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar plan",
            description = "Actualiza la información de un plan existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> actualizarPlan(
            @Parameter(description = "ID del plan a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Información actualizada del plan")
            @RequestBody PlanRequestDTO planRequestDTO
    ) {
        return new ResponseEntity<>(
                planService.actualizarPlan(id, planRequestDTO),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Eliminar plan",
            description = "Elimina un plan a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> eliminarPlan(
            @Parameter(description = "ID del plan a eliminar", example = "1")
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(
                planService.eliminarPlan(id),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Listar planes",
            description = "Obtiene la lista de todos los planes registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de planes obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> listarPlanes() {
        return new ResponseEntity<>(
                planService.getPlanes(),
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Obtener plan por ID",
            description = "Obtiene la información de un plan específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Plan no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> encontrarPlan(
            @Parameter(description = "ID del plan", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                planService.getPlan(id),
                HttpStatus.OK
        );
    }
}
