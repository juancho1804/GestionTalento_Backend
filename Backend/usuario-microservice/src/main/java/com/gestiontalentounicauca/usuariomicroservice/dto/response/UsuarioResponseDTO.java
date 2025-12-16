package com.gestiontalentounicauca.usuariomicroservice.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
@Schema(description = "Respuesta con la información del usuario")
public class UsuarioResponseDTO {

    @Schema(
            description = "Identificador único del usuario",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nombre del usuario",
            example = "Juan"
    )
    private String nombre;

    @Schema(
            description = "Apellido del usuario",
            example = "Pérez"
    )
    private String apellido;

    @Schema(
            description = "Correo electrónico del usuario",
            example = "usuario@unicauca.edu.co"
    )
    private String correo;

    @Schema(
            description = "Número de cédula del usuario",
            example = "1234567890"
    )
    private String cedula;

    @Schema(
            description = "Roles asociados al usuario",
            example = "[\"ADMIN\", \"USER\"]"
    )
    private Set<String> roles;
}
