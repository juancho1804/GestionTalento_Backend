package com.gestiontalentounicauca.actividadesmicroservice.exception;

public record ApiError(
        int estado,
        String error,
        String mensaje,
        String ruta
) {
}
