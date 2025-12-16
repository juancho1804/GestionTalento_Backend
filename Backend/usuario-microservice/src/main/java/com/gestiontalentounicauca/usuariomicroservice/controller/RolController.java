package com.gestiontalentounicauca.usuariomicroservice.controller;

import com.gestiontalentounicauca.usuariomicroservice.dto.request.RolRequestDTO;
import com.gestiontalentounicauca.usuariomicroservice.dto.response.RolResponseDTO;
import com.gestiontalentounicauca.usuariomicroservice.service.IRolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rol")
@Tag(
        name = "Roles",
        description = "Gestión de roles del sistema"
)
public class RolController {

    @Autowired
    private IRolService rolService;

    // ------------------ CREAR ROL ------------------
    @Operation(
            summary = "Crear un rol",
            description = "Registra un nuevo rol en el sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rol creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del rol inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping
    public ResponseEntity<RolResponseDTO> crearRol(
            @RequestBody RolRequestDTO rolRequestDTO) {

        RolResponseDTO rolResponseDTO = rolService.crearRol(rolRequestDTO);
        return new ResponseEntity<>(rolResponseDTO, null, HttpStatus.CREATED);
    }
}
