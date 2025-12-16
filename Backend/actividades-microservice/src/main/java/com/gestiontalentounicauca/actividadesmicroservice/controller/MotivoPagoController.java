package com.gestiontalentounicauca.actividadesmicroservice.controller;

import com.gestiontalentounicauca.actividadesmicroservice.dto.request.MotivoPagoRequestDTO;
import com.gestiontalentounicauca.actividadesmicroservice.dto.response.MotivoPagoResponseDTO;
import com.gestiontalentounicauca.actividadesmicroservice.service.IMotivoPagoService;
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
@RequestMapping("/motivopago")
@Tag(
        name = "Motivo de Pago",
        description = "Gestión de motivos de pago"
)
public class MotivoPagoController {

    @Autowired
    private IMotivoPagoService motivoPagoService;

    @Operation(
            summary = "Crear motivo de pago",
            description = "Permite crear un nuevo motivo de pago."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Motivo de pago creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
    })
    @PostMapping
    public ResponseEntity<MotivoPagoResponseDTO> crearMotivoPago(
            @Parameter(description = "Información del motivo de pago a crear")
            @RequestBody MotivoPagoRequestDTO motivoPagoRequestDTO
    ) {
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.crearMotivoPago(motivoPagoRequestDTO);
        return new ResponseEntity<>(motivoPagoResponseDTO, null, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Actualizar motivo de pago",
            description = "Permite actualizar un motivo de pago existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motivo de pago actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Motivo de pago no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MotivoPagoResponseDTO> actualizarMotivoPago(
            @Parameter(description = "ID del motivo de pago a actualizar")
            @PathVariable Long id,
            @Parameter(description = "Información del motivo de pago a actualizar")
            @RequestBody MotivoPagoRequestDTO motivoPagoRequestDTO
    ) {
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.actualizarMotivoPago(id, motivoPagoRequestDTO);
        return new ResponseEntity<>(motivoPagoResponseDTO, null, HttpStatus.OK);
    }

    @Operation(
            summary = "Eliminar motivo de pago",
            description = "Permite eliminar un motivo de pago por su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motivo de pago eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Motivo de pago no encontrado")
    })
    @DeleteMapping
    public ResponseEntity<Boolean> eliminarMotivoPago(
            @Parameter(description = "ID del motivo de pago a eliminar")
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(motivoPagoService.eliminarMotivoPago(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener motivo de pago",
            description = "Obtiene el motivo de pago asociado al ID proporcionado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Motivo de pago obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Motivo de pago no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MotivoPagoResponseDTO> getMotivoPago(
            @Parameter(description = "ID del motivo de pago")
            @PathVariable Long id
    ) {
        MotivoPagoResponseDTO motivoPagoResponseDTO = motivoPagoService.encontrarPorId(id);
        return new ResponseEntity<>(motivoPagoResponseDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener todos los motivos de pago",
            description = "Obtiene una lista con todos los motivos de pago registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de motivos de pago obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<MotivoPagoResponseDTO>> findAll() {
        return new ResponseEntity<>(motivoPagoService.listarMotivoPagos(), HttpStatus.OK);
    }
}
