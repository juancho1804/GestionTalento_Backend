package com.gestiontalentounicauca.actividadesmicroservice.dto.request;

import lombok.Data;

@Data
public class MotivoPagoRequestDTO {
    private Long idTipoMotivoPago;
    private String nombreTipoMotivoPago;
    private Double monto;
    private Long idActividad;
}
