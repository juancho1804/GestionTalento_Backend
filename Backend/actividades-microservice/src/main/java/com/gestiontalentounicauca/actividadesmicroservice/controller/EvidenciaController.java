package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.EvidenciaRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.EvidenciaResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IEvidenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(
        name = "Evidencias",
        description = "Gestión de evidencias relacionadas con las actividades"
)
public class EvidenciaController {

    @Autowired
    private IEvidenciaService evidenciaService;

    @Operation(
            summary = "Guardar evidencia",
            description = "Permite subir una nueva evidencia asociada a una actividad."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evidencia subida correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al subir la evidencia")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EvidenciaResponseDTO> guardarEvidencia(
            @Parameter(description = "ID de la actividad asociada a la evidencia", example = "1")
            @RequestParam("actividadId") Long id,
            @Parameter(description = "Archivo de evidencia", required = true)
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        EvidenciaRequestDTO evidencia = EvidenciaRequestDTO.builder().idActividad(id).build();
        EvidenciaResponseDTO evidenciaResponseDTO = evidenciaService.crearEvidencia(evidencia, file);
        return new ResponseEntity<>(evidenciaResponseDTO, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar evidencia",
            description = "Elimina una evidencia asociada a una actividad."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evidencia eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Evidencia no encontrada")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> eliminarEvidencia(
            @Parameter(description = "ID de la actividad de la cual eliminar la evidencia", example = "1")
            @RequestParam("actividadId") Long id
    ) {
        return new ResponseEntity<>(evidenciaService.eliminarEvidencia(id), null, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener evidencia",
            description = "Obtiene los detalles de una evidencia asociada a una actividad."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Evidencia obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "Evidencia no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EvidenciaResponseDTO> getEvidencia(
            @Parameter(description = "ID de la evidencia", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(evidenciaService.getEvidencia(id), null, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todas las evidencias",
            description = "Obtiene un listado de todas las evidencias registradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de evidencias obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<EvidenciaResponseDTO>> getAllEvidencias() {
        return new ResponseEntity<>(evidenciaService.getEvidencias(), null, HttpStatus.OK);
    }
}
