package com.gestiontalentounicauca.actividadesmicroservice.service.client;

import lombok.Data;

import java.util.Set;

@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String correo;
    private String cedula;
}
