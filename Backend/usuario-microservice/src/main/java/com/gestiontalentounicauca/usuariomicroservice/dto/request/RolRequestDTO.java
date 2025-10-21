package com.gestiontalentounicauca.usuariomicroservice.dto.request;

import com.gestiontalentounicauca.usuariomicroservice.model.EnumRol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolRequestDTO {
    private EnumRol rol;
}
