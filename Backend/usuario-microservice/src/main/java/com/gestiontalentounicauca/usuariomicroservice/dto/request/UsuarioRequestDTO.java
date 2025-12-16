package com.gestiontalentounicauca.usuariomicroservice.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Datos necesarios para la creación o actualización de un usuario")
public class UsuarioRequestDTO {

    @Schema(
            description = "Correo electrónico del usuario",
            example = "usuario@unicauca.edu.co",
            required = true
    )
    private String correo;

    @Schema(
            description = "Número de cédula del usuario",
            example = "1234567890",
            required = true
    )
    private String cedula;

    @Schema(
            description = "Nombre del usuario",
            example = "Juan",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Apellido del usuario",
            example = "Pérez",
            required = true
    )
    private String apellido;

    @Schema(
            description = "Listado de roles asociados al usuario"
    )
    private Set<RolRequestDTO> roles = new HashSet<>();
}
