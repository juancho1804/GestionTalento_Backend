package com.gestiontalentounicauca.usuariomicroservice.dto.request;

import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Datos necesarios para asignar un rol a un usuario")
@Builder
public class RolRequestDTO {

    @Schema(
            description = "Rol asignado al usuario",
            example = "ADMIN",
            required = true
    )
    private EnumRol rol;
}
