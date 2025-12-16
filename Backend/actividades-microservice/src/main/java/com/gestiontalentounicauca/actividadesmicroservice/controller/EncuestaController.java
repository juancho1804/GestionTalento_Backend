package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EncuestaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EncuestaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEncuestaService;
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
@RequestMapping("/encuestas")
@Tag(
        name = "Encuestas",
        description = "Gestión de encuestas del sistema"
)
public class EncuestaController {

    @Autowired
    private IEncuestaService encuestaService;

    @Operation(
            summary = "Crear una encuesta",
            description = "Registra una nueva encuesta en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Encuesta creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de la encuesta inválidos")
    })
    @PostMapping
    public ResponseEntity<EncuestaResponseDTO> saveEncuesta(
            @RequestBody EncuestaRequestDTO encuesta
    ) {
        return new ResponseEntity<>(
                encuestaService.crearEncuesta(encuesta),
                null,
                HttpStatus.CREATED
        );
    }

    @Operation(
            summary = "Actualizar una encuesta",
            description = "Actualiza la información de una encuesta existente a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encuesta actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Encuesta no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> actualizarEncuesta(
            @Parameter(description = "ID de la encuesta a actualizar", example = "1")
            @PathVariable Long id,
            @RequestBody EncuestaRequestDTO encuesta
    ) {
        return new ResponseEntity<>(
                encuestaService.actualizarEncuesta(id, encuesta),
                null,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Obtener una encuesta",
            description = "Obtiene la información de una encuesta por su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encuesta encontrada"),
            @ApiResponse(responseCode = "404", description = "Encuesta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EncuestaResponseDTO> getEncuesta(
            @Parameter(description = "ID de la encuesta", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                encuestaService.getEncuesta(id),
                null,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Listar todas las encuestas",
            description = "Obtiene el listado completo de encuestas registradas"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de encuestas obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<EncuestaResponseDTO>> getAllEncuestas() {
        List<EncuestaResponseDTO> encuestaResponseDTOS =
                encuestaService.getEncuestas();
        return new ResponseEntity<>(
                encuestaResponseDTOS,
                null,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Eliminar una encuesta",
            description = "Elimina una encuesta del sistema a partir de su ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Encuesta eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Encuesta no encontrada")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> deleteEncuesta(
            @Parameter(description = "ID de la encuesta a eliminar", example = "1")
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(
                encuestaService.eliminarEncuesta(id),
                null,
                HttpStatus.OK
        );
    }
}
