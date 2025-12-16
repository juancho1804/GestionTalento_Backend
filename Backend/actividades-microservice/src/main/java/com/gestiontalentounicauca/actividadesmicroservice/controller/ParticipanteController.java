package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.ParticipanteRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.ParticipanteResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IParticipanteService;
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

@RequestMapping("/participantes")
@RestController
@Tag(
        name = "Participantes",
        description = "Gestión de participantes en actividades"
)
public class ParticipanteController {

    @Autowired
    private IParticipanteService participanteService;

    @Operation(
            summary = "Crear participante",
            description = "Registra un nuevo participante en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participante creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> guardarParticipante(
            @Parameter(description = "Datos del participante a registrar")
            @RequestBody ParticipanteRequestDTO participanteRequestDTO
    ) {
        ParticipanteResponseDTO participanteResponseDTO =
                participanteService.crearParticipante(participanteRequestDTO);
        return new ResponseEntity<>(participanteResponseDTO, null, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar participante",
            description = "Actualiza la información de un participante existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Participante actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> actualizarParticipante(
            @Parameter(description = "ID del participante a actualizar", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Datos actualizados del participante")
            @RequestBody ParticipanteRequestDTO participanteRequestDTO
    ) {
        ParticipanteResponseDTO participanteResponseDTO =
                participanteService.actualizarParticipante(id, participanteRequestDTO);
        return new ResponseEntity<>(participanteResponseDTO, null, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Listar participantes",
            description = "Obtiene la lista de todos los participantes registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de participantes obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<ParticipanteResponseDTO>> obtenerParticipantes() {
        List<ParticipanteResponseDTO> participantes =
                participanteService.listarParticipantes();
        return new ResponseEntity<>(participantes, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener participante por ID",
            description = "Obtiene la información de un participante específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> obtenerParticipante(
            @Parameter(description = "ID del participante", example = "1")
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(
                participanteService.encontrarPorId(id),
                null,
                HttpStatus.OK
        );
    }

    @Operation(
            summary = "Eliminar participante",
            description = "Elimina un participante a partir de su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Participante eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Participante no encontrado")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> eliminarParticipante(
            @Parameter(description = "ID del participante a eliminar", example = "1")
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(
                participanteService.eliminarParticipante(id),
                null,
                HttpStatus.OK
        );
    }
}
