package com.gestiontalentounicauca.usuariomicroservice.exception;

public record ApiError(
        int estado,
        String error,
        String mensaje,
        String ruta
) {
}
