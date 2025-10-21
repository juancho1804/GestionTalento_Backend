package com.gestiontalentounicauca.usuariomicroservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {
    private String correo;
    private String cedula;
    private String nombre;
    private String apellido;
    private Set<RolRequestDTO> roles = new HashSet<>();
}
