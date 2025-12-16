package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ActividadRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ActividadResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IActividadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/actividades")
@RestController
@Tag(
        name = "Actividades",
        description = "Gestión de actividades del sistema"
)
public class ActividadController {

    @Autowired
    private IActividadService actividadService;

    @Operation(
            summary = "Crear una nueva actividad",
            description = "Registra una nueva actividad en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la actividad inválidos")
    })
    @PostMapping
    public ResponseEntity<ActividadResponseDTO> createActividad(
            @RequestBody ActividadRequestDTO actividadRequestDTO
    ) {
        ActividadResponseDTO actividadResponseDTO =
                actividadService.crearActividad(actividadRequestDTO);
        return new ResponseEntity<>(actividadResponseDTO, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Actualizar una actividad",
            description = "Actualiza la información de una actividad existente a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> updateActividad(
            @Parameter(description = "ID de la actividad a actualizar", example = "1")
            @PathVariable Long id,
            @RequestBody ActividadRequestDTO actividadRequestDTO
    ) {
        ActividadResponseDTO actividadResponseDTO =
                actividadService.actualizarActividad(id, actividadRequestDTO);
        return new ResponseEntity<>(actividadResponseDTO, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar una actividad",
            description = "Elimina una actividad del sistema a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> deleteActividad(
            @Parameter(description = "ID de la actividad a eliminar", example = "1")
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(
                actividadService.eliminarActividad(id),
                null,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Obtener una actividad",
            description = "Obtiene la información de una actividad por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Actividad encontrada"),
            @ApiResponse(responseCode = "404", description = "Actividad no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActividadResponseDTO> getActividad(
            @Parameter(description = "ID de la actividad", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                actividadService.getActividad(id),
                null,
                HttpStatus.OK
        );
    }
}
