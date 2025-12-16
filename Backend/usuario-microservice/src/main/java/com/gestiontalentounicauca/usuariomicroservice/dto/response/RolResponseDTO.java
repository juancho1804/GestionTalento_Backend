package com.gestiontalentounicauca.usuariomicroservice.dto.response;

import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta con la información del rol asignado")
public class RolResponseDTO {

    @Schema(
            description = "Rol del usuario",
            example = "ADMIN"
    )
    private EnumRol rol;
}
